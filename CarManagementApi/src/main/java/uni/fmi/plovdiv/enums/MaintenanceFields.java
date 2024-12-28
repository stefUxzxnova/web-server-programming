package uni.fmi.plovdiv.enums;

public enum MaintenanceFields {
  
  ID("id"), 
  SCHEDULEDDATE("scheduledDate"),
  GARAGE("garageId"),
  CAR("car");

  
  private String fieldName;
  
  MaintenanceFields(String value) {
    this.fieldName = value;
  }
  
  public String getFieldName() {
    return fieldName;
  }
}
