public class HW6 {
	
	public static CirBuffer fifoBuff;
	public static int bufferSize;
	
	public static void main(String[] args) throws InterruptedException {
		int numOfCons = Integer.parseInt(args[0]);
		bufferSize = Integer.parseInt(args[1]);
		int numItemsProduced = Integer.parseInt(args[2]);
		int maxWaitTime = Integer.parseInt(args[3]);
		fifoBuff = new CirBuffer(bufferSize);
		
		Prod p = new Prod(bufferSize, maxWaitTime, numItemsProduced);
		// Create producer thread
        Thread t1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    p.produce();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
        
        t1.start();
        
        Cons[] totCons = new Cons[numOfCons];
        
        for(int x = 0; x < numOfCons; x++){
        	Cons c = new Cons(bufferSize, maxWaitTime, numItemsProduced, x+1);
        	totCons[x] = c;
        }


        Thread[] ta = new Thread[numOfCons];
        for(int i = 0; i < numOfCons; ++i){
        	Cons c = totCons[i];
            ta[i] = new Thread(new Runnable(){
            	public void run(){
        			try{
        				c.consume();	
        			}
        			catch(InterruptedException e)
        			{
        				e.printStackTrace();
        			}
        		}
            });
        }
        
        for(int j = 0; j < numOfCons; j++){
        	ta[j].start();
        }
 
        // t1 finishes before t2
        t1.join();
        
        for(int k = 0; k < numOfCons; k++){
        	ta[k].join();
        }
        p.getSum();
        for(int z = 0; z < numOfCons; z++){
        	totCons[z].getSum();
        }
        
	}

}

