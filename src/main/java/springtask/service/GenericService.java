package springtask.service;

import java.util.List;

public interface GenericService<T, ID> {
    T save(T entity);
    T findById(ID id);
    List<T> getAll();
    T updateById(ID id, T newEntity);
    void deleteById(ID id);
}
