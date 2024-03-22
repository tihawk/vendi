package bg.kpb;

import bg.kpb.Common.ProductDTO;
import bg.kpb.Inventory.InventoryService;
import bg.kpb.Inventory.ProductEntity;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class OnStartup {

  @Inject
  InventoryService inventoryService;
  public void loadInventory(@Observes StartupEvent evt) {
    ProductDTO[] products = {
        new ProductDTO(100, "Coca-Cola", 120, 9),
        new ProductDTO(101, "Vita-Cola", 70, 10),
        new ProductDTO(102, "Club-Mate", 150, 11),
        new ProductDTO(103, "Wasser", 170, 11)
    };

    for (ProductDTO product : products) {
      inventoryService.createProduct(product);
    }
  }
}
