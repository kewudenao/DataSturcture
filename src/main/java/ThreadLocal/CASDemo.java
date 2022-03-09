package ThreadLocal;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jinman.zhu@hand-china.com 2022-01-28 17:20
 **/
public class CASDemo {
	public static void main(String[] args) {
		AtomicInteger atomicInteger = new AtomicInteger(2);

		System.out.println(atomicInteger.compareAndSet(2,22)+"当前数据："+atomicInteger.get());
		System.out.println(atomicInteger.compareAndSet(1,23)+"当前数据："+atomicInteger.get());
	}
}
