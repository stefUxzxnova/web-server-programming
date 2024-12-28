package uni.fmi.plovdiv.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uni.fmi.plovdiv.model.Garage;

@Repository
public interface GarageRepository extends JpaRepository<Garage, Integer>, JpaSpecificationExecutor<Garage>{

  List<Garage> findAllByIdIn(List<Integer> ids);
}
