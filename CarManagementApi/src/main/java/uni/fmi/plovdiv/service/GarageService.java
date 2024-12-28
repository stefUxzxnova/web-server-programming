package uni.fmi.plovdiv.service;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import uni.fmi.plovdiv.dto.garage.GarageRequest;
import uni.fmi.plovdiv.dto.garage.GarageResponse;
import uni.fmi.plovdiv.model.Garage;


public interface GarageService {
  
  GarageResponse getById(int id);
  
  List<GarageResponse> findAllGarages();
  
  GarageResponse createGarage(GarageRequest garageRequest);
  
  List<GarageResponse> findBySearchCriteria(Specification<Garage> spec);
  
  GarageResponse updateGarage(int id, GarageRequest garageRequest);
  
  GarageResponse deleteGarage(int id);
  
  List<Garage> findAllWhereIdIn(List<Integer> ids);
  
  Garage findGarageById(int id);
  
}
