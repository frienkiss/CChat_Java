package 聊天室;

import java.awt.event.*;
import javax.swing.*;
import java.net.*;
public class ChatUDPJFrame extends JFrame implements ActionListener{
	private String name;
	private InetAddress destip;
	private int destport;
	private JTextArea text_receiver;
	private JTextField text_sender,text_port;
	
	public ChatUDPJFrame(String name,String host,int destport,int receiveport)throws Exception{
		super("波波聊天室    "+name+"  "+InetAddress.getLocalHost()+":"+receiveport);
		this.setBounds(320,240,400,240);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.text_receiver=new JTextArea();
		this.text_receiver.setEditable(false);
		this.getContentPane().add(new JScrollPane(this.text_receiver));
		JToolBar toolbar=new JToolBar();
		this.getContentPane().add(toolbar,"South");
		toolbar.add(this.text_sender=new JTextField(20));
		JButton button=new JButton("发送");
		toolbar.add(button);
		button.addActionListener(this);
		toolbar.add(this.text_port=new JTextField());
		this.setVisible(true);
		
		this.name=name;
		this.destip=InetAddress.getByName(host);
		this.destport=destport;
		
		byte[] data=new byte[512];
		DatagramPacket pack=new DatagramPacket(data,data.length);
		DatagramSocket dsocket=new DatagramSocket(receiveport);
		while(dsocket!=null){
			dsocket.receive(pack);
			text_receiver.append(new String(pack.getData(),pack.getLength()+"\r\n"));
			
		}
	}
	public void actionPerformed(ActionEvent ev){
		byte[] buffer=(name+" 说：  "+text_sender.getText()).getBytes();
		try{
			DatagramPacket pack=new DatagramPacket(buffer,buffer.length,destip,destport);
			DatagramSocket dsocket=new DatagramSocket();
			dsocket.send(pack);
			this.text_port.setText(dsocket.getLocalPort()+"");
			
		}
		catch(Exception ex) {};
		text_receiver.append("我说： "+text_sender.getText()+"\n");
		text_sender.setText("");
		
	}
	public static void main(String args[])throws Exception
	{
		//new ChatUDPJFrame("玉公主","58.212.230.243",3001,3002);
		new ChatUDPJFrame("波波","192.168.1.3",3001,3002);
	}

}
