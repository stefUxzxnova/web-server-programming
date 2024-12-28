package uni.fmi.plovdiv.service;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import uni.fmi.plovdiv.dto.car.UpdateCarDTO;
import uni.fmi.plovdiv.dto.car.CarResponse;
import uni.fmi.plovdiv.dto.car.CreateCarDTO;
import uni.fmi.plovdiv.model.Car;

public interface CarService {
  
  List<CarResponse> findBySearchCriteria(Specification<Car> spec);

  CarResponse createCar(CreateCarDTO carRequest);

  CarResponse updateCar(int id, UpdateCarDTO carRequest);
  
  CarResponse deleteCar(int id);
  
  CarResponse getById(int id);
  
  CarResponse getCarResponseById(int id);
  
  Car getCarById(int id);

}
