import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.math.RoundingMode;
import java.math.BigDecimal;

public class Matriks {

    int BrsEf,KolEf;
    int BrsMin = 0;
    int KolMin = 0;
    int Tukar = 0;
    boolean nosolutionspl;
    boolean solusiparametrik;
    boolean singular;
    double[][] M;

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
        M = new double[nbrs+BrsMin][nklm+KolMin];
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
        M = new double [BrsEf][KolEf];
        for (i=this.GetFirstIdxBrs();i<=this.GetLastIdxBrs();i++)
        {
            for (j=this.GetFirstIdxKol();j<=this.GetLastIdxKol();j++)
            {
                System.out.print("Masukkan Elemen Matriks Baris "+(i+1)+" Kolom "+(j+1)+" : ");
                this.M[i][j] = rounding(scanner.nextDouble());
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
        M = new double [BrsEf][KolEf];
        for (i=GetFirstIdxBrs();i<=GetLastIdxBrs();i++)
        {
            for (j=GetFirstIdxKol();j<=GetLastIdxKol();j++)
            {
                System.out.print("Masukkan Elemen Matriks Baris "+(i+1)+" Kolom "+(j+1)+" : ");
                SetElmt(i,j,scanner.nextDouble());
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
            Scanner Readaline = new Scanner(Reader.nextLine());
            this.KolEf=0;
            while (Readaline.hasNextDouble()) {
                this.KolEf++;
                Readaline.nextDouble();
            }
            
            Readaline.close();
            Reader.close();

            System.out.println(BrsEf);
            System.out.println(KolEf);

            this.M = new double [this.BrsEf][this.KolEf];
            Reader = new Scanner(f);

            for(int i = 0; i <= BrsEf-1; ++i)
            {
                for(int j = 0; j <= KolEf-1;++j)
                {
                    SetElmt(i,j,rounding(Reader.nextDouble()));
                }
            }
            Reader.close();
        } catch (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public double Elmt(int idxbrs, int idxkol){
        // Mendapat elemen matriks M baris id ke [idxbrs][idxkol]
        return this.M[idxbrs][idxkol];
    }

    public void SetElmt(int idbrs,int idklm, double val){
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
    	double temp;
    	
    	// Menukar baris
    	for (j=GetFirstIdxKol(); j<=GetLastIdxKol(); j++) {
    		temp = Elmt(i1, j);
    		SetElmt(i1, j, Elmt(i2, j));
    		SetElmt(i2, j, temp);
        }
    }
    
    public void ForwardPhase() {
    	int i=this.GetFirstIdxBrs(),j, k;
        double faktor, temp;
        boolean foundnot0;
    	
    	for (k=this.GetFirstIdxKol(); k<this.GetLastIdxKol(); k++) {
            if (i<=this.GetLastIdxBrs()){
                // Menukar baris jika elemen pertama adalah 0
                foundnot0 = true;
                if (this.Elmt(i, k) == 0) {
                    foundnot0=false;
                    j=i+1;
                    while (j<=this.GetLastIdxBrs() && !foundnot0){
                        if (this.Elmt(j, k)!=0){
                            foundnot0=true;
                        } else{
                            j++;
                        }
                    }
                    if (foundnot0){
                        this.TukarBaris(i, j);
                        Tukar++;
                    }
                }
                
                if (foundnot0){ //Melakukan reduksi
                    for (j=i+1; j<=this.GetLastIdxBrs(); j++) {
                        faktor = this.Elmt(j, k)/this.Elmt(i, k);
                        for (int l=this.GetFirstIdxKol(); l<=this.GetLastIdxKol(); l++) {
                            // SetElmt(i, k, 0);
                            temp = this.Elmt(j, l) - (this.Elmt(i, l) * faktor);
                            this.SetElmt(j, l, rounding(temp));
                        }
                    }
                    i++;
                }
            }
        }
    }
    
    public double[] BackSubs() {
        int i, j,k;
        boolean eksak;

    	// Membuat array solusi dan inisialisasi value
    	double[] solusi = new double[KolEf-1];
    	for (j=GetFirstIdxKol(); j<=GetLastIdxKol()-1; j++) {
    		solusi[j] = -999;
        }
        
    	// Menghitung solusi
    	for (i=GetLastIdxBrs(); i>=GetFirstIdxBrs(); i--) {
            eksak=true;
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
                        solusiparametrik = true;
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

    public double rounding(double num) {
        BigDecimal bd = new BigDecimal(num).setScale(5, RoundingMode.DOWN);
        double out = bd.doubleValue();
        return out;
    }

    public void printsolusispl(double[] solusi){
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

    public void printsolusisplunik(double[] solusi){
        for (int i=0;i<=GetLastIdxKol()-1;i++){
            System.out.println("x" + (i+1) + " = " + rounding(solusi[i]));
        }
    }

    public void printsolusiparametrik(double[] solusi){

        // Mencari leading 1 mulai dari baris terakhir
        int i;
        int j=this.GetFirstIdxKol();
        char cc ='t';
        int idemptypar=this.GetLastIdxKol()-1; //indeks dari elemen terakhir array solusi parametrik yg akan diisi

        //Membuat sebuah matriks yg menyimpan nilai konstanta dan koefisien masing-masing variabel
        //Baris menyatakan variabel (Baris 1 : x1, Baris 2 : x2, dst)
        //Kolom menyatakan nilai nilai integer persamaan parametriks variabel pd baris yg bersangkutan dimana Kolom 1 : konstanta, (cth) Kolom 2 : koefisien parametrik p , Kolom 3 : koefisien q , dst
        Matriks koef=new Matriks(this.GetLastIdxKol(),this.GetLastIdxKol());


        for (i=this.GetLastIdxBrs();i>=this.GetFirstIdxBrs();i--){
            j = this.GetFirstIdxKol();
            while (this.Elmt(i,j)==0 && j<=this.GetLastIdxKol()-1){
                j++;
            }
            //Elmt(i,j) == 1 or j=GetLastIdxKol()-1
            if (Elmt(i,j)==1){
                if (solusi[j]==-999){
                    //Merupakan Solusi parametrik

                    // 
                    for (int k=GetLastIdxKol()-1;k>=j+1;k--){
                        if (solusi[k]==-999){
                            koef.SetElmt(k, idemptypar, 1); //Set elemen matriks koef baris ke k kolom ke idemptypar dgn 1 menandakan koefisien dari cc
                            // solusipar[k]= cc+"";
                            idemptypar--;
                            solusi[k]=(double) 999; //Penanda bahwa solusi parametrik telah ditemukan
                        }
                    }

                    // Menjumlahkan
                    koef.SetElmt(j,0, this.Elmt(i,this.GetLastIdxKol()));
                    for (int k=j+1;k<GetLastIdxKol();k++){
                        if (Elmt(i,k)!=0){
                            if (solusi[k]!=999 && solusi[k]!=-999){
                                //solusi eksak -> penjumlahan solusi eksak
                                koef.SetElmt(j, 0, koef.Elmt(j,0)-(solusi[k]*Elmt(i,k)));
                            }
                            if (solusi[k]==999){
                                //Menjumlahkan pada koefisien parametrik yang bersesuaian
                                System.out.println(i);
                                System.out.println(j);
                                for (int l=0;l<=koef.GetLastIdxKol();l++){
                                    koef.SetElmt(j, l,koef.Elmt(j,l) - (Elmt(i,k)*koef.Elmt(k,l)));
                                }
                            }
                        }
                    }
                    solusi[j]=(double) 999; //Penanda bahwa persamaan parametrik untuk solusi indeks j telah ditemukan


                } else if (solusi[j]!=-999 && solusi[j]!=999){
                    // Elmt(i,j) memiliki solusi eksak
                    koef.SetElmt(j,0,solusi[j]);
                }
            }
        }
        j--;
        
        while (j>=GetFirstIdxKol()){
            koef.SetElmt(j, idemptypar, 1);
            idemptypar--;
            j--;
        }
        // char[] usedcc = new char[this.GetLastIdxKol()-1-idemptypar];
        koef.TulisMatriks();

        boolean first;
        for (i = koef.GetFirstIdxBrs();i<=koef.GetLastIdxBrs();i++){
            first=true;
            System.out.print("x"+(i+1)+" = ");
            if (koef.isbaris0(i)){
                System.out.print(0);
            } else{
                for (j=koef.GetFirstIdxKol();j<=koef.GetLastIdxKol();j++){
                    if (first && koef.Elmt(i,j)!=0){
                        System.out.print(koef.Elmt(i, j));
                        if (j!=0){
                            System.out.print(((char) ((int) cc - koef.KolEf +j+1)));
                        }
                        first=false;
                    } else if (koef.Elmt(i, j)<0 && !first){
                        System.out.print("-"+(-1*koef.Elmt(i, j)));
                        if (j!=0){
                            System.out.print(((char) ((int) cc - koef.KolEf +j+1)));
                        }
                    } else if (koef.Elmt(i,j)>0 && !first){
                        System.out.print("+"+(koef.Elmt(i, j)));
                        if (j!=0){
                            System.out.print(((char) ((int) cc - koef.KolEf +j+1)));
                        }
                    }
                }
            }
            System.out.println("");
        }

        // Menulis keterangan variabel parametrik adalah bilangan real
        char firstcc= (char) ((int) 't' + (koef.GetFirstIdxKol()-idemptypar));
        System.out.print("Dengan "+ firstcc);
            for (i=(int) firstcc+1;i<=(int) 't';i++){
                System.out.print(","+((char) i));
            }
            System.out.println(" bilangan real.");
    }

    public boolean isbaris0(int i){
        // Mengembalikan true jika semua elemen baris suatu matriks adalah 0
        boolean isbrs0=true;
        int j=GetFirstIdxKol();
        while (j<=GetLastIdxKol() && isbrs0){
            if (Elmt(i,j)!=0){
                isbrs0=false;
            } else{
                j++;
            }
        }
        return isbrs0;
    }



        //             if (konssolusi!=0){
        //                 solusipar[j]=konssolusi+"";
        //             } else{
        //                 solusipar[j]="";
        //             }

        //             for (int k=j+1;k<GetLastIdxKol();k++){
        //                 if (solusi[k]==-999){
        //                     if (Elmt(i,k)>0){
        //                         SetElmt(i,k,Elmt(i, k));

        //                         solusipar[j]+="-"+Elmt(i,k)+solusipar[k];
        //                     } else if (Elmt(i,k)<0){
        //                         solusipar[j]+="+"+(-1*Elmt(i,k))+solusipar[k];
        //                     }
        //                 }
        //             }


        // for (i=0;i<=GetLastIdxKol()-1;i++){
        //     if (solusi[i]==-999){
        //         System.out.println("x" + (i+1) + " = " + solusipar[i]);
        //     } else{
        //         System.out.println("x" + (i+1) + " = " + solusi[i]);
        //     }
        // }

        // cc = (char) (((int) cc)+1);
        // System.out.print("Dengan "+ cc);

        // for (i=(int) cc+1;i<=(int) 't';i++){
        //     System.out.print(","+((char) i));
        // }
        // System.out.println(" bilangan real.");
    
    public void LeadingOne(){
        int i, j=this.GetFirstIdxKol(),k;
        Double pembagi;

        this.TulisMatriks();

        for (i=this.GetFirstIdxBrs();i<=this.GetLastIdxBrs();i++){
            while (j<this.GetLastIdxKol() && this.Elmt(i,j)==0){
                j++;
            }
            if (this.Elmt(i, j)!=0.0){
                pembagi = this.Elmt(i,j);
                for (k=j;k<=this.GetLastIdxKol();k++){
                    this.SetElmt(i,k,this.Elmt(i,k)/pembagi);
                }
            }
        }
    }

    public void splGauss(){
        double[] solusi;

        ForwardPhase();
        LeadingOne();
        solusi = BackSubs();
        printsolusispl(solusi);
    }

    public void BackwardPhase() {
        int i, j, k, l;
        Double faktor, temp;

        i=this.GetLastIdxBrs();
        j=this.GetFirstIdxKol();
        while (i>=this.GetFirstIdxBrs() && j<=this.GetLastIdxKol()) {
            if (this.Elmt(i, j) != 0) {
                // Melakukan reduksi
                for (k=GetFirstIdxBrs(); k<i; k++) {
                    faktor = this.Elmt(k, j)/this.Elmt(i, j); // udh aman kalau nilai utama udh 1
                    for (l=j; l<=this.GetLastIdxKol(); l++) {
                        temp = this.Elmt(k, l) - (this.Elmt(i, l)*faktor);
                        this.SetElmt(k, l, temp);
                    }
                }
                i--;
                j=GetFirstIdxKol();
            } 
            else{
                j++;
            }
        }
    }

    public void splGaussJordan(){
        double[] solusi;

        ForwardPhase();
        LeadingOne();
        BackwardPhase();
        solusi = BackSubs();
        printsolusispl(solusi);
    }

    public void splMatriksBalikan() {
        int i, j; 
        double temp;
        boolean homogen = true;

        // Dimisalkan SPL berbentuk AX=B maka dideklarasikan matriks berikut
        Matriks A = new Matriks(this.BrsEf,this.KolEf-1);
        Matriks B = new Matriks(this.BrsEf, 1);
        double[] X = new double[this.BrsEf];

        // Menyalin elemen ke matriks A
        for(i=A.GetFirstIdxBrs();i<=A.GetLastIdxBrs();i++)
        {
            for (j=A.GetFirstIdxKol();j<=A.GetLastIdxKol();j++)
            {
                A.SetElmt(i, j, this.Elmt(i, j));
            }
        }

        // Menyalin elemen ke matriks B
        for(i=this.GetFirstIdxBrs();i<=this.GetLastIdxBrs();i++)
        {
            B.SetElmt(i, 0, this.Elmt(i, this.GetLastIdxKol()));
        }

        // Pengecekan apakah SPL homogen AX=0 atau B=0
        for (i=B.GetFirstIdxBrs(); i<=B.GetLastIdxBrs(); i++) {
            if(B.Elmt(i, 0) != 0) {
                homogen = false;
                break;
            }
        }

        A.TulisMatriks();

        // A^(-1)*A*x = A^(-1)*B maka dicari invers A
        if (A.Determinan2() == 0) {
            // A tidak punya invers
            if (homogen) {
                // SPL memiliki solusi non-trivial
                System.out.println("\nSistem Persamaan Linear tidak memiliki balikan dan memiliki solusi non-trivial");
                // Menghitung solusi menggunakan metode lain
                splGauss();
            }
            else {
                // SPL tidak memiliki solusi yang tunggal (unik)
                System.out.println("\nSistem Persamaan Linear tidak memiliki balikan dan tidak memiliki solusi yang unik");
                splGauss();
            }
            // System.out.println("\nSistem Persamaan Linear tidak memiliki penyelesaian.");
        }
        else { 
            // A memiliki invers
            A=InversMatriks1();
            // Perkalian invers A dengan B
            for (i=A.GetFirstIdxBrs(); i<=A.GetLastIdxBrs(); i++) {
                temp = 0;
                for (j=A.GetFirstIdxKol(); j<=A.GetLastIdxKol(); j++) {
                    temp += A.Elmt(i, j) * B.Elmt(j, 0);
                }
                // X.SetElmt(j, 1, temp);
                X[i] = temp;
            }
            if (homogen) {
                // SPL memiliki solusi trivial
                System.out.println("\nSistem Persamaan Linear memiliki balikan dan memiliki solusi trivial");
                // Menghitung solusi menggunakan metode lain
                printsolusisplunik(X);
            }
            else {
                // SPL memiliki solusi yang tunggal (unik)
                System.out.println("\nSistem Persamaan Linear memiliki balikan dan memiliki solusi yang unik");
                printsolusisplunik(X);
            }
        }

    }

    public void splCramer() {
        int i, j, k;
        double det, detMod;
        boolean homogen = true;

        // Dimisalkan SPL berbentuk AX=B maka dideklarasikan matriks berikut
        Matriks A = new Matriks(this.BrsEf,this.KolEf-1);
        Matriks AMod = new Matriks(this.BrsEf,this.KolEf-1);
        Matriks B = new Matriks(this.BrsEf, 1);
        double[] X = new double[this.BrsEf];

        // Menyalin elemen ke matriks A
        for(i=A.GetFirstIdxBrs();i<=A.GetLastIdxBrs();i++) {
            for (j=A.GetFirstIdxKol();j<=A.GetLastIdxKol();j++) {
                A.SetElmt(i, j, this.Elmt(i, j));
            }
        }

        // Menyalin elemen ke matriks AMod
        for(i=AMod.GetFirstIdxBrs();i<=AMod.GetLastIdxBrs();i++){
            for (j = AMod.GetFirstIdxKol(); j<=AMod.GetLastIdxKol(); j++) {
                AMod.SetElmt(i, j, this.Elmt(i, j)); //eror disini huhuhu :(
            }
        }

        // Menyalin elemen ke matriks B
        for(i=this.GetFirstIdxBrs();i<=this.GetLastIdxBrs();i++) {
            B.SetElmt(i, 0, this.Elmt(i, this.GetLastIdxKol()));
        }

        // Pengecekan apakah SPL homogen AX=0 atau B=0
        for (i=B.GetFirstIdxBrs(); i<=B.GetLastIdxBrs(); i++) {
            if(B.Elmt(i, 0) != 0) {
                homogen = false;
                break;
            }
        }

        // Menghitung determinan matriks A
        det = A.Determinan2();

        if (det == 0) {
            // A tidak punya invers
            if (homogen) {
                // SPL memiliki solusi non-trivial
                System.out.println("\nSistem Persamaan Linear tidak memiliki balikan, homogen, dan memiliki solusi non-trivial");
                // Menghitung solusi menggunakan metode lain
                splGauss();
            }
            else {
                // SPL tidak memiliki solusi yang tunggal (unik)
                System.out.println("\nSistem Persamaan Linear tidak memiliki balikan, tidak homogen, dan tidak memiliki solusi yang unik");
                splGauss();
            }
            // System.out.println("\nSistem Persamaan Linear tidak memiliki penyelesaian.");
        }
        else { 
            // A memiliki invers
            for (j=A.GetFirstIdxKol(); j<=A.GetLastIdxKol(); j++) {
                detMod = 0;
    
                // Mengganti elemen A kolom j dengan elemen di matriks B
                for (i=A.GetFirstIdxBrs(); i<=A.GetLastIdxBrs(); i++) {
                    AMod.SetElmt(i, j, B.Elmt(i, 0));
                }
    
                // Menghitung determinan 
                detMod = AMod.Determinan2();
    
                // Menghitung nilai X
                if (detMod!= 0) {
                    X[j] = detMod/det;
                }
                else {
                    X[j] = 0;
                }

                // Menyalin elemen ke matriks AMod
                for(i=AMod.GetFirstIdxBrs();i<=AMod.GetLastIdxBrs();i++){
                    for (k = AMod.GetFirstIdxKol(); k<=AMod.GetLastIdxKol(); k++) {
                        AMod.SetElmt(i, k, this.Elmt(i, k)); //eror disini huhuhu :(
                    }
                }
            }

            if (homogen) {
                // SPL memiliki solusi trivial
                System.out.println("\nSistem Persamaan Linear memiliki balikan, homogen, dan memiliki solusi trivial");
                // Menghitung solusi menggunakan metode lain
                printsolusisplunik(X);
            }
            else {
                // SPL memiliki solusi yang tunggal (unik)
                System.out.println("\nSistem Persamaan Linear memiliki balikan, tidak homogen, dan memiliki solusi yang unik");
                printsolusisplunik(X);
            }
        }

        for (j=A.GetFirstIdxKol(); j<=A.GetLastIdxKol(); j++) {
            detMod = 0;

            // Mengganti elemen A kolom j dengan elemen di matriks B
            for (i=A.GetFirstIdxBrs(); i<=A.GetLastIdxBrs(); i++) {
                AMod.SetElmt(i, j, B.Elmt(i, 0));
            }

            // Menghitung determinan 
            detMod = AMod.Determinan2();

            // Menghitung nilai X
            X[j] = detMod/det;
        }
    }

    public Matriks InversMatriks1(){
        // Mendeklarasikan Matriks Aug yang merupakan matriks augmented matriks M dengan matriks satuannya
        Matriks Aug = new Matriks(this.BrsEf,2*this.BrsEf);
        
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
            for (j=Aug.GetLastIdxBrs()+1;j<=Aug.GetLastIdxKol();j++)
            {
                if (i==j-Aug.BrsEf){
                    Aug.SetElmt(i, j, 1);
                } else{
                    Aug.SetElmt(i, j, 0);
                }
            }
        }
        //Melakukan operasi OBE dengan metode Gauss-Jordan
        Aug.ForwardPhase();
        Aug.LeadingOne();
        Aug.BackwardPhase();

        Matriks Invers = new Matriks(Aug.GetLastIdxBrs()+1,Aug.GetLastIdxBrs()+1);
        if (Aug.Elmt(Aug.GetLastIdxBrs(),Aug.GetLastIdxBrs()) == 1){
            // Punya invers
            for (i=Aug.GetFirstIdxBrs();i<=Aug.GetLastIdxBrs();i++){
                for (j=Aug.GetLastIdxBrs()+1;j<=Aug.GetLastIdxKol();j++){
                    Invers.SetElmt(i, j-Aug.GetLastIdxBrs()-1, Aug.Elmt(i, j));
                }
            }
        } else{
            //Tidak punya invers (hanya mengembalikan matriks kosong)
            singular=true;   
        }
        return Invers;
    }

    public void printInversMatriks1(Matriks Invers){
        // Ambil matriks dri kolom this.getlastkol()+1 sampe Aug.getlastkol()
        if (!singular){
            Invers.TulisMatriks();
        } else{
            // Tidak punya invers
            System.out.println("\nMatriks tidak memiliki invers");
        }
    }

    public double Determinan1() {
        // Mencari Determinan dengan menggunakan metode Reduksi Baris
        double Det=1;

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

    public double Determinan2() {
        // Mencari Determinan dengan menggunakan metode Ekspansi Kofaktor
        double Det=0;
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

        // Membuat matriks invers dari pembagian matriks adjoin dengan determinan matriks
        Matriks Adjoin = new Matriks(this.BrsEf,this.KolEf);
        Matriks Invers = new Matriks(this.BrsEf,this.KolEf);
        Adjoin = Kofaktor.Transpose();

        double determinan=Determinan1();

        if (determinan !=0){
            for (int i=GetFirstIdxBrs();i<=GetLastIdxBrs();i++){
                for (int j=GetFirstIdxKol();j<=GetLastIdxKol();j++){
                    Invers.SetElmt(i, j, Adjoin.Elmt(i, j)/determinan);
                }
            }
            Invers.TulisMatriks();
        } else{
            System.out.println("\nMatriks tidak memiliki invers.");
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

    public void Interpolasi() {
        // Mencari polinom interpolasi derajat n
        double Taksir=0;
        System.out.print("Masukkan derajat polinom interpolasi : ");
        int N = scanner.nextInt();

        // Indeks titik X dan Y
        int x = 0;
        int y = 1;

        // Membuat matriks sistem persamaan lanjar
        Matriks Persamaan = new Matriks(N+1,N+2);
        for (int i=Persamaan.GetFirstIdxBrs();i<=Persamaan.GetLastIdxBrs();i++) {
            for (int j=Persamaan.GetFirstIdxKol();j<=Persamaan.GetLastIdxKol();j++) {
                if (j==Persamaan.GetFirstIdxKol()) {
                    Persamaan.SetElmt(i, j, 1);
                }
                else if (j==Persamaan.GetLastIdxKol()) {
                    Persamaan.SetElmt(i, j, this.Elmt(i,y));
                }
                else {
                    double Pangkat = Math.pow(this.Elmt(i,x),j);
                    Persamaan.SetElmt(i, j, Pangkat);
                }
            }
        }

        // Mencari dan menampilkan koefisien persamaan
        double[] solusi;
        Persamaan.ForwardPhase();
        Persamaan.LeadingOne();
        Persamaan.BackwardPhase();
        solusi = Persamaan.BackSubs();
        for (int i=0;i<Persamaan.GetLastIdxKol();i++){
            System.out.println("a" + (i+1) + " = " + rounding(solusi[i]));
        }

        // Menampilkan polinom interpolasi
        for (int i=Persamaan.GetFirstIdxBrs();i<=Persamaan.GetLastIdxBrs();i++) {
            if (i==0) {
                System.out.print("p" + N + "(x) = ");
                if (Persamaan.Elmt(i,Persamaan.GetLastIdxKol())!=0) {
                    System.out.print(rounding(Persamaan.Elmt(i,Persamaan.GetLastIdxKol())));
                }
            }
            else if (i==1) {
                if (Persamaan.Elmt(i,Persamaan.GetLastIdxKol())>0) {
                    System.out.print(" + " + rounding(Persamaan.Elmt(i,Persamaan.GetLastIdxKol())) + "x");
                }
                else if (Persamaan.Elmt(i,Persamaan.GetLastIdxKol())<0) {
                    System.out.print(" - " + rounding(Math.abs(Persamaan.Elmt(i,Persamaan.GetLastIdxKol()))) + "x");
                }
            }
            else {
                if (Persamaan.Elmt(i,Persamaan.GetLastIdxKol())>0) {
                    System.out.print(" + " + rounding(Persamaan.Elmt(i,Persamaan.GetLastIdxKol())) + "x^" + i);
                }
                else if (Persamaan.Elmt(i,Persamaan.GetLastIdxKol())<0) {
                    System.out.print(" - " + rounding(Math.abs(Persamaan.Elmt(i,Persamaan.GetLastIdxKol()))) + "x^" + i);
                }
            }
        }
        System.out.println("");

        // Menerima nilai X yang ingin ditaksir
        System.out.print("Masukkan nilai X yang ingin ditaksir : ");
        double X = scanner.nextDouble();

        // Menampilkan hasil taksiran
        for (int i=Persamaan.GetFirstIdxBrs();i<Persamaan.GetLastIdxBrs();i++) {
            Taksir += (rounding(Persamaan.Elmt(i,Persamaan.GetLastIdxKol()))*(Math.pow(X,i)));
        }
        System.out.println("Hasil taksiran polinom interpolasi yaitu : " + rounding(Taksir));
    }

    public void BacaInputRegresi(){
        //Membaca banyak elemen variabel independen x
        System.out.println("Input nilai variabel peubah x dan variabel terkait y");
        System.out.print("Masukkan banyak variabel peubah x : ");
        int N = scanner.nextInt();
        this.KolEf = N+1;
        System.out.print("Masukkan banyak data yang ingin diinput : ");
        this.BrsEf = scanner.nextInt();


        // Membuat matriks M yg berisi data nilai x dan y sesuai masukan user
        this.M = new double [BrsEf][KolEf];
        for (int i=this.GetFirstIdxBrs();i<=this.GetLastIdxBrs();i++){
            for (int j=this.GetFirstIdxKol();j<this.GetLastIdxKol();j++){
                System.out.print("Masukkan nilai x"+(j+1)+(i+1)+" : ");
                this.SetElmt(i, j, scanner.nextDouble());
            }
            System.out.print("Masukkan nilai y"+(i+1)+"  : ");
            this.SetElmt(i, this.GetLastIdxKol(), scanner.nextDouble());
        }
        this.TulisMatriks();
    }
    
    public void regresilinearganda(){

        // Membuat matriks sesuai persamaan SPL untuk regresi linear ganda
        Matriks splxy = new Matriks(KolEf,KolEf+1);

        // Set elemen baris pertama kolom pertama dengan N
        splxy.SetElmt(splxy.GetFirstIdxBrs(), splxy.GetFirstIdxKol(), BrsEf);

        // Set elemen baris pertama kolom kedua hingga akhir dan kolom pertama baris kedua hingga akhir
        for (int i=splxy.GetFirstIdxKol()+1;i<=splxy.GetLastIdxKol();i++){
            double sum = 0;
            for (int j=GetFirstIdxBrs();j<=GetLastIdxBrs();j++){
                sum+=Elmt(j, i-1);
            }
            splxy.SetElmt(splxy.GetFirstIdxBrs(), i, sum);
            if (i<=splxy.GetLastIdxBrs()){
                splxy.SetElmt(i, splxy.GetFirstIdxKol(), sum);
            }
        }

        // Set elemen tersisa yg belum diisi
        for (int i=splxy.GetFirstIdxBrs()+1;i<=splxy.GetLastIdxBrs();i++){
            for (int j=splxy.GetFirstIdxKol()+1;j<=splxy.GetLastIdxKol();j++){
                double sum = 0;
                for (int k=GetFirstIdxBrs();k<=GetLastIdxBrs();k++){
                    sum+=Elmt(k, i-1)*Elmt(k, j-1);
                }
                splxy.SetElmt(i, j, sum);
            }
        }

        
        //Melakukan penyelesaian spl dengan metode gauss
        splxy.TulisMatriks();
        splxy.ForwardPhase();
        splxy.TulisMatriks();
        splxy.LeadingOne();
        splxy.TulisMatriks();
        double[] hasilspl=splxy.BackSubs();

        // Meminta input xk yaitu nilai nilai x yang ingin ditaksir nilai fungsinya
        Matriks xk = new Matriks(1,this.KolEf-1);
        System.out.println("\nInput nilai-nilai x yang ingin ditaksir nilai fungsinya");
        for (int j=xk.GetFirstIdxKol();j<=xk.GetLastIdxKol();j++){
            System.out.print("Masukkan nilai x"+(j+1)+" : ");
            xk.SetElmt(xk.GetFirstIdxBrs(), j, scanner.nextDouble());
        }

        if (!splxy.nosolutionspl && !splxy.solusiparametrik){
            System.out.print("\nPersamaan regresi linear: \ny ="+rounding(hasilspl[0]));
            for (int i=1;i<hasilspl.length;i++){
                if (hasilspl[i]>0){
                    System.out.print(" + "+rounding(hasilspl[i])+"x"+(i));
                } else{
                    System.out.print(" - "+(-1 * rounding(hasilspl[i]))+"x"+(i));
                }
            }

            // Menuliskan hasil taksiran fungsi
            System.out.print("\n\nHasil taksiran fungsi : ");
            double sum=hasilspl[0];
            for (int i=1;i<hasilspl.length;i++){
                sum+=hasilspl[i]*xk.Elmt(xk.GetFirstIdxBrs(), i-1);
            }
            System.out.println(rounding(sum));
        } else{
            System.out.println("Terdapat kesalahan/kekurangan input data sehingga nilai taksiran tidak dapat diperoleh.");
        }
    }
}
