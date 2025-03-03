package abc.skeleton.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, SkeletonAuthenticationFilter skeletonAuthenticationFilter) throws Exception {
        http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated())
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterAfter(skeletonAuthenticationFilter, LogoutFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(SkeletonAuthenticationProvider skeletonAuthenticationProvider) {
        return new ProviderManager(skeletonAuthenticationProvider);
    }

    @Bean
    public SkeletonAuthenticationFilter skeletonAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new SkeletonAuthenticationFilter(authenticationManager);
    }

    @Bean
    public SkeletonAuthenticationProvider skeletonAuthenticationProvider(SkeletonAuthoritiesService authoritiesService) {
        return new SkeletonAuthenticationProvider(authoritiesService);
    }

    @Bean
    public SkeletonAuthoritiesService authoritiesService() {
        return new SkeletonAuthoritiesService();
    }
}
