import bricks.Application;
import org.junit.Before;
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

import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                    post("/orders").content(String.format("{\"bricks\": \"%s\"}", Utils.getNextRandom()))
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
        String orderToBeUpdatedId = "5";
        String bricksNoToBeUpdated = "555";

        // first, retrieve the order to be updated
        MvcResult getBeforeUpdateResult = mockMvc.perform(
                get("/bricks_api/GetOrder/{id}", orderToBeUpdatedId))
                .andExpect(status().isOk()).andReturn();

        long bricksNoBeforeUpdate = Utils.getBricks(getBeforeUpdateResult.getResponse().getContentAsString());

        // then update the order by other number of bricks
        MvcResult putResult = mockMvc.perform(
                put("/bricks_api/UpdateOrder/{id}", orderToBeUpdatedId)
                        .content("{\"bricks\": " + bricksNoToBeUpdated + "}")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        // finally, retrieve the order after update
        MvcResult getAfterUpdateResult = mockMvc.perform(
                get("/bricks_api/GetOrder/{id}", orderToBeUpdatedId))
                .andExpect(status().isOk()).andReturn();

        long bricksNoAfterUpdate = Utils.getBricks(getAfterUpdateResult.getResponse().getContentAsString());

        assertNotEquals(bricksNoBeforeUpdate, bricksNoAfterUpdate);
    }
}
