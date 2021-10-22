package dataStructure.linerList;


import java.util.ArrayList;
import java.util.Collection;

public class Linear extends ArrayList {
	int maxsize;

	public Linear(int initialCapacity, int maxsize) {
		super(initialCapacity);
		this.maxsize = maxsize;
	}

	public Linear(int maxsize) {
		this.maxsize = maxsize;
	}

	public Linear(Collection c, int maxsize) {
		super(c);
		this.maxsize = maxsize;
	}

	public int getMaxsize() {
		return maxsize;
	}

	public void setMaxsize(int maxsize) {
		this.maxsize = maxsize;
	}
}
