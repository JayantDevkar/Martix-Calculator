import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;
public class Determinants
{
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the filename: ");
        String filename = input.nextLine();
        File file = new File(filename);
        Scanner inputFile = new Scanner(file);
        if (!inputFile.hasNextDouble()) {
            System.out.println("Empty File");
        }
        int a_row = inputFile.nextInt();
        int a_col = inputFile.nextInt();
        double[][] A = new double[a_row][a_col];
        for (int i = 0; i < a_row; i++) {
            for (int j = 0; j < a_col; j++) {
                A[i][j] = inputFile.nextDouble();
            }
        }
//        Testing
        double[][]C = ref(  A,0,1.0);

    }
    private static double[][] ref(double[][] A,int pivot,double d)
    {
//        BASE CASE
        if(pivot >= A.length)
        {
            System.out.println("Determinant:-");
            DecimalFormat numberFormat = new DecimalFormat("#.00");
            if(d!=0.0)
            {
                System.out.println(numberFormat.format(d));
            }
            else
            {
                System.out.println(d);
            }
            return A;
        }
//    ***** turning pivot into 1 ******
        if(A[pivot][pivot]!=1&&A[pivot][pivot]!=0)
        {
            double factor = 1.0/A[pivot][pivot];
            div(A, pivot, 1.0 / A[pivot][pivot]);
            d*=1/factor;
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
            }
            else
            {
                int ind=-1;
                for(int i=pivot+1;i<A.length;i++)
                {
                    if(A[i][pivot]!=0)
                    {
                        ind=i;
                        break;
                    }
                }
                if(ind!=-1)
                {
                    double factor=A[ind][pivot];
                    div(A, ind, 1.0 / A[ind][pivot]);
                    d*=factor;
                    add(A, pivot, ind, 1);
                }
                else
                {
                    d=0;
                    pivot=A.length;
                }
            }
        }
//        ******************************
//        Zeroing the bottom
        for(int i=pivot+1;i<A.length;i++)
        {
            double factor= -(A[i][pivot]);
            if(A[i][pivot]!=0)
            {
                add(A, i, pivot, -(A[i][pivot]));
            }
        }
//        Zeroing the top
        pivot++;
        return ref(A,pivot,d);
    }



    //    Works
    private static void add(double[][]A, int pivot, int row, double mul) {
        for (int j = 0; j < A[0].length; j++) {
            DecimalFormat numberFormat = new DecimalFormat("#.0000");
            double factor = Double.parseDouble(numberFormat.format(mul * A[row][j]));
//            System.out.println(factor);
//            System.out.println(A[pivot][j]);
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
