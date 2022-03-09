package ThreadLocal;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author jinman.zhu@hand-china.com 2022-02-11 10:01
 **/
public class BlockingQueueDemo {
	public static void main(String[] args) {
		BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

		blockingQueue.add("A");
		blockingQueue.add("A");
		blockingQueue.add("A");

		blockingQueue.remove();
		blockingQueue.remove();
		blockingQueue.remove();
		blockingQueue.remove();
	}
}
