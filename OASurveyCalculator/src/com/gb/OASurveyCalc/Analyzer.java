package com.gb.OASurveyCalc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;



/*==========================================================================
* Class: 
* Variables: 
* Description: 
===========================================================================*/
public class Analyzer {
	
	private LinkedHashMap<Integer,Collection<String>> answerMap = new LinkedHashMap<Integer,Collection<String>>();
	private LinkedHashMap<Integer,HashMap<String,Float>> answerFrequencyMap = new LinkedHashMap<Integer,HashMap<String,Float>>();
	

	
	
/*==========================================================================
* importFile
* Inputs:  String filename  
* Outputs: None
* Returns: None
* Description: Takes in a csv file of answers to survey questions and parses it into a Map for further work in another class
===========================================================================*/
	public void importFile(String filename){
		try {
			CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-16")),'\t','"',1);
			String[] line;
			Integer offset = 10;
			while((line = reader.readNext()) != null){
				for(int i = offset; i < line.length; i++){
					if(!line[4].equals("INCOMPLETE")){
						System.out.println(line[i]);
						Collection<String> value = answerMap.get(i-offset+1);
						if(value == null){
							value = new ArrayList<String>();
							answerMap.put(i-offset+1, value);
						}
						value.add(line[i].trim());  //CONVERT TO LOWER CASE IN ANALYSIS
					}
				}
				//System.out.println(" ");
			}
			reader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		

	}
	
	
/*==========================================================================
* outputMap
* Inputs:  None  
* Outputs: Prints the Keys of the maps followed by the contents of that entry
* Returns: None
* Description: Outputs map data to allow for testing and making sure the values are correct
===========================================================================*/
	public void outputMap(){
		for(Map.Entry<Integer,Collection<String>> entry : answerMap.entrySet()){
			System.out.println(entry.getKey());
			for(String s : entry.getValue()){
				System.out.println(s);
			}
		}
	}
	
	
	
/*==========================================================================
* getMap
* Inputs:  None  
* Outputs: None
* Returns: The hashmap that contains the parsed input file
* Description: Returns the hash map so that another class can use the data from the input file
===========================================================================*/
	public HashMap<Integer,Collection<String>> getMap(){
		return answerMap;
	}
	
	
	
	// how to store pair
	// when you find a word. and its not in the other list add it to list w/ freq 1
	// else increment frequency
 
	
	public void analyze(){
		for(Map.Entry<Integer,Collection<String>> answerEntry : answerMap.entrySet()){
			HashMap<String,Float> tmpFreqMap = new HashMap<String,Float>();
			
			Float answerAmount = (float) answerEntry.getValue().size();
			
			
			// Gets the frequency of each answer in the collection 
			for(String s : answerEntry.getValue()){
				if(tmpFreqMap.containsKey(s)){
					tmpFreqMap.put(s, tmpFreqMap.get(s)+1);
				}
				else {
					//System.out.println("Key "+s+" not found");
					tmpFreqMap.put(s, (float) 1.0);
				}
			}
			
			for(Map.Entry<String,Float> percentCalcEntry : tmpFreqMap.entrySet()){
				percentCalcEntry.setValue(percentCalcEntry.getValue()/answerAmount); //turns the frequency into a decimal percentage
			}
			
//			for(Map.Entry<String,Float> tmp : tmpFreqMap.entrySet()){
//				System.out.println("Word: "+tmp.getKey()+ " Frequency: "+tmp.getValue());
//			}
			
			answerFrequencyMap.put(answerEntry.getKey(), tmpFreqMap);
		}
	}
	
	
	public void outputAnalysis(String filename){
		File outputFile = new File(filename);
		try {
			PrintWriter writer = new PrintWriter(outputFile/*, "UTF-8"*/);
			
			for(Map.Entry<Integer, HashMap<String, Float>> questionEntry : answerFrequencyMap.entrySet()){
				writer.println("Question "+questionEntry.getKey());
				//System.out.println("Question "+questionEntry.getKey());
				
				for(Map.Entry<String, Float> freqEntry : questionEntry.getValue().entrySet()){
					writer.println("\t"+"Word: "+freqEntry.getKey()+ "  Frequency: "+(freqEntry.getValue()*100));
				}
			}
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Analyzer test = new Analyzer();
		//System.out.println("Created Test Parser");
		test.importFile("e:\\Users\\Garrett\\Desktop\\Reader's_Guide_2013.csv");
		//System.out.println("Finished Import");
		//test.outputMap();
		//test.analyze();
		//test.outputAnalysis("e:\\Users\\Garrett\\Desktop\\trash.txt");
	}

}
