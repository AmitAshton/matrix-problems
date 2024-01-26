package internetProgrammingProject;


import java.io.Serializable;
import java.util.*;


/**
 * This class uses the adapter pattern, also known as
 * wrapper/decorator/adapter.
 * It adapts a Matrix to the functionality of Graph Interface
 */
public class MatrixAsGraph implements Graph<Index>, Serializable {
    private Matrix innerMatrix;
    private Index source;

    /**
     * This constructor creates a new MatrixAsGraph object
     * @param matrix setting the matrix attribute to the matrix given
     */
    public MatrixAsGraph(Matrix matrix){
        if (matrix != null) {
            this.innerMatrix = matrix;
        }
        else{
            System.out.println("Matrix hasn't been created");
        }
    }

    /**
     * This method is a setter method for the source Index attribute
     * @param source the source Index to set as the source of this class
     */
    public void setSource(Index source){
        if (source != null) {
            this.source = source;
        }
        else{
            System.out.println("Source hasn't been updated");
        }
    }

    /**
     * This method overriding the getRoot method from Graph interface
     * @return returns the root of the graph as a node that wraps an Index
     */
    @Override
    public Node<Index> getRoot() {
        return new Node<>(source);
    }


    /**
     * A reachable node is a node that wraps a neighboring index whose value is equal to 1
     * this way we create a node for each Index whose value is 1 and is a neighbor of the Index that wrapped in the node and save it to a list
     * finally, we get the connected 1's component for an index whose value is 1
     * @param aNode the node to check for his reachable nodes
     * @return returns a list with all the nodes that wraps a value of "1" Index(A.K.A. the connected component)
     */
    @Override
    public Collection<Node<Index>> getReachableNodes(Node<Index> aNode) {
        if (innerMatrix.getValue(aNode.getData())==1){
            List<Node<Index>> reachableNodes = new ArrayList<>();
            for(Index index:innerMatrix.getNeighbors(aNode.getData())){
                if (innerMatrix.getValue(index)==1){
                    Node<Index> singleNode = new Node<>(index,aNode);
                    reachableNodes.add(singleNode);
                }
            }
            return reachableNodes;
        }
        return null;
    }
}
