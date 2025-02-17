package abc.skeleton.rest_client.config;

import com.example.api.PetApi;
import com.example.invoker.ApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApisConfig {

    private static final String PETSTORE_API_BASE_PATH = "https://petstore3.swagger.io/api/v3";

    @Bean
    public PetApi petApi() {
        PetApi petApi = new PetApi();
        petApi.setApiClient(createPetApiClient());
        return petApi;
    }

    private ApiClient createPetApiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(PETSTORE_API_BASE_PATH);
        return apiClient;
    }

}
