package entity;

import java.util.ArrayList;
import java.util.Random;

public class Map {
    private final ArrayList<ArrayList<String>> matrix;
    private int garbageCount;

    private int size;

    public Map(int size){
        this.size = size;
        this.matrix = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            matrix.add(new ArrayList<>(size));
            for (int j = 0; j <size; j++) {
                matrix.get(i).add("X");
            }
        }
    }

    public Map(int size, int garbage) {

        this.size = size;
        this.matrix = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            matrix.add(new ArrayList<>(size));
            for (int j = 0; j <size; j++) {
                matrix.get(i).add(String.valueOf(0));
            }
        }
        addGarbage(garbage);
    }

    public Map(Map map){
        this.garbageCount = 0;
        this.size = map.getSize();
        this.matrix = new ArrayList<>(map.getSize());
        for (int i=0;i<map.getSize();i++) {
            matrix.add(new ArrayList<>(map.getSize()));
            for (int j=0;j<map.getSize();j++) {
                if(map.getMatrix().get(i).get(j).equals("0")){
                    matrix.get(i).add(".");
                } else {
                    matrix.get(i).add(map.getMatrix().get(i).get(j));
                    this.garbageCount++;
                }
            }
        }
    }

    public void addGarbage(int garbage){
        Random gen = new Random();
        int x, y;

        for (int i = 1; i <= 3; i+=2) {
            for (int j = 0; j < garbage; j++) {
                do {
                    x = gen.nextInt(0, size-1);
                    y = gen.nextInt(0, size-1);
                } while (!matrix.get(x).get(y).equals("0") || (x==0 && y==0) || (x==0 && y==size-1));
                matrix.get(x).set(y , String.valueOf(i));
                this.garbageCount++;
            }
        }
    }

    public ArrayList<String> perception( int xPos, int yPos, int opXPos, int opYPos ){
        ArrayList<String> adjascentContent = new ArrayList<>(4);

        adjascentContent.add(0, (xPos <= 0) || (xPos-1 == opXPos && yPos == opYPos)?  "-1" : matrix.get(xPos-1).get(yPos)); // cima
        adjascentContent.add(1, (yPos >= size-1) || (yPos+1 == opYPos && xPos == opXPos)? "-1" : matrix.get(xPos).get(yPos+1)); // direita
        adjascentContent.add(2, (xPos >= size-1) || (xPos+1 == opXPos && yPos == opYPos)? "-1" : matrix.get(xPos+1).get(yPos)); // baixo
        adjascentContent.add(3, (yPos <= 0) || (yPos-1 == opYPos  && xPos == opXPos)? "-1" : matrix.get(xPos).get(yPos-1)); // esquerda

        return adjascentContent;
    }

    public void removeGarbage(int x, int y) {
        matrix.get(x).set(y , "0");
        garbageCount--;
    }

    public void viewMap(int ag1x, int ag1y, int ag2x, int ag2y, int size){
        for (int i=0;i<size;i++) {
            for (int j=0;j<size;j++) {
                if ( i == ag1x && j == ag1y) {
                    System.out.print("A1 ");
                }else if ( i == ag2x && j == ag2y) {
                    System.out.print("A2 ");
                }else
                    System.out.print(matrix.get(i).get(j)+"  ");
            }
            System.out.println();
        }
    }

    public void viewMapGoal(int ag1x, int ag1y, int size){

        for (int i=0;i<size;i++) {
            for (int j=0;j<size;j++) {
                if ( i == ag1x && j == ag1y) {
                    System.out.print("Ag ");
                }else
                    System.out.print(matrix.get(i).get(j)+"  ");
            }
            System.out.println();
        }
    }

    public void viewMap(int ag1x, int ag1y, int size){
        for (int i=0;i<size;i++) {
            for (int j=0;j<size;j++) {
                if ( i == ag1x && j == ag1y) {
                    System.out.print("A1 ");
                }else
                    System.out.print(matrix.get(i).get(j)+"  ");
            }
            System.out.println();
        }
    }

    public boolean isEmpty(){
        return garbageCount == 0;
    }

    public ArrayList<ArrayList<String>> getMatrix() {
        return matrix;
    }

    public int getSize() {
        return size;
    }

}
