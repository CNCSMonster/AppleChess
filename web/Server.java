package web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(666);
        Socket socket=serverSocket.accept();
        
        //使用一个线程处理输入流，


        //使用一个线程处理输出流



    }
}
