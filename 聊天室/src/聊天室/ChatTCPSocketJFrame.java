package 聊天室;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class ChatTCPSocketJFrame extends JFrame implements ActionListener
{
    private String name;                                   //网名
    private JTextArea text_receiver;                       //显示对话内容的文本区
    private JTextField text_sender;                        //输入发送内容的文本行
    private PrintWriter cout;                              //格式化字符输出流
    private JButton[] buttons;                             //按钮数组
    
    public ChatTCPSocketJFrame(String name, Socket socket) throws IOException
    {
        super("聊天室  "+name+"  "+InetAddress.getLocalHost()+" : "+socket.getLocalPort());
        this.setBounds(320,240,400,240);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);       
        this.text_receiver = new JTextArea();
        this.text_receiver.setEditable(false);             //文本区不可编辑
        this.getContentPane().add(new JScrollPane(this.text_receiver));
        
        JToolBar toolbar = new JToolBar();                 //工具栏
        this.getContentPane().add(toolbar,"South");
        toolbar.add(this.text_sender=new JTextField(30));
        String[] buttonstr={"发送","离线"};
        buttons=new JButton[buttonstr.length];
        for (int i=0; i<buttonstr.length; i++)
        {
            toolbar.add(buttons[i]=new JButton(buttonstr[i]));
            buttons[i].addActionListener(this);
        }
        this.setVisible(true);

        this.name = name;
        this.cout = new PrintWriter(socket.getOutputStream(),true);//获得Socket的输出流，立即flush
        this.cout.println(name);                           //发送自己网名给对方

        BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                  //将Socket的字节输入流转换成字符流，默认GBK字符集，再创建缓冲字符输入流
        String line="连接"+br.readLine()+"成功";               //接收到对方网名
        while (line!=null && !line.endsWith("bye"))
        {
            text_receiver.append(line+"\r\n");             //显示对方发来的内容
            line=br.readLine();                            //从输入流接收对方发来的字符串
        }
        br.close();
        this.cout.close();
        socket.close();                                    //关闭TCP连接
        buttons[0].setEnabled(false);
        buttons[1].setEnabled(false);
    }
    
    public ChatTCPSocketJFrame(String name, String host, int port) throws IOException //客户端
    {
        this(name, new Socket(host, port));                //客户端向服务端主机的端口发出TCP连接请求
    }
    
    public void actionPerformed(ActionEvent ev)
    {
        if (ev.getActionCommand().equals("发送"))
        {
            this.cout.println(name+" 说："+text_sender.getText()); //通过输出流发送给对方
            text_receiver.append("我说："+text_sender.getText()+"\n");
            text_sender.setText("");
        }
        if (ev.getActionCommand().equals("离线"))
        {
            text_receiver.append("我离线\n");
            this.cout.println(name+"离线\n"+"bye\n");        //发送给对方离线约定
            buttons[0].setEnabled(false);
            buttons[1].setEnabled(false);
        }
    }

    public static void main(String args[]) throws IOException
    {
        new ChatTCPSocketJFrame("波波", "192.168.43.174", 2001); //客户端向服务端主机的端口发出TCP连接请求
    }
}
