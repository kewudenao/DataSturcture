package Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author jinman.zhu@hand-china.com 2022-03-04 16:59
 **/
public class ProxyDemo implements InvocationHandler {

	private final  Object target;

	public ProxyDemo(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return null;
	}
}
