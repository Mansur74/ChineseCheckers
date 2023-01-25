
public class Node implements Comparable<Node> {

	public BoardState state;
	public Node parent;
	public double utility;
	public int depth;
	
	Node(BoardState state)
	{
		this.state = state;
		this.parent = null;
		this.utility = 0.0;		
		this.depth = 0;
	}	
	
	Node(BoardState state, Node parent, double utility, int depth)
	{
		this.state = state;
		this.parent = parent;
		this.utility = utility;
		this.depth = depth;
	}	

    @Override
    public int hashCode() 
    {
    	return state.hashCode();
    }
    
    @Override
    public boolean equals(Object obj)
    {
    	if (this == obj) {
    		return true;
    	}
    	
    	if (obj instanceof BoardState) { 
    		BoardState state = (BoardState)obj;
    		return state.equals(this.state);
    	}
    	
    	return false;
    }   
    
	@Override
	public int compareTo(Node node) 
	{		
		return (int)Math.signum(this.utility - node.utility);
	}
	
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder(); 
		sb.append(state);
		sb.append(parent == null ? "Has no parent \n" : "Has parent \n");
		sb.append("Utility = " + utility + "\n");
		sb.append("Depth = " + depth + "\n");
		
		return sb.toString();
	}

}
