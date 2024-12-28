package uni.fmi.plovdiv.controller;

import java.time.LocalDate;
import java.time.YearMonth;
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
import uni.fmi.plovdiv.dto.maintenance.CreateMaintenanceDTO;
import uni.fmi.plovdiv.dto.maintenance.ResponseMaintenanceDTO;
import uni.fmi.plovdiv.dto.maintenance.UpdateMaintenanceDTO;
import uni.fmi.plovdiv.dto.report.MonthlyRequestsReportDTO;
import uni.fmi.plovdiv.dto.report.MonthlyResponseReportDTO;
import uni.fmi.plovdiv.enums.MaintenanceFields;
import uni.fmi.plovdiv.model.Maintenance;
import uni.fmi.plovdiv.search.SearchCriteria;
import uni.fmi.plovdiv.search.specificationBuilder.SpecificationBuilder;
import uni.fmi.plovdiv.service.MaintenanceService;
import uni.fmi.plovdiv.service.ReportService;

@RestController
@RequestMapping("/maintenance")
@CrossOrigin(origins = "*")
public class MaintenanceController {
  
  @Autowired
  private MaintenanceService maintenanceService;
  
  @Autowired
  private ReportService reportService;
  

  @GetMapping
  public ResponseEntity<List<ResponseMaintenanceDTO>> getMaintenances(
          @RequestParam(required = false) Integer carId,
          @RequestParam(required = false) Integer garageId,
          @RequestParam(value="fromDate", required = false) @DateTimeFormat(pattern="YYYY-MM-DD") LocalDate fromDate,
          @RequestParam(value="toDate", required = false) @DateTimeFormat(pattern="YYYY-MM-DD") LocalDate toDate)
  {
      SpecificationBuilder<Maintenance> builder = new SpecificationBuilder<>();
      List<SearchCriteria> criteriaList = constructMaintenanceSearchCriteria(carId, garageId, fromDate, toDate);

      if (!criteriaList.isEmpty()) {
          criteriaList.forEach(builder::with);
      }

      List<ResponseMaintenanceDTO> maintenances = maintenanceService.findBySearchCriteria(builder.build());
      return new ResponseEntity<>(maintenances, HttpStatus.OK);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> addMaintenance(@RequestBody @Valid CreateMaintenanceDTO createMaintenanceDTO, BindingResult result) {
      if (result.hasErrors()) {
        StringBuilder sb = new StringBuilder();
        result.getAllErrors().forEach(error -> sb.append(error.getDefaultMessage()).append("\n"));
        return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
      }
      ResponseMaintenanceDTO maintenance = maintenanceService.createMaintenance(createMaintenanceDTO);
      return new ResponseEntity<>(maintenance, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateMaintenance(@PathVariable int id, @RequestBody @Valid UpdateMaintenanceDTO updateMaintenanceDTO, BindingResult result) {
      if (result.hasErrors()) {
        StringBuilder sb = new StringBuilder();
        result.getAllErrors().forEach(error -> sb.append(error.getDefaultMessage()).append("\n"));
        return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
      }
      ResponseMaintenanceDTO maintenance = maintenanceService.updateMaintenance(id, updateMaintenanceDTO);
      return new ResponseEntity<>(maintenance, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteMaintenance(@PathVariable int id) {
      ResponseMaintenanceDTO maintenance = maintenanceService.deleteMaintenance(id);
      return new ResponseEntity<>(maintenance, HttpStatus.OK);
  }

  @GetMapping("/monthlyRequestsReport")
  public ResponseEntity<List<MonthlyResponseReportDTO>> monthlyRequestsReport(
      @RequestParam int garageId, 
      @RequestParam(value="startMonth") @DateTimeFormat(pattern = "yyyy-MM") YearMonth startMonth, 
      @RequestParam(value="endMonth") @DateTimeFormat(pattern = "yyyy-MM") YearMonth endMonth){

    List<MonthlyResponseReportDTO> reports = reportService.getMonthlyReports(garageId, startMonth, endMonth);
                                       
    return new ResponseEntity<>(reports, HttpStatus.OK);
  }
  
  private List<SearchCriteria> constructMaintenanceSearchCriteria( Integer carId, Integer garageId, LocalDate fromDate, LocalDate toDate) {
      List<SearchCriteria> sc = new ArrayList<>();
      if (carId != null) {
          sc.add(new SearchCriteria(MaintenanceFields.CAR.getFieldName(), carId, "eq"));
      }
      if (garageId != null) {
          sc.add(new SearchCriteria(MaintenanceFields.GARAGE.getFieldName(), garageId, "eq"));
      }
      if (fromDate != null) {
          sc.add(new SearchCriteria(MaintenanceFields.SCHEDULEDDATE.getFieldName(), fromDate, "ge"));
      }
      if (toDate != null) {
          sc.add(new SearchCriteria(MaintenanceFields.SCHEDULEDDATE.getFieldName(), toDate, "le"));
      }
      return sc;
  }
  
  
}
