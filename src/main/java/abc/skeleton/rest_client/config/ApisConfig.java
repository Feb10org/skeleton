package abc.skeleton.rest_client.config;

import com.example.api.PetApi;
import com.example.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class ApisConfig {

    private final String PETSTORE_API_BASE_PATH;

    public ApisConfig(@Value("${remote-apis.petstore.base-path}") String petstoreBasePath) {
        this.PETSTORE_API_BASE_PATH = petstoreBasePath;
    }

    @Bean
    public PetApi petApi() {
        PetApi petApi = new PetApi();
        petApi.setApiClient(createPetApiClient());
        return petApi;
    }

    private ApiClient createPetApiClient() {
        RestClient restClient = RestClient.builder()
                .requestInterceptor(new CorrelationIdInterceptor())
                .build();
        ApiClient apiClient = new ApiClient(restClient);
        apiClient.setBasePath(UriComponentsBuilder.fromUriString(PETSTORE_API_BASE_PATH)
                .path(apiClient.getBasePath())
                .toUriString());
        apiClient.addDefaultHeader("X-Application-ID", "petstore-service");
        return apiClient;
    }

}
