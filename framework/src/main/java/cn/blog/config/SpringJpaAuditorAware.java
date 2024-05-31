package cn.blog.config;

import cn.blog.constants.SystemConstants;
import cn.blog.model.pojo.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * @Author : KaelvihN
 * @Email : AraneidaSword@gmail.com
 * @Date : 2024/3/3
 **/
@Configuration
public class SpringJpaAuditorAware implements AuditorAware<Integer> {
    /**
     * Returns the current auditor of the application.
     * @return the current auditor.
     */
    @Override
    public Optional<Integer> getCurrentAuditor() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (SystemConstants.DEFAULT_PRINCIPAL_NAME.equals(principal)) {
            return Optional.empty();
        }
        return Optional.of(((User) principal).getId());
    }
}
