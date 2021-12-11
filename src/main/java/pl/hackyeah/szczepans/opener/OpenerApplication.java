package pl.hackyeah.szczepans.opener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import pl.hackyeah.szczepans.opener.properties.FileStorageProperties;
import pl.hackyeah.szczepans.opener.properties.ValidationApiProperties;

@SpringBootApplication
@EnableConfigurationProperties ({
    FileStorageProperties.class,
	ValidationApiProperties.class
})
public class OpenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenerApplication.class, args);
	}

}
