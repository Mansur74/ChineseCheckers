
// I.Q. scores for imbecile: 25 to 50

public class ChineseCheckerImbecileAgent implements BoardGameAgent {

	final int maximumExpansionCount;
	
	ChineseCheckerImbecileAgent(int maximumExpansionCount) 
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
		
		Point2D playerCog = chineseCheckerState.calculateCenterOfGravity(player);
		Point2D opponentRegionCog = (player == Player.One ? chineseCheckerState.player2cog : chineseCheckerState.player1cog);
		
		Point2D delta = Point2D.subtract(opponentRegionCog, playerCog);

		double utility = 1.0 / (1.0 + delta.manhattan());
		
		return utility;
	}
	
	@Override
	public String toString()
	{
		return "Imbecile Agent";
	}
	
}
