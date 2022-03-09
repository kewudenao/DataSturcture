package reload;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author jinman.zhu@hand-china.com 2022-02-09 15:56
 **/
public class NotSafeDemo {
	public static void main(String[] args) {
		Set<String> set = new HashSet<>();

		for (int i = 0; i < 30; i++) {
			new Thread(()->{
				set.add(UUID.randomUUID().toString().substring(0,8));
				System.out.println(set);
			},String.valueOf(i)).start();
		}
	}
}
