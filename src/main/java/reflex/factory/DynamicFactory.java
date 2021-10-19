package reflex.factory;

import reflex.entity.Fruit;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class DynamicFactory {
	public static Fruit getInstance(String className)  {
		Fruit f = null;
		try {
			f = (Fruit)Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;


	}
}
