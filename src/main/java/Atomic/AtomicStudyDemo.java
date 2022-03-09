package Atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicStudyDemo {
	void demo(){
		AtomicInteger atomicInteger = new AtomicInteger();
		atomicInteger.get();
		atomicInteger.incrementAndGet();
	}
}
