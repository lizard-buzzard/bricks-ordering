import bricks.config.WebConfigUpdateOrderTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import utills.Utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Stage 2
 * As a Rest Client
 * I want to update orders for bricks
 * So I can update customers’ orders
 * Given
 * A customer has ordered a number of bricks
 * When
 * An "Update Order" request for an existing order reference and a number of bricks is submitted
 * Then
 * An Order reference is returned
 * And
 * The Order reference is unique to the submission
 */
@ContextConfiguration(classes = WebConfigUpdateOrderTest.class)
public class UpdateOrderTest extends AbstractControllerTest {

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private MockMvc mockMvc;

    @Override
    public WebApplicationContext getWebAppContext() {
        return this.webAppContext;
    }

    /**
     * Before test start we simulate that Many customers have submitted orders for bricks
     */
    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(getWebAppContext()).build();
        mockMvc = getTestData();
    }

    /**
     * Method checks that PUT request change number of bricks for existed order and do not change the order ID
     *
     * @throws Exception
     */
    @Test
    public void updateOrderTest() throws Exception {
        String orderToBeUpdatedId = "5";
        String bricksNoToBeUpdated = "555";

        // first, retrieve the order to be updated to check its attributes before the update
        MvcResult getBeforeUpdateResult = mockMvc.perform(
                get("/bricks_api/GetOrder/{id}", orderToBeUpdatedId))
                .andExpect(status().isOk()).andReturn();

        long bricksNoBeforeUpdate = Utils.getBricks(getBeforeUpdateResult.getResponse().getContentAsString());

        int orderIdBeforeUpdate = Utils.getOrderId(getBeforeUpdateResult.getResponse().getContentAsString());

        // then update the order by other number of bricks
        MvcResult putResult = mockMvc.perform(
                put("/bricks_api/UpdateOrder/{id}", orderToBeUpdatedId)
                        .content("{\"bricks\": \"" + bricksNoToBeUpdated + "\"}")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        int orderIdAfterUpdate = Utils.getOrderId(putResult.getResponse().getContentAsString());

        assertEquals(orderIdBeforeUpdate, orderIdAfterUpdate);

        // finally, retrieve again the order after update
        MvcResult getAfterUpdateResult = mockMvc.perform(
                get("/bricks_api/GetOrder/{id}", orderToBeUpdatedId))
                .andExpect(status().isOk()).andReturn();

        long bricksNoAfterUpdate = Utils.getBricks(getAfterUpdateResult.getResponse().getContentAsString());

        assertNotEquals(bricksNoBeforeUpdate, bricksNoAfterUpdate);
    }
}
