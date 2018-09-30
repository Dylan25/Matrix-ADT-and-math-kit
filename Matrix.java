//-----------------------------------------------------------------------------
//  Matrix.java
//  An ADT for creating, managing, manipulating, and performing
//  operations on matrices that hold the double data type.
//  Matrix ADT by Dylan Welch Cruzid: dtwelch
//  assignment: pa3
//
//-----------------------------------------------------------------------------

public class Matrix {

    private class Entry {
        // fields
        int col;
        double val;

        // Constructor
        Entry(int col, double val) {
            this.col = col;
            this.val = val;
        }

        // toString():  overrides Object's toString() method
        public String toString() {
            return "(" + String.valueOf(col) + ", " + String.valueOf(val) + ")";
        }

        // equals(): overrides Object's equals() method
        public boolean equals(Object x) {
            boolean eq = false;
            Entry that;
            if (x instanceof Entry) {
                that = (Entry) x;
                eq = ((this.val == that.val) && (this.col == that.col));
            }
            return eq;
        }
    }

    // Fields
    private int n;//dimension of the matrix is nxn
    private List [] row;


    // Constructor
    // Makes a new n x n zero Matrix. pre: n>=1
    Matrix(int n) {
        if (n < 1) {
            throw new RuntimeException(
                    "Matrix Error: Invalid Matrix dimension.");
        }

        this.n = n;

        this.row = new List[n + 1];

        for (int count = 0; count <= n; count++) {
            this.row[count] = new List();
        }
    }


// Access Functions --------------------------------------------------------

    // getSize()
    // Returns n, the number of rows and columns of this Matrix
    int getSize() {
        return n;
    }


    // int getNNZ()
    // Returns the number of non-zero entries in this Matrix
    int getNNZ() {

        int nonz = 0;

        for (int count = 0; count <= n; count++) {
            row[count].moveFront();
            nonz += row[count].length();
        }
        return (nonz);
    }

    //public boolean equals(Object x)
    // overrides Object's equals() method
    public boolean equals(Object x) {

        Matrix Q = (Matrix)x;
        boolean eq = true;
        int count1 = 0;
        eq = (n == Q.n);

        while ((count1 <= n) && eq) {
            eq = (row[count1].equals(Q.row[count1]));
            count1++;
        }

        return (eq);
    }

    // Manipulation procedures --------------------------------------------------------

    // void makeZero()
    // sets this Matrix to the zero state
    void makeZero() {
        for (int count = 0; count <= n; count++) {
            row[count].clear();
        }
    }


    // Matrix copy()
    // returns a new Matrix having the same entries as this Matrix
    Matrix copy() {

        Matrix N = new Matrix(this.n);

        for (int count = 0; count <= n; count++) {
            this.row[count].moveFront();
            while (this.row[count].index() != -1) {
                Entry E = (Entry) row[count].get();
                N.changeEntry(count, E.col, E.val);
                this.row[count].moveNext();
            }
        }
        return (N);
    }

    // changeEntry(int i, int j, double x)
    // changes ith row, jth column of this Matrix to x
    // pre: 1<=i<=getSize(), 1<=j<=getSize()
    void changeEntry(int i, int j, double x) {
        if (!((1 <= i || i <= this.getSize()) || (1 <= j || j <= this.getSize()))) {
            throw new RuntimeException(
                    "Matrix Error: Invalid Matrix coordinates.");
        }

        boolean actionTaken = false;
        if (!row[i].isEmpty()) {
            row[i].moveFront();
            while (row[i].index() != -1) {
                Entry E = (Entry) row[i].get();
                if (E.col == j) {

                    if (x == 0) {
                       row[i].delete();
                    } else {
                        E.val = x;
                        row[i].moveBack();
                    }
                    actionTaken = true;
                } else if (E.col > j && x != 0) {
                    row[i].insertBefore(new Entry(j, x));
                    row[i].moveBack();
                    actionTaken = true;
                }
                row[i].moveNext();
            }
        }
        if (!actionTaken && x != 0) {
            row[i].append(new Entry(j, x));
        }
    }


    //Matrix scalarMult(double x)
    // returns a new Matrix that is the scalar product of this Matrix with x
    Matrix scalarMult(double in) {
        Matrix N = new Matrix(this.n);

        for (int count = 0; count <= n; count++) {
            this.row[count].moveFront();
            while (this.row[count].index() != -1) {
                Entry E = (Entry) row[count].get();
                N.changeEntry(count, E.col, E.val * in);
                this.row[count].moveNext();
            }
        }
        return (N);
    }


    //Matrix add(Matrix M)
    // returns a new Matrix that is the sum of this Matrix with M
    // pre: getSize()==M.getSize()
    Matrix add(Matrix M) {
        if (getSize() != M.getSize()) {
            throw new RuntimeException(
                    "Matrix Error: Cannot add matrices of different size.");
        }

        Matrix N = new Matrix(this.n);

        for (int count = 0; count <= n; count++) {
            rAdd(count, M, N);
        }
        return (N);
    }

    //Matrix sub(Matrix M)
    // returns a new Matrix that is the difference of this Matrix with M
    // pre: getSize()==M.getSize()
    Matrix sub(Matrix M) {
        if (getSize() != M.getSize()) {
            throw new RuntimeException(
                    "Matrix Error: Cannot subtract matrices of different size.");
        }

        Matrix N = new Matrix(this.n);

        for (int count = 0; count <= n; count++) {
            rSub(count, M, N);
        }
        return (N);
    }

    // Matrix transpose()
    // returns a new Matrix that is the transpose of this Matrix
    Matrix transpose() {

        Matrix N = new Matrix(this.n);

        for (int count = 0; count <= n; count++) {
            this.row[count].moveFront();
            while (this.row[count].index() != -1) {
                Entry E = (Entry) row[count].get();
                N.changeEntry(E.col, count, E.val);
                this.row[count].moveNext();
            }
        }
        return (N);
    }

    //Matrix mult(Matrix M)
    // returns a new Matrix that is the product of this Matrix with M
    // pre: getSize()==M.getSize()
    Matrix mult(Matrix M) {

        if (!(this.getSize() == M.getSize())) {
            throw new RuntimeException(
                    "Matrix Error: Cannot multiply matrices of different size.");
        }

        Matrix N = M.transpose();
        Matrix P = new Matrix(N.getSize());

        double sum = 0.0;
        for (int count = 0; count <= n; count++) {
            for (int Ncount = 0; Ncount <= N.getSize(); Ncount++) {
                if (!row[count].isEmpty() && !N.row[Ncount].isEmpty()) {
                    sum = this.rMult(count, Ncount, N);
                    if (sum != 0.0) {
                        P.changeEntry(count, Ncount, sum);
                    }
                }
            }
            sum = 0.0;
        }
        return (P);
    }

    // rMult(int i, int row2, Matrix M)
    // returns the dot product of a row i of one matrix, and row2 of M.
    private double rMult(int i, int row2, Matrix M) {
        Matrix D = M.copy();
        double sumr = 0.0;
            row[i].moveFront();
            D.row[row2].moveFront();
            while (row[i].index() != -1 && D.row[row2].index() != -1) {
                Entry E = (Entry) row[i].get();
                Entry F = (Entry) D.row[row2].get();
                if (E.col == F.col) {
                    sumr += E.val * F.val;
                    row[i].moveNext();
                    D.row[row2].moveNext();
                }
                else if (E.col > F.col) {
                    D.row[row2].moveNext();
                }
                else {
                    row[i].moveNext();
                }
            }
        return (sumr);
    }

    // rAdd(int i, Matrix M, Matrix N)
    // Utilized by the add function
    // adds row i of the calling object with i of Matrix M
    // and generates row i of Matrix N
    private void rAdd(int i, Matrix M, Matrix N) {
        double sumr = 0.0;

        Matrix D = M.copy();

        if (!(row[i].isEmpty() && D.row[i].isEmpty())) {
            D.row[i].moveFront();
            row[i].moveFront();

            while ((row[i].index() != -1) || (D.row[i].index() != -1)) {

                if (row[i].index() != -1 && D.row[i].index() == -1) {
                    Entry E = (Entry) row[i].get();
                    N.row[i].append(E);
                    row[i].moveNext();
                }
                if (D.row[i].index() != -1 && row[i].index() == -1) {
                    Entry F = (Entry) D.row[i].get();
                    N.row[i].append(F);
                    D.row[i].moveNext();
                }
                if (row[i].index() != -1 && D.row[i].index() != -1) {
                    Entry E = (Entry) row[i].get();
                    Entry F = (Entry) D.row[i].get();
                    if (F.col == E.col) {
                        sumr = E.val + F.val;
                        N.changeEntry(i, F.col, sumr);
                        row[i].moveNext();
                        D.row[i].moveNext();
                    } else if (F.col > E.col) {
                        N.changeEntry(i, E.col, E.val);
                        row[i].moveNext();
                    } else if (E.col > F.col) {
                        N.changeEntry(i, F.col, F.val);
                        D.row[i].moveNext();
                    }
                }
            }
        }
    }

    // rSub(int i, Matrix M, Matrix N)
    // Utilized by the sub function
    // subtracts row i of M from row i of the calling object
    // and generates row i of N
    private void rSub(int i, Matrix M, Matrix N) {

        double sumr = 0.0;

        Matrix D = M.copy();

        if (!(row[i].isEmpty() && D.row[i].isEmpty())) {
            D.row[i].moveFront();
            row[i].moveFront();

            while ((row[i].index() != -1) || (D.row[i].index() != -1)) {

                if (row[i].index() != -1 && D.row[i].index() == -1) {
                    Entry E = (Entry) row[i].get();
                    N.row[i].append(new Entry(E.col, E.val));
                    row[i].moveNext();
                }
                if (D.row[i].index() != -1 && row[i].index() == -1) {
                    Entry F = (Entry) D.row[i].get();
                    N.row[i].append(new Entry(F.col, -F.val));
                    D.row[i].moveNext();
                }
                if (row[i].index() != -1 && D.row[i].index() != -1) {
                    Entry E = (Entry) row[i].get();
                    Entry F = (Entry) D.row[i].get();
                    if (F.col == E.col) {
                        sumr = E.val - F.val;
                        N.changeEntry(i, F.col, sumr);
                        row[i].moveNext();
                        D.row[i].moveNext();
                    } else if (F.col > E.col) {
                        N.changeEntry(i, E.col, E.val);
                        row[i].moveNext();
                    } else if (E.col > F.col) {
                        N.changeEntry(i, F.col, -F.val);
                        D.row[i].moveNext();
                    }
                }

            }
        }

    }


// Other functions ------------------------------------------


    // toString()
    // overrides Object's toString() method
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= n; i++) {
            if (!row[i].isEmpty()) {
                sb.append(i + ": ");
                sb.append(row[i].toString());
                sb.append("\n");
            }
        }
        return new String(sb);
    }
}
