package schedule;

import java.util.Comparator;
public class process 
{
	int processid;
	int bt;
	int at,prio;
	
	process(int processid,int bt, int at)
	{
		this.processid=processid;
		this.bt=bt;
		this.at=at;
	}
	process(int processid,int bt,int at,int prio)
	{
		this.processid=processid;
		this.bt=bt;
		this.at=at;
		this.prio=prio;
	}
}
class sortByPriority implements Comparator<process>
{
	public int compare(process p1,process p2)
	{
		return p1.prio-p2.prio;
	}
}
class sortByArrival implements Comparator<process>
{
	public int compare(process p1,process p2)
	{
		return p1.at-p2.at;
	}
}
class sortByBurst implements Comparator<process>
{
	public int compare(process p1,process p2)
	{
		return p1.bt-p2.bt;
	}
}