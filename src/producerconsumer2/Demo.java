package producerconsumer2;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class Demo {

	private JFrame frame;
	private JButton btnStart,btnStop;
	private TextArea txtProducer;
	private JProgressBar kho;
	Producer p1,p2;
	Consumer c1,c2;
	Kho queue;
	private JLabel lblSleepTime;
	private JTextField txtSleepTime;
	private int sleepTime;
	private JButton btnClear;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Demo window = new Demo();
					window.frame.setBounds(350, 250, 500, 300);
					window.frame.setVisible(true);
					window.frame.setTitle("Producer Consumer Demo");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Demo() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblSleepTime = new JLabel("Sleep Time");
		frame.getContentPane().add(lblSleepTime);
		
		txtSleepTime = new JTextField();
		txtSleepTime.setText("1000");
		frame.getContentPane().add(txtSleepTime);
		txtSleepTime.setColumns(10);
		
		btnStart = new JButton("Start");
		frame.getContentPane().add(btnStart);
		
		btnStop = new JButton("Stop");
		frame.getContentPane().add(btnStop);
		
		btnClear = new JButton("Clear");
		frame.getContentPane().add(btnClear);
		
		txtProducer = new TextArea();
		txtProducer.setBackground(Color.decode("#c7cecc"));
		txtProducer.setForeground(Color.RED);
		frame.getContentPane().add(txtProducer);
		
		kho = new JProgressBar();
		kho.setMaximum(10);
		kho.setMinimum(0);
		frame.getContentPane().add(kho);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addListener();
	}
	public void appendTextProducer(String str) {
		txtProducer.append(str);
	}
	
	public void setChangeProgress(int number) {
		kho.setValue(number);
		kho.setStringPainted(true);
	}
	
	public void start() {
		queue = new Kho(10,this);
		p1 = new Producer(queue,1,sleepTime);
		c1 = new Consumer(queue,1,sleepTime);
		p2 = new Producer(queue,2,sleepTime);
		c2 = new Consumer(queue,2,sleepTime);
		
		p1.start();
		c1.start();
		p2.start();
		c2.start();
	}
	public void stop(Thread t) {
		p1.pause();
		p2.pause();
		c1.pause();
		c2.pause();
	}
	public void showDialog(String msg) {
		JOptionPane.showMessageDialog(null,msg,"Thông báo", JOptionPane.WARNING_MESSAGE);
	}
	void addListener() {
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					sleepTime = Integer.parseInt(txtSleepTime.getText());
					if(sleepTime<0) {
						throw new Exception("Nhập số lớn hơn 0");
					}
					txtProducer.setText("");
					start();
				} catch (NumberFormatException e2) {
					showDialog("Vui lòng nhập số");
				}catch (Exception e2) {
					showDialog(e2.getMessage());
				}
			}
		});
		btnStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				stop(p1);
				stop(p2);
				stop(c1);
				stop(c2);
			}
		});
		btnClear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtProducer.setText("");
			}
		});
	}

}
