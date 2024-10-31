package demo.template.sb3_3template.repository;

import demo.template.sb3_3template.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Optional<Channel> findByChannelIdAndUseYnTrue(String channelId);

}
