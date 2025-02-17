package abc.skeleton.openapi;

import com.sample.api.UsersApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SampleController implements UsersApi {
    @Override
    public ResponseEntity<List<String>> usersGet() {
        return ResponseEntity.ok(List.of("John", "Anna"));
    }
}
