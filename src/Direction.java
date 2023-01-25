
public class Direction {
	public final int x;
	public final int y;
	
	Direction(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}
	
	public Direction getOppositeDirection()
	{
		return new Direction(-x, -y);
	}
	
	@Override
	public String toString()
	{
		return "Direction is (" + x + ", " + y + ") ";
	}
}
