package DesignModPackage;

/**
 * @author jinman.zhu@hand-china.com 2022-02-22 17:10
 **/
public class InnerStaticClass {
	private static volatile InnerStaticClass instance;
	private InnerStaticClass(){}
	private static class SingletonInstance{
		private static final InnerStaticClass INSTANCE = new InnerStaticClass();
	}
	public static synchronized InnerStaticClass getInstance(){
		return SingletonInstance.INSTANCE;
	}
}
