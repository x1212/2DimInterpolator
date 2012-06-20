
public class BiCubic extends BasicInterpolation {
	
	
	private static int[][] matr = {
		{ 1, 0, 0, 0,   0, 0, 0, 0,   0, 0, 0, 0,   0, 0, 0, 0 } ,
		{ 0, 0, 0, 0,   0, 0, 0, 0,   1, 0, 0, 0,   0, 0, 0, 0 } ,
		{-3, 0, 0, 3,   0, 0, 0, 0,  -2, 0, 0,-1,   0, 0, 0, 0 } ,
		{ 2, 0, 0,-2,   0, 0, 0, 0,   1, 0, 0, 1,   0, 0, 0, 0 } ,
		{ 0, 0, 0, 0,   1, 0, 0, 0,   0, 0, 0, 0,   0, 0, 0, 0 } ,
		{ 0, 0, 0, 0,   0, 0, 0, 0,   0, 0, 0, 0,   1, 0, 0, 0 } ,
		{ 0, 0, 0, 0,  -3, 0, 0, 3,   0, 0, 0, 0,  -2, 0, 0,-1 } ,
		{ 0, 0, 0, 0,   2, 0, 0,-2,   0, 0, 0, 0,   1, 0, 0, 1 } ,
		{-3, 3, 0, 0,  -2,-1, 0, 0,   0, 0, 0, 0,   0, 0, 0, 0 } ,
		{ 0, 0, 0, 0,   0, 0, 0, 0,  -3, 3, 0, 0,  -2,-1, 0, 0 } ,
		{ 9,-9, 9,-9,   6, 3,-3,-6,   6,-6,-3, 3,   4, 2, 1, 2 } ,
		{-6, 6,-6, 6,  -4,-2, 2, 4,  -3, 3, 3,-3,  -2,-1,-1,-2 } ,
		{ 2,-2, 0, 0,   1, 1, 0, 0,   0, 0, 0, 0,   0, 0, 0, 0 } ,
		{ 0, 0, 0, 0,   0, 0, 0, 0,   2,-2, 0, 0,   1, 1, 0, 0 } ,
		{-6, 6,-6, 6,  -3,-3, 3, 3,  -4, 4, 2,-2,  -2,-2,-1,-1 } ,
		{ 4,-4, 4,-4,   2, 2,-2,-2,   2,-2,-2, 2,   1, 1, 1, 1 }
	};
	/*private static int[][] matr1 = {
		{ 1, 0, 0, 0,   0, 0, 0, 0,   0, 0, 0, 0,   0, 0, 0, 0 } ,
		{ 0, 0, 0, 0,   1, 0, 0, 0,   0, 0, 0, 0,   0, 0, 0, 0 } ,
		{-3, 3, 0, 0,  -2,-1, 0, 0,   0, 0, 0, 0,   0, 0, 0, 0 } ,
		{ 2,-2, 0, 0,   1, 1, 0, 0,   0, 0, 0, 0,   0, 0, 0, 0 } ,
		
		{ 0, 0, 0, 0,   0, 0, 0, 0,   1, 0, 0, 0,   0, 0, 0, 0 } ,
		{ 0, 0, 0, 0,   0, 0, 0, 0,   0, 0, 0, 0,   1, 0, 0, 0 } ,
		{ 0, 0, 0, 0,   0, 0, 0, 0,  -3, 3, 0, 0,  -2,-1, 0, 0 } ,
		{ 0, 0, 0, 0,   0, 0, 0, 0,   2,-2, 0, 0,   1, 1, 0, 0 } ,
		
		{-3, 0, 3, 0,   0, 0, 0, 0,  -2, 0,-1, 0,   0, 0, 0, 0 } ,
		{ 0, 0, 0, 0,  -3, 0, 3, 0,   0, 0, 0, 0,  -2, 0,-1, 0 } ,
		{ 9,-9,-9, 9,   6, 3,-6,-3,   6,-6, 3,-3,   4, 2, 2, 1 } ,
		{-6, 6, 6,-6,  -3,-3, 3, 3,  -4, 4,-2, 2,  -2,-2,-1,-1 } ,
		
		{ 2, 0,-2, 0,   0, 0, 0, 0,   1, 0, 1, 0,   0, 0, 0, 0 } ,
		{ 0, 0, 0, 0,   2, 0,-2, 0,   0, 0, 0, 0,   1, 0, 1, 0 } ,
		{-6, 6, 6,-6,  -4,-2, 4, 2,  -3, 3,-3, 3,  -2,-1,-2,-1 } ,
		{ 4,-4,-4, 4,   2, 2,-2,-2,   2,-2, 2,-2,   1, 1, 1, 1 }
	};*/
	
	private static float pot(float f, int p) {
		float ret = f;
		
		
		if (p>=0) {
			if (p == 0) return 1;
			if (p == 1) return f;
			for (int i = 1; i<p; i++) {
				ret *= f;
			}
		} else {
			p = -p;
			for (int i = 1; i<p; i++) {
				ret /= f;
			}
		}
		
		return ret;
	}
	
	@Deprecated
	protected float interpolate(float[] val, float[] d1, float[] d2, float[] d1d2, float posx1, float posx2) { 
		float t = posx1;
		float u = posx2;
		
		float[] tmp = new float[16];
		for (int i = 0; i<4; i++) {
			tmp[i] = val[i];
			tmp[i+4] = d1[i]*1;
			tmp[i+8] = d2[i]*1;
			tmp[i+12] = d1d2[i]*1;
		}
		
		float[] c = new float[16];
		
		// c = matr*tmp
		for (int i = 0; i<16; i++) {
			float line = 0.0f;
			for (int s = 0; s<16; s++) {
				line += tmp[s] * matr[i][s];
			}
			c[i] = line;
		}
		float ret = 0.0f;
		
		for (int i = 0; i<4; i++) {
			
			for (int j = 0; j<4; j++) {
				//ret = ret + j*c[i*4+j]*pot(t,i)*pot(u,j-1);
				ret = ret + c[i*4+j]*pot(t,i)*pot(u,j);
			}
		}
		
		
		return ret;
	}
	public float getInterpolatedValue(float[][] src, int cur1, int cur2, float u, float t) {
		
		float[] y = new float[4], d1 = new float[4], d2 = new float[4], d1d2 = new float[4];
		
		y[0] = src[cur1][cur2];
		y[1] = src[cur1+1][cur2];
		y[2] = src[cur1+1][cur2+1];
		y[3] = src[cur1][cur2+1];
		
		
		// d1----------------------------------------------------------
		
		if (cur1>0) {
			d1[0] = (src[cur1+1][cur2] - src[cur1-1][cur2]) / 2.0f;
			d1[3] = (src[cur1+1][cur2+1] - src[cur1-1][cur2+1]) / 2.0f;
		} else {
			d1[0] = (src[cur1+1][cur2] - src[cur1][cur2]) / 1.0f;//0.0f;
			d1[3] = (src[cur1+1][cur2+1] - src[cur1][cur2+1]) / 1.0f;//0.0f;
		}
		if (cur1+2<src.length) {
			d1[1] = (src[cur1+2][cur2] - src[cur1][cur2]) / 2.0f;
			d1[2] = (src[cur1+2][cur2+1] - src[cur1][cur2+1]) / 2.0f;
		} else {
			d1[1] = d1[1] = (src[cur1+1][cur2] - src[cur1][cur2]) / 2.0f;
			d1[2] = d1[2] = (src[cur1+1][cur2+1] - src[cur1][cur2+1]) / 2.0f;
		}
		
		
		
		
		// d2-----------------------------------------------------------
		
		if (cur2>0) {
			d2[0] = (src[cur1][cur2+1] - src[cur1][cur2-1]) / 2.0f;
			d2[1] = (src[cur1+1][cur2+1] - src[cur1+1][cur2-1]) / 2.0f;
		} else {
			d2[0] = (src[cur1][cur2+1] - src[cur1][cur2]) / 1.0f;
			d2[1] = (src[cur1+1][cur2+1] - src[cur1+1][cur2]) / 1.0f;
		}
		if (cur2+2<src[0].length) {
			d2[2] = (src[cur1+1][cur2+2] - src[cur1+1][cur2]) / 2.0f;
			d2[3] = (src[cur1][cur2+2] - src[cur1][cur2]) / 2.0f;
		} else {
			d2[2] = (src[cur1+1][cur2+1] - src[cur1+1][cur2]) / 1.0f;
			d2[3] = (src[cur1][cur2+1] - src[cur1][cur2]) / 1.0f;
		}
		
		
		
		
		// d1d2 ---------------------------------------------------
		
		for (int i = 0; i<4; i++) {
			d1d2[i]=0.0f;
		}
		if ( cur1>0&&cur1+2<src.length && cur2>0&&cur2+2<src[0].length ) {
			d1d2[0] = (src[cur1+1][cur2+1] - src[cur1+1][cur2-1] - src[cur1-1][cur2+1] + src[cur1-1][cur2-1])/4.0f;
			d1d2[1] = (src[cur1+2][cur2+1] - src[cur1+2][cur2-1] - src[cur1][cur2+1] + src[cur1][cur2-1])/4.0f;
			d1d2[2] = (src[cur1+2][cur2+2] - src[cur1+2][cur2] - src[cur1][cur2+2] + src[cur1][cur2])/4.0f;
			d1d2[3] = (src[cur1+1][cur2+2] - src[cur1+1][cur2] - src[cur1-1][cur2+2] + src[cur1-1][cur2])/4.0f;
		}
		for (int i = 0; i<4; i++) {
			d1d2[i]=0.0f;
		}
		
		
		
		// interpolieren ----------------------------------------------------------------------------
		float ret = interpolate(y, d1, d2, d1d2, (float)u, (float)t);

		return ret;
	}
}
