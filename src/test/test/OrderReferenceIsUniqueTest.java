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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Stage 1
 * As a Rest Client
 * I want to submit new orders for bricks
 * So I can start customers’ orders
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class OrderReferenceIsUniqueTest {

    @Autowired
    private MockMvc mockMvc;

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
    public void orderReferenceIsUnique() throws Exception {
        MvcResult mvcResult1 = mockMvc.perform(
                post("/bricks_api/CreateOrder")
                        .content("{\"bricks\": \"15\"}")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(
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
