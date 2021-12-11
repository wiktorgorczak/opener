package pl.hackyeah.szczepans.opener.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import pl.hackyeah.szczepans.opener.controller.dto.FileDto;
import pl.hackyeah.szczepans.opener.dao.DocumentDAO;
import pl.hackyeah.szczepans.opener.dao.exception.FileStorageException;
import pl.hackyeah.szczepans.opener.model.Document;
import pl.hackyeah.szczepans.opener.properties.FileStorageProperties;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;
	private final DocumentDAO dao;
	
	
	public FileStorageService(FileStorageProperties fileStorageProperties, DocumentDAO dao) {
        this.dao = dao;
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
	
	public Path storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return targetLocation;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
	
//	public Document saveFile(File file) {
//		return dao.save(new Document(file.getName(), file.getAbsolutePath()));
//	}
	
	public Document saveDocument(Document document) {
		return dao.save(document);
	}
	
	public byte[] downloadFile(Integer id) throws IOException {
		Optional<Document> document = dao.findById(id);
		if(document.isEmpty()) {
			return null;
		}
		return Files.readAllBytes(Path.of(document.get().getPath()));
	}
	
	public byte[] downloadAnonymisedFile(Integer id) throws IOException {
		Optional<Document> document = dao.findById(id);
		if(document.isEmpty()) {
			return null;
		}
		return Files.readAllBytes(Path.of(fileStorageLocation.toString() + "/anonymised/" + document.get().getName()));
	}
	
    public Path storeFile(FileDto fileDto) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(fileDto.getName());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(new ByteArrayInputStream(fileDto.getBytes()), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return targetLocation;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    
    public Document anonymizeDocument(Integer id, List<String> words) throws FileNotFoundException, IOException, DocumentException {
    	Optional<Document> wrappedDocument = dao.findById(id);
    	if(wrappedDocument.isEmpty()) {
    		return null;
    	}
    	Document document = wrappedDocument.get();
    	File file = new File(document.getPath());
    	String parsedText;
        PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
        parser.parse();
        COSDocument cosDoc = parser.getDocument();
        PDFTextStripper pdfStripper = new PDFTextStripper();
        PDDocument pdDoc = new PDDocument(cosDoc);
        parsedText = pdfStripper.getText(pdDoc);
        
        for(String s : words) {
        	parsedText = parsedText.replaceAll(s, "xxxxxx");                        
        }        
        
        File directory = new File(fileStorageLocation.toString() + "/anonymised/");
        if (! directory.exists()){
            directory.mkdir();            
        }
        
        Path toFile = Path.of(fileStorageLocation.toString(), "/anonymised/", document.getName());
        Files.createFile(toFile);
        
        com.itextpdf.text.Document pdfDoc = new com.itextpdf.text.Document(PageSize.A4);
        PdfWriter.getInstance(pdfDoc, new FileOutputStream(toFile.toString()))
          .setPdfVersion(PdfWriter.PDF_VERSION_1_7);
        pdfDoc.open();
        
        Font myfont = new Font();
        myfont.setStyle(Font.NORMAL);
        myfont.setSize(11);
        pdfDoc.add(new Paragraph("\n"));
                                         
        for(String strLine : parsedText.split("\n")) {            	                    
            Paragraph para = new Paragraph(strLine + "\n", myfont);
            para.setAlignment(Element.ALIGN_JUSTIFIED);
            pdfDoc.add(para);
        }	
        pdfDoc.close();
        return document;
    }
}
