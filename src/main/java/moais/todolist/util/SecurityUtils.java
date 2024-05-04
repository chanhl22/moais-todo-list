package moais.todolist.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class SecurityUtils {

    public static String getLoginManagerName() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername();
    }

}
