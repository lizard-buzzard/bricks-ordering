import bricks.Application;
import bricks.config.WebConfigCreateOrderTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Stage 1
 * As a Rest Client
 * I want to submit new orders for bricks
 * So I can start customers’ orders
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class /*, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT*/)
@AutoConfigureMockMvc
@ContextConfiguration(classes = WebConfigCreateOrderTest.class)
public class CreateOrderTest extends AbstractControllerTest {

    @Autowired
    private WebApplicationContext webAppContext;

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Before
//    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
//    }

    @Override
    public WebApplicationContext getWebAppContext() {
        return this.webAppContext;
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
    public void orderReferenceIsReturnedTest() throws Exception {
        MvcResult result = getMockMvc().perform(
                post("/bricks_api/CreateOrder").content("{\"bricks\": \"15\"}")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.id").isNumber()  // this is the order reference which is returned
        ).andExpect(jsonPath("$.bricks").value("15")
        ).andDo(print()).andReturn();
    }

}