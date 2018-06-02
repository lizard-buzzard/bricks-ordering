package bricks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerOrderController {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @GetMapping("/allorders")
    public List<CustomerOrder> retrieveAllOrders() {
        return customerOrderRepository.findAll();
    }
}
