package uni.fmi.plovdiv.dto.maintenance;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMaintenanceDTO {
  
  private int carId;
  private int garageId;
  private String serviceType;
  private LocalDate scheduledDate; 
}
