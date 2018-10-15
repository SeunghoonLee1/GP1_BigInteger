package edu.miracosta.cs113;


public interface BigNumber {

    /*TODO:
     * - make sure to document interface methods below!!!
     * - these are specific to Sprint 2, so for Sprint 1 you can simply have the methods throw an UnsupportedOperationException
     * - do not forget to consult your UML for each sprint so you know ALL methods to build (interface
     * does not contain constructors and toString methods! don't forget!!)
     */


    public void add(int n) throws UnsupportedOperationException; //throws this exception just for Sprint 1.

    public void add(String n) throws UnsupportedOperationException;

    public void subtract(int n) throws UnsupportedOperationException;

    public void subtract(String n) throws UnsupportedOperationException;
}