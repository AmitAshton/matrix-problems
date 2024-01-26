package internetProgrammingProject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ShortestPaths {

    public ShortestPaths() {
    }

    public static boolean noVisit(Index index, List<Index> path)
    {
        for(int i = 0; i < path.size(); i++)
            if (path.contains(index))
                return false;
        return true;
    }

    /**
     * @allPathsList
     * @finalList - list of path from source index to destination index
     * @param matrix
     * @param source
     * @param destination
     * @return
     */

    public LinkedList<List<Index>> findMinimumWeightPath(Matrix matrix, Index source, Index destination) {
        double infinity = Double.POSITIVE_INFINITY;
        LinkedList<List<Index>> pathsList = new LinkedList<>();
        LinkedList<List<Index>> lastList = new LinkedList<>();
        Queue<List<Index>> listQueue = new LinkedList<>();
        List<Index> indicesPathList = new ArrayList<>();
        indicesPathList.add(source);
        listQueue.offer(indicesPathList);

        while (!listQueue.isEmpty()) {
            indicesPathList = listQueue.poll();
            Index index = indicesPathList.get(indicesPathList.size() - 1);
            int sumCounter = 0;
            if (index.equals(destination))
            {
                for (Index indexInPathList : indicesPathList) {
                    sumCounter += matrix.getValue(indexInPathList);
                }
                if (sumCounter == infinity) {
                    infinity = sumCounter;
                    lastList.add(indicesPathList);
                    System.out.println("sum: " + sumCounter);
                }
                if (sumCounter < infinity) {
                    lastList.clear();
                    infinity = sumCounter;
                    lastList.add(indicesPathList);
                }
            }

            List<Index> achieveable = (List<Index>) matrix.getNeighbors(index);
            pathsList.add(achieveable);

            List<Index> indexList = pathsList.get(pathsList.size() - 1);

            for (int i = 0; i < indexList.size(); i++) {
                if (noVisit(indexList.get(i), indicesPathList)) {
                    List<Index> newPath = new ArrayList<>(indicesPathList);
                    newPath.add(indexList.get(i));
                    listQueue.offer(newPath);
                }
            }
        }

        return lastList;
    }
}

