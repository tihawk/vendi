package bg.kpb.Common.Exception;

public class ItemsOutOfBoundsException extends RuntimeException {
  private String message;

  public ItemsOutOfBoundsException(String _message) {
    message = _message;
  }

  @Override
  public String getMessage(){
    return message;
  }
}
