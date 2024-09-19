package demo.template.common.filter;

import demo.template.common.utils.MDCUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class HttpLoggingFilter extends OncePerRequestFilter {

    private final SessionHistoryCreation sessionHistoryCreation;

    private final List<String> returnUri = List.of("/favicon.ico", "/invalid-session");
    private final List<String> excludeUri = List.of("");
    private final AtomicLong id = new AtomicLong(0);

    public HttpLoggingFilter(SessionHistoryCreation sessionHistoryCreation) {
        this.sessionHistoryCreation = sessionHistoryCreation;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            log.info("Header: {}={}", headerName, headerValue);
        }

        if (returnUri.stream().anyMatch(uri -> request.getRequestURI().startsWith(uri))) {
            return;
        }


        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "{user-name}", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // todo Channel Filter
        if (excludeUri.stream().anyMatch(uri -> request.getRequestURI().startsWith(uri))) {

            initializeMDC(request);
            logRequest(request);

            try {
                sessionHistoryCreation.createSessionHistory(request.getSession());
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
                sessionHistoryCreation.createSessionHistory(request.getSession());
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
        MDCUtil.setValue(MDCUtil.REQUEST_UUID, RandomStringUtils.randomAlphanumeric(8));
        MDCUtil.setValue(MDCUtil.REQUEST_START_TIME, String.valueOf(startTime));
        MDCUtil.setJsonValue(MDCUtil.HEADER_MAP, request.getHeaderMap());
        MDCUtil.setValue(MDCUtil.REQUEST_BODY, new String(request.getContentAsByteArray(), StandardCharsets.UTF_8));
        MDCUtil.setValue(MDCUtil.REQUEST_URI, request.getRequestURI());
        MDCUtil.setValue(MDCUtil.REQUEST_QUERY_STRING, request.getQueryString());
    }

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