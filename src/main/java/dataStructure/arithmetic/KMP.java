package dataStructure.arithmetic;
// kmp  https://blog.csdn.net/v_july_v/article/details/7041827#t10
public class KMP {
	public static void main(String[] args) {

		int[] next = GetNext("abcdef");
		System.out.println(next);
	}

	static int[] GetNext(String p1) {
		int pLen = p1.length();
		char[] p = p1.toCharArray();
		int[] next = new int[p.length];
		next[0] = -1;
		int k = -1;
		int j = 0;
		while (j < pLen-1 ) {
			//p[k]表示前缀，p[j]表示后缀
			if (k == -1 || p[j] == p[k]) {
				++k;
				++j;
				next[j] = k;
			} else {
				k = next[k];
			}
		}
		return next;
	}
}



