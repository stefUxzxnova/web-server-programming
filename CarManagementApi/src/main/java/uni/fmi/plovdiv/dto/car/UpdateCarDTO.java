package uni.fmi.plovdiv.dto.car;

import java.util.List;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarDTO {
  
  @NotBlank(message = "Make cannot be blank")
  private String make;
  
  @NotBlank(message = "Model cannot be blank")
  private String model;
  
  @Min(1)
  private int productionYear;
  
  @NotBlank(message = "License plate cannot be blank")
  private String licensePlate;
  
  @NonNull
  private List<Integer> garageIds;
}
