package uni.fmi.plovdiv.search.specification;

import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import uni.fmi.plovdiv.model.Car;
import uni.fmi.plovdiv.model.Garage;
import uni.fmi.plovdiv.search.SearchCriteria;
import uni.fmi.plovdiv.search.SearchOperation;

public class SearchSpecification<T> implements  Specification<T> {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private final SearchCriteria searchCriteria;

  public SearchSpecification(final SearchCriteria searchCriteria){
    super();
    this.searchCriteria = searchCriteria;
  }

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
  
    String fieldToSearch = searchCriteria.getValue().toString().toLowerCase();
  
    switch(Objects.requireNonNull(SearchOperation.getSimpleOperation(searchCriteria.getOperation()))){
        case CONTAINS:
           return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + fieldToSearch + "%");
        case EQUAL:
          if(searchCriteria.getFilterKey().equals("garage")) {
            return cb.equal(garageJoin(root).get("id"), Integer.parseInt(fieldToSearch));
          }else if(searchCriteria.getFilterKey().equals("garageId")) {
            return cb.equal(root.get("garage").get("id"), Integer.parseInt(fieldToSearch));
          }
          else if (searchCriteria.getFilterKey().equals("car")) {
            return cb.equal(carJoin(root).get("id"), Integer.parseInt(fieldToSearch));
          }
          return cb.equal(cb.lower(root.get(searchCriteria.getFilterKey())), fieldToSearch);
        case GREATER_THAN_EQUAL:
          return cb.greaterThanOrEqualTo(root.get(searchCriteria.getFilterKey()), Integer.parseInt(fieldToSearch));
        case LESS_THAN_EQUAL:
          return cb.lessThanOrEqualTo(root.get(searchCriteria.getFilterKey()), Integer.parseInt(fieldToSearch));
        default:  
          return null;
    
    }
  }
  private Join<T,Garage> garageJoin(Root<T> root){
    return root.join("garages");
  }
  
  private Join<T,Car> carJoin(Root<T> root){
    return root.join("car");
  }
}
