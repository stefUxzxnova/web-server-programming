package uni.fmi.plovdiv.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearMonthDTO {
  
  private int year;
  private String month;
  private boolean leapYear;
  private int monthValue;
}
