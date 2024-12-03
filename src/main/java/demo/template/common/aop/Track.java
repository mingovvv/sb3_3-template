package demo.template.common.aop;

import lombok.Getter;
import lombok.ToString;

@Getter
public class Track {

    private int index;
    private long elapsedTime;
    private String query;

    public Track(int index, long elapsedTime, String query) {
        this.index = index;
        this.elapsedTime = elapsedTime;
        this.query = query;
    }

}
