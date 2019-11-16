package vcasino.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants {
	  public static final List<String> SUITS = Collections.unmodifiableList(Arrays.asList("spades", "clubs", "diamonds", "hearts"));
	  public static final List<Integer> RANKS = Collections.unmodifiableList(Arrays.asList(2,3,4,5,6,7,8,9,10,11,12,13,14 ));
	  public static final List<String> CARD_NAMES= Collections.unmodifiableList(Arrays.asList("1","2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"));

}
