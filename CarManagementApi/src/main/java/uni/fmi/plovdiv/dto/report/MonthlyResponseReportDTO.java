package uni.fmi.plovdiv.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyResponseReportDTO {
  
  private YearMonthDTO yearMonth;
  private Long requests;
}
