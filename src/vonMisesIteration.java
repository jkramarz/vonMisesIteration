import Jama.Matrix;

public class vonMisesIteration {
	static Matrix rotation;
	static Matrix identity;
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
		double ide[][] = {
				{1, 0, 0, 0, 0, 0},
				{0, 1, 0, 0, 0, 0},
				{0, 0, 1, 0, 0, 0},
				{0, 0, 0, 1, 0, 0},
				{0, 0, 0, 0, 1, 0},
				{0, 0, 0, 0, 0, 1},
		};
		identity = new Matrix(ide);
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
		
		vectorB = normalize(Matrix.random(6, 1));	
		vectorB = computeRayleighIteration(matA, vectorB, 1000);
		System.out.println(getEigenvalue(matA, vectorB));
		vectorB.print(8, 6);
	}
	
	static Matrix computeVonMisesIteration(Matrix matrix, Matrix vector, int iterations){
		for(int i = 0; i<iterations; i++){
			vector = normalize(matrix.times(vector));
		}
		return vector;
	}
	
	
	static double rayleighQuotient(Matrix matrix, Matrix vector){
		Matrix quotient = vector.transpose().times(vector).solveTranspose(vector.transpose().times(matrix).times(vector));
		return quotient.get(0, 0);
	}
	static Matrix computeRayleighIteration(Matrix matrix, Matrix vector, int iterations){
		for(int i = 0; i<iterations; i++){
			vector = normalize(
					matrix.minus(
							identity.times(rayleighQuotient(matrix, vector))
						).inverse().
						times(vector)
					);
		}
		return vector;
	}
	
	static Matrix normalize(Matrix vector){
		return vector.times(1/vector.normF()); //Frobenius norm
	}
	
	static double getEigenvalue(Matrix matrix, Matrix eigenvector){
		Matrix resultVector = matrix.times(eigenvector);
		double result = 0;
		for(int i = 0; i<resultVector.getRowDimension(); i++){
			result += resultVector.get(i, 0)/eigenvector.get(i,0);
		}
		return result/resultVector.getRowDimension();
	}
}
