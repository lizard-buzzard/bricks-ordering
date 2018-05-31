package bricks;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * A domain object
 */
@Entity
public class CustomerOrder {

    // The order details contains the CustomerOrder reference and the number of bricks ordered
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;    // CustomerOrder reference is unique to the submission

    private int bricks; // number of bricks in the CustomerOrder
    private String orderSpec;

    public String getOrderSpec() {
        return orderSpec;
    }

    public void setOrderSpec(String orderSpec) {
        this.orderSpec = orderSpec;
    }

    public int getBricks() {
        return bricks;
    }

    public void setBricks(int bricks) {
        this.bricks = bricks;
    }

    public long getId() {
        return id;
    }
}
