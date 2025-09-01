package marln.corp.ai.service.marln_user_service.multitenancy;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TenantFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain) throws ServletException, IOException {
		String tenantId = request.getHeader("tenantId");
		if (tenantId == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing tenantId header");
			return;
		}
		try {
			TenantContext.setTenantId(tenantId);
			filterChain.doFilter(request, response);
		} finally {
			TenantContext.clear();
		}
	}
}
