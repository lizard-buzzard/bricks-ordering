import bricks.config.WebConfigOrderReferenceIsUniqueTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import utills.Utils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Stage 1
 * As a Rest Client
 * I want to submit new orders for bricks
 * So I can start customers’ orders
 */
@ContextConfiguration(classes = WebConfigOrderReferenceIsUniqueTest.class)
public class OrderReferenceIsUniqueTest extends AbstractControllerTest {

    @Autowired
    private WebApplicationContext webAppContext;

    @Override
    public WebApplicationContext getWebAppContext() {
        return this.webAppContext;
    }

//    /**
//     * Very first test to check for start of a Spring application context
//     */
//    @Test
//    public void contextLoads() throws Exception {
//    }

    /**
     * Use case:
     * When
     * A "Create Order" request for a number of bricks is submitted
     * Then
     * The Order reference is unique to the submission
     *
     * @throws Exception
     */
    @Test
    public void orderReferenceIsUniqueTest() throws Exception {
        MvcResult mvcResult1 = getMockMvc().perform(
                post("/bricks_api/CreateOrder")
                        .content("{\"bricks\": \"15\"}")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        MvcResult mvcResult2 = getMockMvc().perform(
                post("/bricks_api/CreateOrder")
                        .content("{\"bricks\": \"17\"}")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        String contentResponse1 = mvcResult1.getResponse().getContentAsString();
        int firstOrderId = Utils.getOrderId(contentResponse1);

        String contentResponse2 = mvcResult2.getResponse().getContentAsString();
        int secondOrderId = Utils.getOrderId(contentResponse2);

        org.junit.Assert.assertNotEquals(firstOrderId , secondOrderId);
    }

}
