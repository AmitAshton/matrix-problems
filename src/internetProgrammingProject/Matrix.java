package internetProgrammingProject;

import java.util.stream.Collectors;
import java.io.Serializable;
import java.util.*;

/**
 * This class represents a 2D array
 */
public class Matrix implements Serializable {
    int[][] primitiveMatrix;
    int numOfRows, numOfColumns;

    /**
     * This constructor is copying a 2D array to a matrix object
     * @param oArray the array to copy for a matrix object
     */
    public Matrix(int[][] oArray){
        List<int[]> list = new ArrayList<>();
        for (int[] row : oArray) { //for each row in the given array, we add the row to the list by cloning it to a 1D array
            int[] clone = row.clone();
            list.add(clone);
        }
        primitiveMatrix = list.toArray(new int[0][]);
        this.numOfRows = primitiveMatrix.length;
        this.numOfColumns = primitiveMatrix[0].length;
    }

    /**
     * This method overriding the toString method and represents a matrix as a 2D array
     * @return string representation of the matrix
     */
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int[] row : primitiveMatrix) {
            stringBuilder.append(Arrays.toString(row));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }


    /**
     * This method returns the 8-directional neighbors for a given index in the matrix
     * @param index the given index to check his neighbors
     * @return returns a collection of the neighbors of a given index
     */
    public Collection<Index> getNeighbors(final Index index){
        Collection<Index> list = new ArrayList<>();
        int extracted = -1;
        //for each of these 8-directional neighbors, we are catching the exceptions to Array out of bounds
        //for example: if the index is (0,0) and we try to get the top neighbor it will throw an exception, and we catch it
        //for each neighbor, we add it to the list we created
        try{
            //top neighbor
            extracted = primitiveMatrix[index.row-1][index.column];
            list.add(new Index(index.row-1,index.column));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            //right neighbor
            extracted = primitiveMatrix[index.row][index.column+1];
            list.add(new Index(index.row,index.column+1));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            //bottom neighbor
            extracted = primitiveMatrix[index.row+1][index.column];
            list.add(new Index(index.row+1,index.column));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            //left neighbor
            extracted = primitiveMatrix[index.row][index.column-1];
            list.add(new Index(index.row,index.column-1));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            //left-top neighbor
            extracted = primitiveMatrix[index.row-1][index.column-1];
            list.add(new Index(index.row-1,index.column-1));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            //right-top neighbor
            extracted = primitiveMatrix[index.row-1][index.column+1];
            list.add(new Index(index.row-1,index.column+1));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            //left-bottom neighbor
            extracted = primitiveMatrix[index.row+1][index.column-1];
            list.add(new Index(index.row+1,index.column-1));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        try{
            //right-bottom neighbor
            extracted = primitiveMatrix[index.row+1][index.column+1];
            list.add(new Index(index.row+1,index.column+1));
        }catch (ArrayIndexOutOfBoundsException ignored){}
        return list;
    }

    /**
     * This method returns the value in a given Index in the matrix
     * @param index the given index to check for the value
     * @return the value of the matrix in the given index
     */
    public int getValue(final Index index){
        return primitiveMatrix[index.row][index.column];
    }

    /**
     * This method filtering the neighbors' method to only neighbors with a value of 1
     * @param index the given index
     * @return a filtered collection of neighbors indices with only value of 1
     */
    public Collection<Index> getCloseOneIndices(Index index) {
        ArrayList<Index> indices = new ArrayList<>();
        this.getNeighbors(index).stream().filter(i-> getValue(i)==1)
                .map(neighbor->indices.add(neighbor)).collect(Collectors.toList());
        return indices;
    }

}
