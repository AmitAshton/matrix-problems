package internetProgrammingProject;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents an index in a 2D matrix
 */
public class Index implements Serializable {
    int row, column;

    /**
     * This constructor initialize an Index object with row and column
     * @param row the row number of the index
     * @param column the column number of the index
     */
    public Index(final int row, final int column) {
        this.row=row;
        this.column=column;
    }

    /**
     * This method checks if the given object is equal for the index or not
     * @param o the given object to check if equals to the index
     * @return true if the given object is equal to the index, false if isn't
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Index index = (Index) o;
        return row == index.row && column == index.column;
    }

    /**
     * This method is implementing the hash code for the Index Class
     * @return the hash code on the row and column attributes
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    /**
     * This method is Overriding the toString method to print an index
     * @return a string representation of an index
     */
    @Override
    public String toString() {
        return "("+row + "," + column + ')';
    }
}
