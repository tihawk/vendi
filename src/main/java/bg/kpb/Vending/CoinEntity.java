package bg.kpb.Vending;

public enum CoinEntity {
  st10(10), st20(20), st50(50), st100(100), st200(200);

  private int value;
  CoinEntity(int _value) {
    this.value = _value;
  }
  public int getValue() {
    return value;
  }
}
