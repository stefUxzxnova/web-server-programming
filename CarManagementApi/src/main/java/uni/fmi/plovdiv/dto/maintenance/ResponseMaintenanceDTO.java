package uni.fmi.plovdiv.dto.maintenance;

import java.sql.Date;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMaintenanceDTO {
  
  private int id;
  private int carId;
  private String carName;
  private int garageId;
  private String garageName;
  private String serviceType;
  private LocalDate scheduledDate; 

}
