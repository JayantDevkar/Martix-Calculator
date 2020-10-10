import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the filename: ");
        String filename = input.nextLine();
        File file = new File(filename);
        Scanner inputFile = new Scanner(file);
        if (!inputFile.hasNextInt()) {
            System.out.println("Empty File");
        }
        int a_row = inputFile.nextInt();
        int a_col = inputFile.nextInt();
        double[][] A = new double[a_row][a_col];
        for (int i = 0; i < a_row; i++) {
            for (int j = 0; j < a_col; j++) {
                A[i][j] = inputFile.nextInt();
            }
        }
        int b_row = inputFile.nextInt();
        int b_col = inputFile.nextInt();
        double[][] B = new double[b_row][b_col];

        for (int i = 0; i < b_row; i++) {
            for (int j = 0; j < b_col; j++) {
                B[i][j] = inputFile.nextInt();
            }
        }

//        Testing
        double[][]C = ref(  A,0,B);
        System.out.println("Matrix after Operations:-");
        for (double[] doubles : C) {
            for (int j = 0; j < C[0].length; j++) {
                System.out.print(doubles[j] + " ");
            }
            System.out.println();
        }
        System.out.println("------------------");
    }
    private static double[][] ref(double[][] A,int pivot,double[][] B)
    {
//        BASE CASE
        if(pivot == A.length)
        {
            System.out.println("------------------");
            if(B[0].length!=1)
            {
                System.out.println("Inverse Matrix:-");
            }
            else
            {
                System.out.println("Solution Matrix:-");
            }
            for (double[] doubles : B) {
                for (int j = 0; j < B[0].length; j++) {
                    System.out.print(doubles[j] + " ");
                }
                System.out.println();
            }
            System.out.println("------------------");
            return A;
        }
//    ***** turning pivot into 1 ******
        if(A[pivot][pivot]!=1&&A[pivot][pivot]!=0)
        {
            double factor = 1.0/A[pivot][pivot];
            div(A, pivot, 1.0 / A[pivot][pivot]);
            div(B, pivot, factor);
        }
        else if(A[pivot][pivot]==0)
        {
            int flag=0;
            int row=-1;
            for(int r=pivot+1;r<A.length;r++)
            {
                if (A[r][pivot] == 1) {
                    row = r;
                    flag = 1;
                    break;
                }
                if (A[r][pivot] == -1) {
                    row = r;
                    flag = -1;
                    break;
                }
            }
            if(flag!=0)
            {
                add(A, pivot, row, flag);
                add(B,pivot,row,flag);
            }
            else
            {
                int ind=-1;
                for(int i=pivot+1;i<A.length;i++)
                {
                    if(A[i][pivot]>0)
                    {
                        ind=i;
                        break;
                    }
                }
                if(ind!=-1)
                {
                    double factor=A[ind][pivot];
                    div(A, ind, 1.0 / A[ind][pivot]);
                    div(B,ind,1.0/factor);
                    add(A, pivot, ind, 1);
                    add(B,pivot,ind,1);
                }
                else
                {
                    if(B[0].length == 1)
                    {
                        if(B[pivot][0]==0.0)
                        {
                            System.out.println("------------------");
                            System.out.println("Infinite Solutions");
                            System.out.println("------------------");
                            System.out.println("Bound variables are:-");
                            for(int i =0;i<A.length;i++)
                            {
                                for(int j=0;j<A[0].length;j++)
                                {
                                    if(A[i][j]==1&&i==j)
                                    {
                                        System.out.print("x"+(j+1)+" ");
                                    }
                                }
                            }
                            System.out.println("variables");
                            System.out.println("------------------");
                            System.out.println("Solution matrix:");
                            for (double[] doubles : B) {
                                for (int j = 0; j < B[0].length; j++) {
                                    System.out.print(doubles[j] + " ");
                                }
                                System.out.println();
                            }
                            System.out.println("------------------");
                            return A;
                        }
                    }
                    System.out.println("---------------");
                    System.out.println("Solution Doesn't Exist");
                    System.out.println("---------------");
                    System.out.println("R-H-S matrix:");
                    for (double[] doubles : B) {
                        for (int j = 0; j < B[0].length; j++) {
                            System.out.print(doubles[j] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println("------------------");
                    return A;
                }
            }
        }
//        ******************************
//        Zeroing the bottom
        for(int i=pivot+1;i<A.length;i++)
        {
            double factor= -(A[i][pivot]);
            if(A[i][pivot]!=0) {
                add(A, i, pivot, -(A[i][pivot]));
                add(B, i, pivot, factor);
            }
        }
//        Zeroing the top
        for(int i=0;i<pivot;i++)
        {
            double factor= -(A[i][pivot]);
            if(A[i][pivot]!=0) {
                add(A, i, pivot, -(A[i][pivot]));
                add(B, i, pivot, factor);
            }
        }
        pivot++;
        return ref(A,pivot,B);
    }
//    Works
    private static void add(double[][]A, int pivot, int row, double mul)
    {
        for (int j = 0; j < A[0].length; j++) {
            DecimalFormat numberFormat = new DecimalFormat("#.0000");
            double factor = Double.parseDouble(numberFormat.format(mul * A[row][j]));
            if (Math.abs(A[pivot][j] + factor) < 0.0001) {
                A[pivot][j] = 0.0;
            } else {
                A[pivot][j] += mul * A[row][j];
            }
        }
    }
    private static void div(double[][]A, int pivot, double mul)
    {
        for(int j=0;j<A[0].length;j++)
        {
            A[pivot][j]=mul*A[pivot][j];
        }
    }
}