package uni.fmi.plovdiv.service.impl;

import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import jakarta.transaction.Transactional;
import uni.fmi.plovdiv.dto.maintenance.CreateMaintenanceDTO;
import uni.fmi.plovdiv.dto.maintenance.ResponseMaintenanceDTO;
import uni.fmi.plovdiv.dto.maintenance.UpdateMaintenanceDTO;
import uni.fmi.plovdiv.dto.report.GarageDailyAvailabilityReportDTO;
import uni.fmi.plovdiv.exception.InvalidCarIdException;
import uni.fmi.plovdiv.exception.InvalidGarageIdException;
import uni.fmi.plovdiv.exception.InvalidMaintenanceIdException;
import uni.fmi.plovdiv.model.Car;
import uni.fmi.plovdiv.model.Garage;
import uni.fmi.plovdiv.model.Maintenance;
import uni.fmi.plovdiv.repository.CarRepository;
import uni.fmi.plovdiv.repository.GarageRepository;
import uni.fmi.plovdiv.repository.MaintenanceRepository;
import uni.fmi.plovdiv.service.MaintenanceService;
import uni.fmi.plovdiv.utils.mapper.MaintenanceMapper;

@Service
@Transactional
public class MaintenanceServiceImpl implements MaintenanceService{

  @Autowired private MaintenanceRepository maintenanceRepository;
  
  @Autowired private MaintenanceMapper maintenanceMapper;
  
  @Autowired private CarRepository carRepository;
  
  @Autowired private GarageRepository garageRepository;
  
  @Override
  public ResponseMaintenanceDTO getById(int id) {
    Maintenance maintenance = validateMaintenanceExistence(id);
    return maintenanceMapper.mapMaintenanceToMaintenanceResponse(maintenance);
  }

  @Override
  public ResponseMaintenanceDTO createMaintenance(CreateMaintenanceDTO createMaintenanceDTO) {
      Maintenance maintenance = maintenanceMapper.mapMaintenanceRequestToMaintenance(createMaintenanceDTO);

          Car car = carRepository.findById(createMaintenanceDTO.getCarId())
                  .orElseThrow(() -> new InvalidCarIdException("Car with ID " + createMaintenanceDTO.getCarId() + " not found"));
          Garage garage = garageRepository.findById(createMaintenanceDTO.getGarageId())
                  .orElseThrow(() -> new InvalidGarageIdException("Garage with ID " + createMaintenanceDTO.getGarageId() + " not found"));
      
      validateGarageCapacity(garage, maintenance);
          
      maintenance.setCar(car);
      maintenance.setGarage(garage);
      maintenanceRepository.save(maintenance);
      return maintenanceMapper.mapMaintenanceToMaintenanceResponse(maintenance);
  }

  private void validateGarageCapacity(Garage garage, Maintenance maintenance) {
    List<GarageDailyAvailabilityReportDTO> reports = maintenanceRepository.dailyAvailabilityReport(garage.getId(), maintenance.getScheduledDate(), maintenance.getScheduledDate());
    if(!reports.isEmpty() && reports.get(0).getRequests() == garage.getCapacity()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The garage is full!");
    }
    
  }

  @Override
  public List<ResponseMaintenanceDTO> findBySearchCriteria(Specification<Maintenance> spec) {
      List<Maintenance> maintenances = maintenanceRepository.findBySearchCriteria(spec);
      return maintenances.stream()
              .map(maintenanceMapper::mapMaintenanceToMaintenanceResponse)
              .toList();
  }

  @Override
  public ResponseMaintenanceDTO updateMaintenance(int id, UpdateMaintenanceDTO updateMaintenanceDTO) {
      Maintenance maintenance = validateMaintenanceExistence(id);

      if (updateMaintenanceDTO.getServiceType() != null && !updateMaintenanceDTO.getServiceType().isBlank()) {
          maintenance.setServiceType(updateMaintenanceDTO.getServiceType());
      }

      if (updateMaintenanceDTO.getScheduledDate() != null) {
          maintenance.setScheduledDate(updateMaintenanceDTO.getScheduledDate());
      }

      if (updateMaintenanceDTO.getCarId() != 0) {
          Car car = carRepository.findById(updateMaintenanceDTO.getCarId())
                  .orElseThrow(() -> new InvalidMaintenanceIdException("Car with ID " + updateMaintenanceDTO.getCarId() + " not found"));
          maintenance.setCar(car);
      }

      if (updateMaintenanceDTO.getGarageId() != 0) {
          Garage garage = garageRepository.findById(updateMaintenanceDTO.getGarageId())
                  .orElseThrow(() -> new InvalidMaintenanceIdException("Garage with ID " + updateMaintenanceDTO.getGarageId() + " not found"));
          maintenance.setGarage(garage);
      }

      maintenanceRepository.save(maintenance);
      return maintenanceMapper.mapMaintenanceToMaintenanceResponse(maintenance);
  }

  @Override
  public ResponseMaintenanceDTO deleteMaintenance(int id) {
      Maintenance maintenance = validateMaintenanceExistence(id);
      maintenanceRepository.delete(maintenance);
      return maintenanceMapper.mapMaintenanceToMaintenanceResponse(maintenance);
  }
  
  private Maintenance validateMaintenanceExistence(int id) {
    return maintenanceRepository.findById(id).orElseThrow(() -> new InvalidMaintenanceIdException("Maintenance with ID " + id + " not found"));
}

}
