package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SFrame extends JFrame{
    private JButton jbtnRun = new JButton("Start");
    private JButton jbtnExit = new JButton("Exit");
    private JButton jbtnSend = new JButton("Send");
    private JTextArea jta = new JTextArea();
    private JTextArea jtaOut = new JTextArea();
    private JScrollPane jsp = new JScrollPane(jta);
    private JScrollPane jspOut = new JScrollPane(jtaOut);
    private JPanel jpnBottom = new JPanel(new BorderLayout(5,5));
    private JPanel jpnFunction = new JPanel(new GridLayout(5,1,5,5));
    private JLabel jlbStatus = new JLabel("Status");
    private Container cp;
    private Server serv;

    public SFrame(){
        initComp();
    }

    private void initComp(){
        this.setBounds(100,100,800,800);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Chat Server");
        cp = getContentPane();
        cp.setLayout(new BorderLayout(5,5));
        jbtnRun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                serv = new Server(SFrame.this);
                serv.start();
                jta.append("Waiting connect in...\n");
                ((JButton)ae.getSource()).setEnabled(false);
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
                serv.send2client(jtaOut.getText());
                jta.append("Server:"+jtaOut.getText()+"\n");
                jtaOut.setText("");
            }
        });
        jta.setBackground(new Color(200,239,189));
        jta.setFont(new Font(null,Font.PLAIN,14));
        jlbStatus.setIconTextGap(10);
        jlbStatus.setPreferredSize(new Dimension(65,75));
        jpnBottom.add(jspOut,BorderLayout.CENTER);
        jpnBottom.add(jbtnSend,BorderLayout.EAST);
        jpnFunction.add(jbtnRun);
        jpnFunction.add(jbtnExit);
        cp.add(jpnFunction,BorderLayout.EAST);
        cp.add(jsp,BorderLayout.CENTER);
        cp.add(jpnBottom,BorderLayout.SOUTH);

    }
    public void addMsg(String inStr){
        jta.append("Client:"+inStr+"\n");
    }
    public static void main(String argv[]){
        SFrame sfrm = new SFrame();
        sfrm.setVisible(true);
    }

}
