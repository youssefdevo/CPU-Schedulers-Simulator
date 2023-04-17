import java.util.ArrayList;
import java.util.Scanner;


public class PriorityScheduling {
	private int n;
	private int RRQT;
	private int CST;
	private ArrayList<Process> process;
	
	public void priority(int n,int RRQT,int CST,ArrayList<Process> pro)
	{
		this.n = n;
		this.RRQT = RRQT;
		this.CST = CST;
		this.process = pro;
	
		ArrayList<String> outputName=new ArrayList<String>();
		ArrayList<Integer> outputTimeStart=new ArrayList<Integer>();
		ArrayList<Integer> outputTimeEnd=new ArrayList<Integer>();
		
		String name =".";
		int start=0;
	    Integer time=(int)1e9;
	    boolean done=true;
	    boolean first=true;
	    int cnt=0;
	    int incrementPR=2;
	    while(done)
	    {
	    	ArrayList<Process> arriv=new ArrayList<Process>();
	    	String name2=name;
	    	int timeMIN=time;
	    	Integer PR=-1;
	    	boolean ok=false;
	    	if(first)
	    	{
	    		for(Process p: process)
				{
					if(p.getArrival_time()<=timeMIN)
					{
						time=p.getArrival_time();
						timeMIN=time;
						start=p.getArrival_time();
						name=p.getProcess_name();
						name2=name;	
					}
				}
	    		first=false;
	    	}
	    	for(Process p: process)
			{
	    		if(p.getArrival_time()<=time && p.getBrust_time()>0)
	    		{
	    			arriv.add(p);
	    		}
			}
	    	int currentPR = 0;
	    	for(Process p: arriv)
			{
	    		if(p.getPriority()>PR )
	    		{
	    			PR=p.getPriority();
	    			name2=p.getProcess_name();
	    		}
	    		if(p.getProcess_name()==name)currentPR=p.getPriority();
			}
	    	if(PR==currentPR)name2=name;
	    	for(Process p:process)
	    	{
	    		if(p.getBrust_time()>0)
	    		{
	    			ok=true;
	    			break;
	    		}
	    	}
    		if(!ok)done=false;
	    	if(name!=name2 || ok==false)
	    	{
	    		outputTimeStart.add(start);
	    		start=time;
	    		outputName.add(name);
	    		outputTimeEnd.add(time);
	    		name=name2;
	    	}
	    	else
	    	{
		    	if(arriv.size()==0)
		    	{
		    		time++;
		    		continue;
		    	}
		    	for(Process p: process)
				{
					if(name==p.getProcess_name())
					{
						p.setBrust_time(p.getBrust_time()-1);// 1->0
						time++;
						cnt++;
						if(cnt==incrementPR)
						{
							for(Process a:arriv)
							{
								if(a.getProcess_name()!=name)a.setPriority(a.getPriority()+1);
							}
							cnt=0;
						}
						if(p.getBrust_time()==0)
						{
							p.setComplete(time);
						}
					}
				}
	    	}
	    	
	    }
	    
	    double sumWaiting=0,sumTurnaround=0;
		for(Process p:process)
		{
			p.setWaiting_time((p.getComplete())-p.getArrival_time()-p.getExuction_time());
			p.setTurnaround_time(p.getWaiting_time()+p.getExuction_time());
			sumWaiting+=p.getWaiting_time();
			sumTurnaround+=p.getTurnaround_time();
		}
	    int sz=outputName.size();
		for(int i=0;i<sz;i++)
	    {
			System.out.println(outputName.get(i)+"-> "+outputTimeStart.get(i)+" : "+outputTimeEnd.get(i));
	    }
		System.out.println();
		for(Process p: process)
		{
			p.print();
			System.out.println();
		}
		System.out.println("Average Waiting Tim: "+sumWaiting/n);
		System.out.println("Average Turnaround Time: "+sumTurnaround/n);
	    

	}
}
