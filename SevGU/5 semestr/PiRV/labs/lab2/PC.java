package worm2fed.pirv.lab2;

import java.util.concurrent.Semaphore;

class PC {
	public static void main(String args[]) {
		Q q = new Q();
		new Producer(q);
		new Consumer(q);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
	
		q.work = false;
	}
}

class Q {
	long n;
	boolean work = true;
	Semaphore emp = new Semaphore(1, true);
	Semaphore ful = new Semaphore(0, true);

	long get() {
		try {
			ful.acquire();
		} catch (InterruptedException e) {
		}
	
		System.out.println("Got: " + n);
		emp.release();
		return n;
	}

	void put(long n) {
		try {
			emp.acquire();
		} catch (InterruptedException e) {
		}
		
		this.n = n;
		System.out.println("Put: " + n);
		ful.release();
	}
}

class Producer implements Runnable {
	long arr[] = new long[1000000];
	Q q;

	Producer(Q q) {
		this.q = q;
		new Thread(this, "Producer").start();
	}

	public void run() {
		for (int sh = 0; sh < 1000000; sh++)
			arr[sh] = sh * sh;
	
		int i = 0;

		while (q.work) {
			q.put(arr[i]);

			i++;
		}
	}
}

class Consumer implements Runnable {
	Q q;

	Consumer(Q q) {
		this.q = q;
		new Thread(this, "Consumer").start();
	}

	public void run() {
		int i = 0;
		long sum = 0;
		long mas[] = new long[5];
		while (q.work) {
			mas[i] = q.get();
			i++;
			if (i == 5) {
				i = 0;
				while (i != 5) {
					sum += mas[i];
					i++;
				}
				i = 0;
				System.out.println("Sum: " + sum);
				System.out.println("___________");
			}
			sum = 0;
		}
	}
}