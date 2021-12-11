package pl.hackyeah.szczepans.opener.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.contentstream.operator.OperatorProcessor;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.text.PDFTextReplacer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
import pl.hackyeah.szczepans.opener.controller.common.ResponseTemplate;
import pl.hackyeah.szczepans.opener.controller.common.ServerResponseFactory;
=======
import pl.hackyeah.szczepans.opener.controller.dto.ResponseTemplate;
import pl.hackyeah.szczepans.opener.controller.dto.ServerResponseFactory;
>>>>>>> ee785e0c9ed0018960cd66c38fd74971d3729132

@RestController
public class TestController {
	
    @GetMapping("/")
    public ResponseEntity<ResponseTemplate<String>> testEndpoint() {
        return ServerResponseFactory.createSuccessResponse("Hello World!");
    }
    
    @PostMapping("/encrypt-personal-data")
    public ResponseEntity<ResponseTemplate<String>> encryptPersonalData(
    		@RequestBody Map<String, String> jsonBody) {
    	File documentFile = new File("test.pdf");
    	
    	List<COSBase> arguments = new ArrayList<>();
    	try {
			PDDocument pdf = PDDocument.load(documentFile);
			PDFTextReplacer replacer = new PDFTextReplacer();
			
			replacer.getText(pdf);
			PDFTextStripper stripper = new PDFTextStripper();
			String text = stripper.getText(pdf);
			for(PDPage page : pdf.getPages()) {
				if(!page.hasContents())
					continue;
				
				PDFStreamParser streamParser = new PDFStreamParser(page);
//				COSBase dictionaryObject = page.getCOSObject().getDictionaryObject(COSName.CONTENTS);
				OperatorProcessor processor = replacer.getTextOperatorProcessor();
				streamParser.parse();
				List<Object> tokens = streamParser.getTokens();
				String s = "";
//				PDPageContentStream.AppendMode.OVERWRITE;
				for(Object token : tokens) {
					if(token instanceof Operator) {
						Operator op = (Operator)token;
						
						if(op.getName().equals("TJ")) {
							processor.process(op, arguments);
							
							arguments = new ArrayList<>();
						}
					} else {
						arguments.add((COSBase)token);
					}
				}
				
//				for(int i = 0; i < tokens.size(); i++) {
//					if(tokens.get(i) instanceof Operator && 
//							((Operator)(tokens.get(i))).getName().equals("Tj")) {
//						Operator op = (Operator)(tokens.get(i));
//						processor.process(op, arguments);
//						
//						arguments = new ArrayList<>();
//					}
//				}
				
//				for(int i = 0; i < tokens.size(); i++) {
//					if(tokens.get(i) instanceof Operator && 
//							((Operator)(tokens.get(i))).getName().equals("TJ")) {
//						if(tokens.get(i - 1) instanceof COSArray) {
//							COSArray elems = (COSArray)tokens.get(i - 1);
//							for(int k = 0; k < elems.size(); k++) {
//								Object elem = elems.getObject(k);
//								
//								if(elem instanceof COSString) {
//									COSString str = (COSString) elem;
//									s += str.getString();
//								}
//							}
//							
//							System.out.println("Text: " + s);
//							s = "";
//						}
//					}
//				}
//				
//				for(Object token : tokens) {
//					if(token instanceof Operator) {
//						Operator operator = (Operator) token;
//						
//						operator.getName();
//					}
//				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	return ServerResponseFactory.createSuccessResponse("OK");
    }
}
