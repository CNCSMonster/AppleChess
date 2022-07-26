package web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Handler;

import javax.swing.JFrame;

import controller.HumanPlayer;
import controller.LocalPlayer;
import controller.Player;
import controller.RemotePlayer;
import model.BoardComponentColor;
import model.ChessBoard;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(666);

        for(int i=0;i<10;i++){
            Socket socket=serverSocket.accept();
            handleSocket(socket);
        }
        
        Scanner in=new Scanner(System.in);
        while(!in.nextLine().equals("stop server"));
    }

    public static void handleSocket(Socket socket){
        new Thread(()->{
            ChessBoard chessBoard = new ChessBoard(560);
            JFrame jFrame = new JFrame();
            jFrame.setVisible(true);
            jFrame.setBounds(600, 100, 600, 600);
            jFrame.add(chessBoard);
            jFrame.setTitle("server");
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Player blackPlayer = new RemotePlayer(BoardComponentColor.BLACK, chessBoard, socket);
            Player humanPlayer = new HumanPlayer(BoardComponentColor.WHITE, chessBoard);
            Player whitePlayer = new LocalPlayer(humanPlayer, socket);
            whitePlayer.play();
            blackPlayer.play();
        }).start();
    }




}
