package com.pegasus.services;

public interface IService<T> {
    void ajouter(T var1);

    void supprimer(T var1);

    void modifier(T var1);

    void getAll();

    void getOneById(int var1);
}
