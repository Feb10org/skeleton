package abc.skeleton.rest_client.service;

import com.example.model.PetDto;

public interface PetService {

    PetDto getPet(Long id);

    PetDto addPet(String name);

}
