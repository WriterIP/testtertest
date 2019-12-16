package homework;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ApiTest {

    private static String author;
    private static String content;
    private static String date;
    private RequestSpec requestSpec = new RequestSpec();

    @BeforeAll
    private static void prepareTestData() {
        author = RandomStringUtils.randomAlphanumeric(10);
        content = RandomStringUtils.randomAlphabetic(0, 30);
        date = String.valueOf(LocalDateTime.now().withNano(0));
    }

    @Test
    @Order(1)
    public void createGistTest() throws JSONException {
//        RequestSpec requestSpec = new RequestSpec();
        JSONObject jsonObject = new JSONObject();

        jsonObject
                .put("author", author)
                .put("type", "INFO")
                .put("content", content)
                .put("validUntil", date);

        requestSpec.createGist("gist", jsonObject, author, content, date);

    }

    @Test
    @Order(2)
    public void getGistTest() {
//        RequestSpec requestSpec = new RequestSpec();
        String uuid = requestSpec.getUuid();

        requestSpec.getGist("gist/" + uuid, author, content, date);
    }
}
