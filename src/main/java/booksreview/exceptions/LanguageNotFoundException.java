package booksreview.exceptions;

public class LanguageNotFoundException extends RuntimeException{
    public LanguageNotFoundException(int id) {super ("Not found language " + id);}
}
