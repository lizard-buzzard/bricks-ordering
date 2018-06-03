package bricks;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A domain object
 */
@Entity
@Table(name="CustomerOrder")
public class CustomerOrder implements Serializable {

    // The order details contains the CustomerOrder reference and the number of bricks ordered
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;                                    // CustomerOrder reference is unique to the submission

    @Column(name="bricks", nullable = false/*, columnDefinition = "int default 0"*/)
    private int bricks;                                 // number of bricks in the CustomerOrder

    @Column(name="isDispatched", columnDefinition="VARCHAR(255) default 'no'")
    private String isDispatched = "no";                 // completion mark

    public long getId() {
        return id;
    }

    public int getBricks() {
        return bricks;
    }

    public void setBricks(int bricks) {
        this.bricks = bricks;
    }

    public String getIsDispatched() {
        return isDispatched;
    }

    public void setIsDispatched(String isDispatched) {
        this.isDispatched = isDispatched;
    }
}
