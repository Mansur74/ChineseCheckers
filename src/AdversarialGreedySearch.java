
public class AdversarialGreedySearch extends AdversarialSearch {

	@Override
	Solution getNextMove(BoardState initialState, BoardGameAgent gameAgent, Player player) {		
		BoardState bestMove = null;
		double maximumUtility = Double.NEGATIVE_INFINITY;
		for (BoardState successor: initialState.getSuccessors(player)) {
			double successorUtility = gameAgent.getUtility(successor, player);
			if (successorUtility > maximumUtility) {
				maximumUtility = successorUtility;
				bestMove = successor;
			}
		}
		
		return new Solution(bestMove, maximumUtility);
	}

}
