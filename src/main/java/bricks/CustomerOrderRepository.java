package bricks;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RepositoryRestResource(collectionResourceRel = "CustomerOrder", path = "order")
public interface CustomerOrderRepository extends BaseRepository<CustomerOrder, Long> {

    Optional<CustomerOrder> findById(Long id);

    // TODO: findBricksById to /GetOrder
    //    @RequestMapping(value = "/GetOrder", method = GET)
    //    @ResponseBody
    @Query("SELECT o FROM CustomerOrder o where o.id = :id")
    CustomerOrder findBricksById(@Param("id") Long id);

    /**
     * This method is out of the scope and introduced for test purposes only
     * @param num
     * @return
     */
    @Query("SELECT o FROM CustomerOrder o where o.bricks = :num")
    List<CustomerOrder> findByBricks(@Param("num") int num);


    @Query("SELECT o FROM CustomerOrder o")
    List<CustomerOrder> findAll();


}
