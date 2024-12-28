package uni.fmi.plovdiv.search.specificationBuilder;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import uni.fmi.plovdiv.search.SearchCriteria;
import uni.fmi.plovdiv.search.specification.SearchSpecification;

public class SpecificationBuilder<T> {

  private final List<SearchCriteria> params;

  public SpecificationBuilder(){
      this.params = new ArrayList<>();
  }

  public final SpecificationBuilder<T> with(String key, String operation, Object value){
      params.add(new SearchCriteria(key, value, operation));
      return this;
  }

  public final SpecificationBuilder<T> with(SearchCriteria searchCriteria){
      params.add(searchCriteria);
      return this;
  }

  public Specification<T> build(){
      if(params.isEmpty()){
          return null;
      }

      Specification<T> result = new SearchSpecification<>(params.get(0));
      for (int i = 1; i < params.size(); i++){
          SearchCriteria criteria = params.get(i);
          result =  Specification.where(result).and(new SearchSpecification<>(criteria));
      }
      return result;
  }
}
