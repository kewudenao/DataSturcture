package reflex.entity.impl;

import reflex.entity.Fruit;

import java.io.Serializable;

public class Apple implements Fruit, Serializable {

	public void eat() {
		System.out.println("Apple");
	}
}
