import bricks.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Stage 1
 * As a Rest Client
 * I want to retrieve orders
 * So I can display simple customers’ orders
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class /*, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT*/)
@AutoConfigureMockMvc
public class RestClientRetriveOrdersTest {

//    @LocalServerPort
//    private int port;

    @Autowired
    private MockMvc mockMvc;

    /**
     * First method for retrieving a valid Order reference
     * When
     * A "Get Order" request is submitted with a valid Order reference
     * Then
     * The order details are returned
     * And
     * The order details contains the Order reference and the number of bricks ordered
     * When
     * A "Get Order" request is submitted with an invalid Order reference
     * Then
     * No order details are returned
     *
     * @throws Exception
     */
    @Test
    public void retrieveOrderDetailsForValidOrderReference() throws Exception {

        MvcResult mvcResult1 = mockMvc.perform(
                post("/order").content("{\"bricks\": \"15\"}")
        ).andExpect(status().isCreated()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(
                post("/order").content("{\"bricks\": \"17\"}")
        ).andExpect(status().isCreated()).andReturn();

        String validOrderReference = mvcResult2.getResponse().getHeader("Location");

        // validOrderReference is a valid Order reference
        // The order details are returned
        // The order details contains the Order reference and the number of bricks ordered
        //
        mockMvc.perform(get(validOrderReference)).andExpect(status().isOk()).andExpect(
                jsonPath("$.bricks").value("17")).andExpect(
                jsonPath("$._links.customerOrder.href").value("http://localhost/order/2"));

        // invalidOrderReference contains invalid order reference
        // HttpStatus.NOT_FOUND (404)
        //
        String invalidOrderReference = "http://localhost/order/2222";
        mockMvc.perform(get(invalidOrderReference)).andExpect(status().isNotFound());
    }

    /**
     * Second method for retrieving a valid Order reference
     * When
     * A "Get Order" request is submitted with a valid Order reference
     * Then
     * The order details are returned
     * And
     * The order details contains the Order reference and the number of bricks ordered
     * When
     * A "Get Order" request is submitted with an invalid Order reference
     * Then
     * No order details are returned
     *
     * @throws Exception
     */
    @Test
    public void retrieveOrderDetailsForValidOrderReference2() throws Exception {
        MvcResult result = mockMvc.perform(
                post("/order").content("{\"bricks\": \"555\"}")
        ).andExpect(status().isCreated()).andReturn();

        String loc = result.getResponse().getHeader("Location");
        String orderNumber = loc.substring(loc.lastIndexOf("/") + 1);

        // valid order reference
        mockMvc.perform(
                get("/order/search/findBricksById?id={id}", orderNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bricks").value("555"))
                .andExpect(jsonPath("$._links.customerOrder.href").value("http://localhost/order/" + orderNumber));

        // invalid order reference
        mockMvc.perform(
                get("/order/search/findBricksById?id={id}", orderNumber+"4567"))
                .andExpect(status().isNotFound());
    }
}