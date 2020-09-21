import java.util.Scanner;

public class MatriksDriver {

    static int pil1;
    static int pil2;
    static int pil3;
    static int pil4;

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        Matriks M = new Matriks();

        System.out.println("---------------------------------------------------------------------");
        System.out.println("PROGRAM MATRIKS DAN APLIKASI MATRIKS\n");

        MainMenu();

        if (pil1==1){
            // Penyelesaian SPL
            Menu1();
            System.out.println("1");
        } else if (pil1==2){
            // Determinan
            Menu2();
            System.out.println("2");
        } else if (pil1==3){
            // Mencari Invers
            Menu3();
            if (pil4==1){
                M.BacaMatriks();
                M.InversMatriks1();
            } else if (pil4==2){
                System.out.print("a");
            } else{
                System.out.print("Input salah. Harap masukan kembali input sesuai nomor menu pilihan.");
                Menu3();
            }
        } else if (pil1==4){
            //Interpolasi polinom
            System.out.println("4");
        } else if (pil1==5){
            //Regresi linear berganda
            System.out.println("5");
        } else{
            System.out.println("Good Bye!");
            System.out.println("------------------------------------------------------------------------------");
        }
    }

    public static void MainMenu(){
        // Method untuk menampilkan list menu utama
        System.out.println("Menu :");
        System.out.println("1. Sistem Persamaan Linear");
        System.out.println("2. Determinan");
        System.out.println("3. Matriks Balikan");
        System.out.println("4. Interpolasi Polinom");
        System.out.println("5. Regresi Linear Berganda");
        System.out.println("6. Keluar");
        System.out.print("\nMasukkan nomor pilihan menu : ");
        pil1 = scanner.nextInt();
    }

    public static void Menu1(){
        //method menampilkan list menu spl
        System.out.println("Pilihan metode peyelesaian SPL : ");
        System.out.println("1. Metode Eliminasi Gauss");
        System.out.println("2. Metode Eliminasi Gauss-Jordan");
        System.out.println("3. Metode Matriks Balikan");
        System.out.println("4. Kaidah Cramer");
        System.out.print("\nMasukkan nomor pilihan menu : ");
        pil2 = scanner.nextInt();
    }

    public static void Menu2(){
        //method menampilkan list determinan
        System.out.println("Pilihan metode pencarian Determinan : ");
        System.out.println("1. Metode Reduksi Baris");
        System.out.println("2. Metode Ekspansi Kofaktor");
        System.out.print("\nMasukkan nomor pilihan menu : ");
        pil3 = scanner.nextInt();
    }

    public static void Menu3(){
        // method menampilkan list invers
        System.out.println("Pilihan metode pencarian Invers Matriks : ");
        System.out.println("1. Metode Reduksi Baris");
        System.out.println("2. Menggunakan Determinan dan Adjoin");
        System.out.print("\nMasukkan nomor pilihan menu : ");
        pil4 = scanner.nextInt();
    }


}