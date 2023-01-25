// Change class name with your group name. 
// For example for group 'Eagles': EaglesChineseCheckerState instead of ChineseCheckerState

interface BoardGameAgent {

	abstract public int estimateDepth(BoardState boardState, Player player);
	
	abstract public long estimateExpansionCount(BoardState boardState, int m, Player player);
	
	abstract public double getUtility(BoardState boardState, Player player);
	
}
