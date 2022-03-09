package ThreadLocal;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author jinman.zhu@hand-china.com 2022-02-16 11:17
 **/
class MyTask extends RecursiveTask<Integer>
{

	@Override
	protected Integer compute() {
		return null;
	}
}
public class ForkJoinDemo {
	public static void main(String[] args) {
		MyTask myTask = new MyTask();
		ForkJoinPool forkJoinPool = new ForkJoinPool();

		forkJoinPool.submit(myTask);
		forkJoinPool.shutdown();
	}
}
