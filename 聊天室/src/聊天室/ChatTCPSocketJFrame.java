package ������;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class ChatTCPSocketJFrame extends JFrame implements ActionListener
{
    private String name;                                   //����
    private JTextArea text_receiver;                       //��ʾ�Ի����ݵ��ı���
    private JTextField text_sender;                        //���뷢�����ݵ��ı���
    private PrintWriter cout;                              //��ʽ���ַ������
    private JButton[] buttons;                             //��ť����
    
    public ChatTCPSocketJFrame(String name, Socket socket) throws IOException
    {
        super("������  "+name+"  "+InetAddress.getLocalHost()+" : "+socket.getLocalPort());
        this.setBounds(320,240,400,240);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);       
        this.text_receiver = new JTextArea();
        this.text_receiver.setEditable(false);             //�ı������ɱ༭
        this.getContentPane().add(new JScrollPane(this.text_receiver));
        
        JToolBar toolbar = new JToolBar();                 //������
        this.getContentPane().add(toolbar,"South");
        toolbar.add(this.text_sender=new JTextField(30));
        String[] buttonstr={"����","����"};
        buttons=new JButton[buttonstr.length];
        for (int i=0; i<buttonstr.length; i++)
        {
            toolbar.add(buttons[i]=new JButton(buttonstr[i]));
            buttons[i].addActionListener(this);
        }
        this.setVisible(true);

        this.name = name;
        this.cout = new PrintWriter(socket.getOutputStream(),true);//���Socket�������������flush
        this.cout.println(name);                           //�����Լ��������Է�

        BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                  //��Socket���ֽ�������ת�����ַ�����Ĭ��GBK�ַ������ٴ��������ַ�������
        String line="����"+br.readLine()+"�ɹ�";               //���յ��Է�����
        while (line!=null && !line.endsWith("bye"))
        {
            text_receiver.append(line+"\r\n");             //��ʾ�Է�����������
            line=br.readLine();                            //�����������նԷ��������ַ���
        }
        br.close();
        this.cout.close();
        socket.close();                                    //�ر�TCP����
        buttons[0].setEnabled(false);
        buttons[1].setEnabled(false);
    }
    
    public ChatTCPSocketJFrame(String name, String host, int port) throws IOException //�ͻ���
    {
        this(name, new Socket(host, port));                //�ͻ��������������Ķ˿ڷ���TCP��������
    }
    
    public void actionPerformed(ActionEvent ev)
    {
        if (ev.getActionCommand().equals("����"))
        {
            this.cout.println(name+" ˵��"+text_sender.getText()); //ͨ����������͸��Է�
            text_receiver.append("��˵��"+text_sender.getText()+"\n");
            text_sender.setText("");
        }
        if (ev.getActionCommand().equals("����"))
        {
            text_receiver.append("������\n");
            this.cout.println(name+"����\n"+"bye\n");        //���͸��Է�����Լ��
            buttons[0].setEnabled(false);
            buttons[1].setEnabled(false);
        }
    }

    public static void main(String args[]) throws IOException
    {
        new ChatTCPSocketJFrame("����", "192.168.43.174", 2001); //�ͻ��������������Ķ˿ڷ���TCP��������
    }
}
