import javax.swing.JFrame;

import view.ChessBoard;
import java.awt.*;

class Main extends JFrame{


    public Main(){
        setBounds(100,100,700,700);
        ChessBoard chessBoard=new ChessBoard(560);
        setLayout(null);
        add(chessBoard);
        setVisible(true);
        
    }

    public static void main(String[] args) {
        new Main();
    }
}