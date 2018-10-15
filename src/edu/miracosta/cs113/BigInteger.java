package edu.miracosta.cs113;

/**
 * BigInteger.java : Represents any positive or negative numbers of
 * any size, and can print the number back to the user.
 * This takes in a number as a String and replaces all "," with "",
 * and then checks for a negative sign out front and stores a boolean
 * value for the sign.  The number is then stored into a LinkedList
 * by looping for each character after the sign.  To print the
 * number, a ListIterator is created and is stored back into a String
 * through a while loop, and "-" is placed in front if negative is
 * true.
 *
 *
 * @author Danny Lee, Stephen Leighton
 * @version 1.0
 *
 */

import java.util.LinkedList;
import java.util.ListIterator;
public class BigInteger implements BigNumber{

    private LinkedList<Integer> bigInt;
    private boolean negative;

    /**
     * The default constructor; will not be called.
     */
    public BigInteger(){
        bigInt = new LinkedList<Integer>();
        negative = false;
    }

    /**
     * The String constructor that creates a LinkedList object and stores
     * the String into a LinkedList, while taking out the commas and storing
     * a boolean for the sign.
     *
     * @param n
     *          The String that the user inputs
     */
    public BigInteger(String n){

        bigInt = new LinkedList<Integer>();

        int numberLength = 0;
        String numberWithoutComma = "";
        String numberWithoutSign = "";

        numberWithoutComma = n.replaceAll(",","");
        if(n.charAt(0) == '-'){
            negative = true;
            numberWithoutSign = numberWithoutComma.substring(1);
            numberLength = numberWithoutSign.length();
            for(int i = 0; i < numberLength; i++){
                bigInt.add(numberWithoutSign.charAt(i)- '0');
            }
        }else{
            negative = false;
            numberWithoutSign = numberWithoutComma.substring(0);
            numberLength = numberWithoutSign.length();
            for(int i = 0; i < numberLength; i++){
                bigInt.add(numberWithoutSign.charAt(i)- '0');
            }
        }

    }

    /**
     * toString that creates a ListIterator and adds the next index
     *  as the next char of the String, as well as places the commas
     *  back into the String using a for loop, and going from right
     *  to left adding a comma at every third index.
     *
     * @return the value of each index of the ListIterator,
     *  as well as a "-" in front if negative is true.
     */
    public String toString(){
        String stringToReturn = "";
        String afterComma = "";
        String beforeComma = "";
        ListIterator i = bigInt.listIterator();


        while(i.hasNext()){
            stringToReturn += i.next();
        }

        for(int j = stringToReturn.length() - 1; j >= 0 ; j = j - 3){
            if(j == stringToReturn.length() - 1){
                continue;
            }else{
                beforeComma = stringToReturn.substring(0, j + 1);//splits the number into two parts
                afterComma = stringToReturn.substring(j + 1);//splits the number into two parts
                stringToReturn = beforeComma + "," + afterComma; //add a comma between the two parts.

            }

        }

        if(negative == true){
            stringToReturn = "-" + stringToReturn;
        }

        return stringToReturn;
    }

    /**
     * Adds an int to the BigInteger, creates a String and of
     * the int and creates a new BigInteger.  Checks the signs of the
     * numbers; if one them is negative then calls the
     * subtract method.
     *
     * Two ListIterators are created starting at
     * the end of the list of the BigIntegers, and adds the two at the
     * end and then adds the previous numbers until the end of one list
     * is reached and is stored into the original BigInteger list.
     *
     * The ListIterator of the original BigInteger is set back to the
     * beginning of the list, and if the previous number is greater than
     * 10, then 10 is subtracted and one is added to the number before that.
     * If the beginning of the list is reached, then we add 1 first.
     *
     * @param n
     *      An int to be added to BigInteger
     * @throws UnsupportedOperationException
     *      Thrown to indicate that the requested operation is not supported
     */
    @Override
    public void add(int n) throws UnsupportedOperationException {

        String integerToAdd = n + "";
        BigInteger anotherInteger = new BigInteger(integerToAdd);
        int number1 = 0;
        int number2 = 0;
        int resultDigit = 0;
        int digitBefore = 0;

        if(this.negative && !(anotherInteger.negative) || !(this.negative) && anotherInteger.negative){
            this.subtract(n);
        }

        //assign listIterators for each lists and place them at the end of the lists.
        ListIterator<Integer> listIterator = bigInt.listIterator(bigInt.size());
        ListIterator<Integer> anotherListIterator = anotherInteger.bigInt.listIterator(anotherInteger.bigInt.size());

        while (listIterator.hasPrevious() && anotherListIterator.hasPrevious()) {
            number1 = listIterator.previous();
            number2 = anotherListIterator.previous();
            listIterator.set(number1 + number2);
        }

        if (listIterator.hasPrevious() && !(anotherListIterator.hasPrevious())) {
            while (listIterator.hasPrevious()) {
                number1 = listIterator.previous();
                listIterator.set(number1);
            }
        } else if (!(listIterator.hasPrevious()) && anotherListIterator.hasPrevious()) {
            while (anotherListIterator.hasPrevious()) {
                number2 = anotherListIterator.previous();
                this.bigInt.addFirst(number2);
            }
        }


        listIterator = bigInt.listIterator(bigInt.size());
        for(int index = this.bigInt.size() -1; index >= 0 ; index --){
            resultDigit = listIterator.previous();
            if(resultDigit >= 10){
                resultDigit = resultDigit - 10;
                listIterator.set(resultDigit);//sets the original value - 10 to the result list
                if(listIterator.hasPrevious()){//appends 1 to the number in front.
                    digitBefore = this.bigInt.get(index -1);
                    this.bigInt.set(index - 1, digitBefore + 1);
                }else if(!(listIterator.hasPrevious())){//if there is no number, add 1 to the front of the list.
                    this.bigInt.addFirst(1);
                }
            }
        }

    }

    /**
     * Adds a String to the BigInteger, creates a a new BigInteger
     * of the String. Checks the signs of the numbers; if one
     * them is negative then calls the subtract method.
     *
     * Two ListIterators are created starting at
     * the end of the list of the BigIntegers, and adds the two at the
     * end and then adds the previous numbers until the end of one list
     * is reached and is stored into the original BigInteger list.
     *
     * The ListIterator of the original BigInteger is set back to the
     * beginning of the list, and if the previous number is greater than
     * 10, then 10 is subtracted and one is added to the number before that.
     * If the beginning of the list is reached, then we add 1 first.
     *
     * @param n
     *      A String to be added to BigInteger
     * @throws UnsupportedOperationException
     *      Thrown to indicate that the requested operation is not supported
     */
    @Override
    public void add(String n) throws UnsupportedOperationException {
        String integerToAdd = n;
        BigInteger anotherInteger = new BigInteger(integerToAdd);
        int number1 = 0;
        int number2 = 0;
        int resultDigit = 0;
        int digitBefore = 0;

        if(this.negative && !(anotherInteger.negative)){
            // BigInteger temp = new BigInteger();
            //flip two numbers. negative to positive, positive to negative
//           temp.bigInt = this.bigInt;
//            this.bigInt = anotherInteger.bigInt;
//            anotherInteger.bigInt = temp.bigInt;


            this.subtract("-" + n);// negative minus negative number.
            return;
        }else if(!(this.negative) && anotherInteger.negative){
            //anotherInteger.negative = false;
            this.subtract(n.substring(1));
            return;
        }


        //assign listIterators for each lists and place them at the end of the lists.
        ListIterator<Integer> listIterator = bigInt.listIterator(bigInt.size());
        ListIterator<Integer> anotherListIterator = anotherInteger.bigInt.listIterator(anotherInteger.bigInt.size());

        while (listIterator.hasPrevious() && anotherListIterator.hasPrevious()) {
            number1 = listIterator.previous();
            number2 = anotherListIterator.previous();
            listIterator.set(number1 + number2);
        }

        if (listIterator.hasPrevious() && !(anotherListIterator.hasPrevious())) {
            while (listIterator.hasPrevious()) {
                number1 = listIterator.previous();
                listIterator.set(number1);
            }
        } else if (!(listIterator.hasPrevious()) && anotherListIterator.hasPrevious()) {
            while (anotherListIterator.hasPrevious()) {
                number2 = anotherListIterator.previous();
                this.bigInt.addFirst(number2);
            }
        }


        listIterator = bigInt.listIterator(bigInt.size());
        for(int index = this.bigInt.size() -1; index >= 0 ; index --){
            resultDigit = listIterator.previous();
            if(resultDigit >= 10){
                resultDigit = resultDigit - 10;
                listIterator.set(resultDigit);//sets the original value - 10 to the result list
                if(listIterator.hasPrevious()){//appends 1 to the number in front.
                    digitBefore = this.bigInt.get(index -1);
                    this.bigInt.set(index - 1, digitBefore + 1);
                }else if(!(listIterator.hasPrevious())){//if there is no number, add 1 to the front of the list.
                    this.bigInt.addFirst(1);
                }
            }
        }


    }
    /**
     * Subtracts an int from the BigInteger by creating a new
     * String of the int, then creates a BigInteger of the String.
     *
     * Checks if the signs are opposite, then calls the add method.
     *
     * If both are negative, call flipSigns and flipIntegers method,
     * and if flipped, flip the ListIterators as well.
     *
     * Subtract the tail in the second BigInteger list from the tail
     * in the first BigInteger list.  If the second number is greater
     * than the first, add 10 and subtract one from the previous
     * number.  Move the iterator and repeat process until hasPrevious()
     * is false.
     *
     * Check if the flipSigns method was called and if the flipIntegers
     * flipped the BigIntegers, and if so flip the sign.
     *
     * Check if the BigInteger starts with 0, and removes those zeros
     * using a ListIterator.
     *
     * @param n
     *      String to be subtracted from the BigInteger
     * @throws UnsupportedOperationException
     *      Thrown to indicate that the requested operation is not supported
     */
    @Override
    public void subtract(int n) throws UnsupportedOperationException {

        String str = n + "";
        BigInteger subInteger = new BigInteger(str);
        int number1 = 0;
        int number2 = 0;
        boolean decrementPrevious = false;
        boolean isIntegerFlipped = false;
        boolean isSignFlipped = false;

        if (!(this.negative) && subInteger.negative) {
            //subInteger.negative = false;
            this.add(str.substring(1));
            return;
        } else if (this.negative && !(subInteger.negative)) {
            this.negative = false;
            this.add(n);
            this.negative = true;
            return;

        } else {//when two numbers have the same signs (+,+) or (-,-)
            ListIterator<Integer> listIterator = null;
            ListIterator<Integer> subListIterator = null;

            //Setting listIterators
            if (this.negative && subInteger.negative) {//when both are negatives!!
                isSignFlipped = this.flipSigns(subInteger);//if two numbers are both negatives, flip them to both positives
                isIntegerFlipped = this.flipIntegers(subInteger);
//                System.out.println("isSignFlipped?? : " + isSignFlipped);
//                System.out.println("isIntegerFlipped?? : " + isIntegerFlipped);
                if (isIntegerFlipped == true) {//if the integers are flipped, flip the listIterators as well.
                    listIterator =  bigInt.listIterator(bigInt.size());
                    subListIterator = subInteger.bigInt.listIterator(subInteger.bigInt.size());
                }else{
                    listIterator = bigInt.listIterator(bigInt.size());
                    subListIterator = subInteger.bigInt.listIterator(subInteger.bigInt.size());

                }
                //  System.out.println("isIntegerFlipped? : " + isIntegerFlipped);

            }else if(!(this.negative) && !(subInteger.negative)){//when both are positives!!
                isIntegerFlipped = this.flipIntegers(subInteger);
//                System.out.println("isIntegerFlipped ?? = " + isIntegerFlipped);
//                System.out.println("@@@ this.bigInt : " + this.bigInt + " Is it negative ? " + this.negative);
//                System.out.println("@@@ subInteger.bigInt" + subInteger.bigInt + " Is it negative ? " + subInteger.negative);
                if(isIntegerFlipped == true){//if the integers are flipped, flip the listIterators as well.
                    listIterator = bigInt.listIterator(bigInt.size());
                    subListIterator = subInteger.bigInt.listIterator(subInteger.bigInt.size());

                    //System.out.println("@@@@@listIterator.previous.previous() : " + listIterator.previous() + " subListIterator.next() : " + subListIterator.previous());
                    // System.out.println(listIterator.previous());
                    // System.out.println(subListIterator.previous());
                }else{
                    listIterator = bigInt.listIterator(bigInt.size());
                    subListIterator = subInteger.bigInt.listIterator(subInteger.bigInt.size());
                }
            }


            while (listIterator.hasPrevious() && subListIterator.hasPrevious()) {//both hasPrevious()
//                if(isIntegerFlipped == true){
//                    number1 = subListIterator.previous();
//                    number2 = listIterator.previous();
//                }else{
                number1 = listIterator.previous();
                number2 = subListIterator.previous();
                //  }
//                System.out.println("@@@@@@@@@number1 : " + number1 + "  number2 : " + number2);

                if (this.negative && subInteger.negative) {//when both are negatives
                    this.flipSigns(subInteger);
                    isIntegerFlipped = flipIntegers(subInteger);
                }
                if (!(this.negative) && !(subInteger.negative)){//when both are positives!
                    // isIntegerFlipped = this.flipIntegers(subInteger);

//                     System.out.println(("~~~isIntegerFlipped ? : " + isIntegerFlipped));
//                     System.out.println("~~~isSignFlipped ? : " + isSignFlipped);
////                    System.out.println("this.bigInt.get(0) = " + this.bigInt.get(0));
//                      System.out.println("decrementPrevious ? : " + decrementPrevious);
//                      System.out.println("number1 : " + number1 + "  number2 : " + number2);

                    if (decrementPrevious == true) {
                        number1--;
                        decrementPrevious = false;
                    }
                    if (number2 > number1) {//borrowing
                        number1 = number1 + 10;
                        decrementPrevious = true;
                    }

//                    if((isSignFlipped && !(isIntegerFlipped)) || (!(isSignFlipped) && isIntegerFlipped)){
//                        System.out.println("put - in front of the result : ");
//                        listIterator.set((-1)*(number1 - number2));
//                    }else{
                    listIterator.set(number1 - number2);
                    // }

                    // System.out.println("subtracting two positive numbers");

                }
            }

            if (listIterator.hasPrevious() && !(subListIterator.hasPrevious())) {//when this.list'size > sublist's size
                while (listIterator.hasPrevious()) {
                    number1 = listIterator.previous();
                    if(decrementPrevious == true) {
                        number1--;
                    }
                    if(number1 < 0) {
                        number1 = number1 + 10;
                        listIterator.set(number1);
                        decrementPrevious = true;
                    }else{
                        listIterator.set(number1);
                        decrementPrevious = false;
                    }

                }
            } else if (!(listIterator.hasPrevious()) && subListIterator.hasPrevious()) {//when this.list's size < subList's size
                while (subListIterator.hasPrevious()) {
                    number2 = subListIterator.previous();
                    if (subInteger.negative) {
                        this.bigInt.addFirst(number2);
                    } else if (!(subInteger.negative)) {
                        this.bigInt.addFirst(0 - number2);
                    }
                }
            }
            if((isSignFlipped && !(isIntegerFlipped)) || (!(isSignFlipped) && isIntegerFlipped)){
                if(this.bigInt.getFirst() != 0){
//                    System.out.println("put - in front of the result : ");
                    this.negative = true;
//                    System.out.println(this.bigInt);
                }
            }
            listIterator = bigInt.listIterator(0);
            while (listIterator.next() == 0 && listIterator.hasNext()) {
                listIterator.remove();
            }

//            if (isSignFlipped == true && isIntegerFlipped == false) {
//                this.negative = true;
//            }

        }


    }//end of subtract method

    /**
     * If negative, change to positive
     *
     * @param anotherInteger
     *      The BigInteger that is going to be subtracted
     *      from the original BigInteger
     * @return the sign for the BigIntegers; should
     *      always return true
     */
    public boolean flipSigns(BigInteger anotherInteger){
        if(this.negative){
            this.negative = false;
        }
        if(anotherInteger.negative){
            anotherInteger.negative = false;
        }

        return (!(this.negative) && !(anotherInteger.negative));
    }

    /**
     * Flips the integer.  Checks for the greater integer through
     * a size check and through a ListIterator checking each
     * digit from left to right to determine which number is
     * greater.  Returns a boolean for if the BigIntegers are flipped
     * @param anotherInteger
     *          The BigInteger that will be checked if greater than the
     *          original BigInteger; if greater will be switch BigIntegers
     *
     * @return true if BigIntegers are flipped, false if not
     */
    public boolean flipIntegers(BigInteger anotherInteger){
        boolean isFlipped = false;
        int number1Next = 0;
        int number2Next = 0;

        if(this.bigInt.size() < anotherInteger.bigInt.size()){
            BigInteger tempInteger = new BigInteger();
            tempInteger.bigInt = this.bigInt;
            this.bigInt = anotherInteger.bigInt;
            anotherInteger.bigInt = tempInteger.bigInt;
            isFlipped =  true;
        }else if(this.bigInt.size() == anotherInteger.bigInt.size()){
            ListIterator<Integer> number1Iterator = bigInt.listIterator(0);
            ListIterator<Integer> number2Iterator = anotherInteger.bigInt.listIterator(0);
            while(number1Iterator.hasNext()){
                number1Next = number1Iterator.next();
                number2Next = number2Iterator.next();
//                    System.out.println("number1Next : " + number1Next + "  number2Next : " + number2Next);

                if(number1Next < number2Next){
//                        System.out.println("The two big integers are flipped because " + number1Next + " < " + number2Next);
                    BigInteger tempInteger = new BigInteger();
                    tempInteger.bigInt = this.bigInt;
                    this.bigInt = anotherInteger.bigInt;
                    anotherInteger.bigInt = tempInteger.bigInt;
                    isFlipped = true;
                    break;
                }else if(number1Next > number2Next){
//                        System.out.println("The two numbers have not been flipped.");
                    isFlipped = false;
                    break;
                }
            }
        }//end of else if
        return isFlipped;
    }//end of method

    /**
     * Subtracts a String from the BigInteger by creating a new
     * BigInteger of the String.
     *
     * Checks if the signs are opposite, then calls the add method.
     *
     * If both are negative, call flipSigns and flipIntegers method,
     * and if flipped, flip the ListIterators as well.
     *
     * Subtract the tail in the second BigInteger list from the tail
     * in the first BigInteger list.  If the second number is greater
     * than the first, add 10 and subtract one from the previous
     * number.  Move the iterator and repeat process until hasPrevious()
     * is false.
     *
     * Check if the flipSigns method was called and if the flipIntegers
     * flipped the BigIntegers, and if so flip the sign.
     *
     * Check if the BigInteger starts with 0, and removes those zeros
     * using a ListIterator.
     *
     * @param n
     *      String to be subtracted from the BigInteger
     * @throws UnsupportedOperationException
     *      Thrown to indicate that the requested operation is not supported
     */
    @Override
    public void subtract(String n) throws UnsupportedOperationException {

        BigInteger subInteger = new BigInteger(n);
        int number1 = 0;
        int number2 = 0;
        boolean decrementPrevious = false;
        boolean isIntegerFlipped = false;
        boolean isSignFlipped = false;

        if (!(this.negative) && subInteger.negative) {
            //subInteger.negative = false;
            this.add(n.substring(1));
            return;
        } else if (this.negative && !(subInteger.negative)) {
            this.negative = false;
            this.add(n);
            this.negative = true;
            return;

        } else {//when two numbers have the same signs (+,+) or (-,-)
            ListIterator<Integer> listIterator = null;
            ListIterator<Integer> subListIterator = null;

            //Setting listIterators
            if (this.negative && subInteger.negative) {//when both are negatives!!
                isSignFlipped = this.flipSigns(subInteger);//if two numbers are both negatives, flip them to both positives
                isIntegerFlipped = this.flipIntegers(subInteger);
//                System.out.println("isSignFlipped?? : " + isSignFlipped);
//                System.out.println("isIntegerFlipped?? : " + isIntegerFlipped);
                if (isIntegerFlipped == true) {//if the integers are flipped, flip the listIterators as well.
                    listIterator =  bigInt.listIterator(bigInt.size());
                    subListIterator = subInteger.bigInt.listIterator(subInteger.bigInt.size());
                }else{
                    listIterator = bigInt.listIterator(bigInt.size());
                    subListIterator = subInteger.bigInt.listIterator(subInteger.bigInt.size());

                }
                //  System.out.println("isIntegerFlipped? : " + isIntegerFlipped);

            }else if(!(this.negative) && !(subInteger.negative)){//when both are positives!!
                isIntegerFlipped = this.flipIntegers(subInteger);
//                System.out.println("isIntegerFlipped ?? = " + isIntegerFlipped);
//                System.out.println("@@@ this.bigInt : " + this.bigInt + " Is it negative ? " + this.negative);
//                System.out.println("@@@ subInteger.bigInt" + subInteger.bigInt + " Is it negative ? " + subInteger.negative);
                if(isIntegerFlipped == true){//if the integers are flipped, flip the listIterators as well.
                    listIterator = bigInt.listIterator(bigInt.size());
                    subListIterator = subInteger.bigInt.listIterator(subInteger.bigInt.size());

                    //System.out.println("@@@@@listIterator.previous.previous() : " + listIterator.previous() + " subListIterator.next() : " + subListIterator.previous());
                    // System.out.println(listIterator.previous());
                    // System.out.println(subListIterator.previous());
                }else{
                    listIterator = bigInt.listIterator(bigInt.size());
                    subListIterator = subInteger.bigInt.listIterator(subInteger.bigInt.size());
                }
            }


            while (listIterator.hasPrevious() && subListIterator.hasPrevious()) {//both hasPrevious()
//                if(isIntegerFlipped == true){
//                    number1 = subListIterator.previous();
//                    number2 = listIterator.previous();
//                }else{
                number1 = listIterator.previous();
                number2 = subListIterator.previous();
                //  }
//                System.out.println("@@@@@@@@@number1 : " + number1 + "  number2 : " + number2);

                if (this.negative && subInteger.negative) {//when both are negatives
                    this.flipSigns(subInteger);
                    isIntegerFlipped = flipIntegers(subInteger);
                }
                if (!(this.negative) && !(subInteger.negative)){//when both are positives!
                    // isIntegerFlipped = this.flipIntegers(subInteger);

//                     System.out.println(("~~~isIntegerFlipped ? : " + isIntegerFlipped));
//                     System.out.println("~~~isSignFlipped ? : " + isSignFlipped);
////                    System.out.println("this.bigInt.get(0) = " + this.bigInt.get(0));
//                      System.out.println("decrementPrevious ? : " + decrementPrevious);
//                      System.out.println("number1 : " + number1 + "  number2 : " + number2);

                    if (decrementPrevious == true) {
                        number1--;
                        decrementPrevious = false;
                    }
                    if (number2 > number1) {//borrowing
                        number1 = number1 + 10;
                        decrementPrevious = true;
                    }

//                    if((isSignFlipped && !(isIntegerFlipped)) || (!(isSignFlipped) && isIntegerFlipped)){
//                        System.out.println("put - in front of the result : ");
//                        listIterator.set((-1)*(number1 - number2));
//                    }else{
                    listIterator.set(number1 - number2);
                    // }

                    // System.out.println("subtracting two positive numbers");

                }
            }

            if (listIterator.hasPrevious() && !(subListIterator.hasPrevious())) {//when this.list'size > sublist's size
                while (listIterator.hasPrevious()) {
                    number1 = listIterator.previous();
                    if(decrementPrevious == true) {
                        number1--;
                    }
                    if(number1 < 0) {
                        number1 = number1 + 10;
                        listIterator.set(number1);
                        decrementPrevious = true;
                    }else{
                        listIterator.set(number1);
                        decrementPrevious = false;
                    }

                }
            } else if (!(listIterator.hasPrevious()) && subListIterator.hasPrevious()) {//when this.list's size < subList's size
                while (subListIterator.hasPrevious()) {
                    number2 = subListIterator.previous();
                    if (subInteger.negative) {
                        this.bigInt.addFirst(number2);
                    } else if (!(subInteger.negative)) {
                        this.bigInt.addFirst(0 - number2);
                    }
                }
            }
            if((isSignFlipped && !(isIntegerFlipped)) || (!(isSignFlipped) && isIntegerFlipped)){
                if(this.bigInt.getFirst() != 0){
//                    System.out.println("put - in front of the result : ");
                    this.negative = true;
//                    System.out.println(this.bigInt);
                }
            }
            listIterator = bigInt.listIterator(0);
            while (listIterator.next() == 0 && listIterator.hasNext()) {
                listIterator.remove();
            }

//            if (isSignFlipped == true && isIntegerFlipped == false) {
//                this.negative = true;
//            }

        }


    }//end of subtract method
}
