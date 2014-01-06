package com.gb.OASurveyCalc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

import org.apache.commons.lang3.time.StopWatch;



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
			DatabaseConnector dbConnection = new DatabaseConnector();
			Boolean status = dbConnection.createConnection();  // CREATE DATABASE CONNECTION
			if(!status){
				System.out.println("Connection Failed");
				System.exit(0);
			}
			
			
			CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-16")),'\t','"',1);
			String[] line;
			Integer offset = 10;
			Integer lineNum = 0;
			Integer[] profQuestions = {14,15,16,17}; //{12,13,14,15} for 2014
			
			while((line = reader.readNext()) != null){
				lineNum=lineNum+1;
				System.out.println("Reading Line: "+lineNum);
				if(!line[4].equals("INCOMPLETE") && !line[5].equals("YES")){
				//--CREATES THE TABLE FOR CONTACT INFORMATION ON COMPLETE SURVEYS
					if(line[line.length-1].contains(",")){
						String[] contact = line[line.length-1].split(",");
						String name=null;
						String contact_email=null;
						String phone_number=null;
						switch(contact.length){
							case 1:
								name = contact[0].trim();
								break;
							case 2:
								name = contact[0].trim();
								contact_email = contact[1].trim();
								break;
							case 3:
								name = contact[0].trim();
								contact_email = contact[1].trim();
								phone_number = contact[2].trim();
								break;
							default:
								name = contact[0].trim();
								contact_email = contact[1].trim();
								phone_number = contact[2].trim();
								break;
						}
					
						dbConnection.insert("INSERT IGNORE INTO contact_information SET "
								+ "ucr_email=\""+line[10].trim()
								+ "\" , name=\""+name
								+ "\" , contact_email=\""+contact_email
								+ "\" , phone_number=\""+phone_number+"\"");
					} else{
						dbConnection.insert("INSERT IGNORE INTO contact_information SET "
								+ "ucr_email=\""+line[10].trim()
								+ "\" , name=\""+line[line.length-1]+"\"");
					}
				//--DONE CONTACT INFOMRATION
				
					for(int i = offset; i < line.length; i++){
						//System.out.println(line[i]);
						if(i == 10 || i == line.length-1) continue;
							
						//PROFESSOR SECTION
						//index 12 is chass prof
						//13 is cnas
						//14 is bcoe
						//15 is soba
						else if(Arrays.asList(profQuestions).contains(i)){
							if(i == 14){//12
								dbConnection.createAnswerTable("answer_chass_prof");
								if(!line[i].trim().equals("")){
									ResultSet exists = dbConnection.getResult("SELECT COUNT(*) FROM professor_list WHERE name=\""+line[i].trim()+"\"");
									exists.next();
									if(exists.getInt(1)>0){
										dbConnection.insert("INSERT INTO answer_chass_prof (answer,count) VALUES (\""+line[i].trim()+"\",1) ON DUPLICATE KEY UPDATE count=count+1");
									}
								}
							}
							if(i == 15){//13
								dbConnection.createAnswerTable("answer_cnas_prof");
								if(!line[i].trim().equals("")){
									ResultSet exists = dbConnection.getResult("SELECT COUNT(*) FROM professor_list WHERE name=\""+line[i].trim()+"\"");
									exists.next();
									if(exists.getInt(1)>0){
										dbConnection.insert("INSERT INTO answer_cnas_prof (answer,count) VALUES (\""+line[i].trim()+"\",1) ON DUPLICATE KEY UPDATE count=count+1");
									}
								}
							}
							if(i == 16){//14
								dbConnection.createAnswerTable("answer_bcoe_prof");
								if(!line[i].trim().equals("")){
									ResultSet exists = dbConnection.getResult("SELECT COUNT(*) FROM professor_list WHERE name=\""+line[i].trim()+"\"");
									exists.next();
									if(exists.getInt(1)>0){
										dbConnection.insert("INSERT INTO answer_bcoe_prof (answer,count) VALUES (\""+line[i].trim()+"\",1) ON DUPLICATE KEY UPDATE count=count+1");
									}
								}
							}
							if(i == 17){//15
								dbConnection.createAnswerTable("answer_soba_prof");
								if(!line[i].trim().equals("")){
									ResultSet exists = dbConnection.getResult("SELECT COUNT(*) FROM professor_list WHERE name=\""+line[i].trim()+"\"");
									exists.next();
									if(exists.getInt(1)>0){
										dbConnection.insert("INSERT INTO answer_soba_prof (answer,count) VALUES (\""+line[i].trim()+"\",1) ON DUPLICATE KEY UPDATE count=count+1");
									}
								}
							}
						}
						
						else{
							dbConnection.createAnswerTable("answer_"+(i-offset+1));
							if(!line[i].trim().equals("")){
								dbConnection.insert("INSERT INTO answer_"+(i-offset+1)+" (answer,count) VALUES (\""+line[i].trim()+"\",1) ON DUPLICATE KEY UPDATE count=count+1");
							}
						}
					}
				}
			}
				//System.out.println(" ");
			reader.close();
			
		} catch (IOException | SQLException e) {
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
		ArrayList<String> tableList = new ArrayList<String>();
	
		
		DatabaseConnector dbCon = new DatabaseConnector();
		Boolean status = dbCon.createConnection();
		
		if(!status){
			System.out.println("Connection Failed");
			System.exit(0);
		}
		
		tableList = dbCon.getTableList();
		
		for(String tableName : tableList){
			HashMap<String,Float> tmpFreqMap = new HashMap<String,Float>();
			try {
				ResultSet totalDBCount = dbCon.getResult("SELECT sum(count) FROM "+tableName);
				totalDBCount.next();
				Integer answerCount = totalDBCount.getInt(1);
				//System.out.println(answerCount);
				
				ResultSet rs = dbCon.getResult("SELECT * FROM "+tableName);
				while(rs.next()){
					tmpFreqMap.put(rs.getString("answer"), (float)rs.getFloat("count")/(float)answerCount);
					//System.out.println((float)rs.getFloat("count")/(float)answerCount);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Integer answerNum;
			if(tableName.contains("chass")) answerNum = 5;
			else if(tableName.contains("cnas")) answerNum = 6;
			else if(tableName.contains("bcoe")) answerNum = 7;
			else if(tableName.contains("soba")) answerNum = 8;
			else answerNum = Integer.valueOf(tableName.substring(7));
			
			answerFrequencyMap.put(answerNum, tmpFreqMap);
		}
		
		
		
//		for(Map.Entry<Integer,Collection<String>> answerEntry : answerMap.entrySet()){
//			HashMap<String,Float> tmpFreqMap = new HashMap<String,Float>();
//			
//			Float answerAmount = (float) answerEntry.getValue().size();
//			
//			
//			// Gets the frequency of each answer in the collection 
//			for(String s : answerEntry.getValue()){
//				if(tmpFreqMap.containsKey(s)){
//					tmpFreqMap.put(s, tmpFreqMap.get(s)+1);
//				}
//				else {
//					//System.out.println("Key "+s+" not found");
//					tmpFreqMap.put(s, (float) 1.0);
//				}
//			}
//			
//			for(Map.Entry<String,Float> percentCalcEntry : tmpFreqMap.entrySet()){
//				percentCalcEntry.setValue(percentCalcEntry.getValue()/answerAmount); //turns the frequency into a decimal percentage
//			}
//			
////			for(Map.Entry<String,Float> tmp : tmpFreqMap.entrySet()){
////				System.out.println("Word: "+tmp.getKey()+ " Frequency: "+tmp.getValue());
////			}
//			
//			answerFrequencyMap.put(answerEntry.getKey(), tmpFreqMap);
//		}
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
		StopWatch time = new StopWatch();
		
		time.start();
		
		Analyzer test = new Analyzer();
		//System.out.println("Created Test Parser");
		//test.importFile("e:\\Users\\Garrett\\Desktop\\Reader's_Guide_2013.csv");
		
		test.analyze();
		test.outputAnalysis("e:\\Users\\Garrett\\Desktop\\trash.txt");
		time.stop();
		
		System.out.println(time.toString());
		//System.out.println("Finished Import");
		//test.outputMap();
		//test.analyze();
		//test.outputAnalysis("e:\\Users\\Garrett\\Desktop\\trash.txt");
	}

}
