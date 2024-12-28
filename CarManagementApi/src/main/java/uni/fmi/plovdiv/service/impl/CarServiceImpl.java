package uni.fmi.plovdiv.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import uni.fmi.plovdiv.dto.car.UpdateCarDTO;
import uni.fmi.plovdiv.dto.car.CarResponse;
import uni.fmi.plovdiv.dto.car.CreateCarDTO;
import uni.fmi.plovdiv.exception.InvalidCarIdException;
import uni.fmi.plovdiv.model.Car;
import uni.fmi.plovdiv.model.Garage;
import uni.fmi.plovdiv.repository.CarRepository;
import uni.fmi.plovdiv.repository.GarageRepository;
import uni.fmi.plovdiv.service.CarService;
import uni.fmi.plovdiv.utils.mapper.CarMapper;

@Service
@Transactional
public class CarServiceImpl implements CarService{
  
  @Autowired
  private CarRepository carRepository;

  @Autowired
  private GarageRepository garageRepository;
  
  @Autowired CarMapper carMapper;

  
  @Override
  public List<CarResponse> findBySearchCriteria(Specification<Car> spec) {
      List<Car> cars = carRepository.findAll(spec);
      return cars.stream()
                 .map(c -> carMapper.mapCarToCarResponse(c))
                 .toList();
  }

  @Override
  public CarResponse createCar(CreateCarDTO createCarDTO) {
      Car car = carMapper.mapCarCreateDTORequestToCar(createCarDTO);

      if (createCarDTO.getGarageIds() != null && !createCarDTO.getGarageIds().isEmpty()) {
          List<Garage> garages = garageRepository.findAllByIdIn(createCarDTO.getGarageIds());
          car.setGarages(garages);
      } 
      
      Car savedCar = carRepository.save(car);

      return carMapper.mapCarToCarResponse(savedCar);
  }

  @Override
  public CarResponse updateCar(int id, UpdateCarDTO carRequest) {
      Car car = validateCarExistence(id);

      if (carRequest.getMake() != null && !carRequest.getMake().isBlank()) {
          car.setMake(carRequest.getMake());
      }
      if (carRequest.getModel() != null && !carRequest.getModel().isBlank()) {
          car.setModel(carRequest.getModel());
      }
      if (carRequest.getProductionYear() > 0) {
          car.setProductionYear(carRequest.getProductionYear());
      }
      if (carRequest.getLicensePlate() != null && !carRequest.getLicensePlate().isBlank()) {
          car.setLicensePlate(carRequest.getLicensePlate());
      }
      if (carRequest.getGarageIds() != null) {
          List<Garage> garages = garageRepository.findAllByIdIn(carRequest.getGarageIds());
          car.setGarages(garages);
      }

      Car updatedCar = carRepository.save(car);

      return carMapper.mapCarToCarResponse(updatedCar);
  }

  
  private Car validateCarExistence(int id) {
      return carRepository.findById(id)
                          .orElseThrow(() -> new InvalidCarIdException("Car with ID " + id + " not found"));
  }

  @Override
  public CarResponse deleteCar(int id) {
    Car car = validateCarExistence(id);
    carRepository.delete(car);
    return carMapper.mapCarToCarResponse(car);
  }

  @Override
  public CarResponse getById(int id) {
    Car car = validateCarExistence(id);
    return carMapper.mapCarToCarResponse(car);
  }

  @Override
  public CarResponse getCarResponseById(int id) {
    Optional<Car> carOpt = carRepository.findById(id); 
    if(carOpt.isEmpty()) {
      throw new InvalidCarIdException("There is no existing car with the given id");
    }
    return carMapper.mapCarToCarResponse(carOpt.get());
  }
  
  @Override
  public Car getCarById(int id) {
    Optional<Car> carOpt = carRepository.findById(id); 
    if(carOpt.isEmpty()) {
      throw new InvalidCarIdException("There is no existing car with the given id");
    }
    return carOpt.get();
  }
}
