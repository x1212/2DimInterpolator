
public class BasicInterpolation {
	@Deprecated
	protected float interpolate(float valp1, float valp2, float valp3, float valp4, float posx1, float posx2) {
		return 0;
	}
	public float getInterpolatedValue(float[][] src, int cur1, int cur2, float u, float t) {
		return interpolate(src[cur1][cur2], src[cur1+1][cur2], src[cur1+1][cur2+1], src[cur1][cur2+1], (float)u, (float)t);
	}
}
