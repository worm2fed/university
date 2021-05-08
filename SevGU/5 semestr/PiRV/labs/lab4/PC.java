package worm2fed.pirv.lab4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;

class PC {
	public static void main(String args[]) {
		Q q = new Q();
		new Generator(q, 0);
		new Worker(q, 1);
		new Worker(q, 2);
		new Worker(q, 3);
		new Worker(q, 4);
		new Worker(q, 5);

		try {
			q.lock.lock();
			q.G.signal(); // запускаем генератор

		} finally {
			q.lock.unlock();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		
		q.work = false;
	}
}

class Q {
	int n;
	boolean work = true;
	final ReentrantLock lock = new ReentrantLock();
	final Condition G = lock.newCondition();
	final Condition W1 = lock.newCondition();
	final Condition W2 = lock.newCondition();
	final Condition W3 = lock.newCondition();
	final Condition W4 = lock.newCondition();
	final Condition W5 = lock.newCondition();
	int A1024[];
	int A512[] = new int[512];
	int A256[] = new int[256];
	int A128[] = new int[128];
	int A64[] = new int[64];
	int A32[] = new int[32];

}

class Generator implements Runnable {
	Q q;
	int num;

	Generator(Q q, int num) {
		this.q = q;
		this.num = num;
		new Thread(this, "Generator " + num).start();
	}

	public void run() {
		Thread t = Thread.currentThread();
		
		while (q.work) {
			q.lock.lock();
			try {
				try {
					q.G.await();
				} catch (InterruptedException e) {
				}
			} finally {
				q.lock.unlock();
			}
			
			System.out.println();
			System.out.println(t.getName() + " start");
			q.A1024 = gener();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			
			System.out.println();
			System.out.println(t.getName() + " stop");
			q.lock.lock();
			
			try {
				q.W1.signal();
			} finally {
				q.lock.unlock();
			}
		}
	}

	int[] gener() {
		int Rand[] = new int[1024];
		Random r = new Random();
		for (int i = 0; i < Rand.length; i++) {
			Rand[i] = r.nextInt(100);
			System.out.print(Rand[i] + " ");
		}
		return Rand;
	}
}

class Worker implements Runnable {
	Q q;
	int num;

	Worker(Q q, int num) {
		this.q = q;
		this.num = num;
		new Thread(this, "Worker " + num).start();
	}

	public void run() {
		Thread t = Thread.currentThread();
		
		while (q.work) {
			switch (num) {
			case 1: { // Worker 1 ожидает сигнала
				q.lock.lock();
				try {
					try {
						q.W1.await();
					} catch (InterruptedException e) {
					}
				} finally {
					q.lock.unlock();
				}
				break;
			}

			case 2: { // Worker 2 ожидает сигнала
				q.lock.lock();
				try {
					try {
						q.W2.await();
					} catch (InterruptedException e) {
					}
				} finally {
					q.lock.unlock();
				}
				break;
			}

			case 3: { // Worker 3 ожидает сигнала
				q.lock.lock();
				try {
					try {
						q.W3.await();
					} catch (InterruptedException e) {
					}
				} finally {
					q.lock.unlock();
				}
				break;
			}

			case 4: { // Worker 4 ожидает сигнала
				q.lock.lock();
				try {
					try {
						q.W4.await();
					} catch (InterruptedException e) {
					}
				} finally {
					q.lock.unlock();
				}
				break;
			}
			case 5: { // Worker 5 ожидает сигнала
				q.lock.lock();
				try {
					try {
						q.W5.await();
					} catch (InterruptedException e) {
					}
				} finally {
					q.lock.unlock();
				}
				break;
			}
			}

			System.out.println();
			System.out.println(t.getName() + " start");

			switch (num) {
			case 1:
				q.A512 = svertka(q.A1024, q.A512);
				print(q.A512);
				break;
			case 2:
				q.A256 = svertka(q.A512, q.A256);
				print(q.A256);
				break;
			case 3:
				q.A128 = svertka(q.A256, q.A128);
				print(q.A128);
				break;
			case 4:
				q.A64 = svertka(q.A128, q.A64);
				print(q.A64);
				break;
			case 5:
				q.A32 = svertka(q.A64, q.A32);
				print(q.A32);
				break;

			}

			// здесь должны быть операторы обработки данных
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			
			System.out.println("\n" + t.getName() + " stop\n");

			switch (num) {
			case 1: { // Worker 1 будит следующие потоки
				q.lock.lock();
				try {
					q.W2.signal();
					q.G.signal();
				} finally {
					q.lock.unlock();
				}
				break;
			}
			case 2: { // Worker 2 будит следующие потоки
				q.lock.lock();
				try {
					q.W1.signal();
					q.W3.signal();
				} finally {
					q.lock.unlock();
				}
				break;
			}

			case 3: { // Worker 3 будит следующий поток
				q.lock.lock();
				try {
					q.W2.signal();
					q.W4.signal();
				} finally {
					q.lock.unlock();
				}
				break;
			}
			case 4: { // Worker 4 будит следующий поток
				q.lock.lock();
				try {
					q.W3.signal();
					q.W5.signal();
				} finally {
					q.lock.unlock();
				}
				break;
			}
			case 5: { // Worker 5 будит следующий поток
				q.lock.lock();
				try {
					q.W4.signal();

				} finally {
					q.lock.unlock();
				}
				break;
			}
			}
		}
	}

	int[] svertka(int Array1[], int Array2[]) {
		for (int j = 0; j < Array2.length; j++) 
			Array2[j] = max(Array1[j * 2], Array1[j * 2 + 1]);

		return Array2;
	}

	int max(int a, int b) {
		int mx = a;
		
		if (a > b)
			mx = a;
		else
			mx = b;
		
		return mx;
	}

	void print(int Array[]) {
		System.out.println();
		
		for (int i = 0; i < Array.length; i++)
			System.out.print(Array[i] + " ");
	}

}