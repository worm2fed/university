package worm2fed.pirv.lab3;

import java.util.Random;
import java.util.concurrent.Semaphore;

class PC {
	public static void main(String[] args) {
		Q q = new Q();
		new Writer(q,1);
		new Writer(q,2);
		new Reader(q,1);
		new Reader(q,2);
		new Reader(q,3);
		new Reader(q,4);
		
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			System.out.println("Прерывание главного потока");
		}
		
		q.work = false;
	}
}

class Q {
	int n;
	boolean work = true;
	int Arr[] = new int[10];
	
	Semaphore sema = new Semaphore(4, true);

	void gener() {
		Random r = new Random();
		for (int i = 0; i < 10; i++) {
			Arr[i] = r.nextInt(100);
			System.out.print(Arr[i] + " ");
		}
	}

	int sr() {
		int sum = 0;
		for (int i = 0; i < 10; i++) {
			sum += Arr[i];
		}
		return sum / 10;
	}

	int read() {
		Thread t = Thread.currentThread();
		int numerOfPerm = sema.availablePermits();
		
		if (numerOfPerm < 0) 
			sema.release(3);
		
		System.out.println(t.getName()+" try...");
		
		try { 
			sema.acquire(); 
		} catch (InterruptedException e) {
		}
		
		System.out.println(t.getName()+" reading...");
		System.out.println(t.getName() + ": " + sr());
		sema.release();
		
		try { 
			Thread.sleep(10); 
		} catch (InterruptedException e) {
		}

		return n;
	}

	void write(int n) {
		Thread t = Thread.currentThread();
		int numerOfPerm = sema.availablePermits();
		
		System.out.println(t.getName() + " try...");
		
		if (numerOfPerm >= 4) {
			try {
				sema.acquire();
				sema.acquire();
				sema.acquire();
				sema.acquire(); 
			} catch (InterruptedException e) {
			}
			
			System.out.println(t.getName()+" writing...");
			
			this.n = n;
			System.out.println(t.getName() + ":");
			gener();
			System.out.println();
			
			sema.release();
			sema.release();
			sema.release();
			sema.release();
			
			try { 
				Thread.sleep(10); 
			} catch (InterruptedException e) {
			}
		}
	}
}

class Writer implements Runnable {
	Q q;

	Writer(Q q, int num) {
		this.q = q;
		new Thread(this, "Writer " + num).start();
	}

	public void run() {
		int i = 0;
		while (q.work) {
			q.write(i++);
		}
	}
}

class Reader implements Runnable {
	Q q;

	Reader(Q q, int num) {
		this.q = q;
		new Thread(this, "Reader " + num).start();
	}

	public void run() {
		while (q.work) {
			q.read();
		}
	}
}