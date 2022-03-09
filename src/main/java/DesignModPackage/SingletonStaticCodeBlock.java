package DesignModPackage;

/**
 * @author jinman.zhu@hand-china.com 2022-02-22 15:56
 **/
public class SingletonStaticCodeBlock {
	private SingletonStaticCodeBlock(){

	}
	private static SingletonStaticCodeBlock instance;

	static{
		instance = new SingletonStaticCodeBlock();
	}

	public static SingletonStaticCodeBlock getInstance(){
		return instance;

	}
}
