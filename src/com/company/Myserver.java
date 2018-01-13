package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Myserver {

    public static ArrayList<ServerThread> list =new ArrayList<ServerThread>();
    public static String []infos;

    public void initServer() {
        try {
            ServerSocket server=new ServerSocket(9090);
            System.out.println("啟動");
            while(true){
                Socket socket=server.accept();
                ServerThread st = new ServerThread(socket);
                list.add(st);
                //開始遊戲
                if(list.size()==2){
                    MyDataBase db = new MyDataBase();
                    String guessinfo=db.getInfo();
                    infos=guessinfo.split("#");
                    //畫方
                    list.get(0).name="draw";
                    //猜方
                    list.get(1).name="guess";
                    list.get(0).start();
                    list.get(1).start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}  