package uni.fmi.plovdiv.dto.car;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uni.fmi.plovdiv.dto.garage.GarageResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {
  
  private int id;
  
  private String make;
  
  private String model;
  
  private int productionYear;
  
  private String licensePlate;
  
  private List<GarageResponse> garages;
}
