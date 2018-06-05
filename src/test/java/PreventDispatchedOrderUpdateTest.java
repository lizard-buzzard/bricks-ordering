import bricks.Application;
import bricks.config.WebConfigCreateOrderTest;
import bricks.config.WebConfigPreventDispatchedOrderUpdateTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import utills.Utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Stage 4
 * As a Rest Client
 * I want to prevent updates to an order, when that order has been dispatched
 * So I don't accept updates to orders that have already shipped
 * Given
 * An order exists
 * And
 * That order has been dispatched
 * When
 * A Update Order request is submitted for a valid Order reference
 * Then
 * a 400 bad request response is returned
 */
@RunWith(SpringRunner.class)
//@WebMvcTest(PreventDispatchedOrderUpdateTest.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = WebConfigPreventDispatchedOrderUpdateTest.class)
public class PreventDispatchedOrderUpdateTest extends AbstractControllerTest {

    @Autowired
    private WebApplicationContext webAppContext;

    @Override
    public WebApplicationContext getWebAppContext() {
        return this.webAppContext;
    }

    @Test
    public void preventUpdateDispatchedOrderTest() throws Exception {
        String originalBricksNoInOrder = "15";

        // create order
        MvcResult postResult = getMockMvc().perform(
                post("/bricks_api/CreateOrder").content("{\"bricks\": \""+originalBricksNoInOrder+"\"}")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.id").isNumber() // this is the order reference which is returned
        ).andExpect(jsonPath("$.bricks").value(originalBricksNoInOrder)
        ).andDo(print()).andReturn();

        int orderIdAfterPost = Utils.getOrderId(postResult.getResponse().getContentAsString());

        // change order's bricks number to ensure it could be changed
        String bricksNoToBeUpdated = "555";

        MvcResult putResult = getMockMvc().perform(
                put("/bricks_api/UpdateOrder/{id}", String.valueOf(orderIdAfterPost))
                        .content("{\"bricks\": \"" + bricksNoToBeUpdated + "\"}")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        int orderIdAfterUpdate = Utils.getOrderId(putResult.getResponse().getContentAsString());
        long bricksNoAfterUpdate = Utils.getBricks(putResult.getResponse().getContentAsString());
        String isDispatchedAfterUpdate = Utils.getDispatchStatus(putResult.getResponse().getContentAsString());

        assertEquals(orderIdAfterPost, orderIdAfterUpdate);
        assertNotEquals(Long.parseLong(originalBricksNoInOrder), bricksNoAfterUpdate);
        assertEquals("no", isDispatchedAfterUpdate);

        // dispatch order
        String isDispatched = "yes";

        MvcResult dispatchResult = getMockMvc().perform(
                put("/bricks_api/FulfilOrder/{id}", String.valueOf(orderIdAfterUpdate))
                        .content("{\"isDispatched\": \"" + isDispatched + "\"}")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        String isDispatchedAfterDispatch = Utils.getDispatchStatus(dispatchResult.getResponse().getContentAsString());
        assertEquals("yes", isDispatchedAfterDispatch);

        // try to change already dispatched order
        getMockMvc().perform(
                put("/bricks_api/UpdateOrder/{id}", String.valueOf(orderIdAfterUpdate))
                        .content("{\"isDispatched\": \"" + isDispatched + "\"}")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

    }
}
