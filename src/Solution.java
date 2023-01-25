
public class Solution {
	final BoardState nextMove;
	final double utility;
	
	Solution(BoardState nextMove, double utility) {
		this.nextMove = nextMove;
		this.utility = utility;
	}
}
