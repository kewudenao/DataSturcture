package reflex.factory;

import reflex.entity.Fruit;

import java.io.*;
import java.util.Properties;

public class FileDynamicFactory {
	public static Properties getPro() throws IOException {
		Properties properties = new Properties();
		File f = new File("fruit.properties");
		if (f.exists()){
			properties.load(new FileInputStream(f));
		}else {
//			properties.setProperty("apple","reflex.entity.impl.Apple");
//			properties.setProperty("orange","reflex.entity.impl.Orange");
//			properties.store(new FileOutputStream(f),"FRUIT CLASS");

		}
		return properties;
	}

	public static Fruit getInstance(String ClassName){
		Fruit f=null;
		try{
			f=(Fruit)Class.forName(ClassName).newInstance();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

}
