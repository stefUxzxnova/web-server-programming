package uni.fmi.plovdiv.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Maintenance {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  
  @NotNull
  private String serviceType;
  
  @DateTimeFormat(pattern = "YYYY-MM-DD")
  private LocalDate scheduledDate;
  
  @ManyToOne(fetch = FetchType.LAZY)  
  @JoinColumn(name = "carId")
  private Car car;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "garageId")
  private Garage garage;
  
}
