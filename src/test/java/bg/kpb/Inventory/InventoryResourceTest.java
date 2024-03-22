package bg.kpb.Inventory;

import bg.kpb.Common.ProductDTO;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(InventoryResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventoryResourceTest {
  private final static Logger logger = Logger.getLogger(InventoryResourceTest.class);

  @Test
  @Order(1)
  void getInventoryList() {
    RestAssured.given()
        .when().get("list")
        .then().log().ifStatusCodeIsEqualTo(200)
        .assertThat().statusCode(200)
        .and().body("size()", is(4));
  }

  @Test
  @Order(2)
  void createProduct() {
    RestAssured.given()
        .body(new ProductDTO(105, "this is sparta", 230, 13))
        .contentType(MediaType.APPLICATION_JSON)
        .when().post("create")
        .then().log().ifStatusCodeIsEqualTo(201)
        .assertThat().statusCode(201)
        .and().body("productCode", is(105))
        .and().body("quantity", is(10));
  }

  @Test
  @Order(3)
  void editProduct() {
    RestAssured.given()
        .body(new ProductDTO(105, "this is NOT sparta", 230, 13))
        .contentType(MediaType.APPLICATION_JSON)
        .when().put("edit")
        .then().log().ifStatusCodeIsEqualTo(200)
        .assertThat().statusCode(200)
        .and().body("productCode", is(105))
        .and().body("productName", is("this is NOT sparta"));
  }

  @Test
  @Order(4)
  void insertProductItem() {
    RestAssured.given()
        .when().patch("insert/101")
        .then().log().ifStatusCodeIsEqualTo(400)
        .assertThat().statusCode(400);
  }

  @Test
  @Order(5)
  void removeProductItem() {
    RestAssured.given()
        .when().patch("remove/100")
        .then().log().ifStatusCodeIsEqualTo(200)
        .assertThat().statusCode(200);
  }

  @Test
  @Order(6)
  void deleteProduct() {
    RestAssured.given()
        .when().delete("delete/102")
        .then().log().ifStatusCodeIsEqualTo(200)
        .assertThat().statusCode(200);
  }
}