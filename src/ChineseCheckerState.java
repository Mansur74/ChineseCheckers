import java.util.*;

public class ChineseCheckerState extends BoardState {
		
	public final int minimumBoardSize = 5;
	public final int boardSize;
	public final int stoneSize;
	public final int layoutKey;
	protected final Player initialBoardCells[][];
	protected final Player boardCells[][];
	protected Point2D player1cog;
	protected Point2D player2cog;	
	protected Direction player1direction;
	private final int X = 1;
	private final int Y = 0;
	private final int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
	private final int Undefined = -1;
	private int pivot_x = Undefined;
	private int pivot_y = Undefined;
	
	ChineseCheckerState(int boardSize, int stoneSize, int layoutKey) 
	{			
		boardSize = Math.max(boardSize, minimumBoardSize);
		
		this.boardSize = boardSize;
		this.stoneSize = stoneSize;
		this.layoutKey = layoutKey;
		this.initialBoardCells = new Player[boardSize][boardSize];
		this.boardCells = new Player[boardSize][boardSize];
		
		createInitialBoard();
	}

	ChineseCheckerState(ChineseCheckerState state)
	{
		this.boardSize = state.boardSize;
		this.stoneSize = state.stoneSize;
		this.layoutKey = state.layoutKey;
		this.initialBoardCells = state.initialBoardCells;
		this.boardCells = new Player[boardSize][boardSize];
		this.player1cog = state.player1cog;
		this.player2cog = state.player2cog;			
		this.player1direction = state.player1direction;
		this.pivot_x = state.pivot_x;
		this.pivot_y = state.pivot_y;
		
		for (int y=0; y<boardSize; y++) {			
			for (int x=0; x<boardSize; x++) {
				this.boardCells[y][x] = state.boardCells[y][x];
			}
		}		
	}
	
	private void createInitialBoard()
	{
		// create initial board configuration
		
		int i = 0;
		for(int y = 0; y < stoneSize; y++){
			if(layoutKey == 1)
				i = y;
			
			for(int x = 0; x < stoneSize - i; x++){
				this.initialBoardCells[y][x] = Player.One;					
				this.initialBoardCells[boardSize-y-1][boardSize-x-1] = Player.Two;
			}
		}
			
		
		// assign initial board configuration to game board
		for (int y=0; y<boardSize; y++) {			
			for (int x=0; x<boardSize; x++) {
				this.boardCells[y][x] = this.initialBoardCells[y][x];
			}
		}			
		
		
		// calculate center of gravity for player 1 and player 2
		player1cog = calculateCenterOfGravity(Player.One);
		player2cog = calculateCenterOfGravity(Player.Two);
		
		
		// move direction for player 1 (player 2 is the opposite direction) 
		player1direction = calculateMoveDirection();
	}
	
	public Point2D calculateCenterOfGravity(Player player) 
	{
		int count = 0;
		double xSum = 0.0;
		double ySum = 0.0;
		
		for (int y=0; y<boardSize; y++) {			
			for (int x=0; x<boardSize; x++) {
				if (boardCells[y][x] == player) {
					count++;
					xSum += x;
					ySum += y;
				}		
			}
		}
		
		if (count > 0)
			return new Point2D(xSum / count, ySum / count);
		else
			return null;
	}
	
	private Direction calculateMoveDirection()
	{
		Point2D difference = Point2D.subtract(player2cog, player1cog);
		
		int dirx = (int)Math.signum(difference.x);
		int diry = (int)Math.signum(difference.y);
		
		return new Direction(dirx, diry);
	}

	public Direction getPlayer1Direction() 
	{
		return player1direction;
	}
	
	public Direction getPlayer2Direction() 
	{
		return player1direction.getOppositeDirection();
	}
	
	public final boolean inside(int x, int y) {
		return (x >= 0 && x < boardSize && y >= 0 && y < boardSize);
	}
			    
    @Override
    public boolean equals(Object obj)
    {
    	if (this == obj) {
    		return true;
    	}
    	
    	if (obj instanceof ChineseCheckerState) { 
    		ChineseCheckerState state = (ChineseCheckerState)obj;
    		for (int y=0; y<boardSize; y++) {			
    			for (int x=0; x<boardSize; x++) {
    				if (this.boardCells[y][x] != state.boardCells[y][x]) {
    					return false;
    				}
    			}
    		}
    		return true;
    	}
    	
    	return false;
    }

    @Override
    public int hashCode() 
    {   	
        return toString().hashCode();
    }	
    
    @Override
    boolean set(int px, int py, int tx, int ty, Player player)
    {
    	if (inside(px, py) && inside(tx, ty)) {
    		swap(px, py, tx, ty) ;

    		return true;
    	}
    	else
    		return false;
    }
    
    public ChineseCheckerState swap(int x1, int y1, int x2, int y2) 
	{
		Player temp = boardCells[y1][x1];
		boardCells[y1][x1] = boardCells[y2][x2];
		boardCells[y2][x2] = temp;			
		return this;
	}
    
    @Override
	Player get(int x, int y) {
		if (inside(x, y))
			return boardCells[y][x];
		else
			return null;
	}
    
    @Override
	public boolean wins(Player player)
	{
    	Player opponent = player.getOpponent();

		for (int y=0; y<boardSize; y++) {			
			for (int x=0; x<boardSize; x++) {
				if (initialBoardCells[y][x] == opponent) {
					if (boardCells[y][x] != player) {
						return false;
					}
				}
				else {
					if (boardCells[y][x] == player) {
						return false;
					}
				}
			}
		}    	
    	
		return true;
	}
	
	@Override
	public boolean isTie()
	{
		return (!wins(Player.One) && 
				!wins(Player.Two) &&
				getSuccessors(Player.One).isEmpty() && 
				getSuccessors(Player.Two).isEmpty()); 
	}

	@Override
	public boolean isTerminal()
	{
		return wins(Player.One) || wins(Player.Two) || isTie();
	}
	
	public boolean isInOpponentArea(Player player, int x, int y)
	{
		if (x>=0 && x<boardSize && y>=0 && y<boardSize)
			return initialBoardCells[y][x] == player.getOpponent();
		else
			return false;		
	}
	
	private void clearPivots()
	{
		pivot_x = Undefined;
		pivot_y = Undefined;
	}
	
	public List<Direction> getMovements(ChineseCheckerState state, Player player, int x, int y)
	{
		List<Direction> movements = new ArrayList<>();

		boolean inOpponentArea = isInOpponentArea(player, x, y);
		
		Direction moveDirection = (player == Player.One ? getPlayer1Direction() : getPlayer2Direction());
		
		for (int[] direction : directions) {
			int nx = x + direction[X];
			int ny = y + direction[Y];
			boolean useDirection = (nx>=0 && nx<boardSize && ny>=0 && ny<boardSize && state.boardCells[ny][nx] == null);
			
			if (!inOpponentArea) {
				if ((direction[X] != 0 && direction[X] != moveDirection.x) || 
					(direction[Y] != 0 && direction[Y] != moveDirection.y)) 
				{
					useDirection = false;
				}
			}
			
			if (useDirection) {
				movements.add(new Direction(direction[X], direction[Y]));
			}
		}

		return movements;
	}
	
	public List<Direction> getJumps(ChineseCheckerState state, Player player, int x, int y)
	{
		List<Direction> movements = new ArrayList<>();

		boolean inOpponentArea = isInOpponentArea(player, x, y);
		
		Direction moveDirection = (player == Player.One ? getPlayer1Direction() : getPlayer2Direction());
		
		for (int[] direction : directions) {
			int nx1 = x + direction[X];
			int ny1 = y + direction[Y];
			int nx2 = nx1 + direction[X];
			int ny2 = ny1 + direction[Y];
			boolean useDirection = (inside(nx1, ny1) && inside(nx2, ny2) &&
									state.boardCells[ny1][nx1] != null &&
									state.boardCells[ny2][nx2] == null);
			
			if (!inOpponentArea) {
				if ((direction[X] != 0 && direction[X] != moveDirection.x) || 
					(direction[Y] != 0 && direction[Y] != moveDirection.y)) 
				{
					useDirection = false;
				}
			}
			
			if (useDirection) {
				movements.add(new Direction(direction[X], direction[Y]));
			}
		}

		return movements;
	}
	
	private ChineseCheckerState createSuccessor(BoardState boardState, Player player, int x, int y, int nx, int ny)
	{
		if (inside(nx, ny)) {
			try {
				ChineseCheckerState successor = (ChineseCheckerState)boardState.clone();
				successor.boardCells[y][x] = null;
				successor.boardCells[ny][nx] = player;

				return successor;
			} 
			catch (CloneNotSupportedException e) 
			{
				e.printStackTrace();
			}					
		}
		
		return null;
	}
	
	@Override
	public List<BoardState> getSuccessors(Player player)
	{
		Set<BoardState> childSet = new HashSet<>();
		
		Stack<ChineseCheckerState> stack = new Stack<>();
		
		// single moves
		stack.push(this);
		while (!stack.isEmpty()) {
			ChineseCheckerState currentState = stack.pop();
			
			for (int y=0; y<boardSize; y++) {			
				for (int x=0; x<boardSize; x++) {				
					if (currentState.boardCells[y][x] == player) {
						currentState.clearPivots();
						
						for (Direction move : getMovements(currentState, player, x, y)) {
							int nx = x + move.x;
							int ny = y + move.y;
							ChineseCheckerState childState = createSuccessor(currentState, player, x, y, nx, ny);

							if (childState != null && !childSet.contains(childState)) {
								childSet.add(childState);
							}								
						}
					}
				}
			}		
		}		
		
		// jumps
		for (int y=0; y<boardSize; y++) {			
			for (int x=0; x<boardSize; x++) {			
				if (boardCells[y][x] == player) {
					for (Direction move : getJumps(this, player, x, y)) {
						int pivot_x = x + 2*move.x;
						int pivot_y = y + 2*move.y;

						ChineseCheckerState childState = createSuccessor(this, player, x, y, pivot_x, pivot_y);
				
						if (childState != null) {
							childState.pivot_x = pivot_x;
							childState.pivot_y = pivot_y;
							
							if (!childSet.contains(childState)) {
								stack.push(childState);
								childSet.add(childState);
							}	
						}
					}
				}
			}
		}				
		while (!stack.isEmpty()) {
			ChineseCheckerState currentState = stack.pop();
			
			int x = currentState.pivot_x;
			int y = currentState.pivot_y;
			
			for (Direction move : getJumps(currentState, player, x, y)) {
				int pivot_x = x + 2*move.x;
				int pivot_y = y + 2*move.y;

				ChineseCheckerState childState = createSuccessor(currentState, player, x, y, pivot_x, pivot_y);
			
				if (childState != null) {
					childState.pivot_x = pivot_x;
					childState.pivot_y = pivot_y;
	
					if (!childSet.contains(childState)) {
						stack.push(childState);
						childSet.add(childState);
					}	
				}
			}
		}		
		
		clearPivots();
		
		return new ArrayList<>(childSet);
	}
		
	@Override
	public BoardState getSwitchedBoard()
	{
		ChineseCheckerState switchedBoard = new ChineseCheckerState(boardSize, stoneSize, layoutKey); 
							
		for (int y=0; y<boardSize; y++) {
			for (int x=0; x<boardSize; x++) {
				if (boardCells[y][x] == null)
					switchedBoard.boardCells[y][x] = null; 
				else
					switchedBoard.boardCells[y][x] = boardCells[y][x].getOpponent();
				
				if (initialBoardCells[y][x] == null)
					switchedBoard.initialBoardCells[y][x] = null;
				else
					switchedBoard.initialBoardCells[y][x] = initialBoardCells[y][x].getOpponent();
			}
		}
		
		switchedBoard.player1direction = player1direction.getOppositeDirection();
		
		switchedBoard.player1cog = player2cog;
		switchedBoard.player2cog = player1cog;
					
		return switchedBoard;			
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("████");
		for (int x=0; x<boardSize; x++) {
			if(x >= 10)
			{
				if(x != boardSize-1)
					sb.append(Integer.toString(x) + " #");
				else
					sb.append(Integer.toString(x));
			}
			else
			{
				if(x != boardSize-1)
					sb.append(Integer.toString(x) + "# #");
				else
					sb.append(Integer.toString(x) + "#");
			}
	
		}
		sb.append("\n████");
		
		for (int x=0; x<boardSize; x++) {
			if(x == boardSize - 1)
				sb.append("##");
			else
			    sb.append("## #");
		}
		sb.append("\n");
		
		for (int y=0; y<boardSize; y++) {
			if(y >= 10)
				sb.append("#" + Integer.toString(y) + " ");
			else
				sb.append("#" + Integer.toString(y) + "# ");
			for (int x=0; x<boardSize; x++) {
				if (boardCells[y][x] == null)
					sb.append(".   ");
				else
					sb.append(boardCells[y][x]);
			}
			if(y != boardSize - 1)
				sb.append("\n###\n");
			else
				sb.append("\n");
		}	
		
		return sb.toString();
	}
	
	@Override
	public BoardState clone() throws CloneNotSupportedException
	{
		return new ChineseCheckerState(this);
	}

}
