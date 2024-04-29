package entity;

import java.util.*;

public class GoalBased extends Agent {
    Map state;

    public GoalBased(int xPosition, int yPosition, Map map) {
        this.state = new Map(map);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.adjascentContent = new ArrayList<>(4);
    }

    private ArrayList<ArrayList<Integer>> findPath(Map map, int xPos, int yPos){
        return PathFinder.findShortestPath(map.getMatrix(), xPos, yPos, this, true);
    }

    //Tomada de decisao
    public void update(Map map, int opXPos, int opYPos){
        if(this.state.isEmpty()){
            walk(String.valueOf(5)); //noOp
        } else {
            String posValue = map.getMatrix().get(xPosition).get(yPosition);
            String direction = lookUp(map,opXPos, opYPos); // atualiza percepcoes e escolhe a direcao
            if(Objects.equals(posValue, "1") || Objects.equals(posValue, "3")){ // se tiver lixo onde esta - come lixo
                this.score += Integer.parseInt(posValue); //incrementa o score
                map.removeGarbage(xPosition, yPosition);
                state.removeGarbage(xPosition, yPosition);
                System.out.println("GoalB: pos("+getxPosition()+","+getyPosition() + ") /score:" +getScore());
            } else {

                walk(direction); //anda na direcao escolhida
            }
        }
    }

    private void mapUpdate(int opXPos, int opYPos){
        int x = this.xPosition, y = this.yPosition;

        if(!(x-1 == opXPos && y == opYPos)) { // cima
            if ( x > 0 ) {
                this.state.getMatrix().get(x - 1).set(y, this.adjascentContent.get(0));
            }
        } else {
            this.state.getMatrix().get(x-1).set(y,"0");
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
//        System.out.println("----------------------");
//        System.out.println("States(GoalBased): ");
//        this.state.viewMapGoal(this.xPosition, this.yPosition, this.state.getSize());
//        System.out.println("----------------------");
    }

    //Escolhe aleatoriamente uma das posicoes vazias
    private int randomEmptySpace(){
        ArrayList<String> emptySpaces = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (Objects.equals(adjascentContent.get(i), "0")) {
                emptySpaces.add(String.valueOf(i));
            }
        }
        Random random = new Random();
        int gen = random.nextInt(0,emptySpaces.size());

        return Integer.parseInt(emptySpaces.get(gen));
    }

    private int nextStep(ArrayList<ArrayList<Integer>> garbageList, int opXPos, int opYPos){
        int direction=4;
        if(!garbageList.isEmpty()){
            if(garbageList.get(1).get(0) > xPosition){
                direction = 2;
            }else if(garbageList.get(1).get(0) < xPosition){
                direction = 0;
            }else if(garbageList.get(1).get(1) > yPosition){
                direction = 1;
            }else if(garbageList.get(1).get(1) < yPosition){
                direction = 3;
            }
        }

        if((direction==0 && (xPosition-1 == opXPos && yPosition == opYPos)))
            direction = randomEmptySpace();//procura espaço vazio
        if((direction==2 && (xPosition+1 == opXPos && yPosition == opYPos)))
            direction = randomEmptySpace();//procura espaço vazio
        if((direction==1 && (yPosition+1 == opYPos && xPosition == opXPos)))
            direction = randomEmptySpace();//procura espaço vazio
        if(direction==3 && (yPosition-1 == opYPos && xPosition == opXPos))
            direction = randomEmptySpace();//procura espaço vazio

        return direction;
    }

    protected String lookUp(Map map, int opXPos, int opYPos){
        adjascentContent = map.perception(xPosition, yPosition, opXPos, opYPos);
        //System.out.println("AdjascentContent" + adjascentContent);

        // Atualiza o mapa
        mapUpdate(opXPos,opYPos);

        int direction;

        // Encontra o menor caminho e Atualiza a Lista de Lixos
        ArrayList<ArrayList<Integer>> garbageList = findPath(state, xPosition, yPosition);

        // Solicita o proximo passo
        direction = nextStep(garbageList, opXPos, opYPos);

        //determina a direcao
        switch (direction){
            case 0 -> {
                return "U";
            }
            case 1 -> {
                return "R";
            }
            case 2 -> {
                return "D";
            }
            case 3 -> {
                return "L";
            }
            default -> {
                return ""; // noOp
            }
        }
    }
}
