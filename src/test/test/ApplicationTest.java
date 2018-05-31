import bricks.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Very first test to check for start of a Spring application context
     */
    @Test
    public void contextLoads() throws Exception {
    }


    /**
     * Second test, it checks if Customer Order is created
     * @throws Exception
     */
    @Test
    public void shouldCreateEntity() throws Exception {

        mockMvc.perform(
                post("/order")
                        .content(
                                "{\"bricks\": \"15\", \"orderSpec\": \"xxxxxxxx\"}"
                        )
        ).andExpect(
                status().isCreated()
        ).andExpect(
                header().string("Location", containsString("order/"))
        )
        ;
    }
}
