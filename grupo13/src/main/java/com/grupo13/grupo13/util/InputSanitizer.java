package com.grupo13.grupo13.util;
import java.util.regex.Pattern;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.web.multipart.MultipartFile;

public class InputSanitizer {

    private static final Pattern WHITELIST_PATTERN = Pattern.compile("^[a-zA-Z0-9.,\\s]+$");

    public static void validateWhitelist(String input) {
        if (input == null || !WHITELIST_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException("Invalid imput. Only numbers and letters permitted");
        }
    }
    
    public static String sanitizeRichText(String input) {
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS); // <b>, <i>, <u> permitted
        return policy.sanitize(input);
    }

    public static boolean isImageValid(MultipartFile file) {
        if (file == null || file.isEmpty()) return false;
        String contentType = file.getContentType();
        if (contentType == null) return false;
        boolean isTypeValid = contentType.equals("image/jpeg");
        return isTypeValid;
    }
}
