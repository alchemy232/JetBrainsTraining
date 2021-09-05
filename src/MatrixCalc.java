import java.util.Scanner;

public class MatrixCalc {
    public static Scanner input = new Scanner(System.in);
    public static void menu(){
        System.out.println("1. Add matrices");
        System.out.println("2. Multiply matrix by a constant");
        System.out.println("3. Multiply matrices");
        System.out.println("4. Transpose matrix");
        System.out.println("5. Calculate a determinant");
        System.out.println("6. Inverse matrix");
        System.out.println("0. Exit");
        System.out.print("Your choice: > ");
    }
    public static void subMenu(){
        System.out.println("1. Main diagonal");
        System.out.println("2. Side diagonal");
        System.out.println("3. Vertical line");
        System.out.println("4. Horizontal line");
    }
    public static class Matrix{
        public double[][] mtr;
        final int a,b;
        public Matrix(double[][] mtr) {
            this.mtr = mtr;
            this.a = mtr.length+1;
            this.b = mtr[0].length+1;
        }
        public Matrix(int a, int b) {
            this.a = a;
            this.b = b;
            this.mtr = new double[a][b];
            for (int i = 0; i < a; i++) {
                for (int j = 0; j < b; j++) {
                    mtr[i][j]= (Math.random()*99);
                }
            }
        }

        public double[][] getMtr() {
            return mtr;
        }

        public int getA() {
            return a;
        }
        public int getB() {
            return b;
        }
        public double getValue(int a, int b) {
            return mtr[a][b];
        }

        public Matrix addMatrix (Matrix input) {
            double out[][] = new double[a-1][b-1];
            for (int i = 0; i < out.length; i++) {
                for (int j = 0; j < out[0].length; j++) {
                    out[i][j]= mtr[i][j]+ input.getValue(i,j);
                }
            }
            return new Matrix(out);
        }
        public Matrix multByConstant (double constant) {
            double fl[][] = new double[mtr.length][mtr[0].length];
            for (int i = 0; i < mtr.length; i++) {
                for (int j = 0; j < mtr[0].length; j++) {
                    fl[i][j]=mtr[i][j]*constant;
                }
            }
            return new Matrix(fl);
        }
        public Matrix multyplyMatrix(Matrix input) {
            double fl[][] = new double[this.mtr.length][input.getB() - 1];
            for (int i = 0; i < fl.length; i++) {
                for (int j = 0; j < input.getB() - 1; j++) {

                    for (int m = 0; m < mtr[0].length; m++) {
                        fl[i][j] += mtr[i][m] * input.getValue(m, j);
                    }

                }
            }
            return new Matrix(fl);
        }
        public void transposeMainDiagonal (){
            for (int i = 0; i < mtr.length; i++) {
                for (int j = 0; j < i; j++) {
//                    if (i==j) {
//                        continue; // заглушка на главную диагональ
//                    } else {
                    double buffer = mtr[i][j];
                    mtr[i][j] = mtr[j][i];
                    mtr[j][i] = buffer;
//                    }
                }
            }
        }
        public void transposeSideDiagonal (){
            for (int i = 0; i < mtr.length; i++) {
                for (int j = mtr[0].length-1-i; j >= 0; j--) {
                    double buffer = mtr[i][j];
                    mtr[i][j] = mtr[mtr.length-1-j][mtr.length-1-i];
                    mtr[mtr.length-1-j][mtr.length-1-i] = buffer;
                }
            }
        }
        public void transposeVertical (){
            for (int i = 0; i < mtr.length; i++) {
                for (int j = 0; j< mtr[0].length/2; j++) {
                    double buffer = mtr[i][j];
                    mtr[i][j] = mtr[i][mtr.length-j-1];
                    mtr[i][mtr.length-j-1] = buffer;
                }
            }
        }
        public void transposeHorizontal (){
            for (int i = 0; i < mtr.length/2; i++) {
                for (int j = 0; j< mtr[0].length; j++) {
                    double buffer = mtr[i][j];
                    mtr[i][j] = mtr[mtr.length-i-1][j];
                    mtr[mtr.length-i-1][j] = buffer;
                }
            }
        }
        public static double[][] getMinor(double[][] mtr, int row ,int col){

            double[][] result = new double[mtr.length-1][mtr[0].length-1];
            for (int i = 0; i < result.length; i++) {
                int ii = i;
                if(i>=row) {
                    ii=i+1;
                }
                for (int j = 0; j < result[0].length; j++) {
                    int jj= j;
                    if (j>=col) {
                        jj=j+1;
                    }
                    result[i][j]=mtr[ii][jj];
                }
            }
            return result;
        } //выпиливаем строку и столбец
        public static double getDet(double[][] arr) {
            double buf=0;
            int sign=1;
            for (int i = 0; i < arr.length; i++) {
                if (arr.length==1) {
                    buf+=arr[0][0];
                } else  {
//                    buf+=sign*arr[0][i]*getDet(getMinor(arr,0,i)) ;
                    buf+=Math.pow(-1,i)*arr[0][i]*getDet(getMinor(arr,0,i)) ;
                    sign = -sign;
                }
            }

            return buf;
        }
        public double getDetetrminant() {
            double buf=0;
            int sign=1;
            for (int i = 0; i < mtr.length; i++) {
                if (mtr.length==1) {
                    buf+=mtr[0][0];
                } else  {
                    buf+=sign*mtr[0][i]*getDet(getMinor(mtr,0,i)) ;
                    sign = -sign;
                }
            }

            return buf;
        }
        public boolean isDeterminantNull(){
            return (this.getDetetrminant()==0);
        }
        public static Matrix inverse(Matrix mtr){
            double buf[][]=new double[mtr.getA()-1][mtr.getB()-1];
            int sign=1;
            double detInverse;
            detInverse = (1/getDet(mtr.getMtr()));

            for (int i = 0; i < mtr.getA() - 1; i++) {
                for (int j = 0; j < mtr.getB()-1; j++) {
                    buf[i][j]=Math.pow(-1,i+j)*detInverse*getDet(getMinor(mtr.getMtr(), i,j));
                    sign=-sign;
                }
            }
            return new Matrix(buf);
        }
        public void printMatrix(){
            for (int i = 0; i < mtr.length; i++) {
                for (int j = 0; j < mtr[0].length; j++) {
//                    System.out.print(mtr[i][j] + " ");
                    System.out.printf("%3.2f ", mtr[i][j]);
                }
                System.out.println();
            }
        }
    }

    public static double[][] fillFl(int a, int b){
        double[][] fl =new double[a][b];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                fl[i][j] = input.nextDouble();
            }
        } return fl;
    }
    public static Matrix fillMatrix(){

        int a = input.nextInt();
        int b = input.nextInt();
        System.out.println("Enter first matrix:");
        return new Matrix(fillFl(a,b));
    }
    public static Matrix fillMatrix2(){

        int a = input.nextInt();
        int b = input.nextInt();
        System.out.println("Enter second matrix:");
        return new Matrix(fillFl(a,b));
    }
    public static void main(String[] args) {
        menu();
        int chose = input.nextInt();
        while (chose!=0) {
            switch (chose) {
                case 1:
                    System.out.println("Enter size of first matrix: > ");
                    Matrix myMatrixA = fillMatrix();
                    System.out.println("Enter size of second matrix: > ");
                    Matrix myMatrixB = fillMatrix2();
                    if (myMatrixB.getA()==myMatrixA.getA() && myMatrixB.getB()==myMatrixA.getB()) {
                        System.out.println("The result is:");
                        myMatrixA.addMatrix(myMatrixB).printMatrix();
                    } else {
                        System.out.println("The operation cannot be performed.");
                    }

                    break;
                case 2:
                    System.out.println("Enter size of first matrix: > ");
                    Matrix myMatrixD = fillMatrix();
                    System.out.println("Enter constant: > ");
                    double con = input.nextDouble();
                    System.out.println("The result is:");
                    myMatrixD.multByConstant(con).printMatrix();

                    break;
                case 3:
                    System.out.println("Enter size of first matrix: > ");
                    Matrix myMatrixE = fillMatrix();
                    Matrix myMatrixF = fillMatrix2();
                    if (myMatrixE.getB()==myMatrixF.getA()) {
                        System.out.println("The result is:");
                        myMatrixE.multyplyMatrix(myMatrixF).printMatrix();
                    } else {
                        System.out.println("The operation cannot be performed.");
                    }
                    break;
                case 4:
                    subMenu();
//                    int ch = input.nextInt();
                    switch (input.nextInt()) {
                        case 1:
                            System.out.println("Enter matrix size: >");
                            Matrix myMatrixH =fillMatrix();
                            myMatrixH.transposeMainDiagonal();
                            myMatrixH.printMatrix();
                            break;
                        case 2:
                            System.out.println("Enter matrix size: >");
                            Matrix myMatrixJ = fillMatrix();
                            myMatrixJ.transposeSideDiagonal();
                            myMatrixJ.printMatrix();
                            break;
                        case 3:
                            System.out.println("Enter matrix size: >");
                            Matrix myMatrixK = fillMatrix();
                            myMatrixK.transposeVertical();
                            myMatrixK.printMatrix();
                            break;
                        case 4:
                            System.out.println("Enter matrix size: >");
                            Matrix myMatrixL = fillMatrix();
                            myMatrixL.transposeHorizontal();
                            myMatrixL.printMatrix();
                            break;
                        default: break;
                    }
                    break;
                case 5:
                    System.out.println("Enter size of matrix: > ");
                    Matrix myMatrixDE = fillMatrix();
//                    System.out.println(myMatrixDE.getDet(myMatrixDE.mtr));
                    System.out.println(myMatrixDE.getDetetrminant());
                    break;
                case 6:
                    System.out.println("Enter matrix size: >");
                    Matrix myMatrixAA = fillMatrix();
                    if (!myMatrixAA.isDeterminantNull()) {
                        Matrix hardMartix = Matrix.inverse(myMatrixAA);
                        hardMartix.transposeMainDiagonal();
                        hardMartix.printMatrix();
                    } else {
                        System.out.println("This matrix doesn't have an inverse.");
                    }

                    break;
                default:
                    menu();

            }
            menu();
            chose=input.nextInt();
        }
    }
}
