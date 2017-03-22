package org.foi.nwtis.dhorvat3;

import org.foi.nwtis.dhorvat3.kvadrati.*;

public class Vjezba_03_1 {

	public static void main(String args[]) {
		if(args.length != 3) {
			System.out.println("Broj argumenta ne odgovara");	
			return;
		}
		
		int odBroja = Integer.parseInt(args[0]);
		int doBroja = Integer.parseInt(args[1]);

		int vrsta = Integer.parseInt(args[2]);
		Kvadrati kvad = null;
		Ispisivac_1 isp1 = null;
		Ispisivac_2 isp2 = null;
		
		switch(vrsta) {
		case 0:
			kvad = new Kvadrati(odBroja, doBroja);
			kvad.ispis();
			break;
		case 1:
			NeparniKvadrati_1 nkvad = new NeparniKvadrati_1(odBroja, doBroja);
			nkvad.ispis();
			break;
		case 2:
			kvad = new NeparniKvadrati_1(odBroja, doBroja);
			kvad.ispis();
			break;
		case 3:
			kvad = new NeparniKvadrati_2(odBroja, doBroja);
			kvad.ispis();
			break;
		case 4:
			kvad = new NeparniKvadrati_3(odBroja, doBroja);
			kvad.ispis();
			break;
		case 5:
			isp1 = new NeparniKvadrati_3(odBroja, doBroja);
			isp1.ispisiPodatke();
			break;
		case 6:
			NeparniKvadrati_4 nkvad4 = new NeparniKvadrati_4(odBroja, doBroja);
			nkvad4.ispisiPodatkeLinijski();
			break;
		case 7:
			kvad = new NeparniKvadrati_4(odBroja, doBroja);
			kvad.ispis();
			break;
		case 8:
			isp1 = new NeparniKvadrati_4(odBroja, doBroja);
			isp1.ispisiPodatke();
			break;
		case 9:
			isp2 = new NeparniKvadrati_4(odBroja, doBroja);
			isp2.ispisiPodatke();
			break;
		case 10:
			NeparniKvadrati_5 nkvad5 = new NeparniKvadrati_5();
			for(int i=0; i<10; i++){
				Ispisivac_1 ispi1 = nkvad5.kreirajIspisivac_1(odBroja, doBroja);
				ispi1.ispisiPodatke();
			}
			break;
		default:
			System.out.println("Argumenti ne odgovaraju");
		}
			
	}
}
		
		