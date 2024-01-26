package internetProgrammingProject;

import java.util.Collection;

/**
 * This interface defines the common functionality required of all concrete graphs
 */
public interface Graph<T> {
    Node<T> getRoot(); //the root of the graph
    Collection<Node<T>> getReachableNodes(Node<T> aNode); //the reachable nodes of the given node
}
