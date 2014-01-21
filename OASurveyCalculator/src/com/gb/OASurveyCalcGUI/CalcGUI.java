package com.gb.OASurveyCalcGUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CalcGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -206995386453883939L;
	private JPanel contentPane;
	private JTextField importFileName;
	private JLabel lblImportFile;
	private JButton runImportButton;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow][]", "[][]"));
		
		lblImportFile = new JLabel("Import File: ");
		contentPane.add(lblImportFile, "cell 0 0,alignx trailing");
		
		importFileName = new JTextField();
		contentPane.add(importFileName, "cell 1 0,growx");
		importFileName.setColumns(10);
		
		JButton importButton = new JButton("...");
		contentPane.add(importButton, "cell 2 0");
		
		runImportButton = new JButton("Import File");
		runImportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		contentPane.add(runImportButton, "cell 1 1,alignx center,aligny center");
	}

}
