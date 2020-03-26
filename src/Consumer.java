import java.util.Random;

public class Consumer extends Thread{

	public Consumer() {
		start();
	}
	
	private void consume() {
		
		Random rdmNum = new Random();
		int sleepTime = rdmNum.nextInt(250-25+1) + 25;
		
		try {
			sleep(sleepTime);
		}
		catch (InterruptedException exc) {
			exc.printStackTrace();
		}
		
		int consumedNumber = Buffer.getStore().poll();
		System.out.println("Consumer: Number "+consumedNumber+"consumed.");
	}
	
	public void run() {
		
		while(true) {
			if(Buffer.getStore().size()==0) {
				System.out.println("Consumer: buffer is empty, waiting for producer to work.");
			}

			try {
				Buffer.getsNoVacio().acquire();
			}
			catch (InterruptedException exc) {
				exc.printStackTrace();
			}
			
			consume();
			
			Buffer.getsNoLleno().release();
		}
		
	}
}