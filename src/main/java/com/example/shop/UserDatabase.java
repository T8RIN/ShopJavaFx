package com.example.shop;

public final class UserDatabase extends Database<User> {
    public static UserDatabase INSTANCE = new UserDatabase();

    private UserDatabase() {
        super(DatabaseLocations.Users, ",", row -> new User(row.get(0), row.get(1)));
    }

    @Override
    ToRowMapper<User> getToRowMapper() {
        return object -> new String[]{object.login(), object.password()};
    }
}