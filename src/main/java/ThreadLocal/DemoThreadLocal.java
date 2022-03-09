package ThreadLocal;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * @author jinman.zhu@hand-china.com 2022-01-14 16:26
 **/
public class DemoThreadLocal implements Runnable{

	private static final ThreadLocal<SimpleDateFormat> formatter = ThreadLocal.withInitial(()->new SimpleDateFormat("yyyyMMdd HHmm"));

	public static void main(String[] args) throws InterruptedException {
		DemoThreadLocal obj = new DemoThreadLocal();
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(obj,""+i);
			Thread.sleep(new Random().nextInt(1000));
			t.start();
		}
		ThreadPoolExecutorDemo s = new ThreadPoolExecutorDemo();

	}

	public void run() {
		System.out.println("Thread Name= "+Thread.currentThread().getName()+" default Formatter = "+formatter.get().toPattern());
		try {
			Thread.sleep(new Random().nextInt(1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//formatter pattern is changed here by thread, but it won't reflect to other threads
		formatter.set(new SimpleDateFormat());

		System.out.println("Thread Name= "+Thread.currentThread().getName()+" formatter = "+formatter.get().toPattern());

	}
}
