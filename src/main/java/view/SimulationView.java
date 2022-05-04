package view;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

@SuppressWarnings("serial")
public class SimulationView extends JFrame {

	private JTextArea textArea = new JTextArea("\n");
	
	public SimulationView() {
		
		this.setTitle("Simulare");
		this.setSize(new Dimension(500,300));
		
		textArea.setEditable(false);
		textArea.setFont(new Font("",Font.PLAIN,15));
		this.add(textArea);
		
		this.setLocationRelativeTo(null);
		this.setVisible(false);
	}

	public JTextArea getTextArea() {
		return textArea;
	}
}
