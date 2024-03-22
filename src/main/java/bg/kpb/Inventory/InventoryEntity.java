package bg.kpb.Inventory;

import bg.kpb.Common.Exception.DuplicateEntryException;
import bg.kpb.Common.Exception.ItemsOutOfBoundsException;
import bg.kpb.Common.Exception.NoSuchProductException;
import bg.kpb.Common.ProductDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class InventoryEntity {
  private Map<ProductEntity, Integer> inventory = new HashMap<>();

  public Map<ProductEntity, Integer> getInventory() {
    return inventory;
  }
  public ProductDTO createProduct(ProductEntity _product, int _count) {
    if (hasProduct(_product.getProductCode())) {
      throw new DuplicateEntryException("Product with provided productCode already exists");
    }
    int count = Math.min(10, _count);
    ProductEntity product = new ProductEntity(_product.getProductCode(), _product.getProductName(), _product.getPrice());
    inventory.put(product, count);
    return new ProductDTO(product.getProductCode(), product.getProductName(), product.getPrice(), count);
  }

  public ProductEntity editProduct(ProductEntity _product, int _productCode) {
    ProductEntity dbProduct = getProductByCode(_productCode);
    if (dbProduct == null) {
      throw new NoSuchProductException("No such product");
    }
    ProductEntity product = new ProductEntity(_product.getProductCode(), _product.getProductName(), _product.getPrice());
    inventory.put(product, inventory.remove(dbProduct));
    return getProductByCode(_productCode);
  }

  public int getProductQuantity (ProductEntity _product) {
    Integer count = inventory.get(_product);
    return count == null ? 0 : count;
  }

  public void insertItem(ProductEntity _product) {
    if (!canFitItem(_product)) {
      throw new ItemsOutOfBoundsException("Product quantity is at max capacity");
    }
    Integer count = inventory.get(_product);
    inventory.put(_product, count == null ? 1 : count + 1);
  }

  public void insertItem(int _productCode) {
    ProductEntity product = getProductByCode(_productCode);
    if (product == null) {
      throw new NoSuchProductException("Product with code " + _productCode + " doesn't exist.");
    }
    insertItem(product);
  }

  public void removeItem(ProductEntity _product) {
    if (!hasItem(_product)) {
      throw new ItemsOutOfBoundsException("No items left");
    }

    int count = inventory.get(_product);
    inventory.put(_product, count - 1);
  }

  public void removeItem(int _productCode) {
    ProductEntity product = getProductByCode(_productCode);
    if (product == null) {
      throw new NoSuchProductException("Product with code " + _productCode + " doesn't exist.");
    }
    removeItem(product);
  }

  public boolean hasProduct(int _productCode) {
    return inventory.keySet().stream().filter(pr -> pr.getProductCode() == _productCode).count() > 0;
  }

  public boolean hasItem(ProductEntity _product) {
    return getProductQuantity(_product) > 0;
  }

  public boolean canFitItem(ProductEntity _product) {
    return getProductQuantity(_product) < 10;
  }

  public ProductEntity getProductByCode(int _productCode) {
    return inventory.keySet().stream().filter(p -> p.getProductCode() == _productCode).findAny().orElse(null);
  }

  public ProductEntity deleteProduct(int _productCode) {
    ProductEntity product = inventory.keySet()
        .stream().filter(p -> p.getProductCode() == _productCode)
        .findAny().orElse(null);

    if (product == null) {
      throw new NoSuchProductException("Product with code " + _productCode + " doesn't exist.");
    }

    inventory.remove(product);
    return product;
  }
}
