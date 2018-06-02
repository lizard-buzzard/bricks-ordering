package bricks;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "CustomerOrder", path = "order")
public interface CustomerOrderRepository extends BaseRepository<CustomerOrder, Long> {

    Optional<CustomerOrder> findById(Long id);

    /**
     * A "Get Order" request, returns one order by its ID
     *
     * @param id - unique Id of the order
     * @return - an Orders object in case of valid Order reference
     * or nothing in other case (response status code is HttpStatus.NOT_FOUND (404))
     */
    @RestResource(path = "/GetOrder")
    @Query("SELECT o FROM CustomerOrder o where o.id = :id")
    CustomerOrder findBricksById(@Param("id") Long id);

   /**
     * A "Get Orders" request, returns all orders (list of the orders)
     *
     * @return - a list of Orders object
     */
    @RestResource(path = "/GetOrders")
    @Query("SELECT o FROM CustomerOrder o")
    List<CustomerOrder> findAll();


    @RestResource(path = "/UpdateOrder")
    @Modifying
    @Query("UPDATE CustomerOrder o set o.bricks = :bricks where o.id = :id")
    void setUserInfoById(@Param("id") long id, @Param("bricks") int  bricks );



    /**
     * This method is out of the scope and introduced for test purposes only
     * @param num
     * @return
     */
    @Query("SELECT o FROM CustomerOrder o where o.bricks = :num")
    List<CustomerOrder> findByBricks(@Param("num") int num);


}
