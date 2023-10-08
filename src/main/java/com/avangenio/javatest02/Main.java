package com.avangenio.javatest02;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {
	
	public static void main(String[] args){
		
		if(args.length < 2) {
			
			System.out.println("The application must have at least two arguments");
			
			return;
			
		}
				
		String filePath = "input.txt";
		
		String op, value;
		
		if(args.length == 3) {
			
			filePath = args[0];
			
			op = args[1];
			
			value = args[2];
			
		} else {
			
			op = args[0];
			
			value = args[1];
			
		}
		
		ParseFile parseFile;
		
		try {
			
			 parseFile = new ParseFile(filePath);
			
		} catch (FileNotFoundException e) {
			
			System.out.println("ERROR: File Not Found");
			
			return;
			
		}
		
		parseFile.ingestData();
		
		switch (op.toUpperCase()) {
		case "ID":
			
			List<String> cities = parseFile.queryForPeople(value);
			
			cities.parallelStream().forEach(System.out::println);
			
			break;

		case "CITY":
			
			List<String> people = parseFile.queryForCity(value);
			
			people.parallelStream().forEach(System.out::println);
			
			break;
			
		default:
			break;
		}
		
		
	}

}
