package uni.fmi.plovdiv.service.impl;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uni.fmi.plovdiv.dto.report.GarageDailyAvailabilityReportDTO;
import uni.fmi.plovdiv.dto.report.MonthlyRequestsReportDTO;
import uni.fmi.plovdiv.dto.report.MonthlyResponseReportDTO;
import uni.fmi.plovdiv.dto.report.YearMonthDTO;
import uni.fmi.plovdiv.exception.InvalidGarageIdException;
import uni.fmi.plovdiv.model.Garage;
import uni.fmi.plovdiv.repository.GarageRepository;
import uni.fmi.plovdiv.repository.MaintenanceRepository;
import uni.fmi.plovdiv.service.ReportService;

@Service
@Transactional
public class ReportServiceImpl implements ReportService{

  @Autowired private MaintenanceRepository maintenanceRepository;
  
  @Autowired private GarageRepository garageRepository;
  
  

  @Override
  public List<GarageDailyAvailabilityReportDTO> dailyAvilabilityReport(int garageId,
      LocalDate startDate, LocalDate endDate) {
    Optional<Garage> garageOpt = garageRepository.findById(garageId);
    if(garageOpt.isEmpty()) {
      throw new InvalidGarageIdException();
    }
    List<GarageDailyAvailabilityReportDTO> reports = maintenanceRepository.dailyAvailabilityReport(garageId, startDate, endDate);
    reports.forEach(r -> r.setAvailableCapacity(garageOpt.get().getCapacity() - r.getRequests()));

    return reports;
  }


  @Override
  public List<MonthlyResponseReportDTO> getMonthlyReports(int garageId, YearMonth startMonth, YearMonth endMonth) {
    LocalDate startDate = startMonth.atDay(1);
    LocalDate endDate = endMonth.atEndOfMonth();
    
    List<MonthlyRequestsReportDTO> reportsFromDb = maintenanceRepository.garageRequestsByMonth(garageId, startDate, endDate);
    return transformReports(reportsFromDb, startMonth, endMonth);
    
  }



  private List<MonthlyResponseReportDTO> transformReports(List<MonthlyRequestsReportDTO> garageRequestsByMonth, YearMonth startMonth, YearMonth endMonth) {

    Map<YearMonth, Long> requestsMap = garageRequestsByMonth.stream()
            .collect(Collectors.toMap(
                    report -> YearMonth.parse(report.getYearMonth()), 
                    MonthlyRequestsReportDTO::getRequests             
            ));

    List<MonthlyResponseReportDTO> result = new ArrayList<>();

    YearMonth currentMonth = startMonth;
    while (!currentMonth.isAfter(endMonth)) {
        
        Long requests = requestsMap.getOrDefault(currentMonth, 0L);
        //YearMonth ym = YearMonth.of(currentMonth.getYear(), currentMonth.getMonth());

        
        YearMonthDTO yearMonthDTO = new YearMonthDTO();
        yearMonthDTO.setYear(currentMonth.getYear());
        yearMonthDTO.setMonth(currentMonth.getMonth().name());
        yearMonthDTO.setMonthValue(currentMonth.getMonthValue());
        yearMonthDTO.setLeapYear(currentMonth.isLeapYear());

        MonthlyResponseReportDTO responseDTO = new MonthlyResponseReportDTO();
        responseDTO.setYearMonth(yearMonthDTO);
        responseDTO.setRequests(requests);
      
        result.add(responseDTO);

        currentMonth = currentMonth.plusMonths(1);
    }

    return result;
  }

  
}
