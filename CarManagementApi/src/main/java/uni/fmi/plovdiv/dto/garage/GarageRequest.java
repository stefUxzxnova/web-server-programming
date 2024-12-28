package uni.fmi.plovdiv.dto.garage;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GarageRequest {
  
  @NotBlank(message = "Name cannot be blank")
  private String name;
  
  @NotBlank(message = "Location cannot be blank")
  private String location;
  
  @NotBlank(message = "City cannot be blank")
  private String city;

  @Min(1)
  private int capacity;
}
