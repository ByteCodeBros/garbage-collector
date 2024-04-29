package control;
import entity.*;

public class Manager {
    Agent ag1, ag2;
    int size = 20;

    public Agent getAg1() {
        return ag1;
    }

    public Agent getAg2() {
        return ag2;
    }

    int garbageCount=10;
    Map map = new Map(size, 0);
    UI ui = new UI(map.getMatrix(), 0, 0, 0, size-1);

    public Manager(){

    }

    public void gameStart(int a1, int a2) {
        this.map = new Map(size, garbageCount);
        switch (a1) {
            case 1 -> this.ag1 = new SimpleReflex(0,0);
            case 2 -> this.ag1 = new ModelBased(0,0, size);
            case 3 -> this.ag1 = new GoalBased(0,0, map);
            case 4 -> this.ag1 = new UtilityBased(0,0, map);
        }
        switch (a2) {
            case 1 -> this.ag2 = new SimpleReflex(0,size-1);
            case 2 -> this.ag2 = new ModelBased(0,size-1, size);
            case 3 -> this.ag2 = new GoalBased(0,size-1, map);
            case 4 -> this.ag2 = new UtilityBased(0,size-1, map);
        }
        duel();
    }

    public void duel() {
        //map.viewMap(ag1.getxPosition(), ag1.getyPosition(), ag2.getxPosition(), ag2.getyPosition(), size);
        this.ui.interfaceUpdate(this.map.getMatrix(), ag1.getxPosition(), ag1.getyPosition(), ag2.getxPosition(), ag2.getyPosition());

        boolean loopGame = true;
        while(loopGame){
            ag1.update(map, ag2.getxPosition(), ag2.getyPosition());

//            System.out.println("----------------------");
//            System.out.println("Map: depois do ag1");
//            map.viewMap(ag1.getxPosition(), ag1.getyPosition(), ag2.getxPosition(), ag2.getyPosition(), size);
//            System.out.println("----------------------");

            ag2.update(map, ag1.getxPosition(), ag1.getyPosition());

//            System.out.println("----------------------");
//            System.out.println("Map: depois do ag2");
//            map.viewMap(ag1.getxPosition(), ag1.getyPosition(), ag2.getxPosition(), ag2.getyPosition(), size);
//            System.out.println("----------------------");

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            this.ui.interfaceUpdate(this.map.getMatrix(),ag1.getxPosition(), ag1.getyPosition(), ag2.getxPosition(), ag2.getyPosition());
            if (map.isEmpty()) {
                System.out.println("\nPontuação - Agente 1: " + ag1.getScore());
                System.out.println("Pontuação - Agente 2: " + ag2.getScore());
                System.out.println("\n");
                loopGame = false;
            }
        }
    }
}
