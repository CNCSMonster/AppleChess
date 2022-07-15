import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.text.PlainDocument;

import aistrategy.RandomStrategy;
import controller.AIPlayer;
import controller.HumanPlayer;
import controller.Player;
import model.BoardComponentColor;
import view.ChessBoard;

class Main extends JFrame{
    BoardComponentColor whiteColor=BoardComponentColor.WHITE;
    BoardComponentColor blackColor=BoardComponentColor.BLACK;


    public Main(){
        setBounds(100,100,700,700);
        ChessBoard chessBoard=new ChessBoard(560);
        Player whitePlayer=new AIPlayer(whiteColor, chessBoard,new RandomStrategy());
        Player blackPlayer=new AIPlayer(blackColor, chessBoard,new RandomStrategy());
        whitePlayer.play();
        blackPlayer.play();
        setLayout(null);
        add(chessBoard);
        setVisible(true);
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            new Main();
        });
    }
}