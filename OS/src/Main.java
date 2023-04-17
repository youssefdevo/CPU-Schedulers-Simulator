import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {


	public static void main(String[] args) {
		Scanner in  = new Scanner(System.in);
		
		System.out.println("Number of processes:");
		int nums = in.nextInt();
		
		System.out.println("Round robin Time Quantum:");
		double quantum = in.nextDouble();
		
		System.out.println("Context switching:");
		int context = in.nextInt();
		
		ArrayList<Process>processes  = new ArrayList<>();
		for(int i = 0; i < nums; i++) {
			
			System.out.println("Process name:");
			String name = in.next();

			System.out.println("Process Arrival time:");
			int arrival = in.nextInt();
			
			System.out.println("Process brust time:");
			int brust = in.nextInt();
			
			System.out.println("Process Priority:");
			int priority = in.nextInt();
			
			System.out.println("Process quantum:");
			int q = in.nextInt();
			
			Process p = new Process(name,arrival,brust,priority,q);
			processes.add(p);
		}
		
        
		Collections.sort(processes);
        System.out.println("1- SJF\n2- RoundRobin\n3- PriorityScheduling\n4- AG");
		int request = in.nextInt();
		
		if(request == 1) {
		//1st
        SJF sj = new SJF();
        sj.sjf(nums,(int)quantum,context,processes);
		}
		
		else if(request == 2) {
        //2nd
        RoundRobin rb = new RoundRobin();
        rb.roundRobin(nums,(int)quantum,context,processes);
		}
		
		else if(request == 3) {
        //3rd
        PriorityScheduling ps = new PriorityScheduling();
        ps.priority(nums,(int)quantum,context,processes);
		}
		
		else if(request == 4) {
        // 4th
        AG agScheduler  = new AG(processes); 
		agScheduler.doWork();
		agScheduler.print();
		}
		
	}
	
	
}
