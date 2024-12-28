package uni.fmi.plovdiv.enums;

public enum GarageFields {
  
  ID("id"), 
  CITY("city");
  
  private String fieldName;
  
  GarageFields(String value) {
    this.fieldName = value;
  }
  
  public String getFieldName() {
    return fieldName;
  }

}
