package internetProgrammingProject;

import java.io.Serializable;
import java.util.*;

/**
 * This class represents a node in graph
 * @param <T> we will wrap the node in an index to convert a matrix to a graph(MatrixAsGraph file)
 */
public class Node<T> implements Serializable{
    private static final Long serialVersionUID = 1L;
    private T data; //the data in the node
    private Node<T> parent; //the connected parent node to the current node

    /**
     * This constructor creates a new node with a data and a parent
     * @param data data in the node(can be any type due to the T type parameter)
     * @param parent the parent node of the created node
     */
    public Node(T data, Node<T> parent){
        this.data = data;
        this.parent = parent;
    }

    /**
     * This constructor creates a new node with a data and a null parent using the first constructor
     * @param data data in the node(can be any type due to the T type parameter)
     */
    public Node(T data){
        this(data,null);
    }

    /**
     * This method is a getter method for the data in the node
     * @return returns the data in the node
     */
    public T getData() {
        return data;
    }

    /**
     * This method is an overriding method for equals that checks for equal nodes
     * @param o the object to check if it equals to the current node
     * @return returns true if the object given is equal to the current node, false if isn't
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node<?> state1 = (Node<?>) o;
        return Objects.equals(data, state1.data);
    }

    /**
     * This method is an overriding method for hashCode that implementing the hash function for the current data node
     * @return
     */
    @Override
    public int hashCode() {
        return data != null ? data.hashCode():0;
        //if(data != null){
        //return data.hashcode()}else{
        //return 0}
    }

}


