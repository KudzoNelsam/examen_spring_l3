package ism.absence.core.validator.annotations;

import ism.absence.core.validator.NotEmptyListValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyListValidator.class)
@Documented
public @interface NotEmpty {
    String message() default "La liste ne doit pas être vide";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}