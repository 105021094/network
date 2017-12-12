package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client extends Thread {
    private Socket socket;
    private PrintStream outStream;
    private BufferedReader inStream;
    private CFrame cFrm;

    public Client(CFrame clientFrm) {
        this.setDaemon(true);
        cFrm = clientFrm;
    }

    public void run() {
        try {
            socket = new Socket(cFrm.grtIP(), 1723);
            outStream = new PrintStream(socket.getOutputStream());
            inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            send2client("Client is Connected");
            String str = "";
            while (!(str = inStream.readLine()).equals("")) {
                cFrm.addMsg(str);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error" + e.toString());
        }
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
    public void closeSocket(){
        try {
            inStream.close();
            socket.close();
        }catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,"Error"+e.toString());
        }
    }
}

