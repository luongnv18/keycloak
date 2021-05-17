package io.getstarted.spring;

import javax.annotation.security.RolesAllowed;

import org.keycloak.KeycloakPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
	
	@Autowired
	private SecurityContextUtils securityContextUtils;

	@GetMapping(value = "/anonymous")
    public ResponseEntity<String> getAnonymous() {
        return ResponseEntity.ok("Hello Anonymous");
    }
	@RolesAllowed({ "admin", "user" })
	@GetMapping(value = "/authen-user")
    public ResponseEntity<String> getAuthentication() {
		String authentication= securityContextUtils.getUserName();
		log.debug("Request authenticated, hence  user name available: "+ authentication);
        return ResponseEntity.ok(authentication);
    }
    
    @RolesAllowed("user")
    @GetMapping(value = "/user-only")
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("Hello User Only");
    }
    @RolesAllowed("admin")
    @GetMapping(value = "/admin-only")
    public ResponseEntity<String> getAdmin() {
        return ResponseEntity.ok("Hello Admin Only");
    }
    
    @RolesAllowed({ "admin", "user" })
    @GetMapping(value = "/all-user")
    public ResponseEntity<String> getAllUser() {
        return ResponseEntity.ok("Hello All User");
    }
    
    
}
