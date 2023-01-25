
public class Minimax extends AdversarialSearch {
	
	Minimax()
	{
	}
	
	Minimax(int maximumDepth) 
	{
		super(maximumDepth);
	}
	
	@Override
	Solution getNextMove(BoardState initialState, BoardGameAgent gameAgent, Player player)
	{
		nextMove = null;
		expandedStateCount = 0;
		maximumDepth = gameAgent.estimateDepth(initialState, player);
	
		Player opponent = (player == Player.One ? Player.Two : Player.One);		
		double utility = getNextMove(initialState, gameAgent, player, opponent, 0);
		
		return new Solution(nextMove, utility);
	}
	
	double getNextMove(BoardState currentState, BoardGameAgent gameAgent, Player player, Player opponent, int depth)
	{	
		expandedStateCount++;
		
		Double currentUtility = gameAgent.getUtility(currentState, player);
		if (depth == maximumDepth) {
			return currentUtility;
		}
		
		boolean isMaximizingPlayer = (depth % 2 == 0);		
		if (isMaximizingPlayer) {
			double maxUtility = Double.NEGATIVE_INFINITY;
			for (BoardState childState : currentState.getSuccessors(player)) {
				double utility = getNextMove(childState, gameAgent,  player, opponent, depth+1);
				if (depth == 0 && utility > maxUtility) {
					nextMove = childState;
				}
				maxUtility = Math.max(utility, maxUtility);
			}
			return maxUtility;
		}
		else {
			double minUtility = Double.POSITIVE_INFINITY;
			for (BoardState childState : currentState.getSuccessors(opponent)) {
				double utility = getNextMove(childState, gameAgent, player, player, depth+1);
				minUtility = Math.min(utility, minUtility);
			}
			return minUtility;
		}
	}
	
}
