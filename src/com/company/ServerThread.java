package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class ServerThread extends Thread {

    public Socket socket;
    public String name;
    public DataInputStream dis;
    public DataOutputStream dos;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {

        try {
            InputStream ins=socket.getInputStream();
            OutputStream ous =socket.getOutputStream();
            dis =new DataInputStream(ins);
            dos = new DataOutputStream(ous);
            //假如是畫方
            if("draw".equals(name)){
                //發送畫給猜方
                sendMsg(socket.getOutputStream(), "draw");
                dos.writeUTF( Myserver.infos[0]);
                //傳送畫
                while(true){
                    int x1=dis.readInt();
                    int y1=dis.readInt();
                    int x2=dis.readInt();
                    int y2=dis.readInt();
                    int color =dis.readInt();
                    int width=dis.readInt();
                    //傳給猜方
                    for (int i = 0; i <Myserver.list.size(); i++) {
                        ServerThread st =Myserver.list.get(i);
                        if(st!=this){
                            sendMsg(st.socket.getOutputStream(), "data");
                            sendMsg1(st.socket.getOutputStream(), x1, y1, x2, y2,color,width);
                        }
                    }
                }
            }


            if("guess".equals(name)){
                //傳消息給畫方
                sendMsg(ous, "guess");
                //提示部分給猜方
                dos.writeUTF(Myserver.infos[1]);
                while(true){
                    String msg=dis.readUTF();

                    for (int i = 0; i <Myserver.list.size(); i++) {
                        ServerThread st =Myserver.list.get(i);
                        sendMsg(st.socket.getOutputStream(), "msg");
                    }

                    //假如猜對了
                    if(Myserver.infos[0].equals(msg)){

                        Myserver.list.get(0).dos.writeUTF("yes");
                        this.dos.writeUTF("yes");
                        this.dos.writeUTF("猜方说:"+msg);

                    }else{

                        for (int i = 0; i <Myserver.list.size(); i++) {
                            ServerThread st =Myserver.list.get(i);
                            if(st==this){
                                st.dos.writeUTF("猜方说:"+msg);
                            }
                            else{
                                st.dos.writeUTF("猜方说:"+msg);
                            }
                        }

                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 傳訊息
    public void sendMsg(OutputStream os, String s) throws IOException {

        // 向畫方傳訊息
        byte[] bytes = s.getBytes();
        os.write(bytes);
        os.write(13);
        os.write(10);
        os.flush();

    }

    // 發送畫
    public void sendMsg1(OutputStream os, int x1, int y1, int x2, int y2,int color ,int width) throws IOException {

        DataOutputStream dos = new DataOutputStream(os);
        dos.writeInt(x1);
        dos.writeInt(y1);
        dos.writeInt(x2);
        dos.writeInt(y2);
        dos.writeInt(color);
        dos.writeInt(width);
        dos.flush();
    }
    public static void main(String argv[]){
        Myserver ms = new Myserver();
        ms.initServer();
    }
}  