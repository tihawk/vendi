package bg.kpb.Vending;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(VendingResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VendingResourceTest {

  @Test
  @Order(1)
  void insertCoin() {
    RestAssured.given()
        .when().put("insertCoin/st10")
        .then().log().ifStatusCodeIsEqualTo(200)
        .assertThat().statusCode(200)
        .body(is("10"));
  }

  @Test
  @Order(2)
  void resetCoins() {
    RestAssured.given()
        .when().post("resetCoins")
        .then().log().ifStatusCodeIsEqualTo(200)
        .assertThat().statusCode(200)
        .body(is("0"));
  }

  @Test
  @Order(3)
  void purchaseItem() {
    RestAssured.given()
        .when().put("insertCoin/st200")
        .then().log().ifStatusCodeIsEqualTo(200)
        .assertThat().statusCode(200)
        .body(is("200"));

    RestAssured.given()
        .when().post("buy/100")
        .then().log().ifStatusCodeIsEqualTo(200)
        .assertThat().statusCode(200)
        .contentType(MediaType.APPLICATION_JSON)
        .body("productCode", is(100));
  }

  @Test
  void purchaseItemDoesntExist() {
    RestAssured.given()
        .when().post("buy/1000")
        .then().log().ifStatusCodeIsEqualTo(404)
        .assertThat().statusCode(404);
  }

  @Test
  void purchaseItemNotEnoughFunds() {
    RestAssured.given()
        .when().post("buy/100")
        .then().log().ifStatusCodeIsEqualTo(417)
        .assertThat().statusCode(417);
  }
}