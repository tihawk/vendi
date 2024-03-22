package bg.kpb.Common.Exception;

public class DuplicateEntryException extends RuntimeException {
  private String message;

  public DuplicateEntryException(String _message) {
    message = _message;
  }

  @Override
  public String getMessage(){
    return message;
  }
}
