package dog.dao;

import dog.entity.Dog;

import java.util.List;

public interface DogDAO {
    List<Dog> getAllDogs();

   void saveDog(Dog dog);

   Dog getDog(int id);

   void deleteDog(int id);
}
