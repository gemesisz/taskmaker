package hu.gemesi.taskmaker.common.security.qualifier;

import hu.gemesi.taskmaker.common.security.role.AuthRole;
import org.apache.deltaspike.security.api.authorization.SecurityBindingType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
@SecurityBindingType
public @interface Auth {

    AuthRole role() default AuthRole.TASK_SOLVER;
}
