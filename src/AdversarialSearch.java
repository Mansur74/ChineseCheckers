
public abstract class AdversarialSearch {

	public int maximumDepth;
	protected BoardState nextMove;
	protected int expandedStateCount;
	
	AdversarialSearch()
	{
		maximumDepth = Integer.MAX_VALUE;
	}
	
	AdversarialSearch(int maximumDepth) 
	{
		this.maximumDepth = maximumDepth;
	}
		
	int getExpandedStateCount()
	{
		return expandedStateCount; 
	}
	
	abstract Solution getNextMove(BoardState initialState, BoardGameAgent gameAgent, Player player);
	
}
