package com.pawszo.keyboardking.dev.comparator;

import com.pawszo.keyboardking.dev.dto.ScoreDTO;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class ScoreTimeComparator implements Comparator<ScoreDTO> {
    @Override
    public int compare(ScoreDTO o1, ScoreDTO o2) {
        Integer time1 = Integer.valueOf(o1.getStats().get("time"));
        Integer time2 = Integer.valueOf(o2.getStats().get("time"));
        return time2 - time1;
    }
}
