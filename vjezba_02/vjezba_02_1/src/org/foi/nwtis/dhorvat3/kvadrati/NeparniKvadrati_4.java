package org.foi.nwtis.dhorvat3.kvadrati;

public class NeparniKvadrati_4 extends NeparniKvadrati_2 implements Ispisivac_2 {
	
	public NeparniKvadrati_4(int odBroja, int doBroja) {
		super(odBroja, doBroja);
	}
	
	public void ispisiPodatkeLinijski(){
		int poc = this.odBroja%2 == 1 ? this.odBroja : this.odBroja++;
		for(int i=this.odBroja;i <= this.doBroja;i+=4) {
//			System.out.println(i + " * " + i + " = " + i*i);
			System.out.printf("%3d * %3d = %3d,", i, i, i*i);
		}
	}
	
	public void ispisiPodatke(){
		ispis();
	}
}