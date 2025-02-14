package abc.skeleton.rest.service;

import com.example.api.PetApi;
import com.example.model.CategoryDto;
import com.example.model.PetDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    private final PetApi petApi;

    public PetServiceImpl(PetApi petApi) {
        this.petApi = petApi;
    }

    public PetDto getPet(Long id) {
        return petApi.getPetById(id);
    }

    @Override
    public PetDto addPet(String name) {
        return PetDto.builder()
                .category(CategoryDto.builder().name("cats").build())
                .name(name)
                .status(PetDto.StatusEnum.AVAILABLE)
                .photoUrls(List.of("http://photo.com/1", "http://photo.com/2"))
                .build();
    }
}
