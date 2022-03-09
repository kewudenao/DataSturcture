package Generic;

/**
 * @author jinman.zhu@hand-china.com 2022-03-04 11:28
 **/
public interface Generator<T> {
	public T method();
}

class GeneratorImpl implements Generator<String>{

	@Override
	public String method() {
		return null;
	}
}