package model;

import java.util.ArrayList;
import java.util.List;

import model.strategy.*;

public class Scheduler {

	private List<Server> servers;
	private int maxNumServers;
	private int maxTasksPerServer;
	private Strategy strategy;
	
	public Scheduler(int maxNumServers, int maxTasksPerServer) {
		this.maxNumServers = maxNumServers;
		this.maxTasksPerServer = maxTasksPerServer;
		this.servers = new ArrayList<Server>(maxNumServers);
		
		for(int i = 0; i < maxNumServers; i++) {
			Server server = new Server(maxTasksPerServer);
			servers.add(server);
		}
	}
	
	/** Schimba strategia de selectie a serverelor **/
	public void changeStrategy(SelectionPolicy policy) {
		if(policy == SelectionPolicy.SHORTEST_QUEUE) {
			strategy = new ConcreteStrategyQueue();
		}
		if(policy == SelectionPolicy.SHORTEST_TIME) {
			strategy = new ConcreteStrategyTime();
		}
	}
	
	/** Adauga un task intr-un anumit server din lista de servere in functie de strategia aleasa **/
	public void dispatchTask(Task task) {
		strategy.addTask(servers, task);
	}

	public List<Server> getServers() {
		return servers;
	}
}
