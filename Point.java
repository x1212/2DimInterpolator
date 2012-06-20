
public class Point {
	public float x1, x2, value;
	
	
	
	public Point copy() {
		Point ret = new Point();
		ret.x1=x1;
		ret.x2=x2;
		ret.value=value;
		return ret;
	}
}
