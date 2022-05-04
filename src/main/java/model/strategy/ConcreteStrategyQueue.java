package model.strategy;

import java.util.List;

import model.Server;
import model.Task;

public class ConcreteStrategyQueue implements Strategy {

	@Override
	public void addTask(List<Server> servers, Task task) {
		int minNumTasks = servers.get(0).getTasks().size(); // initializam valoarea minima de clienti per server cu numarul clientilor de pe primul server
		int bestServerIndex = 0; // initializam indexul celui mai bun server pentru strategie cu indexul primului server: 0
		int currentNumTasks = 0;
		
		for(Server server : servers.subList(1,servers.size())) { // pentru fiecare server de dupa primul server din lista de servere
			currentNumTasks = server.getTasks().size(); // se ia numarul de clienti din serverul curent
			if(currentNumTasks < minNumTasks) { // daca numarul de clienti din serverul curent e mai mic decat minimul curent
				minNumTasks = currentNumTasks; // salvam cel mai mic numar de clienti per server
				bestServerIndex = servers.indexOf(server); // salvam indexul celui mai bun server
			}
		}
		
		servers.get(bestServerIndex).addTask(task); // adaugam task-ul in cel mai bun server gasit
	}
}
