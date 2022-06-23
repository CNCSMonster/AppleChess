package model;


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
        // TODO Auto-generated method stub
        return "["+x+","+y+"]";
    }


}
