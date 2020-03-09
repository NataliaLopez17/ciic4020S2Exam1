package ciic4020S2Exam1;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedListWrapper {
	public static interface List<E> extends Iterable<E> {
		
		public int size();
		public boolean isEmpty();
		public boolean isMember(E e);
		public int firstIndexOf(E e);
		public int lastIndexOf(E e);
		public void add(E e);
		public void add(E e, int index);
		public E get(int index);
		public E remove(int index);
	    public boolean remove(E e);
	    public int removeAll(E e);
		public E replace(int index, E newElement);
		public void clear();
		public Object[] toArray();
		public boolean isPalindrome();
	}
	
	public static class DoublyLinkedList<E> implements List<E> {

		@SuppressWarnings("hiding")
		private class DoublyLinkedListIterator<E> implements Iterator<E>{
			private Node<E> nextNode;
			@SuppressWarnings("unchecked")
			public DoublyLinkedListIterator() {
				this.nextNode = (Node<E>) header.getNext();
			}
			@Override
			public boolean hasNext() {
				return nextNode != trailer;
			}
			@Override
			public E next() {
				if (this.hasNext()) {
					E result = this.nextNode.getValue();
					this.nextNode = this.nextNode.getNext();
					return result;
				}
				else {
					throw new NoSuchElementException();
				}
			}
		}

		private static class Node<E> {
			private E value;
			private Node<E> next, prev;
			
			public Node(E value, Node<E> next, Node<E> prev) {
				super();
				this.value = value;
				this.next = next;
				this.prev = prev;
			}
			public Node() {
				this(null, null, null);
			}
			public E getValue() {
				return value;
			}
			public void setValue(E value) {
				this.value = value;
			}
			public Node<E> getNext() {
				return next;
			}
			public void setNext(Node<E> next) {
				this.next = next;
			}
			public Node<E> getPrev() {
				return prev;
			}
			public void setPrev(Node<E> prev) {
				this.prev = prev;
			}
		}

		private Node<E> header, trailer;
		private int currentSize;
		
		public DoublyLinkedList() {
			this.header = new Node<>();
			this.trailer = new Node<>(null, null, header);
			header.setNext(trailer);
			this.currentSize = 0;
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
		public boolean isMember(E e) {
			return this.firstIndexOf(e) >= 0;
		}

		@Override
		public int firstIndexOf(E e) {
			int i = 0;
			for (Node<E> temp = this.header.getNext(); temp != trailer; 
					temp = temp.getNext(), ++i) {
				if (temp.getValue().equals(e)) {
					return i;
				}
			}
			// not found
			return -1;
		}

		@Override
		public void add(E e) {
			Node<E> newNode = new Node<E>(e, trailer, trailer.getPrev());
			trailer.getPrev().setNext(newNode);
			trailer.setPrev(newNode);
			currentSize++;
		}

		@Override
		public void add(E e, int index) {
			if ((index < 0) || (index > this.currentSize)) {
				throw new IndexOutOfBoundsException();
			}
			if (index == this.currentSize) {
				this.add(e);
			}
			else {
				Node<E> temp = null;
				if (index == 0) {
					temp = this.header;
				}
				else {
					temp = this.getPosition(index -1);
				}
				Node<E> newNode = new Node<>(e, temp.getNext(), temp);
				temp.getNext().setPrev(newNode);
				temp.setNext(newNode);
				this.currentSize++;
			}
		}

		@Override
		public E get(int position) {
			if ((position < 0) || position >= this.currentSize) {
				throw new IndexOutOfBoundsException();
			}
			
			Node<E> temp  = this.getPosition(position);
			return temp.getValue();
		}

		private Node<E> getPosition(int index){
			int currentPosition=0;
			Node<E> temp = this.header.getNext();
			
			while(currentPosition != index) {
				temp = temp.getNext();
				currentPosition++;
			}
			return temp;
		}
		
		@Override
		public E remove(int index) {
			if ((index < 0) || (index >= this.currentSize)){
				throw new IndexOutOfBoundsException();
			}
			else {
				Node<E> curNode = this.header.getNext();
				
				for (int pos = 0; pos < index; pos++)
					curNode = curNode.getNext();
				E result = curNode.getValue();
				curNode.getPrev().setNext(curNode.getNext());
				curNode.getNext().setPrev(curNode.getPrev());
				curNode.setValue(null);
				curNode.setNext(null);
				curNode.setPrev(null);
				this.currentSize--;
				return result;
			}
		}

		@Override
		public E replace(int position, E newValue) {
			if ((position < 0) || position >= this.currentSize) {
				throw new IndexOutOfBoundsException();
			}
			Node<E> temp  = this.getPosition(position);
			E result = temp.getValue();
			temp.setValue(newValue);
			return result;
		}

		@Override
		public void clear() {
			while(!this.isEmpty()) {
				this.remove(0);
			}
		}

		@Override
		public Object[] toArray() {
			Object[] result = new Object[this.size()];
			for (int i=0; i < this.size(); ++i) {
				result[i] = this.get(i);
			}
			return result;
		}

		@Override
		public Iterator<E> iterator() {
			return new DoublyLinkedListIterator<E>();
		}

		@Override
		public int lastIndexOf(E e) {
			int i = size() - 1;
			for (Node<E> temp = this.trailer.getPrev(); temp != header; 
					temp = temp.getPrev(), --i) {
				if (temp.getValue().equals(e)) {
					return i;
				}
			}
			// not found
			return -1;
		}

		@Override
		public boolean remove(E e) {
			int i = this.firstIndexOf(e);
			if (i < 0) {
				return false;
			}else {
				this.remove(i);
				return true;
			}
		}

		@Override
		public int removeAll(E e) {
			int count = 0;
			while (this.remove(e)) {
				count++;
			}
			return count;
		}

		/*
		 * Consider a member method of the List ADT named isPalindrome. 
		 * This method determines whether the elements in the list form a palindrome. 
		 * That is, whether you get the same result if you read the elements moving forward 
		 * as moving backwards. If the current list is empty, it should return true. 
		 * Implement this method for the DoublyLinkedList class.
		 * The following lists examples of palindromes: 
		 * {}, {Ken}, {Ken, Ken}, {Ken, Bob, Ken}, {Ken, Bob, Bob, Ken}, 
		 * {Ken, Bob, Ron, Bob, Ken}, {1, 3, 5, 7, 5, 3, 1}.
		 * This doubly linked list has a dummy header and a dummy trailer.
		 */
		@Override
		public boolean isPalindrome() {
			Node<E> curNode = this.header;
			Node<E> nextNode = curNode.getNext();
			Node<E> tailNode = this.trailer;
			Node<E> prevNode = tailNode.getPrev();
			
			if(this.isEmpty()) {
				return true;
			}
			while(nextNode != tailNode) {
				if(nextNode.getValue().equals(prevNode.getValue())) {
					curNode = curNode.getNext();
					tailNode = tailNode.getPrev();
					
				}
				return true;
			}
			return false;
		}

	}
}