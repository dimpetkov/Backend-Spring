package booksreview.exceptions;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(int id) {super("Not found category " + id);}
}
