package entity;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class SimpleReflex extends Agent {

    public SimpleReflex(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.adjascentContent = new ArrayList<>();
    }

    //Tomada de decisao
    public void update(Map map, int opXPos, int opYPos){
        String posValue = map.getMatrix().get(xPosition).get(yPosition);
        String direction = lookUp(map,opXPos, opYPos); // atualiza percepcoes e escolhe a direcao
        if(Objects.equals(posValue, "1") || Objects.equals(posValue, "3")){ // se tiver lixo onde esta - come lixo
            this.score += Integer.parseInt(posValue); //incrementa o score
            map.removeGarbage(xPosition, yPosition);
            System.out.println("SimpleR: pos("+getxPosition()+","+getyPosition() + ") /score:" +getScore());
        } else {
            walk(direction); //anda na direcao escolhida
        }
    }

    // Verifica se ha lixo na vizinhanca-4 - retorna 0 se nao houver e o indice se houver
    private int garbageIndex(){
        String elm;
        for (int i = 0; i < 4; i++) {
            elm = adjascentContent.get(i);
            if (Objects.equals(elm, "3") || Objects.equals(elm, "1")) {
                return i; // indice do primeiro lixo identificado
            }
        }
        return -1; // nao ha lixo
    }

    //Escolhe aleatoriamente uma das posicoes vazias
    private int randomEmptySpace(){
        ArrayList<String> emptySpaces = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (Objects.equals(adjascentContent.get(i), "0")) {
                emptySpaces.add(String.valueOf(i));
            }
        }
        //System.out.println("emptySpaces" + emptySpaces);
        Random random = new Random();
        int gen = random.nextInt(0,emptySpaces.size());

        return Integer.parseInt(emptySpaces.get(gen));
    }

    public String lookUp(Map map, int opXPos, int opYPos) {
        //atualiza as percepcoes
        adjascentContent = map.perception(xPosition, yPosition, opXPos, opYPos);
        //System.out.println("adjascentContent" + adjascentContent);

        int direction = garbageIndex();

        // Se nao houver lixo - Escolhe uma direcao vazia
        if (direction<0) {
            direction = randomEmptySpace();
        }

        //determina a direcao
        switch (direction) {
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
                return "";
            }
        }
    }
}
