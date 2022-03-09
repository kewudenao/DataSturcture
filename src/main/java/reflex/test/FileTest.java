package reflex.test;

import reflex.entity.Fruit;
import reflex.factory.FileDynamicFactory;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class FileTest {
	public static void main(String[] args) throws IOException {
		Properties properties = FileDynamicFactory.getPro();
		Fruit fruit = FileDynamicFactory.getInstance(properties.getProperty("apple"));
		if (fruit!=null){
			fruit.eat();
		}
		String a = "asdfasdf";
		a = "sadfasdf";
		System.out.println(a);

	}
}
