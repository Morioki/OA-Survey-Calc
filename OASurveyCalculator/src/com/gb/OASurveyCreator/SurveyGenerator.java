package com.gb.OASurveyCreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class SurveyGenerator {

	private ArrayList<String> dictionary = new ArrayList<String>();
	private Integer dictLength = 0;
	
	public void buildDictionary(String filename){
		File input = new File(filename);
		String line;
		try {
			BufferedReader br = new BufferedReader(new FileReader(input));
			while((line=br.readLine()) != null){
				dictionary.add(line);
			}
			br.close();
			
			dictLength = dictionary.size()-1;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void buildTestSurvey(Integer questionAmount , Integer numAnswers, String writeFileName){
		File output = new File(writeFileName);
		Date timestamp = new Date();
		try {
			PrintWriter writer = new PrintWriter(output/*, "UTF-8"*/);
			
			//BUILD THE FIRST LINE OF THE STRING
			StringBuilder outputString = new StringBuilder("Timestamp");
			for(int i = 1; i <= questionAmount; i++){
				outputString.append(",Question "+i);
			}
			//System.out.println(outputString.toString());
			writer.println(outputString.toString());
			
			for(int i = 0; i < numAnswers; i++){
				outputString = new StringBuilder(new Timestamp(timestamp.getTime()).toString());
				for(int j = 0; j < questionAmount;j++){
					outputString.append(","+dictionary.get((int) (Math.random()*dictLength)));
				}
				writer.println(outputString.toString());
			}
			
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printDictionary(){
		for(String s : dictionary){
			System.out.println(s);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SurveyGenerator test = new SurveyGenerator();
		test.buildDictionary("e:\\Users\\Garrett\\Desktop\\surveyDictionary.txt");
		//test.printDictionary();
		test.buildTestSurvey(10, 1000,"e:\\Users\\Garrett\\Desktop\\test.csv");
	}

}
