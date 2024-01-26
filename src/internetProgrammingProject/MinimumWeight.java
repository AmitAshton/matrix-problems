package internetProgrammingProject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This class is used to handle Question 4 and deliver the answer
 * to the MatrixHandler File
 */
public class MinimumWeight {

    /**
     * This method checks whether the given index is in the path or not
     * @param index the given index
     * @param path the given list that represents the path
     * @return return true if it is not in the set, false if it is
     */
    public static boolean noVisit(Index index, List<Index> path)
    {
        for(int i = 0; i < path.size(); i++)
            if (path.contains(index))
                return false;
        return true;
    }

    /**
     /**
     * This method returns
     * @param matrix the given matrix
     * @param source the given source index
     * @param destination the given destination index
     * @return list of minimum weight paths from source index to destination index
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

            List<Index> achievable = (List<Index>) matrix.getNeighbors(index);
            pathsList.add(achievable);

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

