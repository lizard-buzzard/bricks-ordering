import bricks.Application;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import utills.Utils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class /*, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT*/)
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(getWebAppContext()).build();
    }

    public abstract WebApplicationContext getWebAppContext();

    public MockMvc getMockMvc() {
        return mockMvc;
    }

    public MockMvc getTestData() throws Exception {
        for (int i = 0; i < 10; i++) {
            mockMvc.perform(
                    post("/bricks_api/CreateOrder")
                            .content(String.format("{\"bricks\": \"%s\"}", Utils.getNextRandom()))
                            .characterEncoding("UTF-8")
                            .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());
        }
        return mockMvc;
    }
}
