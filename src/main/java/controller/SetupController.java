package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.strategy.SelectionPolicy;
import view.SetupView;

public class SetupController implements ActionListener {

	private SetupView view;
	
	public SetupController(SetupView view) {
		this.view = view;
		
		view.getSimulationButton().addActionListener(this);
		view.getClearButton().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == view.getSimulationButton()) {
			if(areEmptyFields()) {
				JOptionPane.showMessageDialog(view,"Completați toate câmpurile!","Eroare",JOptionPane.ERROR_MESSAGE);
			}
			else {
				int timeLimit = Integer.parseInt(view.getTimeField().getText());
				int minArrivalTime = Integer.parseInt(view.getMinArrivalTimeField().getText());
				int maxArrivalTime = Integer.parseInt(view.getMaxArrivalTimeField().getText());
				int minProcessingTime = Integer.parseInt(view.getMinProcessingTimeField().getText());
				int maxProcessingTime = Integer.parseInt(view.getMaxProcessingTimeField().getText());
				int numServers = Integer.parseInt(view.getServersField().getText());
				int numClients = Integer.parseInt(view.getClientsField().getText());
				SelectionPolicy policy = (SelectionPolicy)view.getStrategyField().getSelectedItem();
				
				if((minArrivalTime > maxArrivalTime) || (minProcessingTime > maxProcessingTime)) {
					JOptionPane.showMessageDialog(view,"Valorile maxime de timp trebuie să fie\nmai mari decât valorile minime!","Eroare",JOptionPane.ERROR_MESSAGE);
				}
				else {
					SimulationManager simulation = new SimulationManager(timeLimit,minArrivalTime,maxArrivalTime,minProcessingTime,maxProcessingTime,numServers,numClients,policy);
				
					simulation.getView().setVisible(true);
			
					Thread simulationThread = new Thread(simulation);
					simulationThread.start();
				}
			}
		}
		if(e.getSource() == view.getClearButton()) {
			view.getTimeField().setText("");
			view.getMinArrivalTimeField().setText("");
			view.getMaxArrivalTimeField().setText("");
			view.getMinProcessingTimeField().setText("");
			view.getMaxProcessingTimeField().setText("");
			view.getServersField().setText("");
			view.getClientsField().setText("");
		}
	}
	
	private boolean areEmptyFields() {
		if(view.getTimeField().getText().isBlank()) {
			return true;
		}
		if(view.getMinArrivalTimeField().getText().isBlank()) {
			return true;
		}
		if(view.getMaxArrivalTimeField().getText().isBlank()) {
			return true;
		}
		if(view.getMinProcessingTimeField().getText().isBlank()) {
			return true;
		}
		if(view.getMaxProcessingTimeField().getText().isBlank()) {
			return true;
		}
		if(view.getClientsField().getText().isBlank()) {
			return true;
		}
		if(view.getServersField().getText().isBlank()) {
			return true;
		}
		
		return false;
	}
}
