package uni.fmi.plovdiv.service;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import uni.fmi.plovdiv.dto.maintenance.CreateMaintenanceDTO;
import uni.fmi.plovdiv.dto.maintenance.ResponseMaintenanceDTO;
import uni.fmi.plovdiv.dto.maintenance.UpdateMaintenanceDTO;
import uni.fmi.plovdiv.model.Maintenance;

public interface MaintenanceService {
  
  ResponseMaintenanceDTO getById(int id);
    
  ResponseMaintenanceDTO createMaintenance(CreateMaintenanceDTO createMaintenanceDTO);
  
  List<ResponseMaintenanceDTO> findBySearchCriteria(Specification<Maintenance> spec);
  
  ResponseMaintenanceDTO updateMaintenance(int id, UpdateMaintenanceDTO updateMaintenanceDTO);
  
  ResponseMaintenanceDTO deleteMaintenance(int id);
  
}
