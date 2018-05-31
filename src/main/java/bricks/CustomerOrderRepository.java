package bricks;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "order", path = "CreateOrder")
public interface CustomerOrderRepository extends PagingAndSortingRepository<CustomerOrder, Long> {

    List<CustomerOrder> findByorderSpec(@Param("name") String name);

    List<CustomerOrder> findByBricks(@Param("num") int num);

}
