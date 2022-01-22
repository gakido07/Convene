package Convene.Backend.Security;

import Convene.Backend.Security.Auth.AuthDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class SecurityUtil {

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public AuthDto.AppUserPrivileges extractUserPrivileges(Collection<? extends GrantedAuthority> grantedAuthorities) {
        List<Long> adminPrivileges = new ArrayList<>();
        List<Long> memberPrivileges = new ArrayList<>();
        for(GrantedAuthority grantedAuthority: grantedAuthorities) {
            String[] authorityStringSplit = grantedAuthority.getAuthority().split(" ", 2);
            Arrays.toString(authorityStringSplit);
            if(authorityStringSplit.length > 2){
                throw new IllegalArgumentException("Authority String array can't exceed two elements");
            }

            Integer lastIndex = authorityStringSplit[1].lastIndexOf("]");
            String[] privilegesString = authorityStringSplit[1]
                    .substring(1, lastIndex)
                    .split(",");
            if (grantedAuthority.getAuthority().startsWith("ADMIN")) {
                adminPrivileges = formatPrivilege(adminPrivileges, privilegesString);
            }

            if(grantedAuthority.getAuthority().startsWith("MEMBER")) {
                memberPrivileges = formatPrivilege(memberPrivileges, privilegesString);
            }
        }
        return new AuthDto.AppUserPrivileges(adminPrivileges, memberPrivileges);
    }

    private List<Long> formatPrivilege(List<Long> memberPrivileges, String[] privilegesString) {
        if(!Arrays.stream(privilegesString).collect(Collectors.toList()).contains("")) {
            memberPrivileges = Arrays.stream(privilegesString).map(privilege -> {
                privilege = privilege.trim();
                Long id = Long.valueOf(privilege);
                return id;
            }).collect(Collectors.toList());
        }
        return memberPrivileges;
    }
}
