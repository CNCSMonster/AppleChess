package web;

import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;

import controller.Player;
import controller.RemotePlayer;
import model.BoardComponentColor;
import view.ChessBoard;

public class Client {


    public static void main(String[] args) {
        try (//两边都需要一个棋盘显示
        Socket socket = new Socket("localhost",666)) {
            ChessBoard chessBoard=new ChessBoard(560);
            Player whitePlayer=new RemotePlayer(BoardComponentColor.WHITE, chessBoard);
            
            JFrame jFrame=new JFrame();
            jFrame.setVisible(true);
            jFrame.setBounds(100,100,700,700);
            jFrame.add(chessBoard);

        } 
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }



}
