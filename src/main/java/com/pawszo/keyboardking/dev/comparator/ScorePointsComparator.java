package com.pawszo.keyboardking.dev.comparator;

import com.pawszo.keyboardking.dev.dto.ScoreDTO;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class ScorePointsComparator implements Comparator<ScoreDTO> {
    @Override
    public int compare(ScoreDTO o1, ScoreDTO o2) {
        Integer points1 = Integer.valueOf(o1.getStats().get("points"));
        Integer points2 = Integer.valueOf(o2.getStats().get("points"));
        if (points1 == points2) {
            return Integer.valueOf(o1.getStats().get("time")) - Integer.valueOf(o2.getStats().get("time"));
        } else {
            return points2 - points1;
        }
    }


}
