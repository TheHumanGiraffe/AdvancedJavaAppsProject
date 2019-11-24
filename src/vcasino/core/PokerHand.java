package vcasino.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vcasino.core.games.PokerRuleset.HandNameAndRank;

public class PokerHand {
    private List<Card> hand;
    private int handRank;
    private String handName;
    private Map<Integer, Integer> rankFreq;
    private Map<String, Integer> suitFreq;


   public PokerHand(){
        this.hand = new ArrayList<>();
    }

   public PokerHand(List<Card> cards){
        rankFreq = new HashMap<Integer,Integer>(5);
        suitFreq = new HashMap<String, Integer>(5);
       
        this.hand = cards;
        sortHand();
        
        for(Card c : hand){
            Integer j = rankFreq.get(c.getRank());
            Integer k = suitFreq.get(c.getSuit());

            rankFreq.put(c.getRank(), (j == null)? 1: j+1);
            suitFreq.put(c.getSuit(), (k == null)? 1: k+1);

        }
    }
   public PokerHand(Card card1,Card card2,Card card3,Card card4,Card card5){
        rankFreq = new HashMap<Integer,Integer>(5);
        suitFreq = new HashMap<String, Integer>(5);
        this.hand = new ArrayList<Card>(5);
        hand.add(card1);
        hand.add(card2);
        hand.add(card3);
        hand.add(card4);
        hand.add(card5);
        sortHand();

        Integer j = null;
        Integer k = null;
        for(Card c : hand){
                 j = rankFreq.get(c.getRank());
                 k = suitFreq.get(c.getSuit());

            rankFreq.put(c.getRank(), (j == null)? 1: j+1);
            suitFreq.put(c.getSuit(), (k == null)? 1: k+1);

        }
    }

    public void makeBestHand(){
       if( isRoyalFlush() ||
        isStrightFlush()||
        isFourOfAKind()||
        isFullHouse()||
        isFlush()||
        isStraight()||
        isThreeOfAKind()||
        isTwoPair()||
        isPair()||
        isHighCard()){
           return;
       }


    }
    
    public static int compare(PokerHand h1, PokerHand h2){
    	int highestFreqH1=0;
    	int highestRankH1=0;
    	int highestFreqH2=0;
    	int highestRankH2=0;
    	for(int i : h1.rankFreq.keySet()) {
    		if(i > highestRankH1) {
    			highestRankH1 = i;
    			highestFreqH1 = h1.rankFreq.get(i);
    		}
    	}
    	for(int i : h2.rankFreq.keySet()) {
    		if(i > highestRankH2) {
    			highestRankH2 = i;
    			highestFreqH2 = h2.rankFreq.get(i);
    		}
    	}
    	if((highestRankH1 == highestRankH2)) {
    		if((highestFreqH1 > highestFreqH2)) {
    			return 0;
    		}else {
    			return 1;
    		}
    	}
    	if((highestRankH1 > highestRankH2)) {
    		return 0;
    	}else {
    		return 1;
    	}
    	


    }

    private boolean isHighCard(){
        this.handName = HandNameAndRank.HIGH_CARD.handName();
        this.handRank = HandNameAndRank.HIGH_CARD.handRank();
        return true;
    }

    private boolean isPair(){

        for(int i: rankFreq.keySet()){
            if(rankFreq.get(i) == 2){
                this.handName = HandNameAndRank.PAIR.handName();
                this.handRank = HandNameAndRank.PAIR.handRank();
                return true;
            }
        }
        return false;
    }
    private boolean isTwoPair(){
        boolean firstPair = false;
        for(int i : rankFreq.keySet()){
            if(rankFreq.get(i) == 2 && !firstPair){
                firstPair = true;
                continue;
            }
            if(rankFreq.get(i) == 2 && firstPair){
                this.handName = HandNameAndRank.TWO_PAIR.handName();
                this.handRank = HandNameAndRank.TWO_PAIR.handRank();
                return  true;
            }
        }
        return false;
    }
    private boolean isThreeOfAKind(){
        for(int i: rankFreq.keySet()){
            if(rankFreq.get(i) == 3){
                this.handName = HandNameAndRank.THREE_OF_A_KIND.handName();
                this.handRank = HandNameAndRank.THREE_OF_A_KIND.handRank();
                return true;
            }
        }
        return false;
    }
    private boolean isStraight(){
    	 	
        int currentRank = hand.get(0).getRank();
                
        for(int i = 0; i < 3; i++) {
        	currentRank = hand.get(0).getRank();
        	for(int k = 0; k < 4; k++) {
        		if(currentRank+1 == hand.get(k).getRank()){
                    currentRank = hand.get(k).getRank();
                }else {
                	break;
                }
        	}
        }

        this.handName = HandNameAndRank.STRIGHT.handName();
        this.handRank = HandNameAndRank.STRIGHT.handRank();
        return true;
    }
    private boolean isFlush(){
        for(String suit: suitFreq.keySet()){
            if(suitFreq.get(suit) == 5){
                this.handName = HandNameAndRank.FLUSH.handName();
                this.handRank = HandNameAndRank.FLUSH.handRank();
                return true;
            }
        }
        return false;
    }
    private boolean isFullHouse(){
        if(isThreeOfAKind() && isPair()){
            this.handName = HandNameAndRank.FULL_HOUSE.handName();
            this.handRank = HandNameAndRank.FULL_HOUSE.handRank();
            return true;
        }
        return false;
    }
    private boolean isFourOfAKind(){
        for (int i: rankFreq.keySet()){
            if(rankFreq.get(i) == 4){
                this.handName = HandNameAndRank.FOUR_OF_A_KIND.handName();
                this.handRank = HandNameAndRank.FOUR_OF_A_KIND.handRank();
                return true;
            }
        }
        return false;
    }
    private boolean isStrightFlush(){
        if(isFlush() && isStraight()){
            this.handName = HandNameAndRank.STRIGHT_FLUSH.handName();
            this.handRank = HandNameAndRank.STRIGHT_FLUSH.handRank();
            return true;
        }
        return false;
    }
    private boolean isRoyalFlush(){
        if(hand.get(0).getRank() == 10){
            if(isFlush() && isStraight()){
                this.handName = HandNameAndRank.ROYAL_FLUSH.handName();
                this.handRank = HandNameAndRank.ROYAL_FLUSH.handRank();
                return true;
            }
        }
        return false;
    }

    private void sortHand(){
        hand.sort(Comparator.comparing(Card::getRank));
    }

    public int getHandRank() {
        return handRank;
    }

    public String getHandName() {
        return handName;
    }

   
}
