package ThreadLocal;

import jdk.nashorn.internal.ir.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author jinman.zhu@hand-china.com 2022-02-10 15:38
 **/
class MyCache{
	private volatile Map<String,Object> map = new HashMap<>();
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	public void put(String key,Object value){
		readWriteLock.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getName()+"\t ---写入数据"+key);
			try {
				TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			map.put(key,value);
			System.out.println(Thread.currentThread().getName()+"\t ---写入数据完成");
		} catch (Exception e){
			e.printStackTrace();
		}finally {
			readWriteLock.writeLock().unlock();
		}
	}

	public void get(String key){
		readWriteLock.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName()+"\t 读取数据");
			try {
				TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Object o = map.get(key);
			System.out.println(Thread.currentThread().getName()+"\t 读取完成"+o);
		} catch (Exception e){
			e.printStackTrace();
		} finally{
		  readWriteLock.readLock().unlock();
		}
	}
}
public class ReadWriteLockDemo {
	public static void main(String[] args) {
		MyCache myCache = new MyCache();
		for (int i = 0; i < 6; i++) {
			final Integer finalI = i;
			new Thread(()->{
				myCache.put(finalI.toString(),"");
			},String.valueOf(i)).start();
		}
		for (int i = 0; i < 6; i++) {
			final Integer finalI = i;
			new Thread(()->{
				myCache.get(finalI.toString());
			},String.valueOf(i)).start();
		}

	}
}
