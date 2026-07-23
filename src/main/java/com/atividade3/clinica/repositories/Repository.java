package com.atividade3.clinica.repositories;

import java.sql.SQLException;
import java.util.List;

public interface Repository<CLAZZ, KEY> {

    void create(CLAZZ c) throws SQLException;

    CLAZZ read(KEY k) throws SQLException;

    void update(CLAZZ c) throws SQLException;

    void delete(KEY k) throws SQLException;

    List<CLAZZ> readAll() throws SQLException;

}