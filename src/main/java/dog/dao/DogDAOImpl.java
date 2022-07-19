package dog.dao;





import dog.entity.Dog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class DogDAOImpl implements DogDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Dog> getAllDogs() {
        Session session = sessionFactory.getCurrentSession();
        //List<Dog> allDogs = session.createQuery("from Dog", Dog.class).getResultList();
        Query query = session.createQuery("from Dog", Dog.class);
        List<Dog> allDogs = query.getResultList();

        return allDogs;
    }

    @Override
    public void saveDog(Dog dog) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(dog);

    }

    @Override
    public Dog getDog(int id) {
        Session session = sessionFactory.getCurrentSession();
        Dog dog = session.get(Dog.class, id);
        return dog;
    }

    @Override
    public void deleteDog(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Dog " +
                "where id =:dogId");
        query.setParameter("dogId",id);
        query.executeUpdate();

    }
}
