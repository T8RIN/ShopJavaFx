package com.example.shop;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Database<T> {

    final ArrayList<T> data = new ArrayList<>();
    protected String fileLocation;
    protected String delimiter;

    public Database(
            String file,
            String delimiter,
            FromStringsMapper<T> fromStringsMapper
    ) {
        this.delimiter = delimiter;
        fileLocation = file;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileLocation))) {
            String line;
            while ((line = reader.readLine()) != null) {
                var split = line.split(delimiter);
                var row = new ArrayList<String>();
                for (String s : split) {
                    row.add(s.strip().trim());
                }
                data.add(fromStringsMapper.map(row));
            }
        } catch (IOException ignored) {
        }
    }

    abstract ToRowMapper<T> getToRowMapper();

    public boolean isEntryExists(T databaseEntry) {
        return data.stream().anyMatch(entry -> entry.equals(databaseEntry));
    }

    public boolean deleteEntry(T databaseEntry) throws IOException {
        if (!isEntryExists(databaseEntry)) {
            return false;
        } else {
            data.remove(databaseEntry);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileLocation))) {
                for (T entry : data) {
                    writer.write(String.join(delimiter, getToRowMapper().map(entry)) + "\n");
                }
            }

            return true;
        }
    }

    public boolean appendEntry(T databaseEntry) {
        if (!isEntryExists(databaseEntry)) {
            data.add(databaseEntry);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileLocation, true))) {
                writer.write(String.join(delimiter, getToRowMapper().map(databaseEntry)) + "\n");
            } catch (IOException ignored) {
            }

            return true;
        }

        return false;
    }

    public interface FromStringsMapper<T> {
        T map(List<String> row);
    }

    public interface ToRowMapper<T> {
        String[] map(T object);
    }
}
