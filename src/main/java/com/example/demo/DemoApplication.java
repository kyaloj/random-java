package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Arrays;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
//		SpringApplication.run(DemoApplication.class, args);
		System.out.println("Numbers: " + getNumbers());
	}

	public static List<Integer> getNumbers() {
		return Arrays.asList(1, 2, 3, 4, 5);
	}
}
