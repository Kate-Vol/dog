package dog.dao;

import dog.entity.Dog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

@Repository
public class DogDAOImpl implements DogDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Dog> getAllDogs() {
        Session session = sessionFactory.getCurrentSession();
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
        query.setParameter("dogId", id);
        query.executeUpdate();

    }


    @Override
    public Dog getNewDog() {
        String dogString = requestDogString();
        Dog dog = parseDog(dogString);
        return dog;
    }

    private String requestDogString() {
        String dogString = "";

        try {
            URL url = new URL("https://dog.ceo/api/breeds/image/random");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            }

            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                dogString += scanner.nextLine();
                System.out.println(dogString);
            }

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dogString;
    }

    private Dog parseDog(String dogString) {
        JSONObject jsonObject = parseToJsonObject(dogString);
        Dog dog = fillDog(jsonObject);
        return dog;
    }

    private JSONObject parseToJsonObject(String dogString) {
        try {
            JSONParser parse = new JSONParser();
            JSONObject jsonObject = (JSONObject) parse.parse(dogString);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Dog fillDog(JSONObject jsonObject) {
        Dog dog = new Dog();

        if (jsonObject == null) {
            return dog;
        }

        if (!jsonObject.get("status").equals("success")) {
            return dog;
        }

        String dogMessage = (String) jsonObject.get("message");
        String[] words = dogMessage.split("/");
        for (String s : words) {
            System.out.println(s);
        }
        String dogName = words[4].substring(0, 1).toUpperCase() + words[4].substring(1).toLowerCase();
        dog.setName(dogName);
        dog.setUrl(dogMessage);

        return dog;
    }
}



