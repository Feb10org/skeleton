package abc.skeleton.rest_client.config;

import com.example.api.PetApi;
import com.example.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class ApisConfig {

    private static final String X_APPLICATION_ID = "X-Application-ID";
    private final String PETSTORE_API_BASE_PATH;
    private final String APP_NAME;

    public ApisConfig(@Value("${remote-apis.petstore.base-path}") String petstoreBasePath, Environment environment) {
        this.PETSTORE_API_BASE_PATH = petstoreBasePath;
        this.APP_NAME = environment.getProperty("spring.application.name");
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
        apiClient.addDefaultHeader(X_APPLICATION_ID, APP_NAME);
        return apiClient;
    }

}
