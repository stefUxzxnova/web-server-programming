package uni.fmi.plovdiv.utils.mapper;

import uni.fmi.plovdiv.dto.garage.GarageRequest;
import uni.fmi.plovdiv.dto.garage.GarageResponse;
import uni.fmi.plovdiv.model.Garage;

public class GarageMapper {

  public static GarageResponse mapGarageToGarageResponse(Garage garage) {
    return new GarageResponse(
        garage.getId(),
        garage.getName(),
        garage.getLocation(), 
        garage.getCity(),
        garage.getCapacity()
        );
  }
  
  public static Garage mapGarageRequestToGarage(GarageRequest garageRequest) {
    return new Garage(
        garageRequest.getName(),
        garageRequest.getLocation(), 
        garageRequest.getCity(),
        garageRequest.getCapacity()
        );
  }
}
