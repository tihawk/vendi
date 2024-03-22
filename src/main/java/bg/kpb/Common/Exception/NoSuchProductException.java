package bg.kpb.Common.Exception;

public class NoSuchProductException extends RuntimeException {
  private String message;

  public NoSuchProductException(String _message) {
    message = _message;
  }

  @Override
  public String getMessage(){
    return message;
  }
}
