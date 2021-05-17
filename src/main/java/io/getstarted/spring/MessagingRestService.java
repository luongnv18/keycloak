package io.getstarted.spring;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagingRestService {

	@RolesAllowed("user")
	@GetMapping(path = "/user/message")
	public String getUserMessage() {
		return "Hello, User";
	}

	@RolesAllowed("admin")
	@GetMapping(path = "/admin/message")
	public String getAdminMessage() {
		return "Hello, Administrator";
	}
}
