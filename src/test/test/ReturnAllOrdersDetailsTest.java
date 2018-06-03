import bricks.Application;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Stage 1
 * Use case:
 * Given
 * Many customers have submitted orders for bricks
 * When
 * A "Get Orders" request is submitted
 * Then
 * All the orders details are returned
 * And
 * The order details contains the Order reference and the number of bricks ordered
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ReturnAllOrdersDetailsTest {

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
     * Test on "Get Orders" request which has a form of "http://localhost:9000/orders"
     * Then
     * All the orders details are returned
     * And
     * The order details contains the Order reference and the number of bricks ordered
     *
     * @throws Exception
     */
    @Test
    public void returnAllOrdersDetails() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/bricks_api/GetOrders/"))
                .andExpect(status().isOk())
//                .andDo(print())
                .andReturn();

        JsonParser parser = new JsonParser();
        Iterator<JsonElement> iterator = parser.parse(result.getResponse().getContentAsString())
                .getAsJsonArray().iterator();

        StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
                .forEach(e -> {
                    JsonObject jo = e.getAsJsonObject();    // jo has a form like this: {"id":3,"bricks":27}

                    String orderId = jo.get("id").getAsString();
                    String bricksInOrder = jo.get("bricks").getAsString();
                    System.out.println(
                            String.format("order # %s, bricks in the order %s", orderId, bricksInOrder)
                    );
                });
    }

    /**
     * WARNING: this method is out of scope and introduced here just for test purpose
     * <p>
     * Test on "Get Orders" request which has a form of "http://localhost:9000/orders"
     * Then
     * All the orders details are returned
     * And
     * The order details contains the Order reference and the number of bricks ordered
     *
     * @throws Exception
     */
    @Test
    public void returnAllOrdersDetailsAuxiliary() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/orders/"))
                .andExpect(status().isOk())
//                .andDo(print())
                .andReturn();

        JsonParser parser = new JsonParser();
        JsonElement je = parser.parse(result.getResponse().getContentAsString());

        Iterator<JsonElement> iterator = je.getAsJsonObject().get("_embedded").getAsJsonObject()
                .get("CustomerOrder").getAsJsonArray().iterator();

        StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
                .forEach(e -> {
                    String orderUri = e.getAsJsonObject().get("_links").getAsJsonObject()
                            .get("customerOrder").getAsJsonObject().get("href").getAsString();
//                    System.out.println(
//                            String.format("order # %s, bricks in the order %s",
//                                    orderUri.substring(orderUri.lastIndexOf("/") + 1),
//                                    e.getAsJsonObject().get("bricks").getAsInt()
//                            )
//                    );
                });
    }

}