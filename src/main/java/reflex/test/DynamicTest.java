package reflex.test;

import reflex.entity.Fruit;
import reflex.entity.impl.Apple;
import reflex.factory.DynamicFactory;

public class DynamicTest {
	public static void main(String[] args) {
		Fruit f = DynamicFactory.getInstance("reflex.entity.impl.Apple");
		if (f!=null){
			f.eat();

		}
	}
}
