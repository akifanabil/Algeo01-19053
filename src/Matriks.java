import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;

public class Matriks {

    int BrsEf,KolEf;
    int BrsMin = 0;
    int KolMin = 0;
    int Tukar = 0;
    boolean nosolutionspl;
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

    public void TukarBaris(int i1, int i2) {
    	int j;
    	float temp;
    	
    	// Menukar baris
    	for (j=GetFirstIdxKol(); j<=GetLastIdxKol(); j++) {
    		temp = Elmt(i1, j);
    		SetElmt(i1, j, Elmt(i2, j));
    		SetElmt(i2, j, temp);
        }
    }
    
    public void ForwardPhase() {
    	int i, j, k, imax;
    	float max, faktor, temp;
    	
    	for (k=GetFirstIdxKol(); k<=GetLastIdxKol(); k++) {
            
            // Menukar baris jika elemen pertama adalah 0
            if (Elmt(GetFirstIdxBrs(), GetFirstIdxKol()) == 0) {
                i = GetFirstIdxBrs()+1;
                while (Elmt(i, GetFirstIdxKol()) == 0 ) {
                    i++;
                }
                TukarBaris(GetFirstIdxBrs(), i);
                Tukar++;
            }
    		
    		// Melakukan reduksi
    		for (i=k+1; i<=GetLastIdxBrs(); i++) {
    			faktor = Elmt(i, k)/Elmt(k, k);
    			for (j=GetFirstIdxKol(); j<=GetLastIdxKol(); j++) {
    				temp = Elmt(i, j) - (Elmt(k, j) * faktor);
    				SetElmt(i, j, temp);
    			// SetElmt(i, k, 0);
    			}
    		}
    	}
    }
    
    public float[] BackSubs() {
        int i, j,k;
        boolean eksak=true;

    	// Membuat array solusi dan inisialisasi value
    	float[] solusi = new float[KolEf-1];
    	for (j=GetFirstIdxKol(); j<=GetLastIdxKol()-1; j++) {
    		solusi[j] = -999;
        }
        
    	// Menghitung solusi
    	for (i=GetLastIdxBrs(); i>=GetFirstIdxBrs(); i--) {
            j=GetFirstIdxKol();
            while (Elmt(i,j)==0 && j<GetLastIdxKol()){
                j++;
            }
            // Ditemukan leading 1 atau j=GetLastIdxKol()

            if (Elmt(i, j)==1 && j<GetLastIdxKol()){
                k=GetLastIdxKol()-1;
                solusi[j]=Elmt(i, GetLastIdxKol());
                while (k>j && eksak){
                    if (Elmt(i,k)!=0 && solusi[k]==-999){
                        eksak = false;
                        solusi[j]=-999;
                    } else{
                        solusi[j]-=solusi[k]*Elmt(i, k);
                        k--;
                    }
                }

            } else{
                // kolom pertama hingga kolom kedua terakhir = 0
                if (Elmt(i, GetLastIdxKol())!=0){ //Tidak ada solusi
                    nosolutionspl=true;
                    break;
                }
            }
    	}
    	return solusi;
    }

    public void printsolusispl(float[] solusi){
        int i;
        boolean isparam=false;
        if (nosolutionspl){
            System.out.println("\nSistem Persamaan Linear tidak memiliki penyelesaian.");
        } else{
            i = 0;
            while (i<=GetLastIdxKol()-1 && isparam==false){
                if (solusi[i]==-999){
                    isparam=true;
                } else{
                    i++;
                }
            }
            if (isparam){
                System.out.println("\nSistem Persamaan Linear memiliki banyak solusi sebagai berikut : ");
                printsolusiparametrik(solusi);
            } else{
                System.out.println("\nSistem Persamaan Linear memilikisebuah solusi unik.");
                printsolusisplunik(solusi);
            }
        }
    }

    public void printsolusisplunik(float[] solusi){
        for (int i=0;i<=GetLastIdxKol()-1;i++){
            System.out.println("x" + (i+1) + " = " + solusi[i]);
        }
    }

    public void printsolusiparametrik(float[] solusi){
        String[] solusipar = new String[KolEf-1];

        // Mencari leading 1 mulai dari baris terakhir
        int i = GetLastIdxBrs();
        int j;
        char cc ='t';
        int idemptypar=GetLastIdxKol()-1; //indeks dari elemen terakhir array solusi parametriks yg akan diisi
        int niterasi;
        float tempsolusi;

        while (i>=GetFirstIdxBrs()){
            j = GetFirstIdxKol();
            while (Elmt(i,j)==0 && j<=GetLastIdxKol()-1){
                j++;
            }
            //Elmt(i,j) == 1 or j=GetLastIdxKol()-1
            if (Elmt(i,j)==1){
                if (solusi[j]==-999){
                    //Solusi parametrik
                    niterasi=0;
                    for (int k=idemptypar;k>=j+1;k--){
                        if (solusi[k]==-999){
                            solusipar[k]= cc+"";
                            cc = (char) (((int) cc)-1);
                            niterasi++;
                        }
                    }

                    tempsolusi = Elmt(i,GetLastIdxKol());
                    //mencari solusi eksak
                    for (int k=j+1;k<GetLastIdxKol();k++){
                        if (solusi[k]!=-999){
                            tempsolusi-=solusi[k]*Elmt(i,k);
                        }
                    }
                    if (tempsolusi!=0){
                        solusipar[j]=tempsolusi+"";
                    } else{
                        solusipar[j]="";
                    }
                    for (int k=j+1;k<GetLastIdxKol();k++){
                        if (solusi[k]==-999){
                            if (Elmt(i,k)>0){
                                solusipar[j]+="-"+Elmt(i,k)+solusipar[k];
                            } else if (Elmt(i,k)<0){
                                solusipar[j]+="+"+(-1*Elmt(i,k))+solusipar[k];
                            }
                        }
                    }
                    idemptypar-=niterasi;
                }
            }
            i--;
        }

        for (i=0;i<=GetLastIdxKol()-1;i++){
            if (solusi[i]==-999){
                System.out.println("x" + (i+1) + " = " + solusipar[i]);
            } else{
                System.out.println("x" + (i+1) + " = " + solusi[i]);
            }
        }

        cc = (char) (((int) cc)+1);
        System.out.print("Dengan "+ cc);

        for (i=(int) cc+1;i<=(int) 't';i++){
            System.out.print(","+((char) i));
        }
        System.out.println(" bilangan real.");
    }
    
    public void LeadingOne(){
        int i, j, k;

        i = GetFirstIdxBrs();
        j = GetFirstIdxKol();
        while (i<=GetLastIdxBrs() && j<=GetLastIdxKol()) {
            if (Elmt(i, j) != 0) {
                for (k=GetFirstIdxKol(); k<=GetLastIdxKol(); k++) {
                    SetElmt(i, k, Elmt(i, k)/Elmt(i, j));
                }
                i++;
            }
            j++;
        }
    }

    public void splGauss(){
        float[] solusi;

        ForwardPhase();
        LeadingOne();
        solusi = BackSubs();
        printsolusispl(solusi);
    }

    public void BackwardPhase() {
        int i, j, k, l;
        float faktor, temp;

        i=GetLastIdxBrs();
        j=GetFirstIdxKol();
        while (i>=GetFirstIdxBrs() && j<=GetLastIdxKol()) {
            if (Elmt(i, j) != 0) {
                // Melakukan reduksi
                for (k=i-1; k>=GetFirstIdxBrs(); k--) {
                    faktor = Elmt(k, j)/Elmt(i, j); // udh aman kalau nilai utama udh 1
                    for (l=GetFirstIdxKol(); l<=GetLastIdxKol(); l++) {
                        temp = Elmt(k, l) - (Elmt(i, l)*faktor);
                        SetElmt(k, l, temp);
                    }
                }
                i--;
            }  
            j++;
        }
    }

    public void splGaussJordan(){
        float[] solusi;

        ForwardPhase();
        LeadingOne();
        BackwardPhase();
        solusi = BackSubs();
        printsolusispl(solusi);
    }

    public void splMatriksBalikan() {
        
    }

    public void splCramer() {}

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
        Aug.ForwardPhase();
        Aug.BackwardPhase();

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

    public float Determinan1() {
        // Mencari Determinan dengan menggunakan metode Reduksi Baris
        float Det=1;

        // Mengubah matriks menjadi matriks segitiga atas
        this.ForwardPhase();

        // Mengalikan diagonal utama matriks untuk mencari determinan
        for (int i=this.GetFirstIdxBrs();i<=this.GetLastIdxBrs();i++) {
            Det *= this.Elmt(i,i);
        }

        // Mengalikan determinan dengan (-1) sebanyak Tukar, yaitu jumlah pertukaran baris
        // saat mengubah matriks menjadi segitiga atas
        Det *= Math.pow(-1,Tukar);

        // Mengembalikan nilai tukar ke 0, untuk proses berikutnya
        Tukar = 0;
        return Det;
    }

    public float Determinan2() {
        // Mencari Determinan dengan menggunakan metode Ekspansi Kofaktor
        float Det=0;
        int Baris1=GetFirstIdxBrs();

        // Menbentuk matriks minor -> mencari minor -> matriks kofaktor
        int Cek1=GetFirstIdxBrs(),Cek2=GetFirstIdxKol();
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
                                Minor.SetElmt(Cek1, Cek2, this.Elmt(k, l));
                                Cek2++;
                            }
                        }
                        Cek1++;
                        Cek2=GetFirstIdxKol();
                    }
                }
                Kofaktor.SetElmt(i,j,((i+j)%2==0)? Minor.Determinan1():-1*Minor.Determinan1());
                Cek1=GetFirstIdxBrs();
            }
        }

        // Mencari Determinan dengan baris pertama sebagai acuan
        for (int j=GetFirstIdxKol();j<=GetLastIdxKol();j++) {
            Det += this.Elmt(Baris1,j)*Kofaktor.Elmt(Baris1,j);
        }

        return Det;
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
                Kofaktor.SetElmt(i,j,((i+j)%2==0)? Minor.Determinan1():-1*Minor.Determinan1());
                im=GetFirstIdxBrs();
            }
        }
        Kofaktor.TulisMatriks();

        // Membuat matriks invers dari pembagian matriks adjoin dengan determinan matriks
        Matriks Adjoin = new Matriks(this.BrsEf,this.KolEf);
        Matriks Invers = new Matriks(this.BrsEf,this.KolEf);
        Adjoin = Kofaktor.Transpose();

        float determinan=Determinan1();

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
