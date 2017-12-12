package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CFrame extends JFrame{
    private JButton jbtnSend = new JButton("Send");
    private JButton jbtnConnect = new JButton("Connect");
    private JButton jbtnExit = new JButton("Exit");
    private JTextArea jtaIn = new JTextArea();
    private JTextArea jtaOut = new JTextArea();
    private JScrollPane jsp = new JScrollPane(jtaIn);
    private JScrollPane jspOut = new JScrollPane(jtaOut);
    private JPanel jpnBottom = new JPanel(new BorderLayout(5,5));

    private JPanel functionPan = new JPanel(new GridLayout(7,1,5,5));
    private JLabel jlbIP = new JLabel("IP");
    private JTextField jtfIP = new JTextField("127.0.1.1");
    private Container cp;
    private Client client;

    public CFrame(){
        initComp();
    }

    private void initComp(){
        this.setBounds(600,100,300,400);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Chat Client");
        cp = getContentPane();
        cp.setLayout(new BorderLayout(5,5));
        client = new Client(CFrame.this);
        jbtnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(((JButton)ae.getSource()).getText().equals("Connect")){
                    client.start();
                    ((JButton)ae.getSource()).setText("Disconnect");
                }else {
                    ((JButton)ae.getSource()).setText("Connect");
                }
            }
        });
        jbtnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        jbtnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                client.send2client(jtaOut.getText());
                jtaIn.append("Client:"+jtaOut.getText()+"\n");
                jtaOut.setText("");
            }
        });
        jtaIn.setBackground(new Color(200,199,239));
        jtaIn.setFont(new Font(null,Font.PLAIN,14));
        jtaIn.setEditable(false);
        functionPan.add(jlbIP);
        functionPan.add(jtfIP);
        functionPan.add(jbtnConnect);
        functionPan.add(jbtnExit);
        jbtnSend.setPreferredSize(new Dimension(85,65));
        cp.add(jsp,BorderLayout.CENTER);
        jpnBottom.add(jspOut,BorderLayout.CENTER);
        jpnBottom.add(jspOut,BorderLayout.EAST);
        cp.add(functionPan,BorderLayout.EAST);
        cp.add(jpnBottom,BorderLayout.SOUTH);

    }
    public void addMsg(String inStr){
        jtaIn.append("Client:"+inStr+"\n");
    }
    public String grtIP(){
        return jtfIP.getText();
    }
    public static void main(String argv[]){
        CFrame cfrm = new CFrame();
        cfrm.setVisible(true);
    }

}
