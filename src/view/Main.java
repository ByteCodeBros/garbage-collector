package view;
import control.*;

import javax.swing.*;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean loopMain = true;

        Manager manager = new Manager();
        System.out.println("Bem-Vindo(a) ao Menu de Teste de Agentes");
        while(loopMain) {
            System.out.println("Selecione dois Agentes:\n"+
                    " 1- Simple Reflex Agent\n" +
                    " 2- Model Based Agent\n" +
                    " 3- Goal Based Agent\n" +
                    " 4- Utility Based Agent\n");
            System.out.println("Agente 1: ");
            int agent1 = input.nextInt();
            System.out.println("Agente 2: ");
            int agent2 = input.nextInt();
            boolean agent1Check = agent1 > 0 && agent1 < 5;
            boolean agent2Check = agent2 > 0 && agent2 < 5;
            if(agent1Check && agent2Check){
                manager.gameStart(agent1, agent2);
            } else {
                System.out.println("Insira um valor válido\n");
            }
        }
    }
}

