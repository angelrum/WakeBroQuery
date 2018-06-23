package system.service;

import system.service.exception.NotFoundException;

import java.util.List;

/*
* Абсстракт
* */

public interface AbstractService <T> {

    T save(T t);

    void delete(int id) throws NotFoundException;

    List<T> getAll() throws NotFoundException;
}
