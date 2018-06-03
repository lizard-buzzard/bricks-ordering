package bricks.controller;

import bricks.CustomerOrder;
import bricks.repo.CustomerOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bricks_api")
public class CustomerOrderController {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @PostMapping("/CreateOrder")
    @ResponseBody
    public CustomerOrder create(@RequestBody CustomerOrder customerOrder)    {
        return customerOrderRepository.save(customerOrder);
    }

    @GetMapping("/GetOrders")
    @ResponseBody
    public List<CustomerOrder> findAll()    {
        return customerOrderRepository.findAll();
    }

    @PutMapping("/UpdateOrder/{id}")
    @ResponseBody
    public CustomerOrder update(@PathVariable("id") Long id, @RequestBody CustomerOrder customerOrder)    {
        CustomerOrder order = customerOrderRepository.getOne(id);
        order.setBricks(customerOrder.getBricks());
        return customerOrderRepository.save(order);
    }

    @GetMapping("/GetOrder/{id}")
    @ResponseBody
    public Optional<CustomerOrder> findOrderById(@PathVariable("id") Long id)    {
        return customerOrderRepository.findById(id);
    }

    @PutMapping("/FulfilOrder/{id}")
    @ResponseBody
    public CustomerOrder fulfilOrder(@PathVariable("id") Long id, @RequestBody CustomerOrder customerOrder)
            throws ResourceNotFoundException {
//        CustomerOrder order = customerOrderRepository.getOne(id);
        Optional<CustomerOrder> order = customerOrderRepository.findById(id);
        if(order.isPresent()) {
            CustomerOrder o1 = order.get();
            o1.setIsDispatched(customerOrder.getIsDispatched());
            return customerOrderRepository.save(o1);
        } else {
            throw new ResourceNotFoundException();
        }
    }

//    @DeleteMapping("/orders/{id}")
//    @ResponseBody
//    public List<CustomerOrder> delete(@PathVariable("id") Long id)
//    {
//        System.out.println("===========>>>>>>>>>>>>>>>> @DeleteMapping(\"/orders/{id}\")");
//
//        customerOrderRepository.deleteById(id);
//        return customerOrderRepository.findAll();
//    }

}
