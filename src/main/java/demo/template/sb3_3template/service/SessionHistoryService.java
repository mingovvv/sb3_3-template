package demo.template.sb3_3template.service;

import demo.template.sb3_3template.entity.SpringSessionHist;
import demo.template.sb3_3template.repository.SpringSessionHistRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SessionHistoryService {

    private final SpringSessionHistRepository springSessionHistRepository;

    public SessionHistoryService(SpringSessionHistRepository springSessionHistRepository) {
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
