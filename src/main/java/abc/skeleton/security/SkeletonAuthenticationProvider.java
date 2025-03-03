package abc.skeleton.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

@RequiredArgsConstructor
public class SkeletonAuthenticationProvider implements AuthenticationProvider {

    private final SkeletonAuthoritiesService skeletonAuthoritiesService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(SkeletonAuthenticationToken.class, authentication,
                "Only SkeletonAuthenticationToken is supported");
        SkeletonAuthenticationToken token = (SkeletonAuthenticationToken) authentication;
        return SkeletonAuthenticationToken.authenticated(token.getName(), skeletonAuthoritiesService.getGrantedAuthorities(token.getName()));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SkeletonAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
