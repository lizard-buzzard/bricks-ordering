import bricks.Application;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Stage 1
 * As a Rest Client
 * I want to retrieve orders
 * So I can display simple customers’ orders
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class RestClientRetriveOrdersTest {
    @Autowired
    private MockMvc mockMvc;
}
