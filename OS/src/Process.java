
public class Process implements Comparable<Process>{
	private String process_name;
	private int arrival_time;
	private int brust_time;
	private int exuction_time;
	private int priority;
	private int turnaround_time;
	private int waiting_time;
	private double quantum;
	private int complete;
	
	Process(String name,int arrival,int brust,int pr){
		this.setProcess_name(name);
		this.setArrival_time(arrival);
		this.setBrust_time(brust);
		this.setPriority(pr);
		this.setExuction_time(brust);
		complete = 0;
		
	}
	
	Process(String name,int arrival,int brust,int pr,double quantum){
		this.setProcess_name(name);
		this.setArrival_time(arrival);
		this.setBrust_time(brust);
		this.setPriority(pr);
		this.setQuantum(quantum);
		this.setExuction_time(brust);
		complete = 0;
	}
	
	
	public String getProcess_name() {
		return process_name;
	}
	public void setProcess_name(String process_name) {
		this.process_name = process_name;
	}
	public int getArrival_time() {
		return arrival_time;
	}
	public void setArrival_time(int arrival_time) {
		this.arrival_time = arrival_time;
	}
	public int getBrust_time() {
		return brust_time;
	}
	public void setBrust_time(int brust_time) {
		this.brust_time = brust_time;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public double getQuantum() {
		return quantum;
	}
	public void setQuantum(double quantum) {
		this.quantum = quantum;
	}
	
	public int getComplete() {
		return complete;
	}


	public void setComplete(int complete) {
		this.complete = complete;
	}
	
	public void updateQuantumPriority(double x) {
		double rem = this.quantum - x;
		this.quantum += Math.ceil(rem/2);
	}
	
	public void updateQuantumSJF(double x) {
		double rem = this.quantum - x;
		this.quantum += rem;
	}
	
	public void print() {
		System.out.println("Name: " + this.process_name + ": ");
	    System.out.println("Arrival time: " + this.arrival_time);
	    System.out.println("Execution: " + this.exuction_time);
	    System.out.println("Waiting Time: " + this.waiting_time);
	    System.out.println("Completion Time: " + this.complete);
	    System.out.println("Turnaround Time: " + this.turnaround_time);
	    
	}


	public void updateBrust_time(int x) {
		this.brust_time -= x;
	}
	
	
	public int compareTo(Process comparePro) {
        int compareage= comparePro.getArrival_time();
 
        //  For Ascending order
        return this.arrival_time - compareage;
 
    }


	public int getTurnaround_time() {
		return turnaround_time;
	}


	public void setTurnaround_time(int turnaround_time) {
		this.turnaround_time = turnaround_time;
	}


	public int getWaiting_time() {
		return waiting_time;
	}


	public void setWaiting_time(int waiting_time) {
		this.waiting_time = waiting_time;
	}


	public int getExuction_time() {
		return exuction_time;
	}


	public void setExuction_time(int exuction_time) {
		this.exuction_time = exuction_time;
	}

}
