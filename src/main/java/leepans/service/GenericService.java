package leepans.service;

import jakarta.inject.Inject;

import java.util.List;

public interface GenericService<T, E> {

    List<T> findAll();
    T findById(Long id);
    T create(T entity);
    void update(Long id, E dto);
    void delete(Long id);
}
