package edu.miracosta.cs113;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Danny

/**
 * BigIntegerDriver.java: Solves Problem 13 on Project Euler by
 * reading the numbers from a file and using the add method
 * from BigInteger.  To print, the toString method is called,
 * all commas are taken out, and the first ten digits are printed.
 *
 * @author Danny Lee, Stephen Leighton
 * @version 1.0
 */
public class BigIntegerDriver {
    /*
     * ALGORITHM:
     *
     * INSTANTIATE file = new File("doc/eulersproject.txt")
     *
     * TRY
     *
     * scan = new Scanner(file)
     * line = scan.nextLine()
     * euler = new BigInteger(line)
     *
     * WHILE scan.hasNextLine()
     * euler.add(scan.nextLine()
     *
     * CATCH FileNotFoundException
     *
     *
     * finalResult = euler.toString()
     *
     * OUTPUT
     * finalResult.replaceAll(",","").subString(0,10)
     */
    public static void main(String[] args) throws UnsupportedOperationException {
        //DECLARATION + INITIALIZATION
        String line = "";
        String finalResult = "";
        BigInteger euler = null;

        File file = new File("doc/eulersproject.txt");
        Scanner scan = null;

        try {
            scan = new Scanner(file);
            line = scan.nextLine();
            euler = new BigInteger(line);

            while(scan.hasNextLine()){
                euler.add(scan.nextLine());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        finalResult = euler.toString();
        //  finalResult.replaceAll(",","");
        System.out.print(finalResult.replaceAll(",","").substring(0,10));
    }
}