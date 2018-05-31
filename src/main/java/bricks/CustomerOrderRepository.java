package bricks;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "order", path = "order")
public interface CustomerOrderRepository extends PagingAndSortingRepository<CustomerOrder, Long> {

    List<CustomerOrder> findByorderSpec(@Param("name") String name);

    List<CustomerOrder> findById(@Param("id") long id);

}
