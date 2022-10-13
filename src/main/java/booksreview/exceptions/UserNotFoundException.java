package booksreview.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(int id) {super ("Not found user " + id);}
}
