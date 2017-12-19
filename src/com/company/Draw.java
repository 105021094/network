package com.company;

import javax.swing.*;
import java.awt.*;

public class Draw extends JFrame {
    private Container cp;
    private JPanel jpn = new JPanel();
    private JPanel jpns = new JPanel(new GridLayout(1,2,5,5));
    private JButton jbtnDraw = new JButton("Draw");
    private JButton jbtnExit = new JButton("Exit");

    public Draw(){
        initcomp();
    }

    private void initcomp(){

    }

}

