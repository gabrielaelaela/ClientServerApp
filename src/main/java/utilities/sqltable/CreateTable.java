package utilities.sqltable;

import java.sql.*;

public class CreateTable {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement;
        String createSQL;

        Class.forName("org.postgresql.Driver");
        Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/lab7", "postgres", "2390");

        createSQL = "CREATE TABLE IF NOT EXISTS organization\n" +
                "(\n" +
                "    id SERIAL,\n" +
                "    name character varying NOT NULL,\n" +
                "    x real NOT NULL,\n" +
                "    y real,\n" +
                "    creation_date date NOT NULL,\n" +
                "    annual_turnover bigint NOT NULL,\n" +
                "    type character varying NOT NULL,\n" +
                "    zipcode character varying NOT NULL,\n" +
                "    username_id int,\n" +
                "    CONSTRAINT organization_pkey PRIMARY KEY (id));";


        preparedStatement = c.prepareStatement(createSQL);
        preparedStatement.executeUpdate();


        createSQL = "DROP TABLE users;" +
                "CREATE TABLE IF NOT EXISTS users\n" +
                "(\n" +
                "    id SERIAL, " +
                "    username character varying COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                "    password character varying COLLATE pg_catalog.\"default\" NOT NULL\n" +
                ");";
        preparedStatement = c.prepareStatement(createSQL);
        preparedStatement.executeUpdate();
    }
}
