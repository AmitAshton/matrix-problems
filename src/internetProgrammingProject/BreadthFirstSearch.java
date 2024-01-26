package internetProgrammingProject;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @bigListOfNodes
 * @arrayList
 * @queue
 * @path
 */

/**
 * This class is used for Question 2 and implements the BFS algorithm
 */
public class BreadthFirstSearch {

    /**
     * This method implements BFS famous algorithm and returns the shortest paths frm a source index to a destination index
     * @param array the given matrix in witch we check for the shortest path
     * @param source the source index
     * @param destination the destination index
     * @return returns a list of the shortest paths between the source and destination indices
     */
    public LinkedList<List<Index>> pathFinder(Matrix array, Index source, Index destination) {
        double maximum= Double.POSITIVE_INFINITY; //infinity distance
        LinkedList<List<Index>> lists = new LinkedList<>();
        LinkedList<List<Index>> temp = new LinkedList<>();
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        Queue<List<Index> > listQueue = new LinkedList<>();
        List<Index> indicesPath = new ArrayList<>();
        indicesPath.add(source);
        listQueue.offer(indicesPath);

        while (!listQueue.isEmpty())
        {
            indicesPath = listQueue.poll();

            if(indicesPath.size()>maximum)
            {
                indicesPath.clear();
                break;
            }

            Index index = indicesPath.get(indicesPath.size()-1);


            if(index.equals(destination))
            {
                if (indicesPath.size() > maximum)
                {
                    indicesPath.clear();
                    listQueue.remove(indicesPath);
                    break;
                }
                else if (indicesPath.size() <= maximum)
                {
                    maximum=indicesPath.size();
                    integerArrayList.add(indicesPath.size());
                    temp.add(indicesPath);
                }
            }

            List<Index> reachPath = (List<Index>) array.getCloseOneIndices(index);
            lists.add(reachPath);
            List<Index> nodeList = (ArrayList)lists.get(lists.size()-1);



            for (int i = 0; i < nodeList.size(); i++)
            {
                for (int j = 0; j < indicesPath.size(); j++){
                    if (!indicesPath.contains(nodeList.get(i))){
                        List<Index> newestPath = new ArrayList<>(indicesPath);
                        newestPath.add(nodeList.get(i));
                        listQueue.offer(newestPath);
                    }
                }
            }
        }
        return temp;
    }
}

