package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import model.Scheduler;
import model.Server;
import model.Task;
import model.strategy.SelectionPolicy;
import view.SimulationView;

public class SimulationManager implements Runnable {

	private int timeLimit;
	private int maxArrivalTime;
	private int minArrivalTime;
	private int maxProcessingTime;
	private int minProcessingTime;
	private int numServers;
	private int numClients;
	private SelectionPolicy policy;
	
	private Scheduler scheduler;
	private SimulationView view;
	private List<Task> generatedTasks;
	private List<Thread> threads;
	
	private double averageWaitingTime;
	private double averageServiceTime;
	private int peakTime;
	
	public SimulationManager(int timeLimit,int minArrivalTime,int maxArrivalTime,int minProcessingTime,int maxProcessingTime,int numServers,int numClients,SelectionPolicy policy) {
		this.timeLimit = timeLimit;
		this.minArrivalTime = minArrivalTime;
		this.maxArrivalTime = maxArrivalTime;
		this.minProcessingTime = minProcessingTime;
		this.maxProcessingTime = maxProcessingTime;
		this.numServers = numServers;
		this.numClients = numClients;
		this.policy = policy;
		
		scheduler = new Scheduler(numServers, numClients);
		scheduler.changeStrategy(policy);
		view = new SimulationView();
		generatedTasks = new ArrayList<Task>();
		threads = new ArrayList<Thread>();
		
		averageWaitingTime = 0;
		averageServiceTime = 0;
		peakTime = 0;
		
		generateTasks();
		startThreads();
	}
	
	/** Sorteaza crescator task-urile generate in functie de timpul de sosire (primul venit, primul servit) **/
	private void sortGeneratedTasks() {
		Collections.sort(generatedTasks, new Comparator<Task>() {
			public int compare(Task o1, Task o2) {
				return o1.getArrivalTime() - o2.getArrivalTime();
			}
        });
	}
	
	/** Returneaza un task cu valorile atributelor aleatoare **/
	private Task generateRandomTask() {
		int randomProcessingTime = (int)(Math.random() * maxProcessingTime + minProcessingTime); // genereaza un timp de procesare intre timpii minim si maxim
		int randomArrivalTime = (int)(Math.random() * maxArrivalTime + minArrivalTime); // genereaza un timp de sosire intre timpii minim si maxim
		
		//Task randomTask = new Task();
		//randomTask.setArrivalTime(randomArrivalTime);
		//randomTask.setProcessingTime(randomProcessingTime);
		
		Task randomTask = new Task(randomArrivalTime,randomProcessingTime);
		
		return randomTask;
	}
	
	/** Genereaza o lista sortata de task-uri aleatoare **/
	private void generateTasks() {
		for(int i = 1; i <= numClients; i++) {
			generatedTasks.add(generateRandomTask());
		}
		
		sortGeneratedTasks();
		
		for(Task task : generatedTasks) {
			task.setId(generatedTasks.indexOf(task) + 1);
		}
	}
	
	private void startThreads() {
		Thread thread;
		for(Server server : scheduler.getServers()) {
			thread = new Thread(server);
			thread.start();
			threads.add(thread);
		}
	}
	
	@Override
	public void run() {
		synchronized(this) {
			int currentTime = 0, numSpaces = 0; // pentru afisare
			averageServiceTime = getAverageServiceTime();
			double sumOfAverageWaitingTimes = 0;
			int currentNumTasksInServers = 0, maxNumTasksInServers = 0;
			boolean isSimulationDone = false; // Daca e false, se va scrie in log starea simularii la un moment de timp. Altfel, scrie la final statistici
			
			while(currentTime < timeLimit) {
				Iterator<Task> iterator = generatedTasks.iterator();
				while(iterator.hasNext()) {
					Task currentTask = iterator.next();
					if(currentTask.getArrivalTime() == currentTime) {
						scheduler.dispatchTask(currentTask);
						iterator.remove();
						numSpaces++;
					}
				}
				updateView(generatedTasks.size(),currentTime,numSpaces);
				updateLog(isSimulationDone);
				if(generatedTasks.isEmpty() && areEmptyServers()) { // daca nu mai exista task-uri de pus si serverele sunt goale, simularea e gata
					break;
				}
				currentNumTasksInServers = getCurrentNumTasksInServers();
				if(currentNumTasksInServers > maxNumTasksInServers) {
					maxNumTasksInServers = currentNumTasksInServers;
					peakTime = currentTime;
				}
				sumOfAverageWaitingTimes += getCurrentAverageWaitingTime();
				currentTime++;
				try {
					wait(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			isSimulationDone = true;
			averageWaitingTime = sumOfAverageWaitingTimes / (double)timeLimit;
			updateLog(isSimulationDone);
			JOptionPane.showMessageDialog(view,"Simulare terminată!\nVerificați fișierul log.txt!","Finalizare",JOptionPane.INFORMATION_MESSAGE);
			view.dispose();
		}
	}
	
	/** Updateaza view-ul simularii in timp real **/
	private void updateView(int numWaitingClients,int time,int numSpaces) {
		view.getTextArea().selectAll();
		view.getTextArea().replaceSelection("");
		view.getTextArea().append("\n");
		
		view.getTextArea().append("Momentul " + time + "\n");
		view.getTextArea().append("\nClienti în asteptare: " + numWaitingClients + "\n");
		
		if(numClients <= 10) {
			for(Task task : generatedTasks) {
				view.getTextArea().append(task.toString() + "\n");
			}
			for(int i = 1; i <= numSpaces; i++) {
				view.getTextArea().append("\n");
			}
		}
		
		view.getTextArea().append("\n");
		
		for(Server server : scheduler.getServers()) {
			view.getTextArea().append("Coada " + (scheduler.getServers().indexOf(server) + 1) + ": ");
			if(server.getTasks().isEmpty()) {
				view.getTextArea().append("Închisa\n");
			}
			else {
				for(Task task : server.getTasks()) {
					view.getTextArea().append(task.toString() + "; ");
				}
				view.getTextArea().append("\n");
			}
		}
	}

	/** Scrie in log la fiecare moment de timp **/
	private void updateLog(boolean isSimulationDone) {  
		try {
			File log = new File("log.txt");
			log.delete();
			log.createNewFile();
			FileWriter writer = new FileWriter(log,true);
			if(isSimulationDone == false) {
				view.getTextArea().write(writer);
			}
			else {
				writer.append("\nTimp mediu de asteptare: " + String.format("%.2f", averageWaitingTime) + "\n");
				writer.append("Timp mediu de servire: " + String.format("%.2f", averageServiceTime) + "\n");
				writer.append("Timpul de varf: " + peakTime);
				writer.flush();
				writer.close();
			}
		} catch (IOException e) {
			System.out.println("Eroare log");
			e.printStackTrace();
		}
	}
	
	private double getAverageServiceTime() {
		double sumOfServiceTimes = 0;
		
		for(Task task : generatedTasks) {
			sumOfServiceTimes += task.getProcessingTime();
		}
		
		return sumOfServiceTimes / (double)numClients;
	}
	
	private int getCurrentNumTasksInServers() {
		int currentNumTasks = 0;
		
		for(Server server : scheduler.getServers()) {
			currentNumTasks += server.getTasks().size();
		}
		
		return currentNumTasks;
	}
	
	/** Calculeaza timpul mediu de asteptare la un moment de timp **/
	private double getCurrentAverageWaitingTime() {
		double currentAverageWaitingTime = 0;
		
		for(Server server : scheduler.getServers()) {
			currentAverageWaitingTime += server.getWaitingPeriod().get();
		}
		
		return currentAverageWaitingTime / (float)numServers;
	}
	
	/** Verifica daca toate serverele sunt goale **/
	private boolean areEmptyServers() {
		for(Server server : scheduler.getServers()) {
			if(server.getTasks().isEmpty() == false) {
				return false;
			}
		}
		
		return true;
	}
	
	public void setMaxProcessingTime(int maxProcessingTime) {
		this.maxProcessingTime = maxProcessingTime;
	}

	public void setMinProcessingTime(int minProcessingTime) {
		this.minProcessingTime = minProcessingTime;
	}

	public void setNumServers(int numServers) {
		this.numServers = numServers;
	}

	public void setNumClients(int numClients) {
		this.numClients = numClients;
	}

	public void setPolicy(SelectionPolicy policy) {
		this.policy = policy;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public SimulationView getView() {
		return view;
	}
}
