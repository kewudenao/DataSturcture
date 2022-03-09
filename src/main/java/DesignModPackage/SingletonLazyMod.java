package DesignModPackage;

/**
 * @author jinman.zhu@hand-china.com 2022-02-22 16:24
 **/
public class SingletonLazyMod {
	private static SingletonLazyMod instance;
	private SingletonLazyMod(){

	}
/**	public static SingletonLazyMod getInstance(){
		if(instance == null){
			instance = new SingletonLazyMod();
		}
		return instance;
 }   **/
	public static synchronized SingletonLazyMod getInstance(){
		if(instance == null){
			instance = new SingletonLazyMod();
		}
		return instance;
	}
}
