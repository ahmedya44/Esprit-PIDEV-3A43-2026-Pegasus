package com.pegasus.dao;

import java.util.List;

public interface IDao<T> {
    void ajouter(T t);
    void modifier(T t);
    void supprimer(int id);
    List<T> getAll();
    T getById(int id);
}