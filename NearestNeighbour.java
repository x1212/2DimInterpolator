
public class NearestNeighbour extends BasicInterpolation {
	@Deprecated
	protected float interpolate(float valp1, float valp2, float valp3, float valp4, float posx1, float posx2) {
		float t = posx1;
		float u = posx2;
		float ret = (1-t)*(1-u)*valp1+t*(1-u)*valp2+t*u*valp3+(1-t)*u*valp4;
		if (t < 0.5) {
			if (u < 0.5) {
				ret = valp1;
			} else if (u >= 0.5) {
				ret = valp4;
			}
		} else if (t >= 0.5) {
			if (u < 0.5) {
				ret = valp2 ;
			} else if (u >= 0.5) {
				ret = valp3;
			}
		}
		return ret;
	}
}
