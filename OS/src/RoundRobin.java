import java.util.ArrayList;
import java.util.Collections;


public class RoundRobin {
	private int n;
	private int RRQT;
	private int CST;
	private ArrayList<Process> process;
	
	public void roundRobin(int n,int RRQT,int CST,ArrayList<Process> pro)
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
	    Integer time=(int) 1e9;
	    boolean done=true;
	    boolean first=true;
    	String last_name="";

	    while(done)
	    {
	    	ArrayList<Process> arriv=new ArrayList<Process>();
	    	boolean ok=false;
	    	if(first)
	    	{
	    		for(Process p: process)
				{
					if(p.getArrival_time()<=time)
					{
						time=p.getArrival_time();
						start=p.getArrival_time();
						name=p.getProcess_name();
					}
				}
	    		first=false;
	    	}
	    	
	    	for(Process p: process)
			{
	    		if(p.getBrust_time()>0)
	    		{
	    			arriv.add(p);
	    		}
			}
	    	for(Process p:process)
	    	{
	    		if(p.getBrust_time()>0)
	    		{
	    			ok=true;
	    			break;
	    		}
	    	}	    	
	    	if(!ok)done=false;
	    	if(arriv.size()==0 && ok)
	    	{
	    		time++;
	    		continue;
	    	}
	    	for(Process p:arriv)
	    	{
	    		if(arriv.size()==1 && p.getArrival_time()<=time ) 
	    		{
	    			outputTimeStart.add(time);
					time+=p.getBrust_time()+CST;
		    		p.setBrust_time(0);
					outputName.add(p.getProcess_name());
		    		outputTimeEnd.add(time);
					p.setComplete(time);
	    		}
	    		else if(p.getArrival_time()<=time)
	    		{
	    			if(p.getProcess_name()!=last_name)
	    			{
		    			outputTimeStart.add(time);
		    			time+=Math.min(p.getBrust_time(),RRQT)+CST;
		    			outputName.add(p.getProcess_name());
			    		outputTimeEnd.add(time);
	    			}
	    			else
	    			{
	    				time+=Math.min(p.getBrust_time(),RRQT);
	    			}
		    		p.setBrust_time(p.getBrust_time()-Math.min(p.getBrust_time(),RRQT));
					if(p.getBrust_time()==0)
					{
						p.setComplete(time);
					}
	    		}
	    		last_name=p.getProcess_name();
	    	}
	    }
	    double sumWaiting=0,sumTurnaround=0;
		for(Process p:process)
		{
			p.setWaiting_time((p.getComplete()-CST)-p.getArrival_time()-p.getExuction_time());
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
