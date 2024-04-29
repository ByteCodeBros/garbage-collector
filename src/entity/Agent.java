package entity;
import java.util.ArrayList;
import java.util.Objects;


public abstract class Agent {

    //Percepcao dos agentes
    protected int xPosition;
    protected int yPosition;
    protected ArrayList<String> adjascentContent;
    protected int score=0;

    //Acoes
    protected abstract String lookUp(Map map, int opXPos, int opYPos);

    protected void walk(String direction) {
        switch (direction) {
            case "L" -> this.yPosition--;
            case "R" -> this.yPosition++;
            case "U" -> this.xPosition--;
            case "D" -> this.xPosition++;
            default -> {} // noOp
        }
    }

    //Tomada de decisao
    public abstract void update(Map map, int opXPos, int opYPos);
//        String posValue = map.getMatrix().get(xPosition).get(yPosition);
//        String direction = lookUp(map,opXPos, opYPos); // atualiza percepcoes e escolhe a direcao
//        if(Objects.equals(posValue, "1") || Objects.equals(posValue, "3")){ // se tiver lixo onde esta - come lixo
//            this.score += Integer.parseInt(posValue); //incrementa o score
//            map.removeGarbage(xPosition, yPosition);
//        } else {
//            walk(direction); //anda na direcao escolhida
//        }
//    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public int getScore(){
        return this.score;
    }

}
