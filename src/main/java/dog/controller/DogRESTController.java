package dog.controller;

import dog.entity.Dog;
import dog.exeption_handling.NoSuchDogException;
import dog.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DogRESTController {

    @Autowired
    private DogService dogService;

    @GetMapping("/dogs")
    public List<Dog> showAllDogs() {
        List<Dog> allDogs = dogService.getAllDogs();
        return allDogs;
    }

    @GetMapping("/dogs/{id}")
    public Dog getDog(@PathVariable int id) { //  аннотация @PathVariable для получения значения переменной из адреса запроса
        Dog dog = dogService.getDog(id);
        if (dog == null) {
            throw new NoSuchDogException("There is no dog with ID = " +
                    id + " in Database");
        }
        return dog;
    }

    @PostMapping("/dogs")
    public Dog addNewDog(@RequestBody Dog dog) { // посылаем в body request json, а обращаемся к нему тут как к
        // объекту Dog. Благодаря автоматической конвертации jacson
        dogService.saveDog(dog);
        return dog;
    }

    @PutMapping("/dogs")
    public Dog updateDog(@RequestBody Dog dog) {
        dogService.saveDog(dog);
        return dog;
    }

    @DeleteMapping("/dogs/{id}")
    public String deleteDog(@PathVariable int id) {
        Dog dog = dogService.getDog(id);
        if (dog == null) {
            throw new NoSuchDogException("There is no dog with ID = " + id + " in Database");
        }
        dogService.deleteDog(id);
        String deleteMessage = "Dog with ID = " + id + " was deleted";
        return deleteMessage;
    }

    @GetMapping("/new-dog")
    public Dog getNewDog() {
        return dogService.getNewDogAndSave();
    }
}
