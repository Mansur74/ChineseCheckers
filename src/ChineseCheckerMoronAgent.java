
// I.Q. scores for moron: 50-70/75

public class ChineseCheckerMoronAgent implements BoardGameAgent {
	
	final int maximumExpansionCount;
	
	ChineseCheckerMoronAgent(int maximumExpansionCount) 
	{
		this.maximumExpansionCount = maximumExpansionCount;
	}

	@Override
	public int estimateDepth(BoardState boardState, Player player) 
	{
		return 1;
	}
	
	@Override
	public long estimateExpansionCount(BoardState boardState, int m, Player player)
	{
		return 1;
	}
	
	@Override
	public double getUtility(BoardState boardState, Player player)
	{
		ChineseCheckerState chineseCheckerState = (ChineseCheckerState)boardState;

		// utility 1
		Point2D playerCog = chineseCheckerState.calculateCenterOfGravity(player);
		Point2D opponentRegionCog = (player == Player.One ? chineseCheckerState.player2cog : chineseCheckerState.player1cog);
		
		Point2D delta = Point2D.subtract(opponentRegionCog, playerCog);

		double utility1 = 1.0 / (1.0 + delta.manhattan());
		
		
		// utility 2		
		Player opponent = player.getOpponent();

    	int playerInplaceCount = 0;
    	int opponentInplaceCount = 0;
    	
		for (int y=0; y<chineseCheckerState.boardSize; y++) {			
			for (int x=0; x<chineseCheckerState.boardSize; x++) {
				if (chineseCheckerState.initialBoardCells[y][x] == opponent && chineseCheckerState.boardCells[y][x] == player) {
					playerInplaceCount++;
				}

				if (chineseCheckerState.initialBoardCells[y][x] == player && chineseCheckerState.boardCells[y][x] == opponent) {
					opponentInplaceCount++;
				}
			}
		}    	
		
		double utility2 = (1.0 + playerInplaceCount) / (1.0 + opponentInplaceCount);
		
		
		// combined utility
		double combinedUtility = (1.0 + utility1) * (1.0 + utility2);
		
		return combinedUtility;
	}
	
	@Override
	public String toString()
	{
		return "Moron Agent\n-----------";
	}
	
}

