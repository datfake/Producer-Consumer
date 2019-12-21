package producerconsumer2;

import java.util.LinkedList;

public class Kho {
	private int max;
	private LinkedList<Object> queue = new LinkedList<>();
	private Demo jframe;

	public Kho(int max,Demo csnm) {
		this.max = max;
		jframe = csnm;
	}

	public int getBound() {
		return max;
	}

	public int getQuanlityOfInventory() {
		return queue.size();
	}

	public boolean isFull() {
		return getQuanlityOfInventory() == max;
	}

	public synchronized void add(int quanlity,int name) {
		try {
			while (quanlity > max - getQuanlityOfInventory()) {
				jframe.appendTextProducer("Producer "+name+" đang chờ nhập "+quanlity+" sản phẩm\n");
				wait();
			}
			for (int i = 0; i < quanlity; i++) {
				queue.add(1);
			}
			jframe.appendTextProducer("Producer "+name+" đã nhập "+quanlity+" sản phẩm vào kho. Kho hiện có: "+getQuanlityOfInventory()+"\n");
			jframe.setChangeProgress(getQuanlityOfInventory());
			notifyAll();
		} catch (Exception e) {
		}
	}

	public synchronized void get(int quanlity,int name) {
		try {
			while (quanlity > getQuanlityOfInventory()) {
				jframe.appendTextProducer("Consumer "+name+" đang chờ xuất "+quanlity+" sản phẩm\n");
				wait();
			}
			for (int i = 0; i < quanlity; i++) {
				queue.poll();
			}
			jframe.appendTextProducer("Consumer "+name+" đã xuất "+ quanlity+" sản phẩm ra khỏi kho.Kho hiện còn: "+getQuanlityOfInventory()+"\n");
			jframe.setChangeProgress(getQuanlityOfInventory());
			notifyAll();
		} catch (Exception e) {
		}
	}
}
