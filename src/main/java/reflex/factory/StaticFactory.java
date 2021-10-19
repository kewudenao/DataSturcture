package reflex.factory;

import reflex.entity.Fruit;
import reflex.entity.impl.Apple;
import reflex.entity.impl.Orange;

/**
 * @author Administrator
 *
 */
public class StaticFactory {
	public static Fruit getInstance(String fruitName){
		Fruit f = null;
		if ("Apple".equals(fruitName)){
			f = new Apple();
		}
		if ("Orange".equals(fruitName)){
			f = new Orange();
		}
		return f;
	}
}
