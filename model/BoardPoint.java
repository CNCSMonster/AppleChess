package model;



//往右边是x，往下面是y,x从1开始，y也从1开始
//对于棋盘基础部件在棋盘中的位置进行封装
public class BoardPoint {
    private int x;
    private int y;

    public BoardPoint(int x,int y){
        this.x=x;
        this.y=y;
    }

    public void setX(int x){
        this.x=x;
    }

    public void setY(int y){
        this.y=y;
    }


    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "["+x+","+y+"]";
    }

    public static BoardPoint valueOf(String s){
        String term=s.substring(1,4);
        String[] sa=term.split(",");
        int x=0;
        int y=0;
        try {
            x=Integer.parseInt(sa[0]);
            y=Integer.parseInt(sa[1]);
        } catch (Exception e) {
            //TODO: handle exception
        }
        return new BoardPoint(x, y);
    }

    public static void main(String[] args) {
        BoardPoint boardPoint=new BoardPoint(1, 2);
        System.out.println(boardPoint);
        BoardPoint boardPoint2=BoardPoint.valueOf(boardPoint.toString());
        System.out.println(boardPoint2);
    }

}
