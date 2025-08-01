package marln.corp.ai.service.marln_user_service.restcall;

import jakarta.servlet.http.HttpServletRequest;
import marln.corp.ai.service.marln_user_service.dto.UserRoleRequestDto;
import marln.corp.ai.service.marln_user_service.exception.ExternalServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Component
public class RestCallImpl implements RestCall{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    HttpServletRequest request;

    @Override
    public void assignRoles(String userId, String roleId, List<String> permissionList) {
try {
    String url = "http://marln-rbac-service/rbac/user";
    UserRoleRequestDto userRoleRequestDto = new UserRoleRequestDto();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    userRoleRequestDto.setUserId(userId);
    userRoleRequestDto.setRoleId(roleId);
    userRoleRequestDto.setPermissionIds(permissionList);
    userRoleRequestDto.setTenantId(request.getHeader("tenantId"));

    HttpEntity<UserRoleRequestDto> entity = new HttpEntity<>(userRoleRequestDto, headers);

    // Make the request to the RBAC service
    ResponseEntity<Void> response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            entity,
            Void.class
    );

    // Print the response
    System.out.println("Status Code: " + response.getStatusCode());
}catch(Exception ex)
{
    System.out.println("EXCEPTION in restcall while storing user role : " +ex);
    ex.printStackTrace();
    throw new ExternalServiceException("marln-rbac-service", "Failed to assign roles to user: " + ex.getMessage(), ex);
}

    }
}
