package dog.service;

import dog.dao.DogDAO;
import dog.entity.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DogServiceImpl implements DogService {

    @Autowired
    private DogDAO dogDAO;

    @Override
    @Transactional
    public List<Dog> getAllDogs() {
        return dogDAO.getAllDogs();
    }

    @Override
    @Transactional
    public void saveDog(Dog dog) {
        dogDAO.saveDog(dog);
    }

    @Override
    @Transactional
    public Dog getDog(int id) {
        return dogDAO.getDog(id);
    }

    @Override
    @Transactional
    public void deleteDog(int id) {
        dogDAO.deleteDog(id);
    }
}
