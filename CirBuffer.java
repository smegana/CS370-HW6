import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;

public class CirBuffer {
		
	private int[] buffer;
	private int tail;
	private int head;
	private int bufLen;

	//Create Circular Buffer of size n
	@SuppressWarnings("unchecked")
	public CirBuffer(int n) {
		buffer = new int[n];
		tail = 0;
		head = 0;
		bufLen = 0;
	}
	
	
	public synchronized int size(){
		return bufLen;
	}

	//Add an element to the buffer
	public synchronized int add(int i) {
		if (head != (tail - 1)) {
			bufLen++;
			buffer[head++] = i;
		} 
		else {
			throw new BufferOverflowException();
		}
		head = head % buffer.length;
		return head;
	}

	
	public synchronized int get() {
		int t = 0;
		int adjTail = tail > head ? tail - buffer.length : tail;
		if (adjTail < head) {
			t = buffer[tail++];
		    tail = tail % buffer.length;
		    bufLen--;
		} 
		else {
			throw new BufferUnderflowException();
		}
		return t;
	}
	
	public synchronized boolean isFull(){
		if (bufLen == HW6.bufferSize)
		    return true;
		else
			return false;
	}
	
	public synchronized boolean isEmpty(){
		if(bufLen == 0)
			return true;
		else
			return false;
	}

	public String toString() {
		return "CirBuffer(size=" + buffer.length + ", head=" + head + ", tail=" + tail + ")";
	}

}

