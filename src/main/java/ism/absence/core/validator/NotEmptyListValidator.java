package ism.absence.core.validator;

import ism.absence.core.validator.annotations.NotEmpty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Collection;
import java.util.Map;

public class NotEmptyListValidator implements ConstraintValidator<NotEmpty, Object> {

    @Override
    public void initialize(NotEmpty constraintAnnotation) {
        // Initialisation si nécessaire
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (value instanceof Collection<?>) {
            return !((Collection<?>) value).isEmpty();
        }

        if (value instanceof Map<?, ?>) {
            return !((Map<?, ?>) value).isEmpty();
        }

        if (value instanceof String) {
            return !((String) value).trim().isEmpty();
        }

        if (value.getClass().isArray()) {
            return ((Object[]) value).length > 0;
        }

        return false;
    }
}