package springtask.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    //ID is Long
    T save(T entity);
    T findById(ID id);
    List<T> getAll();
    T updateById(ID id, T newEntity);
    void deleteById(ID id);
}
