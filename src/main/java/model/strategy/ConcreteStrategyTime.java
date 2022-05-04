package model.strategy;

import java.util.List;

import model.Server;
import model.Task;

public class ConcreteStrategyTime implements Strategy {

	@Override
	public void addTask(List<Server> servers, Task task) {
		int minWaitingPeriod = servers.get(0).getWaitingPeriod().get(); // initializam timpul minim de asteptare per server cu timpul de pe primul server
		int bestServerIndex = 0; // initializam indexul celui mai bun server pentru strategie cu indexul primului server: 0
		int currentMinWaitingPeriod = 0;
		
		for(Server server : servers.subList(1,servers.size())) { // pentru fiecare server de dupa primul server din lista de servere
			currentMinWaitingPeriod = server.getWaitingPeriod().get(); // se ia timpul de asteptare din serverul curent
			if(currentMinWaitingPeriod < minWaitingPeriod) { // daca timpul de asteptare din serverul curent e mai mic decat minimul curent
				minWaitingPeriod = currentMinWaitingPeriod; // salvam cel mai bun timp de asteptare
				bestServerIndex = servers.indexOf(server); // salvam indexul celui mai bun server
			}
		}
		
		servers.get(bestServerIndex).addTask(task); // adaugam taskul in cel mai bun server gasit
	}
}
