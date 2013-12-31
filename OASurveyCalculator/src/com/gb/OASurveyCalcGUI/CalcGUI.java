package com.gb.OASurveyCalcGUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.gb.OASurveyCalc.Filename;
import com.gb.OASurveyCalc.Analyzer;
import com.gb.OASurveyCreator.SurveyGenerator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CalcGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6225029311299921194L;
	private JPanel contentPane;
	private JTextField inputfileNameField;
	private JLabel lblCreateTestSurvey;
	private JLabel lblOutputFile;
	private JTextField outputFileField;
	private JButton button_1;
	private JTextField numQuestionField;
	private JTextField numAnswerField;
	private JLabel lblQuestions;
	private JLabel lblAnswers;
	private JLabel lblDictionaryFile;
	private JTextField dictionaryFileField;
	private JButton button_2;
	private JButton btnBuildTestSurvey;
	private JLabel lblAnalyzeSurvey;
	private JLabel lblAnalysisOutput;
	private JTextField analysisOutputField;
	private JButton button_3;
	private JButton btnRunAnalysis;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					CalcGUI frame = new CalcGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CalcGUI() {
		setTitle("Open Answer Survey Analyzer");
		
		final Filename dictionaryFile = new Filename();
		final Filename testOutputFile = new Filename();
		
		final Filename inputFile = new Filename();
		final Filename analysisOutputFile = new Filename();
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][][][][grow][][][][][]", "[][][][][][][][][][]"));
		
		lblCreateTestSurvey = new JLabel("Create Test Survey");
		lblCreateTestSurvey.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCreateTestSurvey, "cell 0 0,alignx center");
		
		lblQuestions = new JLabel("# Questions:");
		contentPane.add(lblQuestions, "cell 2 1,alignx trailing");
		
		numQuestionField = new JTextField();
		contentPane.add(numQuestionField, "cell 4 1,growx");
		numQuestionField.setColumns(10);
		
		lblAnswers = new JLabel("# Answers:");
		contentPane.add(lblAnswers, "cell 7 1,alignx trailing");
		
		numAnswerField = new JTextField();
		contentPane.add(numAnswerField, "cell 8 1,growx");
		numAnswerField.setColumns(10);
		
		lblDictionaryFile = new JLabel("Dictionary File:");
		lblDictionaryFile.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblDictionaryFile, "cell 0 2,alignx center");
		
		dictionaryFileField = new JTextField();
		contentPane.add(dictionaryFileField, "cell 1 2 9 1,growx");
		dictionaryFileField.setColumns(10);
		
		button_2 = new JButton("...");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					dictionaryFile.setFilename(chooser.getSelectedFile().getAbsolutePath());
					dictionaryFileField.setText(dictionaryFile.getFilename());
				}
			}
		});
		contentPane.add(button_2, "cell 10 2");
		
		lblOutputFile = new JLabel("Output File:");
		lblOutputFile.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblOutputFile, "cell 0 3,alignx center");
		
		outputFileField = new JTextField();
		contentPane.add(outputFileField, "cell 1 3 9 1,growx");
		outputFileField.setColumns(10);
		
		button_1 = new JButton("...");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showSaveDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					testOutputFile.setFilename(chooser.getSelectedFile().getAbsolutePath());
					outputFileField.setText(testOutputFile.getFilename());
				}
			}
		});
		contentPane.add(button_1, "cell 10 3");
		
		btnBuildTestSurvey = new JButton("Build Test Survey");
		btnBuildTestSurvey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SurveyGenerator sg = new SurveyGenerator();
				if(!numAnswerField.getText().equals("") && !numQuestionField.getText().equals("") && !dictionaryFile.getFilename().equals("") && !testOutputFile.getFilename().equals("")){
					if(numAnswerField.getText().matches("[0-9]+") && numQuestionField.getText().matches("[0-9]+")){
						sg.buildDictionary(dictionaryFile.getFilename());
						sg.buildTestSurvey(Integer.parseInt(numQuestionField.getText()), Integer.parseInt(numAnswerField.getText()), testOutputFile.getFilename());
						JOptionPane.showMessageDialog(null, "Test Survey Building Complete");
					} else{
						JOptionPane.showMessageDialog(null, "# Answers and # Questions can only contain numbers!");
					}
				} else{
					JOptionPane.showMessageDialog(null, "Input Incorrect");
				}
			}
		});
		contentPane.add(btnBuildTestSurvey, "cell 5 4");
		
		lblAnalyzeSurvey = new JLabel("Analyze Survey");
		lblAnalyzeSurvey.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblAnalyzeSurvey, "cell 0 6,alignx center");
		
		JLabel lblInputFile = new JLabel("Input File: ");
		lblInputFile.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblInputFile, "cell 0 7,alignx center");
		
		inputfileNameField = new JTextField();
		contentPane.add(inputfileNameField, "cell 1 7 9 1,growx");
		inputfileNameField.setColumns(10);
		
		JButton button = new JButton("...");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					JFileChooser chooser = new JFileChooser();
					int returnVal = chooser.showOpenDialog(null);
					if(returnVal == JFileChooser.APPROVE_OPTION){
						inputFile.setFilename(chooser.getSelectedFile().getAbsolutePath());
						inputfileNameField.setText(inputFile.getFilename());
				}
			}
		});
		contentPane.add(button, "cell 10 7");
		
		lblAnalysisOutput = new JLabel("Analysis Output:");
		lblAnalysisOutput.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblAnalysisOutput, "cell 0 8,alignx center");
		
		analysisOutputField = new JTextField();
		contentPane.add(analysisOutputField, "cell 1 8 9 1,growx");
		analysisOutputField.setColumns(10);
		
		button_3 = new JButton("...");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showSaveDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					analysisOutputFile.setFilename(chooser.getSelectedFile().getAbsolutePath());
					analysisOutputField.setText(analysisOutputFile.getFilename());
				}
			}
		});
		contentPane.add(button_3, "cell 10 8");
		
		btnRunAnalysis = new JButton("Run Analysis");
		btnRunAnalysis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Analyzer surveyAnalyzer = new Analyzer();
				if(!analysisOutputFile.getFilename().equals("") && !inputFile.getFilename().equals("")){
					surveyAnalyzer.importFile(inputFile.getFilename());
					surveyAnalyzer.analyze();
					surveyAnalyzer.outputAnalysis(analysisOutputFile.getFilename());
					JOptionPane.showMessageDialog(null, "Analysis Complete!");
				} else{
					JOptionPane.showMessageDialog(null, "Input Incorrect");
				}
			}
		});
		contentPane.add(btnRunAnalysis, "cell 5 9,alignx center");
	}

}
