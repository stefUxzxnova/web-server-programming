package uni.fmi.plovdiv.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyRequestsReportDTO {
  
  private String yearMonth;
  private Long requests;

}
