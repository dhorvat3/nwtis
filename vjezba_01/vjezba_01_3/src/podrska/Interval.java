package podrska;

public class Interval{
	int odBroja;
	int doBroja;
	
	public Interval(int odBroja, int doBroja){
		this.odBroja = odBroja;
		this.doBroja = doBroja;
	}
	
	public long dajZbroj() {
		long zbroj = 0;
		for(int i = odBroja; i<=doBroja;i++){
			zbroj += i;
		}
		return zbroj;
	}
}