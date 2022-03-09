package ThreadLocal;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author jinman.zhu@hand-china.com 2022-02-09 09:39
 **/
class Phone{
	public synchronized void sendEmail()throws Exception{
		System.out.println("----邮件----");
	}

	public synchronized void sendEMS()throws Exception{
		System.out.println("----短信----");
	}

}
public class Lock8 {
	public static void main(String[] args) throws Exception{
		CopyOnWriteArrayList arrayList = new CopyOnWriteArrayList();
		arrayList.add("1");
		Phone phone = new Phone();
		new Thread(()->{
			try {
				phone.sendEmail();
			} catch (Exception e) {
				e.printStackTrace();
			}
		},"A").start();

		Thread.sleep(100);

		new Thread(()->{
			try {
				phone.sendEMS();
			} catch (Exception e) {
				e.printStackTrace();
			}
		},"B").start();
	}
}
