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
        String uuid = requestSpec.getUuid();

        requestSpec.getGist("gist/" + uuid, author, content, date, 200);
    }

    @Test
    @Order(3)
    public void updateGistTest() throws JSONException {
        String uuid = requestSpec.getUuid();
        JSONObject jsonObject = new JSONObject();

        jsonObject
                .put("uuid", uuid)
                .put("author", "changedAuthor")
                .put("type", "INFO")
                .put("content", "changedContent")
                .put("validUntil", "2019-12-17T11:04:34");

        requestSpec.updateGist("gist/" + uuid, jsonObject, "changedAuthor", "changedContent", "2019-12-17T11:04:34");

    }

    @Test
    @Order(4)
    public void getUpdatedGistTest() {
        String uuid = requestSpec.getUuid();

        requestSpec.getGist("gist/" + uuid,"changedAuthor", "changedContent", "2019-12-17T11:04:34", 200);
    }

    @Test
    @Order(5)
    public void deleteGistTest() {
        String uuid = requestSpec.getUuid();

        requestSpec.deleteGist("gist/" + uuid);
    }

    @Test
    @Order(6)
    public void getDeletedGistTest() {
        String uuid = requestSpec.getUuid();

        requestSpec.getGist("gist/" + uuid, null, null, null, 404);
    }

}
