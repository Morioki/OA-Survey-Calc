package com.gb.OASurveyCalc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/*==========================================================================
* Class: 
* Variables: 
* Description: 
===========================================================================*/
public class Analyzer {
	
	private HashMap<Integer,Collection<String>> answerMap = new HashMap<Integer,Collection<String>>();
	private HashMap<Integer,HashMap<String,Float>> answerFrequencyMap = new HashMap<Integer,HashMap<String,Float>>();
	
/*==========================================================================
* importFile
* Inputs:  String filename  
* Outputs: None
* Returns: None
* Description: Takes in a csv file of answers to survey questions and parses it into a Map for further work in another class
===========================================================================*/
	public void importFile(String filename){
		File input = new File(filename);
		String line;
		String csvDelimiter = ",";
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(input));
			br.readLine(); //Throw Away the first line
			while((line=br.readLine()) != null){
				String[] lineSplit = line.toLowerCase().split(csvDelimiter);				
				for(int i = 1; i < lineSplit.length; i++){ //i = 1 because first index is the time stamp and we dont want that
					Collection<String> value = answerMap.get(i);
					if(value == null){
						value = new ArrayList<String>();
						answerMap.put(i,value);
					}
					value.add(lineSplit[i].replaceAll("[^a-zA-Z ]", "").trim());
				}
			}
			br.close();
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
					System.out.println("Key "+s+" not found");
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
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Analyzer test = new Analyzer();
		//System.out.println("Created Test Parser");
		test.importFile("e:\\Users\\Garrett\\Desktop\\test.csv");
		//System.out.println("Finished Import");
		//test.outputMap();
		test.analyze();
	}

}
