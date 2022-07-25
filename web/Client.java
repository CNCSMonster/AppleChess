package web;

import java.io.IOException;
import java.net.Socket;

public class Client {


    public static void main(String[] args) {
        try (//两边都需要一个棋盘显示
        Socket socket = new Socket("localhost",666)) {
        } 
            //等待接收到信息
            //启动一个棋盘，这个棋盘只有人类才能够使用，
        
        
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }



}
