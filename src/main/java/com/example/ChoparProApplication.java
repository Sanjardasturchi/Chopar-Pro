package com.example;

import com.example.service.SMSHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ChoparProApplication {

	public static void main(String[] args) throws InterruptedException {
	Scanner scanner=new Scanner(System.in);
		SpringApplication.run(ChoparProApplication.class, args);
	}

}
