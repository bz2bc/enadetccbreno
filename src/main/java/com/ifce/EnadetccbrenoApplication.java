package com.ifce;
import com.ifce.controller.Config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import db.DB;

@SpringBootApplication
@Import(Config.class)
public class EnadetccbrenoApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(EnadetccbrenoApplication.class, args);			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
