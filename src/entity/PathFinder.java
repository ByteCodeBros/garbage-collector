package entity;
import java.util.ArrayList;

public class PathFinder {

    public PathFinder() {
    }

    public static ArrayList<ArrayList<Integer>> findShortestPath(ArrayList<ArrayList<String>> space, int startX, int startY, Agent ag, boolean collected3) {
        int n = space.size();
        int totalItems = 0;

        // Encontrar todas as posições dos itens de lixo
        ArrayList<ArrayList<Integer>> itemPositions = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(ag instanceof GoalBased){
                    if (space.get(i).get(j).equals("1") || space.get(i).get(j).equals("3")) {
                        ArrayList<Integer> position = new ArrayList<>();
                        position.add(i);
                        position.add(j);
                        itemPositions.add(position);
                        totalItems++;
                    }
                } else if (ag instanceof UtilityBased) {
                    if(collected3){
                        if (space.get(i).get(j).equals("1")) {
                            ArrayList<Integer> position = new ArrayList<>();
                            position.add(i);
                            position.add(j);
                            itemPositions.add(position);
                            totalItems++;
                        }
                    }else {
                        if (space.get(i).get(j).equals("3")) {
                            ArrayList<Integer> position = new ArrayList<>();
                            position.add(i);
                            position.add(j);
                            itemPositions.add(position);
                            totalItems++;
                        }
                    }
                }

            }
        }

        if (totalItems == 0) {
            // Não há itens de lixo para encontrar no mapa do agente
            return new ArrayList<>();
        }

        // Criar um grafo ponderado com distâncias entre todas as posições (S e L)
        ArrayList<ArrayList<Integer>> allPositions = new ArrayList<>(itemPositions);
        ArrayList<Integer> startPosition = new ArrayList<>();
        startPosition.add(startX);
        startPosition.add(startY);
        allPositions.add(startPosition);

        int[][] distances = new int[allPositions.size()][allPositions.size()];
        for (int i = 0; i < allPositions.size(); i++) {
            for (int j = i + 1; j < allPositions.size(); j++) {
                ArrayList<Integer> source = allPositions.get(i);
                ArrayList<Integer> target = allPositions.get(j);
                int dist = Math.abs(source.get(0) - target.get(0)) + Math.abs(source.get(1) - target.get(1));
                distances[i][j] = dist;
                distances[j][i] = dist;
            }
        }

        // Encontrar o menor caminho usando o algoritmo de Dijkstra
        ArrayList<ArrayList<Integer>> shortestPath = new ArrayList<>();
        boolean[] visited = new boolean[allPositions.size()];

        int currentIndex = allPositions.size() - 1; // Comece a partir da posição inicial (posição atual do agente)

        for (int i = 0; i <= totalItems; i++) {
            visited[currentIndex] = true;
            shortestPath.add(allPositions.get(currentIndex));

            int minDist = Integer.MAX_VALUE;
            int nextIndex = -1;

            for (int j = 0; j < allPositions.size(); j++) {
                if (!visited[j] && distances[currentIndex][j] < minDist) {
                    minDist = distances[currentIndex][j];
                    nextIndex = j;
                }
            }

            currentIndex = nextIndex;
        }

        return shortestPath;
    }

}