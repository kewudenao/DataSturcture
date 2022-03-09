package ThreadLocal;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author jinman.zhu@hand-china.com 2022-02-15 13:41
 **/
public class CompletableFutureDemo {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		CompletableFuture<Integer> completableFuture =
				CompletableFuture.supplyAsync(()->{
					System.out.println(Thread.currentThread().getName());
					return 1;
		});
		completableFuture.whenComplete((t,u)->{
			System.out.println("**********+a"+t);
			System.out.println("**********+a"+u);
		}).exceptionally(f -> {
			System.out.println("asdfasf");
			return 444;
		}).get();
	}
}
