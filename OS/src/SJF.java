import java.util.ArrayList;


public class SJF {
	private int n;
	private int RRQT;
	private int CST;
	private ArrayList<Process> process;
	public void sjf(int n,int RRQT,int CST,ArrayList<Process> pro)
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
	    while(done)
	    {
	    	ArrayList<Process> arriv=new ArrayList<Process>();
	    	String name2=name;
//	    	int timeMIN=time;
	    	Integer EXT=(int) 1e9;
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
						name2=name;	
					}
				}
	    		
	    	}
	    	//System.out.println(name);
	    	for(Process p: process)
			{
	    		if(p.getArrival_time()<=time && p.getBrust_time()>0)
	    		{
	    			arriv.add(p);
	    		}
			}
	    	int currentEX = 0;
	    	for(Process p: arriv)
			{
	    		if(p.getBrust_time()<EXT )
	    		{
	    			EXT=p.getBrust_time();
	    			name2=p.getProcess_name();
	    		}
	    		if(p.getProcess_name()==name)currentEX=p.getBrust_time();
			}
	    	if(first)
			{
	    		name=name2;
				first=false;
			}
	    	if(EXT==currentEX)name2=name;
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
	    		time+=CST;
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
						p.setBrust_time(p.getBrust_time()-1);
						time++;
						if(p.getBrust_time()==0)
						{
							p.setComplete(time+CST);
						}
					}
				}
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
