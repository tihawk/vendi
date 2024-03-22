package bg.kpb.Inventory;

import bg.kpb.Common.Exception.DuplicateEntryException;
import bg.kpb.Common.Exception.ItemsOutOfBoundsException;
import bg.kpb.Common.Exception.NoSuchProductException;
import bg.kpb.Common.ProductDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Produces(MediaType.APPLICATION_JSON)
@Path("/product")
public class InventoryResource {

  @Inject
  InventoryService inventoryService;

  @GET
  @Path("/list")
  @APIResponse(
      responseCode = "200",
      description = "Returns a list of all available Products and their quantity",
      content = @Content(
          mediaType = "application/json",
          schema = @Schema(
              type = SchemaType.ARRAY,
              implementation = ProductDTO.class
          )
      )
  )
  public Response getInventoryList() {
    return Response.status(Response.Status.OK).entity(inventoryService.listInventory()).build();
  }

  @POST
  @Path("/create")
  @Consumes(MediaType.APPLICATION_JSON)
  @APIResponse(
      responseCode = "201",
      description = "Creates a new ProductEntity",
      content = @Content(
          mediaType = "application/json",
          schema = @Schema(
              description = "The newly created ProductEntity",
              implementation = ProductEntity.class
          )
      )
  )
  @APIResponse(
      responseCode = "409",
      description = "Duplicate record found. Product not created",
      content = @Content(
          mediaType = "text/plain"
      )
  )
  public Response createProduct(ProductDTO _product) {
    try {
      ProductEntity dbProduct = inventoryService.createProduct(_product);
      if (dbProduct == null) {
        return Response.status(501).build();
      }

      return Response.status(Response.Status.CREATED).entity(dbProduct).build();
    } catch (DuplicateEntryException e) {
      return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
    }
  }

  @PUT
  @Path("/edit")
  @Consumes(MediaType.APPLICATION_JSON)
  @APIResponse(
      responseCode = "200",
      description = "Edited a ProductEntity",
      content = @Content(
          mediaType = "application/json",
          schema = @Schema(
              description = "The newly edited ProductEntity",
              implementation = ProductEntity.class
          )
      )
  )
  @APIResponse(
      responseCode = "404",
      description = "Record not found. ProductEntity not edited",
      content = @Content(
          mediaType = "text/plain"
      )
  )
  public Response editProduct(ProductDTO _product) {
    try {
      ProductEntity editedProduct = inventoryService.editProduct(_product, _product.getProductCode());
      return Response.status(Response.Status.OK).entity(editedProduct).build();
    } catch (NoSuchProductException e) {
      return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }
  }
  @PATCH
  @Path("/insert/{productCode}")
  @Produces(MediaType.TEXT_PLAIN)
  @APIResponse(
      responseCode = "200",
      description = "Inserts a single item of a Product into the Inventory"
  )
  @APIResponse(
      responseCode = "400",
      description = "Trying to insert beyond item capacity OR product doesn't exist",
      content = @Content(
          mediaType = "text/plain"
      )
  )
  public Response insertProductItem(@PathParam("productCode") Integer _productCode) {
    try {
      inventoryService.insertItem(_productCode);
      return Response.status(Response.Status.OK).build();
    } catch (ItemsOutOfBoundsException | NoSuchProductException e) {
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @PATCH
  @Path("/remove/{productCode}")
  @Produces(MediaType.TEXT_PLAIN)
  @APIResponse(
      responseCode = "200",
      description = "Removes a single item of a Product from the Inventory"
  )
  @APIResponse(
      responseCode = "400",
      description = "Has no available items OR product doesn't exist",
      content = @Content(
          mediaType = "text/plain"
      )
  )
  public Response removeProductItem(@PathParam("productCode") Integer _productCode) {
    try {
      inventoryService.removeItem(_productCode);
      return Response.status(Response.Status.OK).build();
    } catch (ItemsOutOfBoundsException | NoSuchProductException e) {
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
  }

  @DELETE
  @Path("/delete/{productCode}")
  @Produces(MediaType.TEXT_PLAIN)
  @APIResponse(
      responseCode = "200",
      description = "Deletes an entire ProductEntity from the Inventory",
      content = @Content(
          mediaType = "text/plain"
      )
  )
  @APIResponse(
      responseCode = "404",
      description = "Product code not found, couldn't delete",
      content = @Content(
          mediaType = "text/plain"
      )
  )
  public Response deleteProduct(@PathParam("productCode") Integer _productCode) {
    try {
      inventoryService.deleteProduct(_productCode);
      return Response.status(Response.Status.OK).build();
    } catch (NoSuchProductException e) {
      return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }

  }
}
