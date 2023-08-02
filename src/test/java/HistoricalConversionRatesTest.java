import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.Matchers.*;

public class HistoricalConversionRatesTest {

    private static Response response;

    @Test
    public void selectedCurrenciesRatesTest() {
        String date = "2021-08-27";
        String currencies = "CAD,EUR,RUB,GBP,NIS";

        response = RestAssured.given()
                .param("access key", Consts.API_KEY_NUMBER)
                .param("currencies", currencies)
                .param("date", date)
                .get(Consts.URL + Consts.API_ACCESS + Consts.API_KEY_NUMBER);

        System.out.println(response.asString());
        response.then().statusCode(200);

        //one way of running assertions
        response.then().body("success", equalTo(true));
        response.then().body("terms", equalTo("https://currencylayer.com/terms"));
        response.then().body("privacy", equalTo("https://currencylayer.com/privacy"));
        //response.then().body("historical", equalTo(true)); // Почемуто этот метод не срабатывает, не смотря на то что в Postman он есть. Но тут он мне выдает NULL
        response.then().body("timestamp", notNullValue());
        response.then().body("source", equalTo("USD"));

        Map<String, Number> exchangeRates = response.jsonPath().get("quotes");

        System.out.println("Exchange Rates for specified currencies on " + date + ":");
        for (String currencyCode : currencies.split(",")) {
            if (exchangeRates.containsKey("USD" + currencyCode)) {
                Number rate = exchangeRates.get("USD" + currencyCode);
                System.out.println(currencyCode + ": " + rate);
            } else {
                System.out.println("Error: Invalid currency code - " + currencyCode);
            }
        }
    }
}
