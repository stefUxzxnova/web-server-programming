package uni.fmi.plovdiv.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import uni.fmi.plovdiv.dto.report.GarageDailyAvailabilityReportDTO;
import uni.fmi.plovdiv.dto.report.MonthlyResponseReportDTO;

public interface ReportService {
  
  List<GarageDailyAvailabilityReportDTO> dailyAvilabilityReport(int garageId, LocalDate startDate, LocalDate endDate);
  
  List<MonthlyResponseReportDTO> getMonthlyReports(int garageId, YearMonth startMonth, YearMonth endMonth);

  //List<MonthlyRequestsReportDTO> getMonthlyReportsV2(int garageId, YearMonth startMonth, YearMonth endMonth);
}
