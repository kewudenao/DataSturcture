package ThreadLocal;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author jinman.zhu@hand-china.com 2022-02-10 14:21
 **/
public class SemaphoreDemo {


	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(3);
		for (int i = 0; i < 6; i++) {
			new Thread(()->{
				try {
					semaphore.acquire();
					System.out.println(Thread.currentThread().getName()+"\t 抢到了车位");

					try {
						TimeUnit.SECONDS.sleep(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()+"\t 离开了车位");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					semaphore.release();
				}
			},String.valueOf(i)).start();
		}
	}
}
