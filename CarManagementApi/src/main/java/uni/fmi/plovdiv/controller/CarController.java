package uni.fmi.plovdiv.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import uni.fmi.plovdiv.dto.car.UpdateCarDTO;
import uni.fmi.plovdiv.dto.car.CarResponse;
import uni.fmi.plovdiv.dto.car.CreateCarDTO;
import uni.fmi.plovdiv.enums.CarFields;
import uni.fmi.plovdiv.model.Car;
import uni.fmi.plovdiv.search.SearchCriteria;
import uni.fmi.plovdiv.search.specificationBuilder.SpecificationBuilder;
import uni.fmi.plovdiv.service.CarService;

@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = "*")
public class CarController {
  
  @Autowired
  private CarService carService;

  @GetMapping
  public ResponseEntity<List<CarResponse>> getCars(
          @RequestParam(required = false) String carMake,
          @RequestParam(required = false) Integer garageId,
          @RequestParam(required = false)  Integer fromYear,
          @RequestParam(required = false)  Integer toYear) {

      SpecificationBuilder<Car> builder = new SpecificationBuilder<>();
      List<SearchCriteria> criteriaList = constructCarSearchCriteria(carMake, garageId, fromYear, toYear);

      if (!criteriaList.isEmpty()) {
          criteriaList.forEach(builder::with);
      }

      List<CarResponse> cars = carService.findBySearchCriteria(builder.build());
      return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CarResponse> getCarById(@PathVariable int id){
      CarResponse car = carService.getCarResponseById(id);
      return new ResponseEntity<>(car, HttpStatus.OK);
  }

  
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> addCar(@RequestBody @Valid CreateCarDTO createCarDTO, BindingResult result) {
      if (result.hasErrors()) {
        StringBuilder sb = new StringBuilder();
        result.getAllErrors().forEach(error -> sb.append(error.getDefaultMessage()).append("\n"));
        return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
      }
      CarResponse car = carService.createCar(createCarDTO);
      return new ResponseEntity<>(car, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateCar(@PathVariable int id, @RequestBody @Valid UpdateCarDTO carRequest, BindingResult result) {
    if (result.hasErrors()) {
      StringBuilder sb = new StringBuilder();
      result.getAllErrors().forEach(error -> sb.append(error.getDefaultMessage()).append("\n"));
      return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
    }
      CarResponse car = carService.updateCar(id, carRequest);
      return new ResponseEntity<>(car, HttpStatus.OK);
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCar(@PathVariable int id) {
    CarResponse car = carService.deleteCar(id);
    return new ResponseEntity<>(car, HttpStatus.OK);
  }

  private List<SearchCriteria> constructCarSearchCriteria(String make, Integer garageId, Integer fromYear, Integer toYear) {
      List<SearchCriteria> sc = new ArrayList<>();

      if (make != null && !make.isBlank()) {
          sc.add(new SearchCriteria(CarFields.MAKE.getFieldName(), make, "cn")); 
      }
      if (garageId != null && garageId > 0) {
          sc.add(new SearchCriteria(CarFields.GARAGE_ID.getFieldName(), garageId, "eq")); 
      }
      if (fromYear != null) {
          sc.add(new SearchCriteria(CarFields.PRODUCTION_YEAR.getFieldName(), fromYear, "ge"));
      }
      if (toYear != null) {
          sc.add(new SearchCriteria(CarFields.PRODUCTION_YEAR.getFieldName(), toYear, "le")); 
      }
      return sc;
      }
}
