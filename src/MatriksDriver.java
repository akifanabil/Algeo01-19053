import java.util.Scanner;

public class MatriksDriver {

    static int pil1;
    static int pil2;
    static int pil3;
    static int pil4;

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        Matriks M = new Matriks();

        MainMenu();
        while (pil1!=6){
            if (pil1==1){
                // Penyelesaian SPL
                Menu1();
                System.out.println("1");
            } else if (pil1==2){
                // Determinan
                Menu2();
                if (pil3==1) {
                    M.BacaMatriksPersegi();
                    System.out.println("Determinan dengan Metode Reduksi Baris : " + Determinan1(M));
                }
                else if (pil3==2) {
                    System.out.println("Determinan 2"); //Belum
                }
                else {
                    System.out.println("Input salah. Harap masukan kembali input sesuai nomor menu pilihan.");
                }
            } else if (pil1==3){
                // Mencari Invers
                Menu3();
                if (pil4==1){
                    // Dengan menggunakan metode Gauss Jordan
                    M.BacaMatriksPersegi();
                    M.InversMatriks1(); 
                } else if (pil4==2){
                    // Dengan menggunakan perkalian 1/determinan dan Adjoin matriks
                    M.BacaMatriksPersegi();
                    M.InversMatriks2();
                } else{
                    System.out.print("Input salah. Harap masukan kembali input sesuai nomor menu pilihan.");
                    Menu3();
                }
            } else if (pil1==4){
                //Interpolasi polinom
                System.out.println("4");
            } else if (pil1==5){
                //Regresi linear berganda
                M.regresilinearganda();
            } else{
                System.out.println("Harap masukkan pilihan sesuai nomor menu pilihan");
            }
            MainMenu();
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Terimakasih");
    }

    public static void MainMenu(){
        // Method untuk menampilkan list menu utama
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println("PROGRAM MATRIKS DAN APLIKASI MATRIKS\n");

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