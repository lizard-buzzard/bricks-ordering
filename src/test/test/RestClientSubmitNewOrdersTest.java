import bricks.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
     * When
     * A "Create Order" request for a number of bricks is submitted
     * Then
     * An Order reference is returned
     *
     * @throws Exception
     */
    @Test
    public void orderReferenceIsReturned() throws Exception {
        mockMvc.perform(
                post("/CreateOrder")
                        .content("{\"bricks\": \"15\", \"orderSpec\": \"xxxxxxxx\"}")
        ).andExpect(
                status().isCreated()
        ).andExpect(
                header().string("Location", equalTo("http://localhost/CreateOrder/1"))
        );
    }

    /**
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
                post("/CreateOrder").content("{\"bricks\": \"15\", \"orderSpec\": \"xxxxxxxx\"}")
        ).andExpect(status().isCreated()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(
                post("/CreateOrder").content("{\"bricks\": \"17\", \"orderSpec\": \"yyyyyyyy\"}")
        ).andExpect(status().isCreated()).andReturn();

        String location1 = mvcResult1.getResponse().getHeader("Location");
        String location2 = mvcResult2.getResponse().getHeader("Location");

        assertFalse(location1.equals(location2));
    }
}
