import bricks.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
public class RestClientSubmitNewOrdersTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Very first test to check for start of a Spring application context
     */
    @Test
    public void contextLoads() throws Exception {
    }

    /**
     * Use case:
     * When
     * A "Create Order" request for a number of bricks is submitted
     * Then
     * An Order reference is returned
     *
     * @throws Exception
     */
    @Test
    public void orderReferenceIsReturned() throws Exception {
        MvcResult result = mockMvc.perform(
                post("/order").content("{\"bricks\": \"15\"}")
        ).andExpect(
                status().isCreated()
        ).andExpect(
                header().string("Location", startsWith("http://localhost/order/"))
        ).andDo(print()).andReturn();
    }

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
                post("/order").content("{\"bricks\": \"15\"}")
        ).andExpect(status().isCreated()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(
                post("/order").content("{\"bricks\": \"17\"}")
        ).andExpect(status().isCreated()).andReturn();

        String location1 = mvcResult1.getResponse().getHeader("Location");
        String location2 = mvcResult2.getResponse().getHeader("Location");

        assertFalse(location1.equals(location2));
    }
}
