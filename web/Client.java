package web;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SelectableChannel;

import javax.swing.JFrame;

import controller.HumanPlayer;
import controller.LocalPlayer;
import controller.Player;
import controller.RemotePlayer;
import model.BoardComponentColor;
import model.ChessBoard;

public class Client {


    public static void main(String[] args) {
        try (//两边都需要一个棋盘显示
        Socket socket = new Socket("192.168.56.1",666))
         {  
            ChessBoard chessBoard=new ChessBoard(560);
            JFrame jFrame=new JFrame();
            jFrame.setVisible(true);
            jFrame.setBounds(0,100,600,600);
            jFrame.add(chessBoard);
            jFrame.setTitle("client");
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Player whitePlayer=new RemotePlayer(BoardComponentColor.WHITE, chessBoard,socket);
            Player humanPlayer=new HumanPlayer(BoardComponentColor.BLACK, chessBoard);
            Player blackPlayer=new LocalPlayer(humanPlayer, socket);
            whitePlayer.play();
            blackPlayer.play();
            while(true){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if(chessBoard.isGameOver()) break;
            }
        } 
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }



}
