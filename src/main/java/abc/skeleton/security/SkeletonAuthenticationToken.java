package abc.skeleton.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SkeletonAuthenticationToken  extends AbstractAuthenticationToken {

    private final String principal;

    public static SkeletonAuthenticationToken authenticated(String principal, Collection<? extends GrantedAuthority> authorities) {
        return new SkeletonAuthenticationToken(principal, authorities);
    }

    public static SkeletonAuthenticationToken unauthenticated(String principal) {
        return new SkeletonAuthenticationToken(principal);
    }

    private SkeletonAuthenticationToken(String principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    private SkeletonAuthenticationToken(String principal) {
        super(null);
        this.principal = principal;
        super.setAuthenticated(false);
    }


    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
