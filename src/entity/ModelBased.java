package entity;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class ModelBased extends Agent {
    Map state;
    String prevMov;
    int startPos;

    public ModelBased(int xPosition, int yPosition, int size) {
        this.state = new Map(size);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.startPos = yPosition / (size-1);
        this.prevMov = "";
        this.adjascentContent = new ArrayList<>(4);
    }

    //Tomada de decisao
    public void update(Map map, int opXPos, int opYPos){
        String posValue = map.getMatrix().get(xPosition).get(yPosition);
        String direction = lookUp(map,opXPos, opYPos); // atualiza percepcoes e escolhe a direcao
        if(Objects.equals(posValue, "1") || Objects.equals(posValue, "3")){ // se tiver lixo onde esta - come lixo
            this.score += Integer.parseInt(posValue); //incrementa o score
            map.removeGarbage(xPosition, yPosition);
            this.state.removeGarbage(xPosition, yPosition);
            this.prevMov = "C";
            System.out.println("ModelB: pos("+getxPosition()+","+getyPosition() + ") /score:" +getScore());
        } else {
            walk(direction); //anda na direcao escolhida
        }
    }

    private void mapUpdate(int opXPos, int opYPos){
        int x = this.xPosition, y = this.yPosition;

        if(!(x-1 == opXPos && y == opYPos)) { // cima
            if ( x > 0 ) {
                this.state.getMatrix().get(x - 1).set(y, this.adjascentContent.get(0));
            }
        } else {
            this.state.getMatrix().get(x - 1).set(y,"0");
        }

        if(!(y+1 == opYPos && x == opXPos)) { // direita
            if(y < state.getSize()-1) {
                this.state.getMatrix().get(x).set(y + 1, this.adjascentContent.get(1));
            }
        } else {
            this.state.getMatrix().get(x).set(y+1,"0");
        }

        if(!(x+1 == opXPos && y == opYPos)) { // baixo
            if(x < state.getSize()-1) {
                this.state.getMatrix().get(x + 1).set(y, this.adjascentContent.get(2));
            }
        } else {
            this.state.getMatrix().get(x+1).set(y,"0");
        }

        if(!(y-1 == opYPos && x == opXPos)) { // esquerda
            if(y > 0) {
                this.state.getMatrix().get(x).set(y - 1, this.adjascentContent.get(3));
            }
        } else {
            this.state.getMatrix().get(x).set(y-1,"0");
        }

        //System.out.println("Map of States: ");
        //this.state.viewMap(this.xPosition, this.yPosition, this.state.getSize());
    }

    // Verifica se ha lixo na vizinhanca-4 - retorna 0 se nao houver e o indice se houver
    private int garbageIndex(){
        String elm;
        for (int i = 0; i < 4;) {
            elm = adjascentContent.get(i);
            if (Objects.equals(elm, "3") || Objects.equals(elm, "1")) {
                return i; // indice do primeiro lixo identificado
            }
            if(i == 0) i = 2;
            else if (i == 2) {
                i = 1;
            }else if (i == 1) {
                i = 3;
            }else{
                i = 4;
            }
        }
        return -1; // nao ha lixo
    }

    private int getDirection(Map map, int opXPos, int opYPos){
        int direction = 3;
        int up = 0; int right = 1; int down = 2; int left = 3;

        boolean leftBound = (this.yPosition==0);
        boolean rightBound = (this.yPosition==map.getSize()-1);
        boolean startedLeft = this.startPos==0;
        boolean startedRight = this.startPos==1;
        boolean evenLine = this.xPosition%2==0;

        if(this.xPosition%3 == 1){ // Linhas do trajeto

            if(evenLine){
                if(startedLeft) {
                    if(leftBound){
                        direction = down;
                    } else {
                        direction = left;
                    }

                }else {
                    if(rightBound){
                        direction = down;
                    } else {
                        direction = right;
                    }
                }

            } else {
                if(startedRight) {
                    if(leftBound){
                        direction = down;
                    } else {
                        direction = left;
                    }
                }else {
                    if(rightBound){
                        direction = down;
                    } else {
                        direction = right;
                    }
                }
            }

        } else if(this.xPosition%3 == 0){  // Linhas fora do trajeto
            direction = down;

        } else if(this.xPosition%3 == 2) { // Linhas fora do trajeto
            if((!leftBound && !rightBound) || Objects.equals(this.prevMov, "C")){
                direction = up;

            } else if(evenLine) {
                if((startedLeft && leftBound) || (startedRight && rightBound)) {
                    direction = down;
                }else if((startedLeft && rightBound) || (startedRight && leftBound)){
                    direction = down;
                }
            }else{ // Linha impar
                if((startedLeft && rightBound) || (startedRight && leftBound)){
                    direction = down;
                }else if((startedLeft && leftBound) || (startedRight && rightBound)){
                direction = down;
                }
            }
        }

        if(direction%2 == 0){
            if((direction==0 && (xPosition-1 == opXPos && yPosition == opYPos)) || (direction==2 && (xPosition+1 == opXPos && yPosition == opYPos)))
                direction = randomEmptySpace();
        } else {
            if((direction==1 && (yPosition+1 == opYPos && xPosition == opXPos)) || (direction==3 && (yPosition-1 == opYPos && xPosition == opXPos)))
                direction = randomEmptySpace();
        }
        return direction;
    }


    private int randomEmptySpace(){
        ArrayList<String> emptySpaces = new ArrayList<>();

        if(this.xPosition%3 == 1){
            for (int i = 0; i < 2; i+=2) {
                if (Objects.equals(adjascentContent.get(i), "0")) {
                    emptySpaces.add(String.valueOf(i));
                }
            }
        }else{
            for (int i = 1; i < 4; i+=2) {
                if (Objects.equals(adjascentContent.get(i), "0")) {
                    emptySpaces.add(String.valueOf(i));
                }
            }
        }

        //System.out.println("emptySpaces" + emptySpaces);
        Random random = new Random();
        int gen = random.nextInt(0,emptySpaces.size());

        return Integer.parseInt(emptySpaces.get(gen));
    }


    protected String lookUp(Map map, int opXPos, int opYPos){
        boolean leftBound = (this.yPosition==0);
        boolean rightBound = (this.yPosition==map.getSize()-1);
        boolean bottomBound = (this.xPosition==map.getSize()-1);
        boolean startedLeft = this.startPos==0;
        boolean startedRight = this.startPos==1;


        //Atualiza as percepcoes
        adjascentContent = map.perception(xPosition, yPosition, opXPos, opYPos);
        //System.out.println("AdjascentContent" + adjascentContent);

        //atualiza o mapa
        mapUpdate(opXPos,opYPos);

        //atualiza direcao
        int direction = garbageIndex();

        if(direction<0 && !((startedLeft && rightBound && bottomBound) || (startedRight && leftBound && bottomBound))){
            direction = getDirection(map, opXPos, opYPos);
        }else if(this.prevMov.equals("C") || (this.prevMov.isEmpty())){
            if (this.xPosition%3 != 1){
                direction = getDirection(map, opXPos, opYPos);
            }else{
                direction = garbageIndex();
            }
        }else{
            direction = garbageIndex();
        }

        if(((startedLeft && rightBound && bottomBound) || (startedRight && leftBound && bottomBound)) && direction<0){
            direction = 5;
        }


        //determina a direcao
        switch (direction) {
            case 0 -> {
                this.prevMov = "U";
                return this.prevMov;
            }
            case 1 -> {
                this.prevMov = "R";
                return this.prevMov;
            }
            case 2 -> {
                this.prevMov = "D";
                return this.prevMov;
            }
            case 3 -> {
                this.prevMov = "L";
                return this.prevMov;
            }
            default -> {
                this.prevMov = "";
                return this.prevMov; // noOp
            }
        }
    }
}
