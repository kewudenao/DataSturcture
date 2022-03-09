package ThreadLocal;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jinman.zhu@hand-china.com 2021-12-27 17:22
 **/
public class MyCallable implements Callable<String> {

	public String call() throws Exception {
		Thread.sleep(1000);
		CopyOnWriteArrayList a = new CopyOnWriteArrayList();
		a.get(1);
		a.add(1);
		ConcurrentLinkedQueue b = new ConcurrentLinkedQueue();
//		AtomicInteger
		return Thread.currentThread().getName();
	}
}
