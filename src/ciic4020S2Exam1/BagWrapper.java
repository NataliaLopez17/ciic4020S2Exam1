package ciic4020S2Exam1;
public class BagWrapper {

	public static interface Bag {
		public int size();
		public boolean isEmpty();
		public void add(Object e);
		public boolean isMember(Object e);
		public boolean remove(Object e);
		public int removeAll(Object e);
		public int count(Object e);
		public void clear();		
		public Object[] toArray();
	}
	
	public static class DynamicBag implements Bag{

	    private Object[] elements;
	    private int currentSize;
	    private static final int DEFAULT_SIZE = 10;
	    
	    public DynamicBag(int initialSize) {
	            if (initialSize < 1) {
	                    throw new IllegalArgumentException("Size must be at least 1");
	            }
	            this.elements = new Object[initialSize];
	            this.currentSize = 0;
	    }
	    
	    public DynamicBag() {
	            this(DEFAULT_SIZE);
	    }

		@Override
		public int size() {
			return this.currentSize;
		}

		@Override
		public boolean isEmpty() {
			return this.size() == 0;
		}

		@Override
		public void add(Object e) {
			if (e == null) {
				throw new IllegalArgumentException("Argument cannot be null");
			}
			if (this.size() == this.elements.length) {
				this.reAllocate();
			}
			this.elements[this.currentSize++] = e;
		}

		private void reAllocate() {
			Object temp[] = new Object[2*this.size()];
			for (int i=0; i < this.size(); ++i) {
				temp[i] = this.elements[i];
	 		}
			this.elements = temp;
		}

		@Override
		public boolean isMember(Object e) {
			return this.count(e) > 0;
		}

		@Override
		public boolean remove(Object e) {
			for (int i=0; i < this.size(); ++i) {
				if (this.elements[i].equals(e)) {
					this.elements[i] = this.elements[this.currentSize-1];
					this.elements[this.currentSize-1] = null;
					--this.currentSize;
					return true;
				}
			}
			return false;
		}

		@Override
		public int removeAll(Object e) {
			int result = 0;
			while(this.remove(e)) {
				result++;
			}
			return result;
		}

		@Override
		public int count(Object e) {
			int result = 0;
			for (int i=0; i < this.size(); ++i) {
				if (this.elements[i].equals(e)) {
					result++;
				}
			}
			return result;
		}

		@Override
		public void clear() {
			for (int i=0; i < this.size(); ++i) {
				this.elements[i] = null;
			}
			this.currentSize = 0;		
		}
	
		public Object[] toArray() {
			Object[] result = new Object[this.size()];
			for (int i=0; i < this.size(); ++i) {
				result[i]  = this.elements[i];
			}
			return result;
		}
		
	}
	
	// NON-MEMBER METHOD
	/*
	 * Write a non-member method named bagScaler, which removes from a Bag all elements that occur less than N times . The method returns the number of copies removed.

For example, if B = {Bob, Joe, Bob, Ned, Bob, Ned, Kim}, then calling bagScaler(B, 3) removes Joe, Ned, and Kim making B = {Bob, Bob, Bob}, and returns 4 because it removed 2 copies of Ned, one of Joe, and one of Kim.

Note: There's no iterator, but there is a toArray method.
	 */
	public static int bagScaler(Bag B, int n) {
		int counter = 0;
		Object[] result = B.toArray();
		for(int i = 0; i < B.size(); i++) {
			if(B.count(result[i]) < n+1) {
				B.remove(result[i]);
				counter++;
			}
		}
		System.out.println(counter);
		return counter;
	
	}
}