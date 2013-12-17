package com.gb.OASurveyCalcGUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.gb.OASurveyCalc.Filename;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CalcGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6225029311299921194L;
	private JPanel contentPane;
	private JTextField fileNameField;

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
		
		final Filename inputFile = new Filename();
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow][]", "[]"));
		
		JLabel lblInputFile = new JLabel("Input File: ");
		lblInputFile.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblInputFile, "cell 0 0,alignx center");
		
		fileNameField = new JTextField();
		contentPane.add(fileNameField, "cell 1 0,grow");
		fileNameField.setColumns(10);
		
		JButton button = new JButton("...");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					inputFile.setFilename(chooser.getSelectedFile().getAbsolutePath());
					fileNameField.setText(inputFile.getFilename());
				}
			}
		});
		contentPane.add(button, "cell 2 0");
	}

}
