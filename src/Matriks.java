import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Matriks {

    int BrsEf,KolEf;
    int BrsMin = 0;
    int KolMin= 0;
    float[][] M;

    Scanner scanner = new Scanner(System.in);

    //Konstruktor Matriks M
    public Matriks(){
        //Membentuk matriks kosong
        BrsEf=0;
        KolEf=0;
    }

    public Matriks(int nbrs,int nklm)
    {
        //Membentuk matriks dengan ukuran baris efektif nbrs dan kolom efektif nklm
        M = new float[nbrs+BrsMin][nklm+KolMin];
        BrsEf = nbrs;
        KolEf = nklm;
    }

    public int GetFirstIdxBrs()
    {
        return BrsMin;
    }

    public int GetLastIdxBrs()
    {
        return this.BrsEf+this.GetFirstIdxBrs()-1;
    }

    public int GetFirstIdxKol()
    {
        return KolMin;
    }

    public int GetLastIdxKol()
    {
        return this.KolEf+this.GetFirstIdxKol()-1;
    }

    public void BacaMatriks()
    {
        // Membaca jumlah baris dan kolom efektif matriks serta mengisi matriks. 
        int i,j;
        System.out.print("Masukkan banyak baris matriks : ");
        this.BrsEf = scanner.nextInt();
        while (BrsEf<=0)
        {
            System.out.print("Input tidak valid. Harap masukan banyak baris dengan integer > 0 : ");
            this.BrsEf = scanner.nextInt();
        }
        System.out.print("Masukkan banyak kolom matriks : ");
        this.KolEf = scanner.nextInt();
        while (KolEf<=0){
            System.out.print("Input tidak valid. Harap masukan banyak kolom dengan integer > 0 : ");
            this.KolEf = scanner.nextInt();
        }
        M = new float [BrsEf][KolEf];
        for (i=this.GetFirstIdxBrs();i<=this.GetLastIdxBrs();i++)
        {
            for (j=this.GetFirstIdxKol();j<=this.GetLastIdxKol();j++)
            {
                System.out.print("Masukkan Elemen Matriks Baris "+(i+1)+" Kolom "+(j+1)+" : ");
                this.M[i][j] = scanner.nextFloat();
            }
        }
    }

    public void BacaMatriksPersegi()
    {
        // Membaca jumlah ukuran efektif matriks persegi serta mengisi matriks.  Tidak menerima ukuran efektif baris/kolom (Akan divalidasi terlebih dahulu)
        int i,j;
        System.out.print("Masukkan ukuran matriks persegi : ");
        this.BrsEf = scanner.nextInt();
        while (BrsEf<=0){
            System.out.print("Input tidak valid. Harap memasukkan ukuran matriks persegi dengan integer >= 0 : ");
            this.BrsEf = scanner.nextInt();
        }
        this.KolEf = this.BrsEf;
        M = new float [BrsEf][KolEf];
        for (i=GetFirstIdxBrs();i<=GetLastIdxBrs();i++)
        {
            for (j=GetFirstIdxKol();j<=GetLastIdxKol();j++)
            {
                System.out.print("Masukkan Elemen Matriks Baris "+(i+1)+" Kolom "+(j+1)+" : ");
                SetElmt(i,j,scanner.nextFloat());
            }
        }
    }

    public void BacaFileMatriks(String file){
        try {
            File f = new File(file);
            Scanner Reader = new Scanner(f);
            this.BrsEf=0;
            while (Reader.hasNextLine()) {
              this.BrsEf++;
              Reader.nextLine();
            }
            Reader.close();
            
            Reader = new Scanner(f);
            int nel=0;
            while (Reader.hasNextFloat()) {
                nel++;
                Reader.nextFloat();
            }
            this.KolEf=nel/this.BrsEf;
            Reader.close();

            M = new float [BrsEf][KolEf];
            Reader = new Scanner(f);
            for (int i=GetFirstIdxBrs();i<=GetLastIdxBrs();i++){
                for (int j=GetFirstIdxKol();j<=GetLastIdxKol();j++){
                    SetElmt(i, j, Reader.nextFloat());
                }
            }
            Reader.close();
        } catch (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public float Elmt(int idxbrs, int idxkol){
        // Mendapat elemen matriks M baris id ke [idxbrs][idxkol]
        return this.M[idxbrs][idxkol];
    }

    public void SetElmt(int idbrs,int idklm, float val){
        this.M[idbrs][idklm] = val;
    }

    public void TulisMatriks() {
        int i,j;
        for (i=GetFirstIdxBrs();i<=GetLastIdxBrs();i++)
        {
            for (j=GetFirstIdxKol();j<=GetLastIdxKol();j++)
            {
                System.out.printf("%.3f    ",this.M[i][j]);
            }
            System.out.println("");
        }
    }

    public void TukarBaris(Matriks M, int i1, int i2) {
    	int j;
    	float temp;
    	
    	// Menukar baris
    	for (j=M.GetFirstIdxKol(); j<=M.GetLastIdxKol(); j++) {
    		temp = M.Elmt(i1, j);
    		M.SetElmt(i1, j, M.Elmt(i2, j));
    		M.SetElmt(i2, j, temp);
        }
    }
    
    public void ForwardPhase(Matriks M) {
    	int i, j, k, imax;
    	float max, faktor, temp;
    	
    	for (k=M.GetFirstIdxKol(); k<=M.GetLastIdxKol(); k++) {
            
            // Mencari nilai maksimum di kolom
            imax = k;
    		max = M.Elmt(M.GetFirstIdxBrs(), k);
    		for (i=k+1; i<=M.GetLastIdxBrs(); i++) {
    			if (M.Elmt(i, k) > max) 
    				max = M.Elmt(i, k);
    				imax = i;
    		}
    		
    		// Menukar Baris dengan Baris yang ada Nilai Max
    		if (imax != k)
    			TukarBaris(M, k, imax);
            
            // Melakukan reduksi
    		for (i=k+1; i<=M.GetLastIdxBrs(); i++) {
    			faktor = M.Elmt(i, k)/M.Elmt(k, k);
    			for (j=M.GetFirstIdxKol(); j<=M.GetLastIdxKol(); j++) {
    				temp = M.Elmt(i, j) - (M.Elmt(k, j) * faktor);
    				M.SetElmt(i, j, temp);
    			M.SetElmt(i, k, 0);
    			}
    		}
    	}
    }
    
    public float[] BackSubs(Matriks M) {
        int i, j;

    	// Membuat array solusi dan inisialisasi value
    	float[] solusi = new float[M.GetLastIdxKol()];
    	for (j=M.GetFirstIdxKol(); j<=GetLastIdxKol(); j++) {
    		solusi[j] = -999;
    	}
    	
    	// Menghitung solusi
    	for (i=M.GetLastIdxBrs(); i>=M.GetFirstIdxBrs(); i--) {
    		solusi[i] = M.Elmt(i, M.GetLastIdxKol());
    		for (j=i+1; j<=M.GetLastIdxBrs(); j++) {
    			solusi[i] -= (M.Elmt(i, j) * solusi[j]);
    		}
    		solusi[i] = solusi[i]/M.Elmt(i, i);
    	}
    	return solusi;
    }
    
    public void printSolusi(float[] array) {
        int panjang = array.length ;
        int i;

        for (i=0; i<panjang; i++) {
            if (array[i] != -999) {
                System.out.println("x" + panjang + " = " + array[i] );
            }
        }
    }

    public void splGauss(Matriks M){
        float[] solusi;

        ForwardPhase(M);
        solusi = BackSubs(M);
        printSolusi(solusi);
    }

    public void BackwardPhase(Matriks M) {
        int i, j, k, l;
        float faktor, temp;

        i=M.GetLastIdxBrs();
        j=M.GetFirstIdxKol();
        while (i>=M.GetFirstIdxBrs() && j<=M.GetLastIdxKol()) {
            if (M.Elmt(i, j) != 0) {
                // Membuat nilai utama 1
                for (k=M.GetFirstIdxBrs(); k<=M.GetLastIdxBrs(); k++) {
                    M.SetElmt(i, k, M.Elmt(i, k)/M.Elmt(i, j));
                }
                // Membuat nilai lain 0
                for (k=i-1; k>=M.GetFirstIdxBrs(); k--) {
                    faktor = M.Elmt(k, j)/M.Elmt(i, j); // udh aman kalau nilai utama udh 1
                    for (l=M.GetFirstIdxKol(); l<=M.GetLastIdxKol(); l++) {
                        temp = M.Elmt(k, l) - (M.Elmt(i, l)*faktor);
                        M.SetElmt(k, l, temp);
                    }
                }
                i++;
            }  
            j++;
        }
        // Menghitung solusi 

    }

    public void splGaussJordan(Matriks M){
        ForwardPhase(M);
        BackwardPhase(M);
    }


    public void InversMatriks1(){
        // Mendeklarasikan Matriks Aug yang merupakan matriks augmented matriks M dengan matriks satuannya
        Matriks Aug = new Matriks(this.BrsEf,this.KolEf+this.BrsEf);
        
        // Membentuk Matriks Augmented dari Matriks M dengan matriks satuannya

        // Menyalin terlebih dahulu elemen matriks M
        int i,j; 
        for(i= GetFirstIdxBrs();i<=GetLastIdxBrs();i++)
        {
            for (j=this.GetFirstIdxKol();j<=this.GetLastIdxKol();j++)
            {
                Aug.SetElmt(i, j, this.Elmt(i, j));
            }
        }
        // Menambah elemen elemen matriks satuan
        for(i=GetFirstIdxBrs();i<=GetLastIdxBrs();i++)
        {
            for (j=this.GetLastIdxKol()+1;j<=Aug.GetLastIdxKol();j++)
            {
                if (i==j-KolEf){
                    Aug.SetElmt(i, j, 1);
                } else{
                    Aug.SetElmt(i, j, 0);
                }
            }
        }
        // Aug.TulisMatriks();

        //Melakukan operasi OBE dengan metode Gauss-Jordan
        ForwardPhase(Aug);

        // Aug.TulisMatriks();
        //Ambil matriks dri kolom this.getlastkol()+1 sampe Aug.getlastkol()
        Matriks Invers = new Matriks(Aug.GetLastIdxBrs()+1,Aug.GetLastIdxBrs()+1);
        if (Aug.Elmt(Aug.GetLastIdxBrs(),Aug.GetLastIdxBrs()) == 1){
            // Punya invers
            for (i=Aug.GetFirstIdxBrs();i<=Aug.GetLastIdxBrs();i++){
                for (j=Aug.GetLastIdxBrs()+1;j<=Aug.GetLastIdxKol();j++){
                    Invers.SetElmt(i, j-Aug.GetLastIdxBrs()-1, Aug.Elmt(i, j));
                }
            }
            Invers.TulisMatriks();
        } else{
            // Tidak punya invers
            System.out.println("Matriks tidak memiliki invers");
        }
    }

    public float determinan(){
        return 2;
        
    }

    public void InversMatriks2(){
        // Mencari Invers Matriks dengan menggunakan Determinan dan Adjoin

        // Cari adjoin
            // Cari minor -> kofaktor -> adjoin
        int im=GetFirstIdxBrs(),jm=GetFirstIdxKol();
        Matriks Kofaktor = new Matriks(this.BrsEf,this.KolEf);
        Matriks Minor=new Matriks(this.BrsEf-1,this.KolEf-1);
        for (int i=GetFirstIdxBrs();i<=GetLastIdxBrs();i++){
            for (int j=GetFirstIdxKol();j<=GetLastIdxKol();j++){
                for (int k=GetFirstIdxBrs();k<=GetLastIdxBrs();k++)
                {
                    if (k!=i){
                        for (int l=GetFirstIdxKol();l<=GetLastIdxKol();l++)
                        {
                            if (j!=l){
                                Minor.SetElmt(im, jm, this.Elmt(k, l));
                                jm++;
                            }
                        }
                        im++;
                        jm=GetFirstIdxKol();
                    }
                }
                Kofaktor.SetElmt(i,j,((i+j)%2==0)? Minor.determinan():-1*Minor.determinan());
                im=GetFirstIdxBrs();
            }
        }
        Kofaktor.TulisMatriks();

        // Membuat matriks invers dari pembagian matriks adjoin dengan determinan matriks
        Matriks Adjoin = new Matriks(this.BrsEf,this.KolEf);
        Matriks Invers = new Matriks(this.BrsEf,this.KolEf);
        Adjoin = Kofaktor.Transpose();

        float determinan=determinan();

        if (determinan !=0){
            for (int i=GetFirstIdxBrs();i<=GetLastIdxBrs();i++){
                for (int j=GetFirstIdxKol();j<=GetLastIdxKol();j++){
                    Invers.SetElmt(i, j, Adjoin.Elmt(i, j)/determinan);
                }
            }
            Invers.TulisMatriks();
        } else{
            System.out.println("Matriks tidak memiliki invers.");
        }
    }


    public Matriks Transpose(){
        Matriks TransposeM = new Matriks(this.KolEf,this.BrsEf);
        for(int i= TransposeM.GetFirstIdxBrs();i<=TransposeM.GetLastIdxBrs();i++)
        {
            for (int j=TransposeM.GetFirstIdxKol();j<=TransposeM.GetLastIdxKol();j++)
            {
                TransposeM.SetElmt(i, j, this.Elmt(j,i));
            }
        }
        return TransposeM;
    }
    
    public void regresilinearganda(){
        //Membaca banyak elemen variabel independen x
        System.out.println("Input nilai variabel peubah x dan variabel terkait y");
        System.out.print("Masukkan banyak variabel peubah x : ");
        int N = scanner.nextInt();
        Matriks xy = new Matriks(N,N+1);
        for (int i=xy.GetFirstIdxBrs();i<=xy.GetLastIdxBrs();i++){
            for (int j=xy.GetFirstIdxKol();j<xy.GetLastIdxKol();j++){
                System.out.print("Masukkan nilai x"+(j+1)+(i+1)+" : ");
                xy.SetElmt(i, j, scanner.nextFloat());
            }
            System.out.print("Masukkan nilai y"+(i+1)+"  : ");
            xy.SetElmt(i, xy.GetLastIdxKol(), scanner.nextFloat());
        }


        Matriks splxy = new Matriks(N+1,N+2);

        // Set elemen baris pertama kolom pertama dengan N
        splxy.SetElmt(splxy.GetFirstIdxBrs(), splxy.GetFirstIdxKol(), N);

        // Set elemen baris pertama kolom kedua hingga akhir dan kolom pertama baris kedua hingga akhir
        for (int i=splxy.GetFirstIdxKol()+1;i<=splxy.GetLastIdxKol();i++){
            float sum = 0;
            for (int j=xy.GetFirstIdxBrs();j<=xy.GetLastIdxBrs();j++){
                sum+=xy.Elmt(j, i-1);
            }
            splxy.SetElmt(splxy.GetFirstIdxBrs(), i, sum);
            if (i<=splxy.GetLastIdxBrs()){
                splxy.SetElmt(i, GetFirstIdxKol(), sum);
            }
        }

        // Set elemen mulai baris kedua dan kolom kedua hingga akhir
        for (int i=splxy.GetFirstIdxBrs()+1;i<=splxy.GetLastIdxBrs();i++){
            for (int j=splxy.GetFirstIdxKol()+1;j<=splxy.GetLastIdxKol();j++){
                float sum = 0;
                for (int k=xy.GetFirstIdxBrs();k<=xy.GetLastIdxBrs();k++){
                    sum+=xy.Elmt(k, i-1)*xy.Elmt(k, j-1);
                }
                splxy.SetElmt(i, j, sum);
            }
        }

        // Meminta input xk yaitu nilai nilai x yang ingin ditaksir nilai fungsinya
        Matriks xk = new Matriks(1,N);
        System.out.println("\nInput nilai-nilai x yang ingin ditaksir nilai fungsinya");
        for (int j=xk.GetFirstIdxKol();j<=xk.GetLastIdxKol();j++){
            System.out.print("Masukkan nilai x"+(j+1)+" : ");
            xk.SetElmt(xk.GetFirstIdxBrs(), j, scanner.nextFloat());
        }

        xk.TulisMatriks();

        //Melakukan penyelesaian spl dengan metode gauss


        Matriks hasilspl = new Matriks(1,N+1);

        System.out.println("\nPersamaan umum regresi linear: \nyi =");
        for (int i=splxy.GetFirstIdxBrs();i<splxy.GetLastIdxBrs();i++){
            System.out.println(/*elemen matriks penyelesaian splxy*/);
        }

        // Menuliskan hasil taksiran fungsi
        System.out.println("\nHasil taksiran fungsi : ");
        float sum=hasilspl.Elmt(hasilspl.GetFirstIdxBrs(), hasilspl.GetFirstIdxKol());
        for (int i=hasilspl.GetFirstIdxKol()+1;i<=GetLastIdxKol();i++){
            sum+=hasilspl.Elmt(hasilspl.GetFirstIdxBrs(), i)*xk.Elmt(xk.GetFirstIdxBrs(), i-1);
        }
        System.out.println(sum);
    }
}