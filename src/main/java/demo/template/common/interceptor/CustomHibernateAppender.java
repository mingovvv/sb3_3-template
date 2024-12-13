package demo.template.common.interceptor;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import demo.template.common.aop.SQLStorage;
import demo.template.common.aop.TrackQueriesAspect;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CustomHibernateAppender extends AppenderBase<ILoggingEvent> {

    private static final Pattern pattern = Pattern.compile("\\[(.*?)]");

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {

        if (TrackQueriesAspect.isTargetQueryActive()) {

            if (iLoggingEvent.getLoggerName().equals("org.hibernate.SQL")) {
                TrackQueriesAspect.addQuery(iLoggingEvent.getFormattedMessage());
            }

            if(iLoggingEvent.getLoggerName().equals("org.hibernate.orm.jdbc.bind")) {
                Matcher matcher = pattern.matcher(iLoggingEvent.getFormattedMessage());
                if (matcher.find()) {
                    SQLStorage SQLStorage = TrackQueriesAspect.getCurrentTrack();
                    SQLStorage.getParameters().add(matcher.group(1));
                }
            }

        }


    }

}
