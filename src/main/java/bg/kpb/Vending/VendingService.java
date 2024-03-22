package bg.kpb.Vending;

import bg.kpb.Common.Exception.ItemsOutOfBoundsException;
import bg.kpb.Common.Exception.NoSuchProductException;
import bg.kpb.Common.Exception.NotEnoughFundsException;
import bg.kpb.Inventory.InventoryService;
import bg.kpb.Inventory.ProductEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class VendingService {
  @Inject
  InventoryService inventoryService;
  private int coinage = 0;

  public int insertCoin(CoinEntity _coin) {
    int amountToAdd = 0;
    try {
      amountToAdd = _coin.getValue();
    } catch (IllegalArgumentException e) {
      amountToAdd = 0;
    } finally {
      coinage += amountToAdd;
      return coinage;
    }
  }

  public int resetCoins() {
    coinage = 0;
    return coinage;
  }

  public ProductEntity purchaseItem(Integer _productCode) {
    ProductEntity product;
    product = inventoryService.getProductByCode(_productCode);
    if (product == null || !inventoryService.hasItem(product)) {
      throw new NoSuchProductException("Item sold out");
    }
    int positiveSum = coinage - product.getPrice();
    if (positiveSum < 0) {
      throw new NotEnoughFundsException("Not enough funds, you need extra lv " + positiveSum/(-100f));
    }
    inventoryService.removeItem(_productCode);
    resetCoins(); //my interpretation of "not giving back change"
    return product;
  }

}
