package marln.corp.ai.service.marln_user_service.controller;

import marln.corp.ai.service.marln_user_service.multitenancy.FlywayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenant")
public class TenantController {

	@Autowired
	private ApplicationContext applicationContext;

	@PostMapping("/{tenantId}")
	public ResponseEntity<Void> createSchema(@PathVariable("tenantId") String tenantId) {
		migrateAllTenants(tenantId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void migrateAllTenants(String tenantId) {
		FlywayService flywayService = applicationContext.getBean(FlywayService.class);
		flywayService.migrateTenant(tenantId);
	}
}
