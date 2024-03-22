package bg.kpb.Common;

import bg.kpb.Inventory.ProductEntity;

import java.io.Serializable;

public class ProductDTO extends ProductEntity implements Serializable {
  private int quantity;

  public ProductDTO() {}

  public ProductDTO(int _productCode, String _productName, int _price, int _quantity) {
    super(_productCode, _productName, _price);
    quantity = _quantity;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
