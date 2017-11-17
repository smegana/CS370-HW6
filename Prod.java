import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.Object;
import java.text.SimpleDateFormat;


public class Prod {
	
	private int bs;
	private int maxWT;
	private int numItemsProd;
	private int sum;
	
	
	public Prod(int buffSize, int maxWaitTime, int itemsToBeProd){
		bs = buffSize;
		maxWT = maxWaitTime;
		numItemsProd = itemsToBeProd;
		sum = 0;
	}
	
	public synchronized void produce() throws InterruptedException{
		
		while (sum < numItemsProd){
            synchronized (this){
                // producer thread waits while list
                // is full
                while (HW6.fifoBuff.isFull()){
                	Date d = new Date();
                	String date = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss.SSSSS").format(d);
                	System.out.println("Producer: Unable to insert, buffer full, at instant: " + date);
                	//long w = (long) (maxWT * Math.pow(10, 6));
                    wait(maxWT);
                }

                int rand = ThreadLocalRandom.current().nextInt(0, 100);
                int pos = HW6.fifoBuff.add(rand) - 1;
                if(pos < 0){
                	pos = 0;
                }
                Date d = new Date();
            	String date = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss.SSSSS").format(d);
            	
    			System.out.println("Producer: Inserted integer " + rand + " at Location " + pos +
    					" at instant: " + date);
    			sum++;

                // notifies the consumer thread that
                // now it can start consuming
                notifyAll();

                // makes the working of program easier
                // to  understand
                wait(1000);
            }
        }
	}
	
	public void getSum(){
		System.out.println("Producer: Sum of numbers generated: " + sum);
	}
}

