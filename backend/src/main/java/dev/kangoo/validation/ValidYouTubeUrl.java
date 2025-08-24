package dev.kangoo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = YouTubeUrlValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface ValidYouTubeUrl {
    String message() default "Invalid YouTube video URL format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}