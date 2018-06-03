import bricks.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import utills.Utils;

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
public class RetriveOrderDetailsTest {

//    @LocalServerPort
//    private int port;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test on "GetOrder" - a primary method for retrieving a valid Order reference
     * Use case:
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
    public void retrieveOrderDetails() throws Exception {
        MvcResult result = mockMvc.perform(
                post("/bricks_api/CreateOrder")
                        .content("{\"bricks\": \"555\"}")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        String contentResponse = result.getResponse().getContentAsString();
        int firstOrderId = Utils.getOrderId(contentResponse);

        // valid order reference
        mockMvc.perform(
                get("/bricks_api/GetOrder/{id}", String.valueOf(firstOrderId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bricks").value("555"))
                .andExpect(jsonPath("$.id").value("1"));

        // invalid order reference
        mockMvc.perform(
                get("/bricks_api/GetOrder/{id}", "4567"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist());
    }

}