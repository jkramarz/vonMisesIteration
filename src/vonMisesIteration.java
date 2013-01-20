import Jama.Matrix;

public class vonMisesIteration {
	static Matrix rotation;
	static {
		double mat[][] = {
				{0, -1, 0,  0,  0, 0},
				{1, 0, 0,  0,  0, 0},
				{0, 0, 0,  1,  0 ,0},
				{0, 0, -1,  0,  0, 0},
				{0, 0, 0,  0,  0, -1},
				{0, 0, 0,  0,  1, 0},
		};
		rotation = new Matrix(mat);
	}
	public static void main(String[] argv) {
		double doubleMatA[][] = {
				{ 19., 13., 10., 10., 13., -17. },
				{ 13., 13., 10., 10., -11., 13. },
				{ 10., 10., 10., -2., 10., 10. },
				{ 10., 10., -2., 10., 10., 10. },
				{ 13., -11., 10., 10., 13., 13. },
				{ -17., 13., 10., 10., 13., 10. }
			};
		Matrix matA = new Matrix(doubleMatA).times((double)1/12);
		
		double doubleVectorB[] = { 0., 0., 0., 0., 0., 0. };
		for (int i = 0; i < 6; i++) {
			doubleVectorB[i] = (Math.random() * 10) - 5;
		}
		Matrix vectorB = normalize(Matrix.random(6, 1));
		
		vectorB = computeVonMisesIteration(matA, vectorB, 1000);
		System.out.println(getEigenvalue(matA, vectorB));
		vectorB.print(8, 6);
		
		vectorB = rotation.times(vectorB);	
		vectorB = computeVonMisesIteration(matA, vectorB, 10);
		System.out.println(getEigenvalue(matA, vectorB));
		vectorB.print(8, 6);
	}
	static double getEigenvalue(Matrix matrix, Matrix eigenvector){
		Matrix resultVector = matrix.times(eigenvector);
		double result = 0;
		for(int i = 0; i<6; i++){
			result += resultVector.get(i, 0)/eigenvector.get(i,0);
		}
		return result/6;
	}
	static Matrix normalize(Matrix vector){
		return vector.times(1/vector.normF());
	}
	static Matrix computeVonMisesIteration(Matrix matrix, Matrix vector, int iterations){
		for(int i = 0; i<iterations; i++){
			vector = matrix.times(vector);
			if(i%100 == 0)
				vector = normalize(vector);
		}
		if(iterations-1 % 100 == 0)
			return vector;
		else
			return normalize(vector);
	}
	
}
