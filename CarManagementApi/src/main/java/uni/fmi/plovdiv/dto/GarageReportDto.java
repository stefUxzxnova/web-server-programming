package uni.fmi.plovdiv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarageReportDto {
      
    private String city;
    
    private int capacity;
}
