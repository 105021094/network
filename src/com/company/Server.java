package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server extends Thread {
    private InetAddress ipAdr;
    private ServerSocket sSocket;
    private Socket socket;
    private PrintStream outStream;
    private BufferedReader inStream;
    private SFrame sFrm;

    public Server(SFrame serverFrm){
        sFrm = serverFrm;
        try {
            ipAdr = InetAddress.getLocalHost();
            sSocket = new ServerSocket(1723);
        }catch (UnknownHostException e){
            javax.swing.JOptionPane.showMessageDialog(null,"Error"+e.toString());
        }catch (IOException ioe){
            javax.swing.JOptionPane.showMessageDialog(null,"Error"+ioe.toString());
        }
    }
    public void run(){
        try {
            socket = sSocket.accept();
            outStream = new PrintStream(socket.getOutputStream());
            inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            send2client("Hi!Welcome");
            String str = "";
            while(!(str = inStream.readLine()).equals("")){
                sFrm.addMsg(str);
            }
        }catch (Exception e){
            javax.swing.JOptionPane.showMessageDialog(null,"Error"+e.toString());
        }
    }

    public String getIP(){
        return ipAdr.getHostAddress();
    }

    public void send2client(String msg){
        try {
            if(outStream!=null){
                outStream.println(msg);
            }else {
                javax.swing.JOptionPane.showMessageDialog(null,"Error:Please make connection with Clint first!!");
            }
        }catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,"Error"+e.toString());
        }
    }

}
