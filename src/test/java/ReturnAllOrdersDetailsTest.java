import bricks.Application;
import bricks.config.WebConfigReturnAllOrdersDetailsTest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
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
@ContextConfiguration(classes = WebConfigReturnAllOrdersDetailsTest.class)
public class ReturnAllOrdersDetailsTest extends AbstractControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReturnAllOrdersDetailsTest.class);

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private MockMvc mockMvc;

    @Override
    public WebApplicationContext getWebAppContext() {
        return this.webAppContext;
    }

    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(getWebAppContext()).build();
        mockMvc = getTestData();
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
    public void returnAllOrdersDetailsTest() throws Exception {
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

                    LOGGER.info(String.format("order # %s, bricks in the order %s", orderId, bricksInOrder));
                });
    }

//    /**
//     * WARNING: this method is out of scope and introduced here just for test purpose
//     * <p>
//     * Test on "Get Orders" request which has a form of "http://localhost:9000/orders"
//     * Then
//     * All the orders details are returned
//     * And
//     * The order details contains the Order reference and the number of bricks ordered
//     *
//     * @throws Exception
//     */
//    @Ignore
//    @Test
//    public void returnAllOrdersDetailsAuxiliaryTest() throws Exception {
//        MvcResult result = mockMvc.perform(
//                get("/orders/"))
//                .andExpect(status().isOk())
////                .andDo(print())
//                .andReturn();
//
//        JsonParser parser = new JsonParser();
//        JsonElement je = parser.parse(result.getResponse().getContentAsString());
//
//        Iterator<JsonElement> iterator = je.getAsJsonObject().get("_embedded").getAsJsonObject()
//                .get("CustomerOrder").getAsJsonArray().iterator();
//
//        StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
//                .forEach(e -> {
//                    String orderUri = e.getAsJsonObject().get("_links").getAsJsonObject()
//                            .get("customerOrder").getAsJsonObject().get("href").getAsString();
////                    System.out.println(
////                            String.format("order # %s, bricks in the order %s",
////                                    orderUri.substring(orderUri.lastIndexOf("/") + 1),
////                                    e.getAsJsonObject().get("bricks").getAsInt()
////                            )
////                    );
//                });
//    }

}
