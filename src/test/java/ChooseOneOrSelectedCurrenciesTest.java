import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ChooseOneOrSelectedCurrenciesTest {


    private static Response response;

    @Test
    public void selectedCurrenciesRatesTest(){
        String currencies = "CAD, EUR, RUB, GBP";

        response = RestAssured.given()
                .param("access key", Consts.API_KEY_NUMBER)
                .param("currencies", currencies)
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

        System.out.println("Exchange Rates: ");
        for(String currencyCode : exchangeRates.keySet()){
            Number rate = exchangeRates.get(currencyCode);
            System.out.println(currencyCode + ": " + rate);
        }
    }
}

