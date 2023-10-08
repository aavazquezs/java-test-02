package com.avangenio.javatest02;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ParseFileTest {

	private ParseFile parseFile;
	
	
	@Test 
	public void filePathException() {
		
		assertThrows(FileNotFoundException.class, 
				()->{
					parseFile = new ParseFile("dummyFileName.txt");
				});
		
	}
	
	@Before
	public void initialize() throws FileNotFoundException {
		parseFile = new ParseFile("input.txt");
	}
	
	
	@Test
	public void parseF1LineTest() {
		
		String[] data = parseFile.parseF1Line("D Erica Burns,BARCELONA,93654902Y ");
		
		assertEquals(3, data.length);
		
		String id = data[0];
		
		String peopleDescription = data[1];
		
		String city= data[2];
		
		assertEquals("93654902Y", id);
		
		assertEquals("Erica Burns,93654902Y ", peopleDescription);
		
		assertEquals("BARCELONA", city);
		
	}
	
	@Test
	public void parseF2LineTest() {
		
		String[] data = parseFile.parseF2Line("D Mitchell Newton ; LAS VEGAS ; 25384390-A ");
		
		assertEquals(3, data.length);
		
		String id = data[0];
		
		String peopleDescription = data[1];
		
		String city= data[2];
		
		assertEquals("25384390A", id);
		
		assertEquals("Mitchell Newton,25384390A ", peopleDescription);
		
		assertEquals("LAS VEGAS", city);
	}
	
	@Test 
	public void dataIngestionTest() {
		
		assertDoesNotThrow(()->{
			parseFile.ingestData();
		});
		
		assertEquals(30, parseFile.getPeople().keySet().size());
		
		assertEquals(15, parseFile.getCities().keySet().size());
		
		assertEquals(3, parseFile.getPeople().get("54808168L").size());
		
		assertTrue(parseFile.getPeople().get("54808168L").contains("BARCELONA"));
		
		assertTrue(parseFile.getPeople().get("54808168L").contains("MADRID"));
		
		assertTrue(parseFile.getPeople().get("54808168L").contains("OVIEDO"));
		
		assertEquals(10, parseFile.getCities().get("BARCELONA").size());
		
		assertTrue(parseFile.getCities().get("BARCELONA").contains("Peter Daniel,58204706D "));
		
	}
	

	@Test
	public void queryForCityTest() {
		
		parseFile.ingestData();
		
		List<String> cities = parseFile.queryForPeople("54808168L");
		
		assertEquals(3, cities.size());
		
		assertTrue(cities.contains("BARCELONA"));
		
		assertTrue(cities.contains("MADRID"));
		
		assertTrue(cities.contains("OVIEDO"));
	}
	
	@Test
	public void querForPeopleTest() {
		
		parseFile.ingestData();
		
		List<String> people = parseFile.queryForCity("BARCELONA");
		
		assertEquals(10, people.size());
		
		people = parseFile.queryForCity("CARTAGENA");
		
		assertEquals(1, people.size());
	}
}
