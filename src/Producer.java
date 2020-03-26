import java.util.Random;

public class Producer extends Thread {
	
	public Producer() {
		start();
	}
	
	private void produce() {
		
		Random rdmNum = new Random();
		int numP = rdmNum.nextInt(999) + 1;
		int sleepTime = rdmNum.nextInt(250 -25 +1) +25;
		
		try {
			sleep(sleepTime);
		}
		catch (InterruptedException exc) {
			exc.printStackTrace();
		}
		
		System.out.println("Producer: Number "+ numP +"produced.");
		
		Buffer.getStore().add(numP);
	}
	
	@Override
	public void run() {
		
		while(true) {
			if(Buffer.getStore().size()==Buffer.bSize) {
				System.out.println("Producer: buffer is full, waiting for consumer to work.");
			}
			
			try {
				Buffer.getsNoLleno().acquire();
			}
			catch (InterruptedException exc) {
				exc.printStackTrace();
			}
			
			produce();
			
			Buffer.getsNoVacio().release();
		}
	}
	
}
