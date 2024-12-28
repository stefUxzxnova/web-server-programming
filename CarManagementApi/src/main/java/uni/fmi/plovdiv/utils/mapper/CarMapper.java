package uni.fmi.plovdiv.utils.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uni.fmi.plovdiv.dto.car.UpdateCarDTO;
import uni.fmi.plovdiv.dto.car.CarResponse;
import uni.fmi.plovdiv.dto.car.CreateCarDTO;
import uni.fmi.plovdiv.model.Car;
import uni.fmi.plovdiv.service.GarageService;

@Component
public class CarMapper {
  
  @Autowired private GarageService garageService;
  
  public CarResponse mapCarToCarResponse(Car car) {
    return new CarResponse(
        car.getId(),
        car.getMake(),
        car.getModel(),
        car.getProductionYear(),
        car.getLicensePlate(),
        car.getGarages().stream()
            .map(GarageMapper::mapGarageToGarageResponse)
            .toList()
    );
}

  public Car mapCarRequestToCar(UpdateCarDTO carRequest) {
    Car car = new Car();
    car.setMake(carRequest.getMake());
    car.setModel(carRequest.getModel());
    car.setProductionYear(carRequest.getProductionYear());
    car.setLicensePlate(carRequest.getLicensePlate());
    car.setGarages(garageService.findAllWhereIdIn(carRequest.getGarageIds()));
    return car;
  }
  
  public Car mapCarCreateDTORequestToCar(CreateCarDTO createCarDTO) {
    Car car = new Car();
    car.setMake(createCarDTO.getMake());
    car.setModel(createCarDTO.getModel());
    car.setProductionYear(createCarDTO.getProductionYear());
    car.setLicensePlate(createCarDTO.getLicensePlate());
    car.setGarages(garageService.findAllWhereIdIn(createCarDTO.getGarageIds()));
    return car;
  }

}
