package dog.exeption_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // Чтобы исключение обрабатывалось во всех контроллерах
public class DogGlobalExceptionHandler {

    @ExceptionHandler // метод, ответственный за обработку исключений и передачу ответа в виде json
    public ResponseEntity<DogIncorrectData> handleException(NoSuchDogException exception){ // В случае выброса
        // NoSuchDogException exception в тело response (http ответа) добавить объект DogIncorrectData
        DogIncorrectData data = new DogIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<DogIncorrectData> handleException(Exception exception){
        DogIncorrectData data = new DogIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
