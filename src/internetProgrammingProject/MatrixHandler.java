package internetProgrammingProject;

import java.io.*;
import java.util.*;

/**
 *  This class handles Matrix-related tasks
 *  Adapts the functionality  of IHandler to a Matrix object
 *  Case Questions 1-4
 */
public class MatrixHandler implements IHandler {
    private Matrix matrix;
    private Index sourceIndex;

    private Index destinationIndex;
    private boolean doWork;//if its turned false - the client will be stopped being handled and the server won't answer requests


    /**
     *
     * @param fromClient input stream that give us the input from the client for what he wants
     * @param toClient output stream that gives us the functionality to write and deliver answers to the client on a socket
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public void handleClient(InputStream fromClient, OutputStream toClient) throws IOException, ClassNotFoundException {
        /*
        data is sent eventually as bytes
        read data as bytes then transform to meaningful data
        ObjectInputStream and ObjectOutputStream can read and write both primitives and Reference types
         */
        ObjectInputStream objectInputStream = new ObjectInputStream(fromClient); //the input stream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(toClient); // the output stream

        doWork = true;

        while(doWork){
            switch(objectInputStream.readObject().toString()){
                //every case is a different task that the client wants us to do when he delivers a request with the case name
                //after the client wrote "Question 1 it will go to case "Question 1" and do the algorithm, and then it will go back
                //to the while(doWork) line and continue to answer requests, until the clients writes a "stop" request
                //more details on the stop request in the bottom of this file.
                case "Question 1":{
                    int[][] anArray = (int[][]) objectInputStream.readObject();
                    List<Set<Index>> outputSet = new ArrayList<>(); //the list that keeps the sets(each set represents a group of 1's)
                    if(anArray != null) {
                        this.matrix = new Matrix(anArray);
                        DfsVisit<Index> algorithm = new DfsVisit<>();
                        for (int i = 0; i < this.matrix.primitiveMatrix.length; i++) {
                            for (int j = 0; j < this.matrix.primitiveMatrix[0].length; j++) {
                                Index index = new Index(i,j);
                                if (this.matrix.getValue(index) == 1) {
                                    MatrixAsGraph matrixAsGraph = new MatrixAsGraph(this.matrix);
                                    matrixAsGraph.setSource(index); //we turned the 2D array to a graph, so now we can use DFS
                                    Set<Index> addToList = algorithm.traverse(matrixAsGraph); //group of 1's
                                    if (!outputSet.contains(addToList)){ //each group can contain more than 1 index so it won't be added twice or more
                                        outputSet.add(addToList);
                                    }
                                }
                            }
                        }
                    }
                    outputSet.sort(Comparator.comparing(Set::size)); //sort the sets in the list according to the size of each component
                    objectOutputStream.writeObject(outputSet);
                    break;
                }
                case "Question 2": {
                    //Creating a new array, source index and a destination index
                    //Each one of them is coming from the input pipeline
                    int[][] anArray = (int[][]) objectInputStream.readObject();
                    Index source = (Index) objectInputStream.readObject();
                    Index destination = (Index) objectInputStream.readObject();
                    if (anArray != null && source != null && destination != null) {
                        //To create a new Matrix class and two new Index class, we should check if they are null or not
                        //if its null than were just breaking out of the case cause there is no path in a null matrix
                        this.matrix = new Matrix(anArray);
                        this.sourceIndex = new Index(source.row, source.column);
                        this.destinationIndex = new Index(destination.row, destination.column);
                        List<List<Index>> arrayList = new ArrayList<>();
                        MatrixAsGraph matrixAsGraph = new MatrixAsGraph(this.matrix);
                        matrixAsGraph.setSource(this.sourceIndex);
                        DfsVisit<Index> dfsVisit = new DfsVisit<>();
                        Set<Index> algorithm = dfsVisit.traverse(matrixAsGraph);
                        if (algorithm.contains(this.destinationIndex)) {
                            BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch();
                            arrayList = breadthFirstSearch.pathFinder(this.matrix, this.sourceIndex, this.destinationIndex);
                            try {
                                objectOutputStream.writeObject(arrayList);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        } else {
                            try {
                                objectOutputStream.writeObject("No path was found");
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }
                    break;
                }
                case "Question 3": {
                    int[][] anArray = (int[][]) objectInputStream.readObject();
                    if (anArray != null) {
                        this.matrix = new Matrix((anArray));
                        boolean wasTriggered = false; //if it's true then we know this is not a submarine
                        MatrixAsGraph matrixAsGraph = new MatrixAsGraph(this.matrix);
                        DfsVisit<Index> algorithm = new DfsVisit<>();
                        //the 4 following integers represent the minimum index and the maximum index in a connected component
                        int minRowIndex = Integer.MAX_VALUE;
                        int minColIndex = Integer.MAX_VALUE;
                        int maxRowIndex = Integer.MIN_VALUE;
                        int maxColIndex = Integer.MIN_VALUE;
                        Set<Set<Index>> submarines = new HashSet<>(); //the set with all the submarines
                        Set<Index> submarine = null; //represents 1 submarine
                        boolean notASubmarine = false; //if it's true then we know if we should add the submarine to the submarines set or not.
                        for (int i = 0; i < this.matrix.primitiveMatrix.length; i++) {
                            for (int j = 0; j < this.matrix.primitiveMatrix[0].length; j++) {
                                Index source = new Index(i, j);
                                matrixAsGraph.setSource(source);
                                if (this.matrix.primitiveMatrix[i][j] == 1){
                                     submarine = algorithm.traverse(matrixAsGraph);
                                }
                                if (submarine != null) { //A.K.A. matrix[i][j] = 1
                                    if (submarine.size()>1){ //submarine has to be at least two indices
                                        for (Index index:submarine) {
                                            /*
                                            this following if-else statements checks for the min row and column and the max row and column
                                            we know that a submarine is a rectangle.
                                            so by taking each of these 4 integers we can check later that if there is
                                            at least 1 index in the rectangle with a 0 value and not a 1 so this is not a submarine
                                             */
                                            if (index.row < minRowIndex){
                                                minRowIndex = index.row;
                                            }
                                            else{
                                                if (index.row > maxRowIndex){
                                                    maxRowIndex = index.row;
                                                }
                                            }
                                            if (index.column < minColIndex){
                                                minColIndex = index.column;
                                            }
                                            else{
                                                if(index.column > maxColIndex){
                                                    maxColIndex = index.column;
                                                }
                                            }
                                        }
                                        for (int r = minRowIndex; r <= maxRowIndex; r++){
                                            for (int k = minColIndex; k <= maxColIndex; k++){ //checking each index in the rectangle
                                                if (this.matrix.primitiveMatrix[r][k] != 1) {
                                                    notASubmarine = true;
                                                    wasTriggered = true;
                                                    break;
                                                }
                                            }
                                        }
                                        if (!notASubmarine){
                                            //finally, we know it's a submarine, so we can add it to the set of submarines
                                            submarines.add(submarine);
                                            //before moving on to search for another submarine, we have to reset those parameters to their first value
                                            minRowIndex = Integer.MAX_VALUE;
                                            minColIndex = Integer.MAX_VALUE;
                                            maxRowIndex = Integer.MIN_VALUE;
                                            maxColIndex = Integer.MIN_VALUE;
                                        }
                                    }
                                }

                            }
                        }
                        if (!wasTriggered){ //if all submarines were legal
                            objectOutputStream.writeObject("The number of valid submarines in the input is: " + submarines.size());
                        }
                        else{ //if at least 1 illegal submarine was found
                            objectOutputStream.writeObject("Invalid Input");
                        }
                    }
                    break;
                }
                case "Question 4":{
                    //Creating a new array, source index and a destination index
                    //Each one of them is coming from the input pipeline
                    int[][] anArray = (int[][]) objectInputStream.readObject();
                    Index source = (Index) objectInputStream.readObject();
                    Index destination = (Index) objectInputStream.readObject();
                    //To create a new Matrix class and two new Index class, we should check if they are null or not
                    //if its null than were just breaking out of the case cause there is no path in a null matrix
                    if (anArray != null && source != null && destination != null){
                        this.matrix = new Matrix(anArray);
                        this.sourceIndex = new Index(source.row, source.column);
                        this.destinationIndex = new Index(destination.row, destination.column);

                    MinimumWeight minimumWeight = new MinimumWeight(); //creating a new shortestPaths Object
                    LinkedList<List<Index>> pathsList = minimumWeight.findMinimumWeightPath(this.matrix,this.sourceIndex,this.destinationIndex);
                    try {
                        objectOutputStream.writeObject(pathsList); //returning the output to the client
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }}
                    break;
                }
                case "stop":{
                    //eventually if the client writes a stop request then doWork will be turned false, and we will stop handle clients
                    doWork = false;
                    break;
            }
        }
        }
    }
}
