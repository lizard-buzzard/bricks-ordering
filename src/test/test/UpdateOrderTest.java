import bricks.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import utills.Utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class UpdateOrderTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Before test start we simulate that Many customers have submitted orders for bricks
     */
    @Before
    public void simulateManyCustomerOrdersSubmission() throws Exception {
        for (int i = 0; i < 20; i++) {
            mockMvc.perform(
                    post("/order").content(String.format("{\"bricks\": \"%s\"}", Utils.getNextRandom()))
            ).andExpect(status().isCreated());
        }
    }

    /**
     * Method checks that POST request change number of bricks for existed order and do not change the order ID
     *
     * @throws Exception
     */
    @Test
    public void updateByPostThroughSaveRepositoryMethodTest() throws Exception {
        // get order #5
        MvcResult orderNoFiveResultBeforeUpdate = mockMvc.perform(
                get("/order/search/GetOrder?id={id}", "5"))
                .andExpect(status().isOk()).andReturn();

        String contentBeforeUpdate = orderNoFiveResultBeforeUpdate.getResponse().getContentAsString();

        // new number of bricks for update order #5
        String bricksNumberToBeUpdated = "10000";

        // check that posted number of bricks for order #5 do not equal to 10_000
        assertThat(Utils.getBricks(contentBeforeUpdate) != Long.parseLong(bricksNumberToBeUpdated));

        // change ordered bricks for order #5 to 10000
        MvcResult mvcResult2 = mockMvc.perform(
                post("/order").content("{  \"id\" : \"5\", \"bricks\" : \"" + bricksNumberToBeUpdated + "\" }")
        ).andExpect(status().isCreated()).andReturn();

        // get order #5 after update
        MvcResult orderNoFiveResultAfterUpdate = mockMvc.perform(
                get("/order/search/GetOrder?id={id}", "5"))
                .andExpect(status().isOk()).andReturn();

        String contentAfterUpdate = orderNoFiveResultAfterUpdate.getResponse().getContentAsString();

        assertEquals(Long.parseLong(bricksNumberToBeUpdated), Utils.getBricks(contentAfterUpdate));
    }

    /**
     * Method checks that POST request change number of bricks for existed order and do not change the order ID
     *
     * @throws Exception
     */
//    @Test
//    public void updateByPatchThroughSaveRepositoryMethodTest() throws Exception {
//        // get order #5
//        MvcResult orderNoFiveResultBeforeUpdate = mockMvc.perform(
//                get("/order/search/GetOrder?id={id}", "5"))
//                .andExpect(status().isOk()).andReturn();
//
//        String contentBeforeUpdate = orderNoFiveResultBeforeUpdate.getResponse().getContentAsString();
//
//        // new number of bricks for update order #5
//        String bricksNumberToBeUpdated = "10000";
//
//        // check that posted number of bricks for order #5 do not equal to 10_000
//        assertThat(Utils.getBricks(contentBeforeUpdate) != Long.parseLong(bricksNumberToBeUpdated));
//
//        // change ordered bricks for order #5 to 10000
//        MvcResult mvcResult2 = mockMvc.perform(
//                patch("/order").content("{  \"id\" : \"5\", \"bricks\" : \"" + bricksNumberToBeUpdated + "\" }")
//         ).andExpect(status().isCreated()).andReturn();
//
//        // get order #5 after update
//        MvcResult orderNoFiveResultAfterUpdate = mockMvc.perform(
//                get("/order/search/GetOrder?id={id}", "5"))
//                .andExpect(status().isOk()).andReturn();
//
//        String contentAfterUpdate = orderNoFiveResultAfterUpdate.getResponse().getContentAsString();
//
//        assertEquals(Long.parseLong(bricksNumberToBeUpdated), Utils.getBricks(contentAfterUpdate));
//    }

}
