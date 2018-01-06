package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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


    private JPanel jpn = new JPanel();
    public int x1  ,y1  ,x2  ,y2  ;

    public SFrame(){
        initComp();
    }

    private void initComp(){
        this.setBounds(100,100,500,500);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Chat Server");
        cp = this.getContentPane();
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


        jpn.setLayout(new GridLayout(5,1,5,5));

        jpn.add(jbtnExit);

        cp.add(jpn,BorderLayout.WEST);

        jta.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                x1=e.getX(); // 取得滑鼠按下時的 x 座標 (繪圖起始點的 x 座標)
                y1=e.getY(); // 取得滑鼠按下時的 y 座標 (繪圖起始點的 y 座標)
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        jta.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Graphics g = jta.getGraphics();
                x2=e.getX(); // 取得拖曳滑鼠時的 x 座標
                y2=e.getY(); // 取得拖曳滑鼠時的 y 座標
                g.drawLine(x1,y1,x2,y2); // 繪出(x1,y1)到(x2,y2)的連線
                x1=x2; // 更新繪圖起始點的 x 座標
                y1=y2; // 更新繪圖起始點的 y 座標
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        cp.add(jta,BorderLayout.CENTER);

    }


    public void addMsg(String inStr){
        jta.append("Client:"+inStr+"\n");
    }
    public static void main(String argv[]){
        SFrame sfrm = new SFrame();
        sfrm.setVisible(true);
    }

}
