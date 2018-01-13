package com.company;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JFrame;


public class ClientCtroller {

    public Socket socket;
    public ClientUI ui;

    public DataInputStream dis;
    public DataOutputStream dos;


    public ClientCtroller(Socket socket, JFrame ui) {
        this.socket = socket;
        this.ui =(ClientUI) ui;
    }

    public void dealwith() {
        try {
            InputStream ins =this.socket.getInputStream();
            OutputStream ous =this.socket.getOutputStream();
            dis=new DataInputStream(ins);
            dos = new DataOutputStream(ous);
            String msg = readMsg(socket.getInputStream());

            if ("draw".equals(msg)) {
                // 如果是draw，猜方顯示畫的內容
                ui.remove(ui.waitPanel);
                ui.addDrawPanel();
                ui.sendBtn.setEnabled(false);
                ui.repaint();
                //接收要畫的題目
                String drawinfo=dis.readUTF();
                ui.contant.setText("題目："+drawinfo);

                while(true){
                    String s=readMsg(ins);
                    //發送猜方消息
                    if(!"data".equals(s)){
                        String s1=dis.readUTF();
                        System.out.println("s1："+s);
                        //如果猜對了，在畫方顯是對方猜對了
                        if("yes".equals(s1)){
                            ui.jta.append("對方猜對了！！"+"\r\n");
                        }
                        //否則一直猜對為止
                        else{
                            ui.jta.append(s1+"\r\n");
                        }
                    }
                }
            }
            if ("guess".equals(msg)) {
                // 如果是猜方
                String guessinfo=dis.readUTF();
                ui.remove(ui.waitPanel);
                ui.addDrawPanel();
                ui.addGuessPanel();
                ui.contant.setText(guessinfo);
                ui.sendBtn.setEnabled(true);
                while(true){
                    String info =readMsg(ins);
                    if("data".equals(info)){
                        readMsg1(socket.getInputStream());
                    }
                    //如果是自己傳送猜的消息
                    else if("msg".equals(info)){
                        String info2=dis.readUTF();
                        System.out.println("info2"+info2);
                        if("yes".equals(info2)){
                            info2=dis.readUTF();
                            ui.jta.append(info2+"\r\n");
                            ui.jta.append("恭喜你猜對了");
                        }
                        else{
                            ui.jta.append(info2+"\r\n");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // 讀訊息
    public String readMsg(InputStream ins) throws Exception {
        // 讀取猜方訊息
        int value = ins.read();
        String str = "";
        while (value != 10) {
            // 關閉猜方
            if (value == -1) {
                throw new Exception();
            }
            str = str + ((char) value);
            value = ins.read();
        }
        str = str.trim();
        return str;
    }

    // 傳訊息
    public void sendMsg1(OutputStream os, int x1, int y1, int x2, int y2,int color,int width) throws IOException {

        DataOutputStream dos = new DataOutputStream(os);
        dos.writeInt(x1);
        dos.writeInt(y1);
        dos.writeInt(x2);
        dos.writeInt(y2);
        dos.writeInt(color);
        dos.writeInt(width);
        dos.flush();

    }

    public void readMsg1(InputStream is) throws IOException {

        DataInputStream dis = new DataInputStream (is);
        int x1=dis.readInt();
        int y1=dis.readInt();
        int x2=dis.readInt();
        int y2=dis.readInt();
        int color =dis.readInt();
        int width=dis.readInt();
        Color c =new Color(color);
        BasicStroke strock = new BasicStroke(width);
        ui.g.setColor(c);
        ui.g.setStroke(strock);
        ui.g.drawLine(x1, y1, x2, y2);


    }
    public static void main(String argv[]){
        ClientUI ui = new ClientUI();
        ui.initFrame();
    }

}  