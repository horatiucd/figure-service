package com.hcd.figureservice.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Collection;

public class ApiKeyFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String apiKey = httpRequest.getHeader("X-API-KEY");
        if (apiKey == null ||
                !apiKey.equals("123-abc-456-def-789-ghi")) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpResponse.addHeader("WWW-Authenticate", "Provide a valid API key.");
            return;
        }

        SecurityContextHolder.getContext()
                .setAuthentication(new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES));

        filterChain.doFilter(request, response);
    }

    static class ApiKeyAuthentication extends AbstractAuthenticationToken {

        private final String apiKey;

        public ApiKeyAuthentication(String apiKey,
                                    Collection<? extends GrantedAuthority> authorities) {
            super(authorities);
            this.apiKey = apiKey;
            setAuthenticated(true);
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return apiKey;
        }

        @Override
        public final boolean equals(Object o) {
            if (!(o instanceof ApiKeyAuthentication that)) {
                return false;
            }
            if (!super.equals(o)) {
                return false;
            }
            return apiKey.equals(that.apiKey);
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + apiKey.hashCode();
            return result;
        }
    }
}
