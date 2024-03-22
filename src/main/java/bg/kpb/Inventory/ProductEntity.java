package bg.kpb.Inventory;

import java.io.Serializable;

public class ProductEntity implements Serializable {
  private int productCode;
  private String productName;
  private int price;

  public ProductEntity() {}

  public ProductEntity(int _productCode, String _productName, int _price) {
    productCode = _productCode;
    productName = _productName;
    price = _price;
  }

  public int getProductCode() {
    return productCode;
  }

  public String getProductName() {
    return productName;
  }

  public int getPrice() {
    return price;
  }

  public void setProductCode(int productCode) {
    this.productCode = productCode;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
