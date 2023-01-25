
public class Point2D {
	public double x;
	public double y;
	
	Point2D() {
		x = 0.0;
		y = 0.0;
	}
	
	Point2D(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	double norm()
	{
		return Math.sqrt(x * x + y * y);
	}
	
	double manhattan()
	{
		return Math.abs(x) + Math.abs(y);
	}	
	
	static Point2D subtract(Point2D p1, Point2D p2)
	{
		return new Point2D(p1.x - p2.x, p1.y - p2.y);
	}
	
	static Point2D add(Point2D p1, Point2D p2)
	{
		return new Point2D(p1.x + p2.x, p1.y + p2.y);
	}
	
	@Override
	public String toString()
	{
		return "Point is (" + x + ", " + y + ") ";
	}	
}
