package uni.fmi.plovdiv.search;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchCriteria {

  private String filterKey;
  private Object value;
  private String operation;

  public SearchCriteria(String filterKey, Object value, String operation){
      super();
      this.filterKey = filterKey;
      this.operation = operation;
      this.value = value;
  }
}
