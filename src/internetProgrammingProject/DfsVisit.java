package internetProgrammingProject;

import java.io.Serializable;
import java.util.*;

/**
 * This class implements the famous algorithm DFS.
 * Its adapts the algorithm to the MatrixAsGraph class, so we can use it with a 2D array and not just a graph
 * @param <T>
 */
public class DfsVisit<T> implements Serializable {
    private Stack<Node<T>> workingStack; //stack for the visited nodes that we discovered
    private Set<Node<T>> finished; //set for the finished nodes

    /**
     * This constructor creates a new object of this class with declaring a new stack and a new linked hash set
     */
    public DfsVisit(){
        workingStack = new Stack<>();
        finished = new LinkedHashSet<>();
    }

    /**
     * This method traversing the graph to find the connected component of 1's for the given graph
     * @param aGraph the graph to find the connected component
     * @return returns a set of the connected component nodes(we will use it for index and finding 1's component)
     */
    public Set<T> traverse(Graph<T> aGraph){
        workingStack.push(aGraph.getRoot()); //starting by adding the source index to the stack
        while (!workingStack.empty()){
            Node<T> removed = workingStack.pop(); //remove the first node in the stack and keep it in a temp variable
            finished.add(removed); // add to finished
            Collection<Node<T>> reachableNodes = aGraph.getReachableNodes(removed); //creates a collection of 1's node due to the getReachableNodes method
            for(Node<T> reachableNode :reachableNodes){ //for each node in the collection we wil push it to stack if it's not there or in the finished set
                if (!finished.contains(reachableNode) &&
                        !workingStack.contains(reachableNode)){
                    workingStack.push(reachableNode);
                }
            }
        }
        Set<T> blackSet = new LinkedHashSet<>();
        for (Node<T> node: finished) //for each node in finished set we will and it to the blackSet
            blackSet.add(node.getData());
        finished.clear(); //clearing the finished set
        return blackSet;
    }

}

