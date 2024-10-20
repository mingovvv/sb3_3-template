package demo.template.common.filter;

import com.fasterxml.jackson.databind.JsonNode;
import demo.template.common.filter.wrapper.CustomContentCachingRequestWrapper;
import demo.template.common.filter.wrapper.CustomContentCachingResponseWrapper;
import demo.template.common.utils.JsonUtil;
import demo.template.common.utils.MDCUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class HttpLoggingFilter extends OncePerRequestFilter {

    private final List<String> excludeUri = List.of("");
    private final AtomicLong id = new AtomicLong(0);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("filter : {}", "HttpLoggingFilter");

        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            log.info("Header: {}={}", headerName, headerValue);
        }

        if (excludeUri.stream().anyMatch(uri -> request.getRequestURI().startsWith(uri))) {

            initializeMDC(request);
            logRequest(request);

            try {
                filterChain.doFilter(request, response);
            } finally {
                logResponse();
                MDCUtil.clear();
            }

        } else {

            CustomContentCachingRequestWrapper cachingRequestWrapper = new CustomContentCachingRequestWrapper(request);
            CustomContentCachingResponseWrapper cachingResponseWrapper = new CustomContentCachingResponseWrapper(response);

            initializeMDC(cachingRequestWrapper);
            logRequest(cachingRequestWrapper);

            try {
                filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);
            } finally {
                logResponse(cachingResponseWrapper);
                cachingResponseWrapper.copyBodyToResponse();
                MDCUtil.clear();
            }

        }

    }

    /**
     * MDC initialize
     */
    private void initializeMDC(HttpServletRequest request) {
        long reqSeqId = id.incrementAndGet();
        long startTime = System.currentTimeMillis();
        MDCUtil.setValue(MDCUtil.REQUEST_SEQ_ID, String.valueOf(reqSeqId));
        MDCUtil.setValue(MDCUtil.REQUEST_UUID, RandomStringUtils.secure().next(8, true, true));
        MDCUtil.setValue(MDCUtil.REQUEST_START_TIME, String.valueOf(startTime));
        MDCUtil.setValue(MDCUtil.REQUEST_URI, request.getRequestURI());
    }

    /**
     * MDC initialize
     *
     * @param request CustomContentCachingRequestWrapper
     */
    private void initializeMDC(CustomContentCachingRequestWrapper request) {
        long reqSeqId = id.incrementAndGet();
        long startTime = System.currentTimeMillis();
        MDCUtil.setValue(MDCUtil.REQUEST_SEQ_ID, String.valueOf(reqSeqId));
        MDCUtil.setValue(MDCUtil.REQUEST_UUID, RandomStringUtils.secure().next(8, true, true));
        MDCUtil.setValue(MDCUtil.REQUEST_START_TIME, String.valueOf(startTime));
        MDCUtil.setJsonValue(MDCUtil.HEADER_MAP, request.getHeaderMap());
        MDCUtil.setValue(MDCUtil.REQUEST_BODY, new String(request.getContentAsByteArray(), StandardCharsets.UTF_8));
        MDCUtil.setValue(MDCUtil.REQUEST_URI, request.getRequestURI());
        MDCUtil.setValue(MDCUtil.REQUEST_QUERY_STRING, request.getQueryString());
//        MDCUtil.setValue(MDCUtil.REQUEST_USER_ID, extractUserIdFromBody(request));
        ;
    }

//    private String extractUserIdFromBody(HttpServletRequest request) throws IOException {
//        String requestBody = IOUtils.toString(request.getReader());
//        // 여기서 requestBody를 파싱하여 userId 추출 (JSON, XML 등)
//        // 예시: JSON을 파싱하여 userId를 찾는 로직
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(requestBody);
//        return jsonNode.get("userId").asText();
//    }

    /**
     * logging request
     *
     * @param request ContentCachingRequestWrapper
     */
    private void logRequest(ContentCachingRequestWrapper request) {

        String uri = request.getRequestURI();
        byte[] requestBody = request.getContentAsByteArray();

        if (!StringUtils.isEmpty(request.getQueryString())) uri += "?" + request.getQueryString();

        log.info("req uri={}, method={}, body={}, ip={}",
                uri,
                request.getMethod(),
                new String(requestBody, StandardCharsets.UTF_8).replaceAll("(\r\n|\r|\n|\n\r|)", ""),
                request.getRemoteAddr()
        );

    }

    private void logRequest(HttpServletRequest request) {

        String uri = request.getRequestURI();

        if (!StringUtils.isEmpty(request.getQueryString())) uri += "?" + request.getQueryString();

        log.info("req uri={}, method={}, ip={}", uri, request.getMethod(), request.getRemoteAddr());

    }

    /**
     * logging response
     *
     * @param response ContentCachingResponseWrapper
     */
    private void logResponse(ContentCachingResponseWrapper response) {

        String uri = MDCUtil.getValue(MDCUtil.REQUEST_URI);
        long startTime = Long.parseLong(MDCUtil.getValue(MDCUtil.REQUEST_START_TIME));

        if (uri.equalsIgnoreCase("/whoami")) return;

        log.info("res uri={}, body={}, totalTime={}",
                uri,
                new String(response.getContentAsByteArray(), StandardCharsets.UTF_8),
                System.currentTimeMillis() - startTime
        );

    }

    /**
     * logging response
     *
     */
    private void logResponse() {

        String uri = MDCUtil.getValue(MDCUtil.REQUEST_URI);
        long startTime = Long.parseLong(MDCUtil.getValue(MDCUtil.REQUEST_START_TIME));

        log.info("res uri={}, totalTime={}", uri, System.currentTimeMillis() - startTime);

    }

}