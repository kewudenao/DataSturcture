package ThreadLocal;

import java.util.concurrent.CountDownLatch;

/**
 * @author jinman.zhu@hand-china.com 2022-02-10 10:54
 **/
public class CountDownLatchDemo {
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(6);
		for (int i = 0; i < 6; i++) {
			new Thread(()->{
				System.out.println(Thread.currentThread().getName()+"\t 离开教室");
				countDownLatch.countDown();
			},String.valueOf(i)).start();
		}
		countDownLatch.await();
		System.out.println(Thread.currentThread().getName()+"\t 班长关门走人");
	}
}
