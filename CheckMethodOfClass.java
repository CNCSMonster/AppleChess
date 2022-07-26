import java.lang.reflect.Method;

import model.ChessBoard;

public class CheckMethodOfClass {
    
    public static void showAllMethodOfClass(Class class1){
        System.out.println("className is "+class1.getName());
        Method[] methods=class1.getMethods();
        for (int i = 0; i < methods.length; i++) {
            System.out.println("a has method: "+methods[i].getName());
            System.out.println();
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        ChessBoard chessBoard=new ChessBoard(1);
        showAllMethodOfClass(ChessBoard.class);
    }
}
