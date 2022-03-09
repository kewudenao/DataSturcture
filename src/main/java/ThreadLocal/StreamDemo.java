package ThreadLocal;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author jinman.zhu@hand-china.com 2022-02-11 17:28
 **/
@AllArgsConstructor
@NoArgsConstructor

class User{
	int age;
}
public class StreamDemo {
	public static void main(String[] args) {

		Function<String,Integer> function = new Function<String, Integer>() {
			@Override
			public Integer apply(String s) {
				return 1;
			}
		};
		Predicate<String> predicate = new Predicate<String>() {
			@Override
			public boolean test(String s) {
				return s.isEmpty();
			}
		};
		function.apply("a");

		Function<String,Integer> functionO = s -> {return s.length();};

		Predicate<String> function1 = s -> {return s.length()>8;};

		Consumer<String> consumer = new Consumer<String>() {
			@Override
			public void accept(String s) {

			}
		};
		Supplier<String> supplier = new Supplier<String>() {
			@Override
			public String get() {
				return null;
			}
		};
	}
}
