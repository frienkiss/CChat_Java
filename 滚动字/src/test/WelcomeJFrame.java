package test;

//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//
//public class WelcomeJFrame  extends JFrame{
//	
//	private static final long serialVersionUID = -4186592362745566749L;
//	public WelcomeJFrame(String texts[]){
//		super("滚动字");
//		this.setBounds(300,240,400,300);
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		if(texts==null||texts.length==0)
//			this.getContentPane().add(new RollbyJPanel("Welcome!"));
//		else{
//			this.getContentPane().setLayout(new GridLayout(texts.length,1));
//			for(int i=0;i<texts.length;i++)
//				this.getContentPane().add(new RollbyJPanel(texts[i]));
//		}
//		this.setVisible(true);
//		
//	}
//	public WelcomeJFrame(){this(null);}
//	private class RollbyJPanel extends JPanel implements ActionListener,Runnable{
//	
//		private static final long serialVersionUID = 1L;
//		JTextField  text_word,text_sleep,text_state;
//		JButton button_start,button_interrupt;
//		Thread thread;
//		int sleeptime;
//		
//		RollbyJPanel(String text){
//			this.setLayout(new GridLayout(2,1));
//			text_word=new JTextField(String.format("%115s",text));
//			this.add(text_word);
//			JPanel panel_sub=new JPanel();
//			this.add(panel_sub);
//			panel_sub.add(new JLabel("sleep"));
//			this.sleeptime=(int)(Math.random()*100);
//			text_sleep=new JTextField(""+sleeptime,5);
//			panel_sub.add(text_sleep);
//			text_sleep.addActionListener(this);
//			
//			button_start=new JButton("启动");
//			panel_sub.add(button_start);
//			button_start.addActionListener((ActionListener) this);
//			button_interrupt= new JButton("中断");
//			panel_sub.add(button_interrupt);
//			button_interrupt.addActionListener(this);
//			
//			thread=new Thread(this);
//			button_interrupt.setEnabled(false);
//			panel_sub.add(new JLabel("state"));
//			text_state=new JTextField(" "+thread.getState(),10);
//			text_state.setEditable(false);
//			panel_sub.add(text_state);
//			
//		}
//		public void run(){
//			while(true)
//				try{
//					String str=text_word.getText();
//					text_word.setText(str.substring(1)+str.substring(0,1));
//					Thread.sleep(sleeptime);
//					
//				}
//			catch(InterruptedException ex){ break;}
//			
//		}
//		
//		public void actionPerformed1(ActionEvent ev){
//			if(ev.getSource()==button_start){
//				thread=new Thread(this);
//				thread.start();
//				text_state.setText(" "+thread.getState());
//				button_start.setEnabled(false);
//				button_interrupt.setEnabled(true);
//			}
//			
//			if(ev.getSource()==button_interrupt){
//				thread.interrupt();
//				text_state.setText(" "+thread.getState());
//				button_start.setEnabled(true);
//				button_interrupt.setEnabled(false);
//			}
//			
//			if(ev.getSource()==text_sleep)
//				try{sleeptime=Integer.parseInt(text_sleep.getText());}
//			catch(NumberFormatException nfex){
//				JOptionPane.showMessageDialog(this,
//						"\""+text_sleep.getText()+"\"不能转换成整数，请重新输入！");
//			}
//		}
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//}
//public static void main(String arg[]){
//			String texts[]={"Welcome","Hello","dabobo"};
//			new WelcomeJFrame(texts);
//		}
//}

