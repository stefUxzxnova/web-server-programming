package uni.fmi.plovdiv.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import jakarta.transaction.Transactional;
import uni.fmi.plovdiv.dto.garage.GarageRequest;
import uni.fmi.plovdiv.dto.garage.GarageResponse;
import uni.fmi.plovdiv.exception.InvalidGarageIdException;
import uni.fmi.plovdiv.model.Garage;
import uni.fmi.plovdiv.repository.GarageRepository;
import uni.fmi.plovdiv.service.GarageService;
import uni.fmi.plovdiv.utils.mapper.GarageMapper;

@Service
@Transactional
public class GarageServiceImpl implements GarageService{
  
  @Autowired
  private GarageRepository garageRepository;

  @Override
  public GarageResponse getById(int id) {
      Optional<Garage> garage = garageRepository.findById(id);
      if(!garage.isPresent()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Garage with the given ID does not exist!");
      }
      return GarageMapper.mapGarageToGarageResponse(garage.get());
  }
  
  public List<GarageResponse> findAllGarages(){
      return garageRepository.findAll().stream().map(GarageMapper::mapGarageToGarageResponse).toList();
  }

  public List<GarageResponse> findBySearchCriteria(Specification<Garage> spec){
      List<Garage> searchResult = garageRepository.findAll(spec);
      return searchResult.stream().map(GarageMapper::mapGarageToGarageResponse).toList();
  }

  @Override
  public GarageResponse createGarage(GarageRequest garageRequest) {
      Garage garage = GarageMapper.mapGarageRequestToGarage(garageRequest);
      garageRepository.save(garage);
      return GarageMapper.mapGarageToGarageResponse(garage);
  }

  @Override
  public GarageResponse updateGarage(int id, GarageRequest garageRequest) {
    Garage garage = validateGarageExistance(id);

    if (garageRequest.getName() != null && !garageRequest.getName().isBlank()) {
        garage.setName(garageRequest.getName());
    }
    if (garageRequest.getCity() != null && !garageRequest.getCity().isBlank()) {
        garage.setCity(garageRequest.getCity());
    }
    if (garageRequest.getLocation() != null && !garageRequest.getLocation().isBlank()) {
        garage.setLocation(garageRequest.getLocation());
    }
    if (garageRequest.getCapacity() > 0) {
        garage.setCapacity(garageRequest.getCapacity());
    }

    Garage updatedGarage = garageRepository.save(garage);

    return GarageMapper.mapGarageToGarageResponse(updatedGarage);
}

  @Override
  public GarageResponse deleteGarage(int id) {
      Garage garage = validateGarageExistance(id);
      garageRepository.delete(garage);
      return  GarageMapper.mapGarageToGarageResponse(garage);
  }
  
  private Garage validateGarageExistance(int id) {
    Optional<Garage> garageOpt = garageRepository.findById(id);
    if(!garageOpt.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Garage with the given ID does not exist!");
    }
    return garageOpt.get();
  }

  @Override
  public List<Garage> findAllWhereIdIn(List<Integer> ids) {
    return garageRepository.findAllByIdIn(ids);
  }

  @Override
  public Garage findGarageById(int id) {
    Optional<Garage> garageOpt = garageRepository.findById(id); 
    if(garageOpt.isPresent()) {
      return garageOpt.get();
    }
    throw new InvalidGarageIdException();
  }
  
  

}
