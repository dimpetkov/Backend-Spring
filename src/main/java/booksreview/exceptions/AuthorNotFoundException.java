package booksreview.exceptions;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException(int id) {super("Not found author " + id);}
}
