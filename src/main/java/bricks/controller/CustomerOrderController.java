package bricks.controller;

import bricks.CustomerOrder;
import bricks.repo.CustomerOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public CustomerOrder create(@RequestBody CustomerOrder customerOrder) {
        return customerOrderRepository.save(customerOrder);
    }

    @GetMapping("/GetOrders")
    @ResponseBody
    public List<CustomerOrder> findAll() {
        return customerOrderRepository.findAll();
    }

    @PutMapping("/UpdateOrder/{id}")
    @ResponseBody
    public ResponseEntity<CustomerOrder> update(@PathVariable("id") Long id, @RequestBody CustomerOrder customerOrder) {
        return customerOrderRepository.findById(id)
                .filter(order->order.getIsDispatched().equals("no"))
                .map(order->{
                    order.setBricks(customerOrder.getBricks());
                    customerOrderRepository.save(order);
                    return new ResponseEntity<>(order, HttpStatus.OK);
                }).orElseThrow(() -> new BadRequestException());

//        CustomerOrder order = customerOrderRepository.getOne(id);
//        order.setBricks(customerOrder.getBricks());
//        return customerOrderRepository.save(order);
    }

    @GetMapping("/GetOrder/{id}")
    @ResponseBody
    public Optional<CustomerOrder> findOrderById(@PathVariable("id") Long id) {
        return customerOrderRepository.findById(id);
    }

    @PutMapping("/FulfilOrder/{id}")
    @ResponseBody
    public ResponseEntity<CustomerOrder> fulfilOrder(@PathVariable("id") Long id, @RequestBody CustomerOrder customerOrder) {
        return customerOrderRepository.findById(id)
                .map(order -> {
                    order.setIsDispatched(customerOrder.getIsDispatched());
                    customerOrderRepository.save(order);
                    return new ResponseEntity<>(order, HttpStatus.OK);
                }).orElseThrow(() -> new BadRequestException());
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
