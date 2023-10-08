package com.avangenio.javatest02;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import lombok.Data;

@Data
public class ParseFile {
	
	private String filePath;
	
	private Scanner input;
	
	private HashMap<String, List<String>> people;
	
	private HashMap<String, List<String>> cities;

	/**
	 * Default constructor. It asume that the data file is located at input.txt on source folder.
	 * 
	 * @throws FileNotFoundException
	 */
	public ParseFile() throws FileNotFoundException {
		
		this.filePath = "input.txt";
		
		this.people = new HashMap<String, List<String>>();
		
		this.cities = new HashMap<String, List<String>>();
		
		this.input = new Scanner(new FileInputStream(filePath));
		
	}
	
	/**
	 * Constructor of ParseFile class, accept one argument.
	 * 
	 * @param filePath File path of the data file
	 * 
	 * @throws FileNotFoundException
	 */
	public ParseFile(String filePath) throws FileNotFoundException {
		
		this.filePath = filePath;
		
		this.people = new HashMap<String, List<String>>();
		
		this.cities = new HashMap<String, List<String>>();
		
		this.input = new Scanner(new FileInputStream(this.filePath));
		
	}
	
	/**
	 * Return an array of string representing the values needed after parse a F1 line.
	 * @param line
	 * @return an String array with this data {id, peopleDescription, city}
	 */
	public String[] parseF1Line(String line) {
		
		String data = line.substring(2);
		
		String[] dataParts = data.split(",");//name surname,city,id
		
		String peopleDescription = dataParts[0]+","+dataParts[2];
		
		String id = dataParts[2].replace(" ", "");
		
		String city = dataParts[1];
		
		return new String[] {id, peopleDescription, city};
	}
	
	/**
	 * Return an array of string representing the values needed after parse a F2 line.
	 * @param line
	 * @return an String array with this data {id, peopleDescription, city}
	 */
	public String[] parseF2Line(String line) {
		
		String data = line.substring(2);
		
		String[] dataParts = data.split(" ; ");//name surname ; city ; id
		
		String[] idParts = dataParts[2].split("-");
		
		String peopleDescription = dataParts[0]+","+idParts[0]+idParts[1];
		
		String id = idParts[0]+idParts[1];
		
		id = id.replace(" ","");
		
		String city = dataParts[1];
		
		return new String[] {id, peopleDescription, city};
	}
	
	/**
	 * Method for ingest the data contained in the data file.
	 */
	public void ingestData() {
		
		boolean isF1Activated = false;
		
		while(input.hasNextLine()) {
			
			String line = input.nextLine();
			
			if(line.contains("F1")) {
				
				isF1Activated = true;
				
			}else if (line.contains("F2")){
				
				isF1Activated = false;
								
			}else {
				
				String data, peopleDescription, city, id;
				
				String[] dataParts;
				
				if(isF1Activated) {
					
					dataParts = this.parseF1Line(line);
					
					id = dataParts[0];
					
					peopleDescription = dataParts[1];
					
					city = dataParts[2];
					
				}else { //F2 is activated
					
					dataParts = this.parseF2Line(line);
					
					id = dataParts[0];
					
					peopleDescription = dataParts[1];
					
					city = dataParts[2];
					
				}
				
				//city = dataParts[1];
				
				//add data to people hashmap
				
				if(people.containsKey(id)) { //if people exist add the new city
					
					people.get(id).add(city);
					
				}else { //if not create a new list and add the city
					
					List<String> cityList = new LinkedList<String>();
					
				 	cityList.add(city);
					
					people.put(id, cityList);
					
				}
				
				//add data to cities hashmap
				
				if(cities.containsKey(city)) { //if city exist add the new people
					
					cities.get(city).add(peopleDescription); 
					
				}else { // if not create a new list of people and add it.
					
					List<String> peopleList = new LinkedList<String>();
					
					peopleList.add(peopleDescription);
					
					cities.put(city, peopleList);
					
				}
				
				
			}
			
		}
		
	}
	
	public List<String> queryForCity(String city){
		
		if(cities.containsKey(city)) {
			
			return cities.get(city);
			
		}else {
			
			return Collections.emptyList();
		}
		
	}
	
	public List<String> queryForPeople(String peopleKey){
		
		if(people.keySet().contains(peopleKey)) {
			
			return people.get(peopleKey);
			
		} else {
			
			return Collections.emptyList();
			
		}
		
	}
	
}
