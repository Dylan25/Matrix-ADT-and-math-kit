//-----------------------------------------------------------------------------
//  List.java
//  An object queue ADT by Dylan Welch Cruzid: dtwelch
//  assignment: pa1
//  Credit to Patrick Tantalo for the "Queue.java" example that formed the basis
//  of this program.
//
//-----------------------------------------------------------------------------

public class List{

    private class Node{
        // fields
        Object data;
        Node next;
        Node previous;

        // Constructor
        Node(Object data) { this.data = data; next = null; previous = null; }

        // toString():  overrides Object's toString() method
        public String toString() {
            return String.valueOf(data);
        }

        // equals(): overrides Object's equals() method
        public boolean equals(Object x){
            boolean eq = false;
            Node that;
            if(x instanceof Node){
                that = (Node) x;
                eq = (this.data.equals(that.data));
            }
            return eq;
        }
    }

    // Fields
    private Node cursor;
    private Node front;
    private Node back;
    private int index;
    private int length;

    // Constructor
    List() {
        cursor = null;
        front = back = null;
        length = 0;
    }


    // Access Functions --------------------------------------------------------

    // isEmpty()
    // Returns true if this Queue is empty, false otherwise.
    boolean isEmpty() {
        return length==0;
    }

    // length()
    // Returns length of this List.
    int length() {
        return length;
    }

    // index()
    // Returns index of cursor element.
    int index() {
        if (cursor != null)
            return index;
        else
            return -1;
    }

    // front()
    // Returns front element.
    // Pre: length()>0
    Object front(){
        if( length() <= 0 ){
            throw new RuntimeException(
                    "List Error: front() called on empty List");
        }
        return front.data;
    }

    // back()
    // Returns front element.
    // Pre: length()>0
    Object back(){
        if( this.length() <= 0 ){
            throw new RuntimeException(
                    "List Error: back() called on empty List");
        }
        return back.data;
    }

    // int get()
    // Returns cursor element
    // Pre: length()>0, index()>=0
    Object get(){
        if( this.length() <= 0 ){
            throw new RuntimeException(
                    "List Error: get() called on empty List");
        }
        else if( index < 0 ){
            throw new RuntimeException(
                    "List Error: get() called on an undefined cursor");
        }
        else
            return cursor.data;
    }

    // boolean equals(List L)
    // Returns true if and only if the List and L are the same
    // integer sequence. The states of the cursors in the two Lists
    // are not used in determining equality.
    public boolean equals(Object x){
        boolean eq  = false;
        List Q;
        Node N, M;

        if(x instanceof List){
            Q = (List)x;
            N = this.front;
            M = Q.front;
            eq = (this.length==Q.length);
            //System.out.println(eq);
            while( eq && N!=null ){
                eq = N.equals(M);
                N = N.next;
                M = M.next;
                //System.out.println(eq);
            }
        }
        return eq;
    }

    // Manipulation Procedures -------------------------------------------------

    // void clear()
    // Resets this List to its original empty state.
    void clear() {
        cursor = null;
        front = null;
        back = null;
        index = -1;
        length = 0;
    }

    // void moveFront()
    // If List is non-empty, places the cursor under the front element,
    // otherwise does nothing.
    void moveFront(){
        if ( !isEmpty()) {
            cursor = front;
            index = 0;
        }
    }

    // void moveBack()
    // If List is non-empty, places the cursor under the back element,
    // otherwise does nothing.
    void moveBack(){
        if ( !isEmpty()) {
            cursor = back;
            index = length - 1;
        }
    }

    // void moveNext()
    // If cursor is defined and not at back, moves cursor one step toward
    // back of this List, if cursor is defined and at back, cursor becomes
    // undefined, if cursor is undefined does nothing.
    void moveNext(){
        if (cursor != null && index != length - 1) {
            cursor = cursor.next;
            index++;
        } else if (index == length - 1) {
            cursor = null;
            index = -1;
        }
    }

    // void movePrev()
    // If cursor is defined and not at front, moves cursor one step toward
    // front of this List, if cursor is defined and at front, cursor becomes
    // undefined, if cursor is undefined does nothing.
    void movePrev(){
        if (index == 0) {
            cursor = null;
            index = -1;
        }
        else if( cursor != null) {
            cursor = cursor.previous;
            index--;
        }
    }

    // void prepend(Object data)
    // Insert new element into this List. If List is non-empty,
    // insertion takes place before front element.
    void prepend(Object data) {
        Node N = new Node(data);
        if( this.isEmpty() ) {
            front = back = N;
        }else{
            N.next = front;
            front.previous = N;
            front = N;
        }
        index++;
        length++;
    }

    // void append()
    // Insert new element into this List. If List is non-empty,
    // insertion takes place after back element.
    void append(Object data) {
        Node N = new Node(data);
        if( this.isEmpty() ) {
            front = back = N;
        }else{
            back.next = N;
            N.previous = back;
            back = N;
        }
        length++;
    }

    // void insertBefore (Object data)
    // Insert new element before cursor.
    // Pre: length()>0, index()>=0
    void insertBefore (Object data) {
        if(length <= 0 || index < 0){
            throw new RuntimeException(
                    "List Error: insertBefore() called on empty List");
        }
        Node N = new Node (data);
        if (index != 0) {
            cursor.previous.next = N;
            N.previous = cursor.previous;
        }
        else
            front = N;
        N.next = cursor;
        cursor.previous = N;
        index++;
        length++;
    }

    // void insertAfter (Object data)
    // Insert new element After cursor.
    // Pre: length()>0, index()>=0
    void insertAfter (Object data) {
        if(length <= 0  || index < 0){
            throw new RuntimeException(
                    "List Error: insertAfter() called on empty List");
        }
        Node N = new Node (data);
        if(index != length-1) {
            N.next = cursor.next;
            cursor.next.previous = N;
        }
        else
            back = N;
        N.previous = cursor;
        cursor.next = N;
        length++;
    }

    // deleteFront()
    // Deletes front element from this List.
    // Pre: !this.isEmpty()
    void deleteFront(){
        if(this.isEmpty()){
            throw new RuntimeException(
                    "List Error: deleteFront() called on empty List");
        }
        if(length>1){
            front = front.next;
            front.previous = null;
        }else{
            front = back = null;
        }
        if(index == 0) {
            cursor = null;
            index = -1;
        }
        if(index > -1) {
            index--;
        }
        length--;
    }

    // void deleteBack()
    // Deletes the bak element.
    // Pre: length()>0
    void deleteBack(){
        if(length <= 0){
            throw new RuntimeException(
                    "List Error: deleteBack() called on empty List");
        }
        if(length>1){
            back = back.previous;
            back.next = null;
        }else{
            front = back = null;
        }
        if(index == length - 1) {
            cursor = null;
            index = -1;
        }
        length--;
    }

    // void delete()
    // Deletes cursor element, making cursor undefined.
    // Pre: length()>0, index()>=0
    void delete(){
        if (length <= 0){
            throw new RuntimeException(
                    "List Error: delete() called on empty List");
        }
        else if (index < 0) {
            throw new RuntimeException(
                    "List Error: delete() undefined cursor");
        }
        else {
            if (length == 1) {
                front = back = null;
            }
            else if (index != 0 && index != length - 1) {
                cursor.previous.next = cursor.next;
                cursor.next.previous = cursor.previous;
            }
            else if (index == 0) {
                front = cursor.next;
                //cursor.next.previous = null;
                //System.out.println(this.get());
                front.previous = null;
            }
            else if (index == length - 1) {
                back = cursor.previous;
                cursor.previous.next = null;
            }
            cursor = null;
            index = -1;
            length--;
        }
    }


    // Other Functions ---------------------------------------------------------

    // toString()
    // Overrides Object's toString method. Returns a String
    // representation of this List consisting of a space
    // separated sequence of integers, with front on left.
    public String toString(){
        StringBuffer sb = new StringBuffer();
        Node N = front;
        while(N!=null){
            if (N != front) {
                sb.append(" ");
            }
            sb.append(N.toString());
            N = N.next;
        }
        return new String(sb);
    }

    // Returns a new List representing the same integer sequence as this
    // List. The cursor in the new list is undefined, regardless of the
    // state of the cursor in this List. This List is unchanged.
    /*List copy(){
        List Q = new List();
        Node N = this.front;

        while( N!=null ){
            Q.append(N.data);
            N = N.next;
        }
        return Q;
    }*/

}