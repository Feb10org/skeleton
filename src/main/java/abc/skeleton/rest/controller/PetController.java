package abc.skeleton.rest.controller;

import abc.skeleton.rest.service.PetService;
import com.example.model.PetDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PetDto> getPet(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(petService.getPet(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PetDto> addPet(@RequestBody CreatePetDto newPet) {
        return ResponseEntity.ok(petService.addPet(newPet.name()));
    }

    private record CreatePetDto(String name) {
    }
}
