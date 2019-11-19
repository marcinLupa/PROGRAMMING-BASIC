package repo;

import model.Car;

import java.util.List;

public interface CrudRepository {
   List<Car> findAll();
}
