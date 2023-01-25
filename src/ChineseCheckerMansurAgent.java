
public class ChineseCheckerMansurAgent implements BoardGameAgent {
	
	final int maximumExpansionCount;
	
	ChineseCheckerMansurAgent(int maximumExpansionCount) 
	{
		this.maximumExpansionCount = maximumExpansionCount;
	}

	@Override
	public int estimateDepth(BoardState boardState, Player player) 
	{
		int depth = 0;
		long expansionCount = 0;
		
		while (true) 
		{
		    depth++;  
			expansionCount = estimateExpansionCount(boardState, depth, player);
			
			// check if expansionCount is more than maximumExpansionCount or not
			// if more, break the loop	        
			
	        if (expansionCount > maximumExpansionCount)
	            break;
	       
	        
		}
		if (expansionCount >= Integer.MAX_VALUE)
		{
			System.out.println("ED: " + (depth - 1) + "\n----------------");
			return depth - 1;
		}
		
		System.out.println("Estimated Depth: " + (depth - 1 + (depth - 1) / 2) + "\n----------------");
	    return depth - 1 + (depth - 1) / 2; // return the previous depth which doesn't pass maximumExpansionCount
	}
	
	@Override
	public long estimateExpansionCount(BoardState boardState, int m, Player player) {
		
		if(boardState.wins(player))
			return Integer.MAX_VALUE;
	    
		if (m == 0) 
	        return 1; //to increase 1
	    
	    ChineseCheckerState currentState = (ChineseCheckerState)boardState;
	    long expansionCount = 0;

	    // get subChilds from the currentState 
	    for (BoardState child : currentState.getSuccessors(player))
	    {
	    	// recursive count
	        expansionCount += estimateExpansionCount(child, m - 1, player.getOpponent());
	        if(expansionCount >= Integer.MAX_VALUE)
	        	break;
	    }
	    
	    return expansionCount;
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
		
		// utility 3
    	int playerPossibleMoves = 0;
    	int opponentPossibleMoves = 0;
    	
    	// count the possible movements of the stones
    	for (int y=0; y<chineseCheckerState.boardSize; y++) {			
			for (int x=0; x<chineseCheckerState.boardSize; x++) {
				
				//get single movements and jumps
				if (chineseCheckerState.boardCells[y][x] == player) {
					playerPossibleMoves += chineseCheckerState.getMovements(chineseCheckerState, player, x, y).size() + chineseCheckerState.getJumps(chineseCheckerState, player, x, y).size();
				}

				if (chineseCheckerState.boardCells[y][x] == opponent) {
					opponentPossibleMoves += chineseCheckerState.getMovements(chineseCheckerState, opponent, x, y).size() + chineseCheckerState.getJumps(chineseCheckerState, opponent, x, y).size();
				}
			}
		}    
    	

    	double utility3 = (1.0 + playerPossibleMoves) / (1.0 + opponentPossibleMoves);
    	  
		
		// combined utility      (priority degree => 0.20)          (priority degree => 1)          (priority degree => 0.04)
		double combinedUtility = Math.pow((1.0 + utility1), 0.20) * Math.pow((1.0 + utility2), 1) * Math.pow((1.0 + utility3), 0.04);
		
		return combinedUtility;
	}
	
	@Override
	public String toString()
	{
		return "Mansur Agent";
	}
	
}