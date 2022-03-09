package DesignModPackage;

/**
 * @author jinman.zhu@hand-china.com 2022-02-22 16:50
 **/
public class DoubleCheck {
	private static volatile DoubleCheck instance;
	private DoubleCheck(){
	}
	public static DoubleCheck getInstance(){
		if (instance==null){
			synchronized (DoubleCheck.class){
				if (instance==null) {
					instance = new DoubleCheck();
				}
			}
		}
		return instance;
	}
}
