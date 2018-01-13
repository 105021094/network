package com.company;

public class Main {

    public static void main(String[] args) {
        Myserver ms = new Myserver();
        ms.initServer();
        ClientUI ui = new ClientUI();
        ui.initFrame();

    }
}
