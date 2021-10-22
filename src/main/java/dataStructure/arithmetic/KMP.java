package dataStructure.arithmetic;

public class KMP {
	void getNext(String T,int[] next ){

	}

	void  get_next(String  S,  int [] next) {
		int  i,  j;
		i  =  1;
		j  =  0;
		char[] T = S.toCharArray();
		next[0]  =  0;
		/*  此处T[0]表示串T的长度  */
		while  (i  <  T.length)
		{/*  T[i]表示后缀的单个字符，  */
			/*
		 T[j]表示前缀的单个字符  */
			if  (j  ==  0  ||  T[i]  ==  T[j])
			{
				++i;
				++j;
				next[i]  =  j;
			}else/*  若字符不相同，则j值回溯  */
				j  =  next[j];
		}
	}
	}



