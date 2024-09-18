package demo.template.common.filter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingResponseWrapper;


@Slf4j
public class CustomContentCachingResponseWrapper extends ContentCachingResponseWrapper {

    public CustomContentCachingResponseWrapper(HttpServletResponse response) {
        super(response);
    }

}
