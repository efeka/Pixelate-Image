
public class Pixel {
		
		private int R, G, B, A;
		
		public Pixel(int r, int g, int b, int a) {
			R = r;
			G = b;
			B = b;
			A = a;
		}
		
		public static Pixel check(Pixel p1, Pixel p2, int sensitivity) {
			double sum1 = p1.R + p1.G + p1.B;
			double  sum2 = p2.R + p2.G + p2.B;
			if (sum1 > sum2 && sum1 * sensitivity / 100 < sum2)
				return p1;
			return p2;
		}
		
		public static boolean isSame(Pixel p1, Pixel p2) {
			return p1.getR() == p2.getR() 
					&& p1.getG() == p2.getG()
					&& p1.getB() == p2.getB();
		}
		
		public int getR() { return R; }
		public int getG() { return G; }
		public int getB() { return B; }
		public int getA() { return A; }
		
		public void setR(int r) { R = r; }
		public void setG(int g) { G = g; }
		public void setB(int b) { B = b; }
		public void setA(int a) { A = a; }
	}
