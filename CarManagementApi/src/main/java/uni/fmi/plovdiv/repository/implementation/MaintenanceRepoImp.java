package uni.fmi.plovdiv.repository.implementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import uni.fmi.plovdiv.dto.report.GarageDailyAvailabilityReportDTO;
import uni.fmi.plovdiv.dto.report.MonthlyRequestsReportDTO;
import uni.fmi.plovdiv.model.Maintenance;
import uni.fmi.plovdiv.repository.MaintenanceRepository;

@Repository
public class MaintenanceRepoImp implements MaintenanceRepository{

  @PersistenceContext
  private EntityManager entityManager;
  
  @Override
  public List<Maintenance> findBySearchCriteria(Specification<Maintenance> spec) {

    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Maintenance> mainQuery = cb.createQuery(Maintenance.class);
    Root<Maintenance> maintenanceRoot = mainQuery.from(Maintenance.class);
    mainQuery.select(maintenanceRoot);
    if(spec != null) {
          mainQuery.where(spec.toPredicate(maintenanceRoot, mainQuery, cb));
    }
    TypedQuery<Maintenance> q = entityManager.createQuery(mainQuery);
    List<Maintenance> result = q.getResultList();
    return result;
  }

  @Override
  public void save(Maintenance maintenance) {
     entityManager.persist(maintenance);
  }

  @Override
  public void delete(Maintenance maintenance) {
    entityManager.remove(maintenance);
  }

  @Override
  public Optional<Maintenance> findById(int id) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();

    CriteriaQuery<Maintenance> mainQuery = cb.createQuery(Maintenance.class);
    Root<Maintenance> maintenanceRoot = mainQuery.from(Maintenance.class);

    mainQuery.where(cb.equal(maintenanceRoot.get("id"), id));

    TypedQuery<Maintenance> query = entityManager.createQuery(mainQuery);
    List<Maintenance> results = query.getResultList();

    return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
  }

  @Override
  public List<GarageDailyAvailabilityReportDTO> dailyAvailabilityReport(int garageId, LocalDate startDate, LocalDate endDate) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<GarageDailyAvailabilityReportDTO> mainQuery = cb.createQuery(GarageDailyAvailabilityReportDTO.class);
    Root<Maintenance> maintenanceRoot = mainQuery.from(Maintenance.class);
    
    Predicate garageCondition = cb.equal(maintenanceRoot.get("garage").get("id"), garageId); 
    Predicate startDateCondition = cb.greaterThanOrEqualTo(maintenanceRoot.get("scheduledDate"), startDate); 
    Predicate endDateCondition = cb.lessThanOrEqualTo(maintenanceRoot.get("scheduledDate"), endDate); 
   
    List<Predicate> predicates = new ArrayList<>();
    predicates.add(garageCondition);
    predicates.add(startDateCondition);
    predicates.add(endDateCondition);

    mainQuery.groupBy(
        maintenanceRoot.get("scheduledDate"));

    mainQuery
        .select(
            cb.construct(
                GarageDailyAvailabilityReportDTO.class,
                maintenanceRoot.get("scheduledDate"),
                cb.count(maintenanceRoot.get("id"))))
        .where(cb.and(predicates.toArray(new Predicate[0])));

    TypedQuery<GarageDailyAvailabilityReportDTO> query = entityManager.createQuery(mainQuery);
    return query.getResultList();    
    
  }

  @Override
  public List<MonthlyRequestsReportDTO> garageRequestsByMonth(int garageId, LocalDate startDate, LocalDate endDate) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<MonthlyRequestsReportDTO> mainQuery = cb.createQuery(MonthlyRequestsReportDTO.class);
    Root<Maintenance> maintenanceRoot = mainQuery.from(Maintenance.class);
    
    Predicate garageCondition = cb.equal(maintenanceRoot.get("garage").get("id"), garageId); 
    Predicate startDateCondition = cb.greaterThanOrEqualTo(maintenanceRoot.get("scheduledDate"), startDate); 
    Predicate endDateCondition = cb.lessThanOrEqualTo(maintenanceRoot.get("scheduledDate"), endDate); 
    
    Expression<String> monthYear = cb.function(
        "TO_CHAR", String.class, 
        maintenanceRoot.get("scheduledDate"), cb.literal("YYYY-MM")
    );
    mainQuery.groupBy(monthYear);

    mainQuery.multiselect(
        monthYear,                               
        cb.count(maintenanceRoot)                
    );

    mainQuery.where(cb.and(garageCondition, startDateCondition, endDateCondition));

    mainQuery.orderBy(cb.asc(monthYear));

    return entityManager.createQuery(mainQuery)
            .getResultList();
  }

  

}
