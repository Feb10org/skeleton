package abc.skeleton.rest.service;

import com.example.api.PetApi;
import com.example.model.PetDto;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl implements PetService {

    private final PetApi petApi;

    public PetServiceImpl(PetApi petApi) {
        this.petApi = petApi;
    }

    public PetDto getPet(Long id) {
        return petApi.getPetById(id);
    }
}
