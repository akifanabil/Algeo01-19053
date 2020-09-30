import java.util.Scanner;

import javax.swing.JOptionPane;

public class MatriksDriver {

    static int pil1;
    static int pil2;
    static int pil3;
    static int pil4;
    static int pilinput;

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        Matriks M = new Matriks();

        MainMenu();
        while (pil1!=6){
            jenisInput();

            //Memperoleh input berdasar pilihan metode input yang dipilih
            if (pilinput==1){
                if (pil1==1){
                    M.BacaMatriks();
                } else if (pil1==2 || pil1==3){
                    M.BacaMatriksPersegi();
                } else if (pil1==4) {
                    System.out.println("");
                    System.out.println("Petunjuk:");
                    System.out.println("Jumlah baris : derajat + 1");
                    System.out.println("Jumlah kolom : 2");
                    System.out.println("");
                    M.BacaMatriks();
                }
            } else{
                // Input dibaca dari file
                M.BacaFileMatriks("matriks.txt");
            }

    
            // Menjalankan menu pilihan
            if (pil1==1){
                // Penyelesaian SPL

                //Menampilkan menu pilihan metode penyelesaian SPL
                Menu1();

                //Melakukan pemrosesan untuk mencari penyelesaian SPL sesuai input pilihan metode penyelesaian SPL
                if (pil2==1){
                    // Metode eliminasi Gauss
                    M.splGauss();
                } else if(pil2==2){
                    // Metode eliminasi Gauss-Jordan
                    M.ForwardPhase();
                    M.TulisMatriks();
                    M.LeadingOne();
                    M.TulisMatriks();
                    M.BackwardPhase();
                    M.TulisMatriks();
                } else if (pil2==3){
                    // Metode matriks balikan
                    M.splMatriksBalikan();
                } else if (pil2==4){
                    // Kaidah Cramer
                    
                } else{

                }

            } else if (pil1==2){
                // Mencari Determinan

                //Menampilkan menu pilihan metode pencarian nilai determinan
                Menu2();
                if (pil3==1) {
                    System.out.println("Determinan dengan Metode Reduksi Baris : " + M.Determinan1());
                }
                else {
                    System.out.println("Determinan dengan Metode Ekspansi Kofaktor : " + M.Determinan2());
                }

            } else if (pil1==3){
                // Mencari Invers

                // Menampilkan pilihan menu metode pencarian invers
                Menu3();

                //Melakukan proses pencarian invers matriks berdasarkan pilihan metode
                if (pil4==1){
                    // Dengan menggunakan metode Gauss Jordan
                    M.InversMatriks1();
                } else if (pil4==2){
                    M.InversMatriks2();
                }

            } else if (pil1==4){
                //Interpolasi polinom

                M.Interpolasi();

            } else{
                //Regresi linear berganda
                M.regresilinearganda();
            }
            MainMenu();
        }
        
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println("Terimakasih!");
        System.exit(0);
    }

    static void validateinput(int input,int min,int max){
        String sinput;
        while (input<min || input>max){
            sinput = JOptionPane.showInputDialog("Invalid Input. Masukkan angka pilihan menu ("+min+"-"+max+") : ");
            input = Integer.parseInt(sinput);
        }
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
        String spil1 = JOptionPane.showInputDialog("Pilihan Menu (1-6) : ");
        pil1 = Integer.parseInt(spil1);
        validateinput(pil1, 1, 6);
    }

    public static void Menu1(){
        //method menampilkan list menu spl
        System.out.println("\nPilihan metode peyelesaian SPL : ");
        System.out.println("1. Metode Eliminasi Gauss");
        System.out.println("2. Metode Eliminasi Gauss-Jordan");
        System.out.println("3. Metode Matriks Balikan");
        System.out.println("4. Kaidah Cramer");
        String spil2 = JOptionPane.showInputDialog("Pilihan Menu (1-4) : ");
        pil2 = Integer.parseInt(spil2);
        validateinput(pil2,1,4);
    }

    public static void Menu2(){
        //method menampilkan list determinan
        System.out.println("\nPilihan metode pencarian Determinan : ");
        System.out.println("1. Metode Reduksi Baris");
        System.out.println("2. Metode Ekspansi Kofaktor");
        String spil3 = JOptionPane.showInputDialog("Pilihan Menu (1-2) : ");
        pil3 = Integer.parseInt(spil3);
        validateinput(pil3,1,2);

    }

    public static void Menu3(){
        // method menampilkan list invers
        System.out.println("\nPilihan metode pencarian Invers Matriks : ");
        System.out.println("1. Metode Reduksi Baris");
        System.out.println("2. Menggunakan Determinan dan Adjoin");
        String spil4 = JOptionPane.showInputDialog("Pilihan Menu (1-2) : ");
        pil4 = Integer.parseInt(spil4);
        validateinput(pil4,1,2);
    }

    public static void jenisInput(){
        // menampilkan list pilihan metode input (keyboard/file)
        System.out.println("\nPilihan metode input data matriks : ");
        System.out.println("1. Keyboard");
        System.out.println("2. File txt");
        String spilinput = JOptionPane.showInputDialog("Pilihan Menu (1-2) : ");
        pilinput= Integer.parseInt(spilinput);
        validateinput(pilinput,1,2);
    }
}