package models;

import java.time.format.DateTimeFormatter;

public class User {
    public static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("HH:mm");

    public long id;
    public String name;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
