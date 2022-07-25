import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.text.PlainDocument;

import aistrategy.LocationFirstStrategy;
import aistrategy.RandomStrategy;
import aistrategy.SwapFirstStrategy;
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
        Player whitePlayer=new AIPlayer(whiteColor, chessBoard,new LocationFirstStrategy());
        Player blackPlayer=new HumanPlayer(blackColor, chessBoard);
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