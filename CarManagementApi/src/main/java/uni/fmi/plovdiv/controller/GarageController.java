package uni.fmi.plovdiv.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import uni.fmi.plovdiv.dto.garage.GarageRequest;
import uni.fmi.plovdiv.dto.garage.GarageResponse;
import uni.fmi.plovdiv.dto.report.GarageDailyAvailabilityReportDTO;
import uni.fmi.plovdiv.enums.GarageFields;
import uni.fmi.plovdiv.model.Garage;
import uni.fmi.plovdiv.search.SearchCriteria;
import uni.fmi.plovdiv.search.specificationBuilder.SpecificationBuilder;
import uni.fmi.plovdiv.service.GarageService;
import uni.fmi.plovdiv.service.ReportService;

@RestController
@RequestMapping("/garages")
@CrossOrigin(origins = "*")
public class GarageController {
  
  @Autowired
  private GarageService garageService;
  
  @Autowired
  private ReportService reportService;

 
  @GetMapping
  public ResponseEntity<List<GarageResponse>> getGarages( @RequestParam(required = false) String city){
    
      SpecificationBuilder<Garage> builder = new SpecificationBuilder<>();
      List<SearchCriteria> criteriaList = constructGarageSearchCriterias(city);
      if(!criteriaList.isEmpty()){
          criteriaList.forEach(builder::with);
      }

      List<GarageResponse> garages = garageService.findBySearchCriteria(builder.build());
                                         
      return new ResponseEntity<>(garages, HttpStatus.OK);
  }

  @GetMapping("/dailyAvailabilityReport")
  public ResponseEntity<List<GarageDailyAvailabilityReportDTO>> getDailyAvailabilityReport(
      @RequestParam int garageId, 
      @RequestParam @DateTimeFormat(pattern = "YYYY-MM-DD") LocalDate startDate, 
      @RequestParam @DateTimeFormat(pattern = "YYYY-MM-DD") LocalDate endDate){

    List<GarageDailyAvailabilityReportDTO> reports = reportService.dailyAvilabilityReport(garageId, startDate, endDate);
                                       
    return new ResponseEntity<>(reports, HttpStatus.OK);
  }
  
  
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> addGarage(@RequestBody @Valid GarageRequest garageRequest, BindingResult result){
    if (result.hasErrors()) {
      StringBuilder sb = new StringBuilder();
      result.getAllErrors().forEach(error -> sb.append(error.getDefaultMessage()).append("\n"));
      return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
    }
    GarageResponse garage = garageService.createGarage(garageRequest);
    
    return new ResponseEntity<>(garage, HttpStatus.CREATED); 
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<?> updateGarage(@PathVariable int id, @RequestBody @Valid GarageRequest garageRequest,  BindingResult result){
    
    if (result.hasErrors()) {
      StringBuilder sb = new StringBuilder();
      result.getAllErrors().forEach(error -> sb.append(error.getDefaultMessage()).append("\n"));
      return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
    }
    GarageResponse garage = garageService.updateGarage(id, garageRequest);
    return new ResponseEntity<>(garage, HttpStatus.OK);
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteGarage(@PathVariable int id) {
    GarageResponse car = garageService.deleteGarage(id);
    return new ResponseEntity<>(car, HttpStatus.OK);
  }
  
  private List<SearchCriteria> constructGarageSearchCriterias(String city){
    List<SearchCriteria> sc = new ArrayList<>();
    if(city != null) {
          SearchCriteria one = new SearchCriteria(GarageFields.CITY.getFieldName(), city, "cn");
          sc.add(one);
    }
    return sc;
  }
  
}
