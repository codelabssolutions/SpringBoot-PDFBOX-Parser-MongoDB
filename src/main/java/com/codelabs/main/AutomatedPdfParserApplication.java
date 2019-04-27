package com.codelabs.main;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.codelabs.services.PdfParserServices;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"com.idpdc.repository"})
@ComponentScan(basePackages = {"com.idpdc.main","com.idpdc.entity","com.idpdc.services"})
public class AutomatedPdfParserApplication implements CommandLineRunner {
	
	
	@Autowired
	PdfParserServices pdfParserServices;
	
    public static void main(String[] args) throws IOException {
		         SpringApplication.run(AutomatedPdfParserApplication.class, args);
     }
    @Override
    public void run(String... args) throws Exception {
    	 
    	pdfParserServices.pdfPars();
		  
    }
  
	
	
}