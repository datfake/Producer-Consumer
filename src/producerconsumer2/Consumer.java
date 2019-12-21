package producerconsumer2;

import java.util.Random;

public class Consumer extends Thread {
	private Kho queue;
	private boolean isRunning = true;
	private int name;
	private int sleepTime;

	public Consumer(Kho queue,int name,int sleepTime) {
		this.queue = queue;
		this.name = name;
		this.sleepTime = sleepTime;
	}
	public void pause() {
		isRunning = false;
	}

	@Override
	public void run() {
		while (isRunning) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Random rd = new Random();
			int n = rd.nextInt(queue.getBound()/2) + 1;
			queue.get(n,name);
			
		}
	}

}
