package DesignModPackage;

/**
 * @author jinman.zhu@hand-china.com 2022-02-21 19:13
 **/
public class SingletonStatic {
	private SingletonStatic(){

	}

	private final static SingletonStatic instance = new SingletonStatic();

	public SingletonStatic getInstance(){
		return instance;
	}
}
