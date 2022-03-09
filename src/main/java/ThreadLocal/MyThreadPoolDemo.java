package ThreadLocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jinman.zhu@hand-china.com 2022-02-11 11:25
 **/
public class MyThreadPoolDemo {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		ExecutorService executorService1 = Executors.newCachedThreadPool();
		ExecutorService executorService2 = Executors.newSingleThreadExecutor();


	}
}
