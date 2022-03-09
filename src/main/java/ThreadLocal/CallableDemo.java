package ThreadLocal;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author jinman.zhu@hand-china.com 2022-02-10 09:59
 **/
class MyThread implements Callable<Integer>{

	@Override
	public Integer call() throws Exception {
		System.out.println("come in here");
		return 1111;
	}
}

public class CallableDemo {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		FutureTask futureTask = new FutureTask(new MyThread());
		new Thread(futureTask,"A").start();
		System.out.println(futureTask.get());
	}

}
