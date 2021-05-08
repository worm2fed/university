package worm2fed.pirv.lab1;

class ThreadTest implements Runnable {
	ThreadTest() {
		Thread t1 = new Thread(this, "Thread 1");
		System.out.println("Thread created: " + t1);
		//t1.setPriority(Thread.MAX_PRIORITY);
		t1.start();
		
		Thread t2 = new Thread(this, "Thread 2");
		System.out.println("Thread created: " + t2);
		//t2.setPriority(Thread.MIN_PRIORITY);
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			System.out.println("interrupted");
		}
		
		System.out.println("exiting main thread");
	}

	public void run() {
		Thread t = Thread.currentThread();
		
		for (int j = 0; j < 50; j++) {
			System.out.println(t.getName());
			
			Thread.yield();
			
			for (int k = 0; k < 1000000; k++) {

				
				if (t.getName().equals("Thread 1")) {
					int fib[] = new int[15];
					fib[0] = fib[1] = 1;
					float avr = 2;
					//System.out.println("fib1 is: 1");
					//System.out.println("fib2 is: 1");
					
					for (int i = 2; i < 14; i++) {
						fib[i] = fib[i-1] + fib[i-2];
						avr += fib[i];
						//System.out.println("fib" +(i+1)+ " is: " +fib[i]);
					}
					
					avr /= 15;
					//System.out.println("Avarege fib is: " + avr);
				} else {
					int n = 6;
					int fact = 1;
					int num = n;
					
					while (num > 0) {
						fact *= num;
						num--;
						
						//System.out.println("Counting " +n+ " factorial...");
					}
					
					if (n == 0)
						fact = 1;
					
					//System.out.println("Factorial is: " +fact);
				}
			}
		}
		
		System.out.println("exiting " + t.getName());
	}

	public static void main(String args[]) {
		new ThreadTest();
	}
}