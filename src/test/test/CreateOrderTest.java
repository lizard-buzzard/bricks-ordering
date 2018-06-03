import bricks.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class CreateOrderTest {

    @Autowired
    private MockMvc mockMvc;

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
                post("/bricks_api/CreateOrder").content("{\"bricks\": \"15\"}")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.id").value("1") // this is the order reference which is returned
        ).andExpect(jsonPath("$.bricks").value("15")
        ).andDo(print()).andReturn();
    }

}