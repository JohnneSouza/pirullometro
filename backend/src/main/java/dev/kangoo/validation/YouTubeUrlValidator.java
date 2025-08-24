package dev.kangoo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class YouTubeUrlValidator implements ConstraintValidator<ValidYouTubeUrl, String> {

    private static final Pattern YOUTUBE_URL_PATTERN = Pattern.compile(
        "^https?://(www\\.)?youtube\\.com/watch\\?v=[A-Za-z0-9_-]{11}$"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        return YOUTUBE_URL_PATTERN.matcher(value).matches();
    }
}
