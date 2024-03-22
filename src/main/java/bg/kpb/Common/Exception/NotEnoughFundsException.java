package bg.kpb.Common.Exception;

public class NotEnoughFundsException extends RuntimeException {
  private String message;

  public NotEnoughFundsException(String _message) {
    message = _message;
  }

  @Override
  public String getMessage(){
    return message;
  }
}
