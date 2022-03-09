package ThreadLocal;

/**
 * @author jinman.zhu@hand-china.com 2022-01-28 16:08
 **/
public class SingletonDemo {
	private  static SingletonDemo instance = null;

	public SingletonDemo() {
		System.out.println(Thread.currentThread().getName()+"我是构造方法");
	}

	public static SingletonDemo getInstance(){
		if (instance==null){
			synchronized (SingletonDemo.class){
				if (instance==null){
					instance =new SingletonDemo();
				}
			}
		}
		return instance;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(()->{
				SingletonDemo.getInstance();
			},String.valueOf(i)).start();
		}
	}
}
