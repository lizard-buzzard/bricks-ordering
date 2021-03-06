import bricks.config.WebConfigRetriveOrderDetailsTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
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
@ContextConfiguration(classes = WebConfigRetriveOrderDetailsTest.class)
public class RetriveOrderDetailsTest extends AbstractControllerTest {

//    @LocalServerPort
//    private int port;

    @Autowired
    private WebApplicationContext webAppContext;

    @Override
    public WebApplicationContext getWebAppContext() {
        return this.webAppContext;
    }

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
    public void retrieveOrderDetailsTest() throws Exception {
        MvcResult result = getMockMvc().perform(
                post("/bricks_api/CreateOrder")
                        .content("{\"bricks\": \"555\"}")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        String contentResponse = result.getResponse().getContentAsString();
        int firstOrderId = Utils.getOrderId(contentResponse);

        // valid order reference
        getMockMvc().perform(
                get("/bricks_api/GetOrder/{id}", String.valueOf(firstOrderId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bricks").value("555"))
                .andExpect(jsonPath("$.id").value("1"));

        // invalid order reference
        getMockMvc().perform(
                get("/bricks_api/GetOrder/{id}", "4567"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist());
    }

}