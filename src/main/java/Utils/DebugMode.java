package Utils;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DebugMode {
    boolean value() default false;
}
