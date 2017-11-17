import java.text.SimpleDateFormat;
import java.util.Date;

public class Cons {
	
	private int bs;
	private int maxWT;
	private int numItemsProd;
	private int sum;
	private int ID;
	
	public Cons(int buffSize, int maxWaitTime, int itemsToBeProd, int id){
		bs = buffSize;
		maxWT = maxWaitTime;
		numItemsProd = itemsToBeProd;
		sum = 0;
		ID = id;
	}
	
	public synchronized void consume() throws InterruptedException{
        while (true){
            synchronized (this){
                // consumer thread waits while list
                // is empty
                if (HW6.fifoBuff.isEmpty()){
                	Date d = new Date();
                	String date = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss.SSSSS").format(d);
                	System.out.println("Consumer " + ID + ": Unable to consume, buffer empty, at Time: " + date);
                	//long w = (long) (maxWT * Math.pow(10, 6));
                    wait(maxWT);
                }
                
                if(HW6.fifoBuff.isEmpty()){
                	//long w = (long) (maxWT * Math.pow(10, 6));
                	wait(maxWT * 14);
                	if (!Thread.currentThread().isInterrupted() && HW6.fifoBuff.isEmpty()) {
                		  // cleanup and stop execution
                		  // for example a break in a loop
                		break;
                	}
                }

                else if(!HW6.fifoBuff.isEmpty()){
                	int loc = HW6.fifoBuff.size() - 1;
                	int val = HW6.fifoBuff.get();
                	Date d = new Date();
                	String date = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss.SSSSS").format(d);

                	System.out.println("Consumer " + ID + ": Removed " + val + " from location: " + loc + " at Time: " + date);
                	sum++;

                	// Wake up producer thread
                	notifyAll();

                	// and sleep
                	Thread.sleep(1000);
                }
            }
        }
    }
	
	public void getSum(){
		System.out.println("Consumer " + ID + ": Sum of numbers consumed: " + sum);
	}
}

