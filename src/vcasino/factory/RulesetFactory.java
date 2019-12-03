package vcasino.factory;

import vcasino.core.Ruleset;
import vcasino.core.games.GoFishRuleset;
import vcasino.core.games.PokerRuleset;
import vcasino.core.games.TexasHoldemRuleSet;
import vcasino.core.games.UnoRuleset;

public class RulesetFactory {
	

	public static Ruleset getRuleset(String gameName) {
		switch (gameName) {
		case "poker":
			return new PokerRuleset();
		case "texasHoldem":
			return new TexasHoldemRuleSet();
		case "uno":
			return new UnoRuleset();
		case "goFish":
			return new GoFishRuleset();
		default:
			return new PokerRuleset();
		}
	}

}
