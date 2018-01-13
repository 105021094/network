package com.company;

import java.util.ArrayList;
import java.util.Random;

    public class MyDataBase {


        private static Random  r = new Random();


        private static ArrayList<String> list = new ArrayList<String>();


        {
            list.add("蘋果#一種水果，二個字");
            list.add("梨子#一種水果，二個字");
            list.add("豬#一種動物，一個字");
            list.add("葡萄#一種水果，二個字");
            list.add("蜜蜂#一種動物，二個字");
            list.add("筆記型電腦#一種電子產品，五個字");
            list.add("滑鼠#配件，二個字");
            list.add("兔子#一種動物，二個字");

        }

        public  String getInfo() {
            int i=r.nextInt(list.size());
            System.out.println(+i);
            return list.get(i);

        }
    }

