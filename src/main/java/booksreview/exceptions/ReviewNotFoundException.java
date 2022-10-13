package booksreview.exceptions;

public class ReviewNotFoundException extends RuntimeException{
    public ReviewNotFoundException(int id) {super ("Not found review " + id);}
}
