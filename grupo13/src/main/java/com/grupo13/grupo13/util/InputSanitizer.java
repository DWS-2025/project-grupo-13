package com.grupo13.grupo13.util;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.web.multipart.MultipartFile;

public class InputSanitizer {

    
    public static String whitelistSanitize(String input) {
        return input.replaceAll("[^a-zA-Z0-9 _.,-]", "");
    }

   
    public static String sanitizeRichText(String input) {
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS); // <b>, <i>, <u> permitted
        return policy.sanitize(input);
    }

    

    public static boolean isImageValid(MultipartFile file) {
        if (file == null || file.isEmpty()) return false;

        String contentType = file.getContentType();
        if (contentType == null) return false;

        boolean isTypeValid = contentType.equals("image/jpeg") || contentType.equals("image/png");
   

        return isTypeValid;
    

    }
}
