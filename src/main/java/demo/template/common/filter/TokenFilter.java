package demo.template.common.filter;

import demo.template.common.aop.Track;
import demo.template.common.aop.TrackQueriesAspect;
import demo.template.common.utils.HmacUtil;
import demo.template.sb3_3template.entity.Channel;
import demo.template.sb3_3template.repository.ChannelRepository;
import demo.template.sb3_3template.repository.SpringSessionRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenFilter extends OncePerRequestFilter {

    private static final String SESSION_ID = "X-Session-Id";
    private static final String CLIENT_ID = "X-Channel-Id";
    private static final String REQUEST_EPOCH = "X-Request-Epoch";
    private static final String SECRET_KEY = "X-Secret-Key";
    private static final String SIGNATURE = "X-Signature";

    private final ChannelRepository channelRepository;
    private final SpringSessionRepository springSessionRepository;

    private static final String AUTH_URI = "/v1/qna/auth";
    private static final List<String> EXCLUDED_URIS = List.of("/favicon.ico");

    public TokenFilter(ChannelRepository channelRepository, SpringSessionRepository springSessionRepository) {
        this.channelRepository = channelRepository;
        this.springSessionRepository = springSessionRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if (EXCLUDED_URIS.stream().anyMatch(requestURI::startsWith)) {
            return;
        }

//        setAuthentication();
        filterChain.doFilter(request, response);

        System.out.println("=========END===========");
        List<Track> query = TrackQueriesAspect.getQuery();
        String collect = query.stream()
                .map(q -> String.format("[index: %s | elapsed: %d ms | query: %s]", q.getIndex(), q.getElapsedTime(), q.getQuery()))
                .collect(Collectors.joining("\n"));
        System.out.println(collect);
        TrackQueriesAspect.clearIndex();
        TrackQueriesAspect.clearTrackedQueries();

//        if (AUTH_URI.equals(requestURI)) {
//
//            if (!validateToken(request)) throw new AuthenticationServiceException("credential does not match.");
//
//            setAuthentication();
//            filterChain.doFilter(request, response);
//
//        } else {
//
//            String sessionId = request.getHeader(SESSION_ID);
//            log.info("> session authorization : [{}]", sessionId);
//            if (ObjectUtils.isEmpty(sessionId)) throw new SessionAuthenticationException("invalid session.");
//
////            springSessionRepository.findBySessionId(sessionId).orElseThrow(() -> new SessionAuthenticationException("invalid session."));
//
//            setAuthentication();
//            filterChain.doFilter(request, response);
//
//        }


    }

    private boolean validateToken(HttpServletRequest request) {
        String channelId = request.getHeader(CLIENT_ID);
        String hmac = request.getHeader(SIGNATURE);
        String unixTime = request.getHeader(REQUEST_EPOCH);
        String secretKey = request.getHeader(SECRET_KEY);

        Channel channel = channelRepository.findByChannelIdAndUseYnTrue(channelId).orElseThrow(() -> new AuthenticationServiceException("'channelId' not found."));

        if (channel.getAuthYn()) {
            log.info("> basic authorization : [{}]", request.getQueryString());
            String value = HmacUtil.generateHmac(Long.parseLong(unixTime), channel.getChannelSecret());
            return Objects.equals(hmac, value);
        } else {
            log.info("> simple authorization : [{}]", request.getQueryString());
            String value = Base64.getEncoder().encodeToString(channel.getChannelSecret().getBytes());
            return Objects.equals(secretKey, value);
        }

    }

    private void setAuthentication() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
