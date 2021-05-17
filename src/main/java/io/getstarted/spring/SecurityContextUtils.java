package io.getstarted.spring;

import java.util.HashSet;
import java.util.Set;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Edward P. Legaspi
 */
@Component
@Slf4j
public class SecurityContextUtils {

	private static final String ANONYMOUS = "anonymous";
	
	public KeycloakPrincipal getAuthentication() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (null != authentication) {
			log.debug("Request authenticated, hence  user name available: "+ authentication);
			KeycloakPrincipal springSecurityUser = (KeycloakPrincipal) authentication.getPrincipal();
			return springSecurityUser;
		}else {
			log.debug("User details not found in Security Context");
		}
		return null;
		
	}
	public String getUserName() {

		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		String username = ANONYMOUS;

		if (null != authentication) {
			if (authentication.getPrincipal() instanceof UserDetails) {
				UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
				username = springSecurityUser.getUsername();

			} else if (authentication.getPrincipal() instanceof String) {
				username = (String) authentication.getPrincipal();

			} else {
				log.debug("User details not found in Security Context");
			}

		} else {
			log.debug("Request not authenticated, hence no user name available");
		}

		return username;
	}

	public Set<String> getUserRoles() {

		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		Set<String> roles = new HashSet<>();

		if (null != authentication) {
			authentication.getAuthorities().forEach(e -> roles.add(e.getAuthority()));
		}

		return roles;
	}

	@SuppressWarnings("unchecked")
	public String getJWTToken() {

		KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext()
				.getAuthentication();

		KeycloakPrincipal<KeycloakSecurityContext> keycloakPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) authentication
				.getPrincipal();

		return keycloakPrincipal.getKeycloakSecurityContext().getTokenString();
	}
}
