import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class CurrencyInvalidKeyTest {

    private static Response response;

    @Test
    public void unauthorizedAccessKeyTest(){
        response = given().get(Consts.URL + Consts.API_ACCESS + "Invalid_API_Key");
        response.then().statusCode(200);

        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(101));
        response.then().body("error.type", equalTo("invalid_access_key"));
        response.then().body("error.info", containsString("You have not supplied a valid API Access Key."));

        System.out.println(response.asString());
    }
}