package Map;

import java.util.*;

/**
 * @author jinman.zhu@hand-china.com 2022-03-06 14:12
 **/
public class MapDemo {
	private Integer age;

	public MapDemo(Integer age) {
		this.age = age;
	}

	public Integer getAge() {
		return age;
	}


	public static void main(String[] args) {
		TreeMap<MapDemo,String> treeMap = new TreeMap<>((key1,key2)->{
			int num = key1.getAge()-key2.getAge();
			ArrayList<Integer> arrayList = new ArrayList<>();
			Stack<Integer> a = new Stack<>();
			LinkedList b = new LinkedList();



			return  Integer.compare(num,0);
		});
	}
}
