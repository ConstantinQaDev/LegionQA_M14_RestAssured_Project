import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
public class CurrencyAuthorizationTest {

    private static Response response;

    @Test
    public void authorizedAccessKeyTest(){
        response = given().get(Consts.URL + Consts.API_ACCESS + Consts.API_KEY_NUMBER);
        response.then().statusCode(200);

        System.out.println(response.asString());
    }
}
