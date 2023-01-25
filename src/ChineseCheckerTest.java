import java.util.*;

public class ChineseCheckerTest {
	static Random rand = new Random(System.currentTimeMillis());
	
	public static void main(String[] args) throws CloneNotSupportedException {
			
		final int maximumExpansionCount = 100000;

		int boardSize = 8, stoneSize = 3, layoutKey = 0;
		Scanner in = new Scanner(System.in);		
		
		
		do
		{
			System.out.print("Choose a scenario square/triangle (0 or 1)? ");
			layoutKey = in.nextInt();
		}
		
		while(layoutKey != 0 && layoutKey != 1);
		
		do
		{
			System.out.print("Enter the number of the board size: ");
			boardSize = in.nextInt();
		}
		
		while(boardSize < 5 || boardSize > 25);
		
		do
		{
			System.out.print("Enter the number of the stone size: ");
			stoneSize = in.nextInt();
		}
		
		while(stoneSize < 2 || (layoutKey == 0 && stoneSize > boardSize/2) || (layoutKey == 1 && stoneSize > boardSize/2));
		
		
		
		ChineseCheckerState board = new ChineseCheckerState(boardSize, stoneSize, layoutKey);
		//AdversarialSearch adversialSearch = new Minimax();
		AdversarialSearch adversialSearch = new AlphaBetaPruning();
		//AdversarialSearch adversialSearch = new AdversarialGreedySearch();
		  
		//BoardGameAgent agent1 = new ChineseCheckerIdiotAgent(maximumExpansionCount);
		//BoardGameAgent agent1 = new ChineseCheckerImbecileAgent(maximumExpansionCount);
		//BoardGameAgent agent1 = new ChineseCheckerMoronAgent(maximumExpansionCount);
		BoardGameAgent agent1 = new ChineseCheckerMansurAgent(maximumExpansionCount);
		  
		//BoardGameAgent agent2 = new ChineseCheckerIdiotAgent(maximumExpansionCount);
		//BoardGameAgent agent2 = new ChineseCheckerImbecileAgent(maximumExpansionCount);
	    BoardGameAgent agent2 = new ChineseCheckerMoronAgent(maximumExpansionCount);
		//BoardGameAgent agent2 = new ChineseCheckerGroup4Agent(maximumExpansionCount);
	
		
		System.out.println();
		System.out.println("Initial state:\n" + board);

		int playerIndex = 1;
	
		Solution solution;
		do {
			playerIndex++;
			
			if (playerIndex % 2 == 0) {
				// Player 1 : CPU AI
				System.out.println(agent1.toString());
				solution = adversialSearch.getNextMove(board, agent1, Player.One);
				board = (ChineseCheckerState)solution.nextMove;
				System.out.println(board + " --> " + solution.utility + " (# = " + adversialSearch.getExpandedStateCount() + ")");
				System.out.println();
				if (board.wins(Player.One)) {
					System.out.println(agent1.toString() + " wins");
					break;
				}

				if (board.wins(Player.Two)) {
					System.out.println(agent2.toString() + "wins");
					break;
				}
			}
			else {
				
				// Player 2 : CPU AI
				System.out.println(agent2.toString());
				solution = adversialSearch.getNextMove(board, agent2, Player.Two);
				board = (ChineseCheckerState)solution.nextMove;
				System.out.println(board + " --> " + solution.utility + " (# = " + adversialSearch.getExpandedStateCount() + ")");
				System.out.println();
				if (board.wins(Player.Two)) {
					System.out.println(agent2.toString() + "wins");
					break;
				}

				if (board.wins(Player.One)) {
					System.out.println(agent1.toString() + "wins");
					break;
				}
				
				/*
				// Player 2 : Human
				System.out.println("Human move \n----------");
				boolean isValid;
				do {
					System.out.print("Choose your stone:\n");
					System.out.print("X= ");
					int px = in.nextInt();
					
					System.out.print("Y= ");
					int py = in.nextInt();
					
					System.out.print("Choose yout target:\n");
					System.out.print("X= ");
					int tx = in.nextInt();
					
					System.out.print("Y= ");
					int ty = in.nextInt();
					
					isValid = board.set(px, py, tx, ty, Player.Two);
					if(!isValid)
						System.out.println("Please give a valid position.");
				}
				while (!isValid);
				
				System.out.println(board);
				if (board.wins(Player.Two)) {
					System.out.println("Human player wins");
					break;
				}

				if (board.wins(Player.One)) {
					System.out.println("AI player wins");
					break;
				}
				*/
			}
		}
		while (true);			
		
		in.close();
		
		/*
		for(BoardState state: board.getSuccessors(Player.One))
		{
		    System.out.println(state);
		}
		*/
	}

}
