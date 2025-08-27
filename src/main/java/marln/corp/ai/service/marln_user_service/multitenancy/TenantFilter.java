package marln.corp.ai.service.marln_user_service.multitenancy;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TenantFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String tenantId = httpRequest.getHeader("tenantId");

		// You can also extract tenant from subdomain or JWT token
		if (tenantId == null) {
			tenantId = extractTenantFromSubdomain(httpRequest);
		}

		try {
			TenantContext.setCurrentTenant(tenantId);
			chain.doFilter(request, response);
		} finally {
			TenantContext.clear();
		}
	}

	private String extractTenantFromSubdomain(HttpServletRequest request) {
		String serverName = request.getServerName();
		if (serverName.contains(".")) {
			return serverName.split("\\.")[0];
		}
		return "public";
	}
}