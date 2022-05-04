package view;

import java.awt.*;
import javax.swing.*;

import model.strategy.SelectionPolicy;

@SuppressWarnings("serial")
public class SetupView extends JFrame {

	JPanel titlePanel = new JPanel();
	JPanel timePanel = new JPanel();
	JPanel arrivalTimePanel = new JPanel();
	JPanel processingTimePanel = new JPanel();
	JPanel clientsAndServersPanel = new JPanel();
	JPanel strategyPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	JPanel clearPanel = new JPanel();
	
	JLabel titleLabel = new JLabel("Simulator de cozi", SwingConstants.CENTER);
	
	private JTextField timeField = new JTextField();
	private JTextField minArrivalTimeField = new JTextField();
	private JTextField maxArrivalTimeField = new JTextField();
	private JTextField minProcessingTimeField = new JTextField();
	private JTextField maxProcessingTimeField = new JTextField();
	private JTextField clientsField = new JTextField();
	private JTextField serversField = new JTextField();
	
	private JComboBox<SelectionPolicy> strategyField = new JComboBox<SelectionPolicy>();
	
	private JButton clearButton = new JButton("Resetare");
	private JButton simulationButton = new JButton("Simulare");
	
	
	Dimension fieldDimension = new Dimension(50,30);
	
	Font textFieldFont = new Font("",Font.PLAIN,16);
	
	public SetupView() {
		
		this.setSize(new Dimension(506,600));
		this.setLayout(new GridLayout(9,1));
		this.setTitle("Simulator");
		
		strategyField.addItem(SelectionPolicy.SHORTEST_TIME);
		strategyField.addItem(SelectionPolicy.SHORTEST_QUEUE);
		
		timeField.setPreferredSize(fieldDimension);
		timeField.setFont(textFieldFont);
		minArrivalTimeField.setPreferredSize(fieldDimension);
		minArrivalTimeField.setFont(textFieldFont);
		maxArrivalTimeField.setPreferredSize(fieldDimension);
		maxArrivalTimeField.setFont(textFieldFont);
		minProcessingTimeField.setPreferredSize(fieldDimension);
		minProcessingTimeField.setFont(textFieldFont);
		maxProcessingTimeField.setPreferredSize(fieldDimension);
		maxProcessingTimeField.setFont(textFieldFont);
		clientsField.setPreferredSize(fieldDimension);
		clientsField.setFont(textFieldFont);
		serversField.setPreferredSize(fieldDimension);
		serversField.setFont(textFieldFont);
		
		clearButton.setFocusable(false);
		simulationButton.setFocusable(false);
		
		bottomPanel.setLayout(new BorderLayout());
		
		clearButton.setForeground(Color.white);
		clearButton.setBackground(new Color(100,100,100));
		
		clearPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		clearPanel.add(clearButton);
		bottomPanel.add(clearPanel, BorderLayout.SOUTH);
		titlePanel.add(titleLabel);
		titleLabel.setFont(new Font("",Font.BOLD,18));
		timePanel.add(new JLabel("Timp de simulare: "));
		timePanel.add(timeField);
		arrivalTimePanel.add(new JLabel(" Timp minim sosire:    "));
		arrivalTimePanel.add(minArrivalTimeField);
		arrivalTimePanel.add(new JLabel("  Timp maxim sosire:   "));
		arrivalTimePanel.add(maxArrivalTimeField);
		processingTimePanel.add(new JLabel("Timp minim servire:   "));
		processingTimePanel.add(minProcessingTimeField);
		processingTimePanel.add(new JLabel("  Timp maxim servire:  "));
		processingTimePanel.add(maxProcessingTimeField);
		clientsAndServersPanel.add(new JLabel("Număr maxim clienți: "));
		clientsAndServersPanel.add(clientsField);
		clientsAndServersPanel.add(new JLabel("  Număr maxim cozi:    "));
		clientsAndServersPanel.add(serversField);
		strategyPanel.add(new JLabel("Strategie:"));
		strategyPanel.add(strategyField);
		buttonPanel.add(simulationButton);
		
		this.add(new JPanel());
		this.add(titlePanel);
		this.add(timePanel);
		this.add(arrivalTimePanel);
		this.add(processingTimePanel);
		this.add(clientsAndServersPanel);
		this.add(strategyPanel);
		this.add(buttonPanel);
		this.add(bottomPanel);
		
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public JTextField getTimeField() {
		return timeField;
	}

	public JTextField getMinArrivalTimeField() {
		return minArrivalTimeField;
	}

	public JTextField getMaxArrivalTimeField() {
		return maxArrivalTimeField;
	}

	public JTextField getMinProcessingTimeField() {
		return minProcessingTimeField;
	}

	public JTextField getMaxProcessingTimeField() {
		return maxProcessingTimeField;
	}

	public JTextField getClientsField() {
		return clientsField;
	}

	public JTextField getServersField() {
		return serversField;
	}

	public JComboBox<SelectionPolicy> getStrategyField() {
		return strategyField;
	}

	public JButton getSimulationButton() {
		return simulationButton;
	}
	
	public JButton getClearButton() {
		return clearButton;
	}
}
