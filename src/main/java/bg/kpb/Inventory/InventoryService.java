package bg.kpb.Inventory;

import bg.kpb.Common.ProductDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class InventoryService {

  @Inject
  InventoryEntity inventory;

  public List<ProductDTO> listInventory() {
    List<ProductDTO> products = new ArrayList<>();
    var list = inventory.getInventory();
    list.forEach((p, q) -> products.add(new ProductDTO(p.getProductCode(), p.getProductName(), p.getPrice(), q)));
    return products;
  }

  public ProductDTO createProduct(ProductDTO _product) {
    ProductEntity product = new ProductEntity(_product.getProductCode(), _product.getProductName(), _product.getPrice());
    int quantity = Math.min(10, _product.getQuantity());
    return inventory.createProduct(product, quantity);
  }

  public ProductEntity editProduct(ProductEntity _product, int _productCode) {
    return inventory.editProduct(_product, _productCode);
  }

  public void insertItem(Integer _productCode) {
    inventory.insertItem(_productCode);
  }

  public void removeItem(Integer _productCode) {
    inventory.removeItem(_productCode);
  }

  public ProductEntity getItem(ProductEntity _product) {
    inventory.removeItem(_product);
    return _product;
  }

  public ProductEntity deleteProduct(Integer _productCode) {
    return inventory.deleteProduct(_productCode);
  }

  public boolean hasItem(ProductEntity _product) {
    return inventory.hasItem(_product);
  }

  public ProductEntity getProductByCode(int _productCode) {
    return inventory.getProductByCode(_productCode);
  }
}
