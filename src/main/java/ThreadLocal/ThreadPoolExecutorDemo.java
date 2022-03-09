package ThreadLocal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author jinman.zhu@hand-china.com 2022-01-24 16:55
 **/
public class ThreadPoolExecutorDemo {
	private static final int CORE_POOL_SIZE = 5;
	private static final int MAX_POOL_SIZE = 10;
	private static final int QUEUE_CAPACITY = 100;
	private static final Long KEEP_ALIVE_TIME = 1L;

	public static void main(String[] args) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(
				CORE_POOL_SIZE,
				MAX_POOL_SIZE,
				KEEP_ALIVE_TIME,
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<>(QUEUE_CAPACITY),
				new ThreadPoolExecutor.AbortPolicy()
		);
		List<Future<String>> futureList = new ArrayList<>();
		Callable<String> callable = new MyCallable();
		for (int i = 0 ;i< 10 ;i ++){
			Future<String> future = executor.submit(callable);
			futureList.add(future);
		}
		for (Future<String> future : futureList) {
			try {
				System.out.println(new Date() + "::" + future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}

		}
		//关闭线程池
		executor.shutdown();
	}
}
