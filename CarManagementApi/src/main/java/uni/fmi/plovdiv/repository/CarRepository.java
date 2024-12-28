package uni.fmi.plovdiv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uni.fmi.plovdiv.model.Car;

public interface CarRepository extends JpaRepository<Car, Integer>, JpaSpecificationExecutor<Car>{
  
}
