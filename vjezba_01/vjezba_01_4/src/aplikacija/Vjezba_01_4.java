package aplikacija;
import podrska.Interval;

class Vjezba_01_4{
	public static void main(String args[]){
		Interval interval = new Interval(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		System.out.println(interval.dajZbroj());
	}
}