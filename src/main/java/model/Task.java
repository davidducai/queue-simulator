package model;

public class Task {

	private int id;
	private int arrivalTime;
	private int processingTime;
	

	public Task() {}
	
	public Task(int arrivalTime, int processingTime) {
		this.arrivalTime = arrivalTime;
		this.processingTime = processingTime;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}
	
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	public int getProcessingTime() {
		return processingTime;
	}
	
	public void setProcessingTime(int processingTime) {
		this.processingTime = processingTime;
	}
	
	public String toString() {
		return "(" + id + "," + arrivalTime + "," + processingTime + ")";
	}
}
