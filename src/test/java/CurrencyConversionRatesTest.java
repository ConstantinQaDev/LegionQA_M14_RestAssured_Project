import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.*;

public class CurrencyConversionRatesTest {

    private static Response response;

    @Test
    public void currentCurrencyConversionRatesTest(){

        response = RestAssured.given()
                .get(Consts.URL + Consts.API_ACCESS + Consts.API_KEY_NUMBER);

        System.out.println(response.asString());
        response.then().statusCode(200);

        //one way of running assertions
        response.then().body("success", equalTo(true));
        response.then().body("terms", equalTo("https://currencylayer.com/terms"));
        response.then().body("privacy", equalTo("https://currencylayer.com/privacy"));
        response.then().body("timestamp", notNullValue());
        response.then().body("source", equalTo("USD"));

        Map<String, Number> exchangeRates = response.jsonPath().get("quotes");

        Set<String> currencyCodes = exchangeRates.keySet();

        System.out.println("Exchange Rates: ");
        for(String currencyCode : currencyCodes){
            Number rate = exchangeRates.get(currencyCode);
            System.out.println(currencyCode + ": " + rate);
        }
    }
}
