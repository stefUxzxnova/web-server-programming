package uni.fmi.plovdiv.enums;

public enum CarFields {
  MAKE("make"),
  GARAGE_ID("garage"),
  PRODUCTION_YEAR("productionYear");

  private final String fieldName;

  CarFields(String fieldName) {
      this.fieldName = fieldName;
  }

  public String getFieldName() {
      return fieldName;
  }
}
