package activities.skills.magic;

import org.osbot.rs07.api.Magic;

public enum BookType {

    NORMAL(Magic.Book.NORMAL),
    ARCEUUS(Magic.Book.ARCEUUS),
    ANCIENT(Magic.Book.ANCIENT),
    LUNAR(Magic.Book.LUNAR);

    private Magic.Book book;

    BookType(final Magic.Book book) {
        this.book = book;

    }

    public String toString() {
        return book.name();
    }
}