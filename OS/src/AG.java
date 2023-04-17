import java.util.ArrayList;

public class AG {

	private int time;
	private ArrayList<Process>readyQueue;
	private ArrayList<Process> processes;
	private Process current;
	private int numOfProcess;
	private int dead;
	private ArrayList<Process>processOrder;
	private ArrayList<Integer> timeOrder;
	private double averageTurnaround;
	private double averageWaitingTime;
	
	public AG(ArrayList<Process> processes) {
		this.processes = processes;
		this.readyQueue = new ArrayList<>();
		this.time = 0;
		this.dead = 0;
		this.current = null;
		this.numOfProcess = this.processes.size();
		this.timeOrder  = new ArrayList<>();
		this.processOrder = new ArrayList<>();
		averageTurnaround = 0;
		averageWaitingTime = 0;
	}


	
	public void checkReadyQueue() {
		this.checkArrival();
		while(readyQueue.isEmpty() && current == null) {
			time++;
			this.checkArrival();
		}
	}
	
	
	public void checkArrival() {
		for(Process p: processes) {
			if(p.getArrival_time() <= time) {
				boolean found = false;
				for(Process r: readyQueue) {
					if(r.getProcess_name()==p.getProcess_name())
					found = true;
				}
				if(found || p.getBrust_time()<= 0 || (current != null && current.getProcess_name() == p.getProcess_name()))
					continue;
				readyQueue.add(p);
			}
			else break;
		}
	}
	
	public int FCFS(Process o) {
		double x25 = Math.ceil(o.getQuantum()/4);
		x25 = Math.min(o.getBrust_time(), x25);
		o.updateBrust_time((int)x25);
		time += (int)x25;
		return (int)x25;
	}
	
	public int non_preemptive_Priority(Process o) {
		
		double x50 = Math.ceil(o.getQuantum()/2);
		double x25 = Math.ceil(o.getQuantum()/4);
		double x = x50 - x25;
		x = Math.min(o.getBrust_time(), (int)x);
		o.updateBrust_time((int)x);
		time += (int)x;
		return (int)x;
	}
	
	public void doWork() {
		
		while(this.dead < this.numOfProcess) {
			quantumUpdate();
			this.checkReadyQueue();
			if(current == null) {
				this.current = this.readyQueue.get(0);
				this.readyQueue.remove(0);
				this.processOrder.add(current);
				this.timeOrder.add(time);
			}
			
			int x = this.FCFS(current);
			
			this.checkArrival();
			
			if(completed(current)) {
				continue;
			}
			// p1 add ready  cur = p2 ,,continue;
			// priority check;
			//p1 ! update
			
			Process process = this.checkPriority();
			if(process.getProcess_name() != this.current.getProcess_name()) {
				this.current.updateQuantumPriority(x);
				this.readyQueue.add(current);
				this.current = process;
				this.readyQueue.remove(process);
				
				// save in answer
				this.processOrder.add(current);
				this.timeOrder.add(time);
				continue;
			}
			x += this.non_preemptive_Priority(current);
			
			this.checkArrival();
			
			if(completed(current)) {
				continue;
			}
			
			///////////////////////////////////////////////////
			
			process = this.checkSJF();
			if(process.getProcess_name() != this.current.getProcess_name()) {
				this.current.updateQuantumSJF(x);
				this.readyQueue.add(current);
				this.current = process;
				this.readyQueue.remove(process);
				
				// save in answer
				this.processOrder.add(current);
				this.timeOrder.add(time);
				continue;
			}

			this.checkArrival();
			
			if(completed(current)) {
				continue;
			}
			
			double rem = this.current.getQuantum() - x;
			boolean found = false;
			
			while(rem>0) {
				rem--;
				this.time++;
				x++;
				this.current.updateBrust_time(1);
				if(this.completed(this.current)) {
					found = true;
					break;
				}
				this.checkArrival();
				process = this.checkSJF();
				if(process.getProcess_name() != this.current.getProcess_name()) {
					this.current.updateQuantumSJF(x);
					this.readyQueue.add(current);
					this.current = process;
					this.readyQueue.remove(process);
					
					// save in answer
					this.processOrder.add(current);
					this.timeOrder.add(time);
					found = true;
					break;
				}
				
			}
			if(found)continue;
			this.current.setQuantum(2);
			this.readyQueue.add(current);
			this.current = null;	
		}
		this.timeOrder.add(time);
		
		
	}
	
	public boolean completed(Process o) {
		if(o.getBrust_time()<=0) {
			dead++;
			o.setQuantum(0);
			
			o.setTurnaround_time(time - o.getArrival_time());
			o.setWaiting_time(o.getTurnaround_time()-o.getExuction_time());
			this.averageTurnaround+=o.getTurnaround_time();
			this.averageWaitingTime+=o.getWaiting_time();
			
			current = null;
			return true;
		}
		return false;
	}
	
	public Process checkPriority() {
		Process minP = this.current;
		int min = this.current.getPriority();
		for(Process p:this.readyQueue) {
			if(p.getPriority()<min) {
				min = p.getPriority();
				minP = p;
			}
		}
		return minP;
	}
	
	
	public Process checkSJF() {
		Process minP = this.current;
		int min = this.current.getBrust_time();
		for(Process p:this.readyQueue) {
			if(p.getBrust_time()<min) {
				min = p.getBrust_time();
				minP = p;
			}
		}
		return minP;
	}
	
	public void print() {
		for(Process p:processOrder) {
			System.out.print(p.getProcess_name() + "   ");
		}
		System.out.print('\n');
		
		
		for(Integer i:this.timeOrder) {
			System.out.print(i);
			System.out.print("   ");
			if(i<10)System.out.print(" ");
		}
		
		System.out.println();
		System.out.println("Process name    Waiting time     Turnaround time");
		for(Process p:processes) {
			System.out.println(p.getProcess_name() + "                  " + p.getWaiting_time() + "                  " + p.getTurnaround_time());
		}
		System.out.println();
		System.out.println("Average waiting time = " + (double)(this.averageWaitingTime/this.numOfProcess));
		System.out.println("Average turnaround time = " + (double)(this.averageTurnaround/this.numOfProcess));
		
		
	}
	
	public void quantumUpdate() {
		System.out.print("Quantum (");
		for(int i=0;i<this.numOfProcess;i++) {
			System.out.print((int)this.processes.get(i).getQuantum());
			if(i!=this.numOfProcess-1)System.out.print(',');
		}
		System.out.println(")");
	}

	public double getAverageTurnaround() {
		return averageTurnaround;
	}

	public void setAverageTurnaround(double averageTurnaround) {
		this.averageTurnaround = averageTurnaround;
	}

	public double getAverageWaitingTime() {
		return averageWaitingTime;
	}

	public void setAverageWaitingTime(double averageWaitingTime) {
		this.averageWaitingTime = averageWaitingTime;
	}

	
}
