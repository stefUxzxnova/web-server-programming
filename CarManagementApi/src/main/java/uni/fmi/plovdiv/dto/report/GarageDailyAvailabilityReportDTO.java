package uni.fmi.plovdiv.dto.report;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarageDailyAvailabilityReportDTO {

  private LocalDate date;
  private Long requests;
  private Long availableCapacity;
  
  public GarageDailyAvailabilityReportDTO(LocalDate date, Long requests) {
    this.date = date;
    this.requests = requests;
  }
}
