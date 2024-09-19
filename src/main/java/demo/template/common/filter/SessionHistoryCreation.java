package demo.template.common.filter;

import demo.template.sb3_3template.entity.SpringSessionHist;
import demo.template.sb3_3template.repository.SpringSessionHistRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class SessionHistoryCreation {

    private final SpringSessionHistRepository springSessionHistRepository;

    public SessionHistoryCreation(SpringSessionHistRepository springSessionHistRepository) {
        this.springSessionHistRepository = springSessionHistRepository;
    }

    @Transactional
    public void createSessionHistory(HttpSession httpSession) {

        if (httpSession.isNew()) {
            springSessionHistRepository.save(SpringSessionHist.of(httpSession));
        } else {
            SpringSessionHist springSessionHist = springSessionHistRepository.findById(httpSession.getId()).orElseThrow();
            springSessionHist.updateLastAccessedTime(httpSession);
        }

    }

}
