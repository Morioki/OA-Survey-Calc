package com.gb.OASurveyCalc;


/*==========================================================================
* Class: Filename
* Variables: String filename - holds the filename and absolute path
* Description: This class is a wrapper around a string to allow for taking the name of an input file from the GUI
===========================================================================*/
public class Filename {
	private String filename= "";
	
	public void setFilename(String tmpFilename){
		filename = tmpFilename;
	}
	
	public String getFilename(){
		return filename;
	}
}
