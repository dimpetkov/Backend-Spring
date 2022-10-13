package booksreview.exceptions;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(int id) {super("Not found article " + id);}
}
