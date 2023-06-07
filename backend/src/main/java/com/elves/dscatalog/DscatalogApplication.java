package com.elves.dscatalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DscatalogApplication implements CommandLineRunner {
	@Autowired
	PasswordEncoder encoder;
	public static void main(String[] args) {
		SpringApplication.run(DscatalogApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("code:" +encoder.encode("123456"));
		System.out.println("maches: "+
	  encoder.matches("123456","$2a$10$DfDxf.8Ae8qh5f4iwr.Bz.fLPm4UCzP1iRrHmST1PLeXITSmvbSaa"));
	}
}
