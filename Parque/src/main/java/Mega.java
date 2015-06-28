
public class Mega {

	static int[] v = new int[6];
	static int count = 0;
	static boolean fim = false;
	static int apostas = 5;
	
	public static void main(String[] args){
		for (int i = 0; i < apostas; i++) {
			geraNum();
			System.out.println("");
		}
	}
	
	private static void geraNum(){
		while(!fim) {
			int aux = (int) ((Math.random() * 100) % 61);
			boolean existe = checkNum(aux);
			if(!existe){
				v[count++] = aux;
				if(count == 6)
					fim = true;
			}
		}
		
		ordenar();
		for (int i = 0; i < v.length; i++) {
			System.out.print(v[i]+", ");
		}
		
		count = 0;
		fim = false;
	}
	
	private static boolean checkNum(int aux){
		for (int i = 0; i < v.length; i++) {
			if(aux == v[i] || aux == 0){
				return true;
			}
		}
		return false;
	}
	
	private static void ordenar(){
		int aux = 0;
		for (int i = 0; i < v.length; i++) {
			for (int j = 0; j < v.length -1; j++) {
				if(v[j] > v[j+1]){
					aux = v[j];
					v[j] = v[j+1];
					v[j+1] = aux;
				}
			}
		}
	}
}
