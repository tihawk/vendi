package bg.kpb.Vending;

import bg.kpb.Common.Exception.NoSuchProductException;
import bg.kpb.Common.Exception.NotEnoughFundsException;
import bg.kpb.Common.ProductDTO;
import bg.kpb.Inventory.ProductEntity;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;

@Path("")
public class VendingResource {
  final private static Logger logger = Logger.getLogger(VendingResource.class);

  @Inject
  VendingService vendingService;

  @PUT
  @Path("/insertCoin/{coinName}")
  @Produces({MediaType.TEXT_PLAIN})
  @APIResponse(
      responseCode = "200",
      description = "Adds the inserted coin to the current credit",
      content = @Content(
          mediaType = "text/plain",
          schema = @Schema(
              description = "Returns the current credit in st"
          )
      )
  )
  public Response insertCoin(@PathParam("coinName") CoinEntity _coin) {
    int result = vendingService.insertCoin(_coin);
    return Response.status(Response.Status.OK).entity(result).build();
  }

  @POST
  @Path("/resetCoins")
  @Produces({MediaType.TEXT_PLAIN})
  @APIResponse(
      responseCode = "200",
      description = "Resets the credit to 0",
      content = @Content(
          mediaType = "text/plain",
          schema = @Schema(
              description = "Returns the current credit in st (0)"
          )
      )
  )
  public Response resetCoins() {
    int result = vendingService.resetCoins();
    return Response.status(Response.Status.OK).entity(result).build();
  }

  @POST
  @Path("/buy/{productCode}")
  @Produces({MediaType.APPLICATION_JSON})
  @APIResponse(
      responseCode = "200",
      description = "Returns the purchased item as a ProductEntity",
      content = @Content(
          mediaType = "application/json",
          schema = @Schema(
              implementation = ProductEntity.class
          )
      )
  )
  @APIResponse(
      responseCode = "417",
      description = "Not enough funds to purchase the item",
      content = @Content(
          mediaType = "text/plain"
      )
  )
  @APIResponse(
      responseCode = "404",
      description = "Item is sold out OR product doesn't exist",
      content = @Content(
          mediaType = "text/plain"
      )
  )
  public Response purchaseItem(@PathParam("productCode") Integer _productCode) {
    try {
      return Response.status(Response.Status.OK).entity(vendingService.purchaseItem(_productCode)).build();
    } catch (NotEnoughFundsException e) {
      return Response.status(Response.Status.EXPECTATION_FAILED).entity(e.getMessage()).build();
    } catch (NoSuchProductException e) {
      return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }

  }

}
