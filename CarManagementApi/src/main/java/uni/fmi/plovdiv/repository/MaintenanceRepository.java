package uni.fmi.plovdiv.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import uni.fmi.plovdiv.dto.report.GarageDailyAvailabilityReportDTO;
import uni.fmi.plovdiv.dto.report.MonthlyRequestsReportDTO;
import uni.fmi.plovdiv.model.Maintenance;

public interface MaintenanceRepository{
  
  List<Maintenance> findBySearchCriteria(Specification<Maintenance> spec);

  void save(Maintenance maintenance);
  
  void delete(Maintenance maintenance);
  
  Optional<Maintenance> findById(int id);
  
  List<MonthlyRequestsReportDTO> garageRequestsByMonth(int garageId, LocalDate starDate, LocalDate endDate);
  
  List<GarageDailyAvailabilityReportDTO> dailyAvailabilityReport(int garageId, LocalDate startDate, LocalDate endDate);


}
