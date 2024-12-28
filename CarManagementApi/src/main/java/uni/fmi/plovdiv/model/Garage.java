package uni.fmi.plovdiv.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Garage {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  
  private String name;
  
  private String location;
  
  private String city;
  
  private int capacity;
  
  @ManyToMany(mappedBy = "garages", fetch = FetchType.LAZY)
  private List<Car> cars;
  
  public Garage(String name, String location, String city, int capacity) {
    this.name= name;
    this.location = location;
    this.city= city;
    this.capacity = capacity;
  }
}
