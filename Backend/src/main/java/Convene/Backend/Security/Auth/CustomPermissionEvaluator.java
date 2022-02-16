package Convene.Backend.Security.Auth;

import Convene.Backend.Security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@Slf4j
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final SecurityUtil securityUtil;

    public CustomPermissionEvaluator(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object object, Object permission) {

        if((authentication == null) || (object == null) || !(permission instanceof String)) {
            return false;
        }
        if(permission.equals("ADMIN")) {
            return hasAdminPrivilege(authentication, object.toString());
        }

        if(permission.equals("MEMBER")) {
            return (hasMemberPrivilege(authentication, object.toString())
                    ||
                    hasAdminPrivilege(authentication, object.toString()));
        }

        log.info("Denying access for user " + authentication.getPrincipal().toString() + " without permission "
                + permission);

        return false;
    }



    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }

    private boolean hasMemberPrivilege(Authentication authentication, String permission) {
        List<Long> memberPrivileges = securityUtil.extractUserPrivileges(authentication.getAuthorities())
                .getMemberPrivileges();
        return memberPrivileges.contains(Long.valueOf(permission));
    }

    private boolean hasAdminPrivilege(Authentication authentication, String permission) {
        List<Long> adminPrvileges = securityUtil.extractUserPrivileges(authentication.getAuthorities())
                .getAdminPrivileges();
        return adminPrvileges.contains(Long.valueOf(permission));
    }
}
