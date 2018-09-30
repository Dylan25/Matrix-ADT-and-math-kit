//-----------------------------------------------------------------------------
//  Note: the base of this file was provided on the cs101 website under pa3 examples
//  as MatrixClient
//  MatrixTest.java
//  Dylan Welch Cruzid: dtwelch
//  Assignment: pa3
//  A test client for the Matrix ADT
//  Tests to sets of Matrices with all operations
//-----------------------------------------------------------------------------

public class MatrixTest{
    public static void main(String[] args){
        int i, j, n=100000;
        Matrix A = new Matrix(n);
        Matrix B = new Matrix(n);

        A.changeEntry(1,1,1); B.changeEntry(1,1,1);
        A.changeEntry(1,2,2); B.changeEntry(1,2,0);
        A.changeEntry(1,3,3); B.changeEntry(1,3,1);
        A.changeEntry(2,1,4); B.changeEntry(2,1,0);
        A.changeEntry(2,2,5); B.changeEntry(2,2,1);
        A.changeEntry(2,3,6); B.changeEntry(2,3,0);
        A.changeEntry(3,1,7); B.changeEntry(3,1,1);
        A.changeEntry(3,2,8); B.changeEntry(3,2,1);
        A.changeEntry(3,3,9); B.changeEntry(3,3,1);

        System.out.println(A.getNNZ());
        System.out.println(A);

        System.out.println(B.getNNZ());
        System.out.println(B);

        Matrix C = A.scalarMult(1.5);
        System.out.println(C.getNNZ());
        System.out.println(C);

        Matrix D = A.add(A);
        System.out.println(D.getNNZ());
        System.out.println(D);

        Matrix E = A.sub(A);
        System.out.println(E.getNNZ());
        System.out.println(E);

        Matrix F = B.transpose();
        System.out.println(F.getNNZ());
        System.out.println(F);

        Matrix G = B.mult(B);
        System.out.println(G.getNNZ());
        System.out.println(G);

        Matrix H = A.copy();
        System.out.println(H.getNNZ());
        System.out.println(H);
        System.out.println(A.equals(H));
        System.out.println(A.equals(B));
        System.out.println(A.equals(A));

        A.makeZero();
        System.out.println(A.getNNZ());
        System.out.println(A);

        Matrix X = new Matrix(n);
        Matrix Y = new Matrix(n);

        X.changeEntry(1,23,20.3); Y.changeEntry(1,12,1);
        X.changeEntry(1,5,2.7); Y.changeEntry(1,22,0);
        X.changeEntry(1,34,3); Y.changeEntry(1,32,1);
        X.changeEntry(2,100,4); Y.changeEntry(2,13,0);
        X.changeEntry(2,2,5); Y.changeEntry(2,200,1);
        X.changeEntry(2,32,6); Y.changeEntry(2,31,0);
        X.changeEntry(3,12,7); Y.changeEntry(3,11,1);
        X.changeEntry(3,21,8); Y.changeEntry(3,211,1);
        X.changeEntry(3,3,9); Y.changeEntry(3,3,1);

        System.out.println(X.getNNZ());
        System.out.println(X);

        System.out.println(Y.getNNZ());
        System.out.println(Y);

        C = X.scalarMult(1.5);
        System.out.println(C.getNNZ());
        System.out.println(C);

        D = X.add(X);
        System.out.println(D.getNNZ());
        System.out.println(D);

        E = X.sub(X);
        System.out.println(E.getNNZ());
        System.out.println(E);

        F = Y.transpose();
        System.out.println(F.getNNZ());
        System.out.println(F);

        G = Y.mult(B);
        System.out.println(G.getNNZ());
        System.out.println(G);

        H = Y.copy();
        System.out.println(H.getNNZ());
        System.out.println(H);
        System.out.println(X.equals(H));
        System.out.println(X.equals(Y));
        System.out.println(X.equals(X));

        X.makeZero();
        System.out.println(X.getNNZ());
        System.out.println(X);
    }
}