package com.company;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientUI extends JFrame {

    public JButton sendBtn;
    public JLabel contant;
    public JPanel drawPanel;
    public JPanel colorPanel;
    public JPanel waitPanel;
    public JPanel drawLeftPanel;
    public JPanel centerPanel;
    public JTextField jtf;
    public JTextArea jta;
    public Graphics2D g;
    public Color color;
    public ClientCtroller control;
    public Socket socket;
    public int x1, y1;
    public BasicStroke strock;
    public JComboBox<Integer> box;

    public ClientUI() {
        try {
            socket = new Socket("localhost", 9090);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClientUI ui = new ClientUI();
        ui.initFrame();
    }

    public void initFrame() {

        this.setTitle("你畫我猜");
        this.setSize(700, 500);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);

        waitPanel = new JPanel();
        waitPanel.setBackground(Color.WHITE);
        JLabel label = new JLabel("正在搜尋房間......");
        waitPanel.add(label);
        this.add(waitPanel);
        this.setVisible(true);

        control = new ClientCtroller(socket, this);
        control.dealwith();
    }

    //畫版
    public void addDrawPanel() {
        drawPanel = new JPanel();
        drawPanel.setLayout(new BorderLayout());
        // 左右版面
        drawLeftPanel = new JPanel();
        drawLeftPanel.setLayout(new BorderLayout());

        //左邊中間版面
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.lightGray);
        //顏色按鈕區
        colorPanel = new JPanel();
        colorPanel.setLayout(null);
        colorPanel.setBackground(Color.GRAY);
        colorPanel.setPreferredSize(new Dimension(0,60));
        Color [] colors={Color.red,Color.black,Color.orange,Color.green,
                Color.pink,Color.blue,Color.cyan,Color.magenta,Color.YELLOW};
        ActionListener btnlistener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                JButton bt =(JButton)e.getSource();
                color =bt.getBackground();
            }
        };
        for (int i = 0; i < colors.length; i++) {
            JButton btn = new JButton();
            btn.setBackground(colors[i]);
            btn.addActionListener(btnlistener);
            btn.setBounds(40+i*30, 15, 30, 30);
            colorPanel.add(btn);
        }


        //畫筆粗細
        box =new JComboBox<Integer>();
        box.setBounds(380, 15, 80, 30);
        for (int i = 0; i < 10; i++) {
            Integer intdata = new Integer(i+1);
            box.addItem(intdata);
        }
        colorPanel.add(box);

        JPanel drawRightPanel = new JPanel();

        drawRightPanel.setLayout(new BorderLayout());
        drawRightPanel.setPreferredSize(new Dimension(200, 0));
        // 傳訊息版面
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(0, 50));
        jta = new JTextArea();
        jta.setLineWrap(true);
        JScrollPane jsp = new JScrollPane(jta);
        jtf = new JTextField(11);
        contant = new JLabel();
        sendBtn = new JButton();
        sendBtn.setText("Send");
        sendBtn.addActionListener(al);
        buttonPanel.add(jtf);
        buttonPanel.add(sendBtn);

        drawRightPanel.add(jsp);
        drawRightPanel.add(buttonPanel, BorderLayout.SOUTH);

        contant.setPreferredSize(new Dimension(0, 20));
        drawLeftPanel.add(contant, BorderLayout.NORTH);
        drawLeftPanel.add(centerPanel, BorderLayout.CENTER);
        drawLeftPanel.add(colorPanel, BorderLayout.SOUTH);
        drawPanel.add(drawLeftPanel);
        drawPanel.add(drawRightPanel, BorderLayout.EAST);
        this.add(drawPanel);
        centerPanel.addMouseListener(ma);
        centerPanel.addMouseMotionListener(ma);
        this.setVisible(true);
        g = (Graphics2D)centerPanel.getGraphics();
    }


    public void addGuessPanel() {
        contant.setText("提示訊息");
        sendBtn.setEnabled(false);
        drawLeftPanel.remove(colorPanel);
        drawLeftPanel.repaint();
        this.setVisible(true);
    }


    MouseAdapter ma = new MouseAdapter() {

        public void mousePressed(MouseEvent e) {
            x1 = e.getX();
            y1 = e.getY();
        };

        public void mouseEntered(MouseEvent e) {
            if(color==null){
                color=Color.black;
            }

            g.setColor(color);

        }

        public void mouseDragged(MouseEvent e) {
            int width=(int)box.getSelectedItem();
            strock = new BasicStroke(width);
            g.setStroke(strock);

            int x2 = e.getX();
            int y2 = e.getY();
            g.drawLine(x1, y1, x2, y2);
            try {

                control.sendMsg1(socket.getOutputStream(), x1, y1, x2, y2,g.getColor().getRGB(),width);
                x1 = x2;
                y1 = y2;
            } catch (IOException e1) {
            }
        }

    };

    ActionListener al =new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            //取得內容
            String str = jtf.getText();
            if(str==null || str.equals("")){
                JOptionPane.showMessageDialog(null, "傳送訊息不能空白！");
            }else{
                try {
                    control.dos.writeUTF(str);
                    jtf.setText("");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    };

}  