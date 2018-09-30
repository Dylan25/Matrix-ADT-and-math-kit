//-----------------------------------------------------------------------------
//  Sparse.java
//  A client program that utilizes Matrix.java and List.java
//  In order to create and perform operations on a set of matrices
//  These matrices are formed by parsing the file at the first argument
//  The output is recorded in the file at the second argument
//  Sparse.java by Dylan Welch Cruzid: dtwelch
//  assignment: pa3
//-----------------------------------------------------------------------------

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Sparse{

    //parses a double from a string by splitting around spaces.
    static double getChar(int ind, int place, List<String> line) {
        String nums[] = line.get(ind).split(" ");
        double convertChar = Double.parseDouble(nums[place]);
        return (convertChar);
    }


    public static void main(String[] args) throws IOException{
        PrintWriter out = null;
        int i = 0;
        int n = 0;

        //reads input file
        Scanner in = new Scanner(new File(args[0]));
        List<String> lines = new ArrayList<String>();
        while (in.hasNextLine()) {
            lines.add(in.nextLine());
            n++;
        }

        Matrix A = new Matrix((int)getChar(0,0,lines));
        Matrix B = new Matrix((int)getChar(0,0,lines));

        //forms first matrix
        for(i=2; i < (int)getChar(0,1,lines) + 2; i++) {
            int count = i;
            A.changeEntry((int)getChar(count,0,lines), (int)getChar(count,1,lines), getChar(count,2,lines));
        }

        //forms second matrix
        for(i=3; i < getChar(0,2,lines) + 3; i++) {
            int count = i + (int)getChar(0,1,lines);
            B.changeEntry((int)getChar(count,0,lines), (int)getChar(count,1,lines), getChar(count,2,lines));
        }

        //Begin writing to output file
        out = new PrintWriter(new FileWriter(args[1]));
        out.println("A has " + A.getNNZ() + " non-zero entries:");
        out.println(A.toString());
        out.println("\n");

        out.println("B has " + B.getNNZ() + " non-zero entries:");
        out.println(B.toString());
        out.println("\n");

        out.println("(1.5)*A =");
        out.println(A.scalarMult(1.5));
        out.println("\n");

        out.println("A+B =");
        out.println(A.add(B));
        out.println("\n");

        out.println("A+A =");
        out.println(A.add(A));
        out.println("\n");

        out.println("B-A =");
        out.println(B.sub(A));
        out.println("\n");

        out.println("A-A =");
        out.println(A.sub(A));

        out.println("Transpose(A) =");
        out.println(A.transpose());
        out.println("\n");

        out.println("A*B =");
        out.println(A.mult(B));
        out.println("\n");

        out.println("B*B =");
        out.println(B.mult(B));
        out.println("\n");

        out.close();
        in.close();
    }
}