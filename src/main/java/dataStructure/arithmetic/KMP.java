package dataStructure.arithmetic;
// kmp  https://blog.csdn.net/v_july_v/article/details/7041827#t10
public class KMP {
	public static void main(String[] args) {
		String s1 = "aaabadefqewabac";
		String s2 = "aaab";
		int[] next = GetNext1(s2);
		int index = kmpSearch(s1,s2,next);
		System.out.println(index);
	}

	static int[] GetNext(String T) {
		int pLen = T.length();
		char[] p = T.toCharArray();
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

	static int[] GetNext1(String P){
		int nLen = P.length();
		char[] chars = P.toCharArray();
		int[] next = new int[chars.length];
		next[0] = -1;
		int k = -1;
		int j = 0;
		while (j < nLen-1 ) {
			//p[k]表示前缀，p[j]表示后缀
			if (k == -1 || chars[j] == chars[k]) {
				++k;
				++j;
				if(next[k]!=next[j]){
					next[j] = k;
				}else
					next[j] = -1;

			} else {
				k = next[k];

			}
		}
		return next;
	}

	static int kmpSearch(String s1,String s2,int[] next){
		int i=0;
		int j=0;
		int sLen = s1.length();
		int pLen = s2.length();
		while (i<sLen&&j <pLen){
			if (j==-1||s1.charAt(i)==s2.charAt(j)){
				i++;
				j++;
			}else {
				j = next[j];
			}
		}
		if(j == pLen)
			return i - j;
		else
			return  -1;
	}


}



