import java.io.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException,
			IOException, InterruptedException {

		String txt_tabuleiro, txt_formiga;

		txt_tabuleiro = "tabuleiro.txt";

		Tabuleiro.leTabuleiro(txt_tabuleiro);

		txt_formiga = "formiga.txt";

		Formiga.leAutomatoFormiga(txt_formiga);

		Formiga ant = new Formiga();
		
		while (true)	
			ant.movimenta(); 
		
	}

}
