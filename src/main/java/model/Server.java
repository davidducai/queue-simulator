package model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {

	private BlockingQueue<Task> tasks;
	private AtomicInteger waitingPeriod;

	public Server(int numTasks) {
		tasks = new ArrayBlockingQueue<Task>(numTasks);
		waitingPeriod = new AtomicInteger(0);
	}
	
	/** Adauga un nou task in server **/
	public void addTask(Task newTask) {
		tasks.add(newTask);
		waitingPeriod.addAndGet(newTask.getProcessingTime());
	}

	public BlockingQueue<Task> getTasks() {
		return tasks;
	}

	public void setTasks(BlockingQueue<Task> tasks) {
		this.tasks = tasks;
	}

	public AtomicInteger getWaitingPeriod() {
		return waitingPeriod;
	}

	public void setWaitingPeriod(AtomicInteger waitingPeriod) {
		this.waitingPeriod = waitingPeriod;
	}

	@Override
	public void run() {
		synchronized(this) {
			Task currentTask = new Task();
			while(true) {
				if(tasks.isEmpty()) { // daca coada e goala, asteapta -> va astepta pana va fi cel putin un task in coada
					try {
						wait(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else { // altfel, proceseaza primul task din coada
					try {
						currentTask = tasks.element();
						while(currentTask.getProcessingTime() > 1) { 
							currentTask.setProcessingTime(currentTask.getProcessingTime() - 1);
							waitingPeriod.decrementAndGet();
							wait(1000);
						}
						tasks.take();
						wait(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
