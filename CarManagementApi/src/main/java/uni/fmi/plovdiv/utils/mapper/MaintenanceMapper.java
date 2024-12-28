package uni.fmi.plovdiv.utils.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uni.fmi.plovdiv.dto.car.UpdateCarDTO;
import uni.fmi.plovdiv.dto.car.CarResponse;
import uni.fmi.plovdiv.dto.maintenance.CreateMaintenanceDTO;
import uni.fmi.plovdiv.dto.maintenance.ResponseMaintenanceDTO;
import uni.fmi.plovdiv.model.Car;
import uni.fmi.plovdiv.model.Maintenance;
import uni.fmi.plovdiv.service.CarService;
import uni.fmi.plovdiv.service.GarageService;

@Component
public class MaintenanceMapper {
  
  @Autowired private CarService carService;
  
  @Autowired private GarageService garageService;
  
  public ResponseMaintenanceDTO mapMaintenanceToMaintenanceResponse(Maintenance maintenance) {
    return new ResponseMaintenanceDTO(
        maintenance.getId(),
        maintenance.getCar().getId(),
        maintenance.getCar().getMake(),
        maintenance.getGarage().getId(),
        maintenance.getGarage().getName(),
        maintenance.getServiceType(),
        maintenance.getScheduledDate()
    );
}

  public Maintenance mapMaintenanceRequestToMaintenance(CreateMaintenanceDTO createMaintenanceDTO) {
    Maintenance maintenance = new Maintenance();
    maintenance.setServiceType(createMaintenanceDTO.getServiceType());
    maintenance.setScheduledDate(createMaintenanceDTO.getScheduledDate());
    maintenance.setGarage(garageService.findGarageById(createMaintenanceDTO.getGarageId()));
    maintenance.setCar(carService.getCarById(createMaintenanceDTO.getCarId()));
    return maintenance;
  }
}
