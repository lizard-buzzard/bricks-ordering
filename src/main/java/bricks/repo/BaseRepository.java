package bricks.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

//    void delete(T deleted);
//
//    List<T> findAll();
//
//    Optional<T> findById(ID id);

//    T save(T persisted);
}
