package abc.skeleton.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SkeletonAuthoritiesService {

    public Set<GrantedAuthority> getGrantedAuthorities(String principal) {
        return new HashSet<>(Arrays.asList(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("OTHER_ROLE")));
    }

}
