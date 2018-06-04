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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Stage 3
 * Given
 * An order exists
 * When
 * A "Fulfil Order" request is submitted for a valid Order reference
 * Then
 * The Order is marked as dispatched
 * Given
 * An order exists
 * When
 * A Fulfil Order request is submitted for a invalid Order reference
 * Then
 * A 400 bad request response is returned
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class FulfilOrderTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Before test start we simulate that Many customers have submitted orders for bricks
     */
    @Before
    public void simulateManyCustomerOrdersSubmissionTest() throws Exception {
        for (int i = 0; i < 20; i++) {
            mockMvc.perform(
                    post("/orders").content(String.format("{\"bricks\": \"%s\"}", Utils.getNextRandom()))
            ).andExpect(status().isCreated());
        }
    }

    /**
     * Method checks that PUT request change number of bricks for existed order and do not change the order ID
     *
     * @throws Exception
     */
    @Test
    public void fulfilValidOrderIdTest() throws Exception {
        String orderToBeFulfilledId = "5";
        String isDispatched = "yes";

        // first, retrieve the order to be fulfilled to check its attributes before the update
        MvcResult getBeforeUpdateResult = mockMvc.perform(
                get("/bricks_api/GetOrder/{id}", orderToBeFulfilledId))
                .andExpect(status().isOk()).andReturn();

        String dispatchStatusBeforeFulfil =
                Utils.getDispatchStatus(getBeforeUpdateResult.getResponse().getContentAsString());
        int orderIdBeforeFulfil = Utils.getOrderId(getBeforeUpdateResult.getResponse().getContentAsString());

        // then update the order by changing its dispatch status
        MvcResult putResult = mockMvc.perform(
                put("/bricks_api/FulfilOrder/{id}", orderToBeFulfilledId)
                        .content("{\"isDispatched\": \"" + isDispatched + "\"}")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        int orderIdAfterFulfil = Utils.getOrderId(putResult.getResponse().getContentAsString());

        assertEquals(orderIdBeforeFulfil, orderIdAfterFulfil);

        // finally, retrieve again the order after update to check its attributes
        MvcResult getAfterUpdateResult = mockMvc.perform(
                get("/bricks_api/GetOrder/{id}", String.valueOf(orderIdAfterFulfil)))
                .andExpect(status().isOk()).andReturn();

        String dispatchStatusAfterFulfil =
                Utils.getDispatchStatus(getAfterUpdateResult.getResponse().getContentAsString());

        assertEquals("yes", dispatchStatusAfterFulfil);
    }

    @Test
    public void fulfilInvalidOrderIdTest() throws Exception {
        String orderToBeFulfilledId = "55555";
        String isDispatched = "yes";

        // first, try to retrieve the order to be fulfilled to check that it doesn't exist
        mockMvc.perform(
                get("/bricks_api/GetOrder/{id}", orderToBeFulfilledId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist());

        // then update the order by changing its dispatch status
        mockMvc.perform(
                put("/bricks_api/FulfilOrder/{id}", orderToBeFulfilledId)
                        .content("{\"isDispatched\": \"" + isDispatched + "\"}")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
}
