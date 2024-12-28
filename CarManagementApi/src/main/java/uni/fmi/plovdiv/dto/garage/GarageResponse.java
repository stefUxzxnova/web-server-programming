package uni.fmi.plovdiv.dto.garage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GarageResponse {

  private int id;
  
  private String name;
  
  private String location;
  
  private String city;
  
  private int capacity;

}
