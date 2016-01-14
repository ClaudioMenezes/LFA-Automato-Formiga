import java.io.*;
import java.util.*;

public class Formiga {

	static List<String> estados = new ArrayList<String>();
	static List<String> alfabeto = new ArrayList<String>();
	static List<String> transicoes = new ArrayList<String>();
	static List<String> estados_finais = new ArrayList<String>();
	static List<List<String>> proximos_estados = new ArrayList<List<String>>();
	static List<String> transicao_lida = new ArrayList<String>();
	static String estado_inicial = null;
	static String[] formiga = new String[6];
	static int acucares_comidos = 0;
	/*
	 * Construtor da formiga, setando suas configurações iniciais lidas dos
	 * arquivos txt
	 */

	public Formiga() throws InterruptedException {

		/* Atribuindo a posição inicial da formiga, linha e coluna */
		formiga[0] = Tabuleiro.posicao_inicial.get(0);
		formiga[1] = Tabuleiro.posicao_inicial.get(1);

		/* Seta a orientação cardeal inicial da formiga para leste ou oeste */
		String aux_direcao = Tabuleiro.posicao_inicial.get(2);

		if (aux_direcao.equals("E")) {

			formiga[2] = "O";

		}

		else if (aux_direcao.equals("D")) {

			formiga[2] = "L";

		}

		/* Estado inicial no autômato */
		formiga[3] = "q0";
		formiga[5] = "olha";

		/*
		 * Setando o caractere que a formiga está olhando no tabuleiro a sua
		 * frente
		 */
		formigaOlha();

		/*
		 * Lê do formiga.txt todas as transições entre estados possíveis no
		 * autômato
		 */
		setaTransicoes();

	}

	/* Atribuindo a direção cardeal que a formiga está */
	public static void orientaFormiga(String aux_direcao) {
		
		if (aux_direcao.equals("M")) {
			
			formiga[5] = "M";
			
		}
		
		else {
			
			formiga[5] = "olha";
			
		}

		if (aux_direcao.equals("E") && formiga[2].equals("L")) {

			formiga[2] = "N";

		}

		else if (aux_direcao.equals("E") && formiga[2].equals("N")) {

			formiga[2] = "O";

		}

		else if (aux_direcao.equals("E") && formiga[2].equals("O")) {

			formiga[2] = "S";

		}

		else if (aux_direcao.equals("E") && formiga[2].equals("S")) {

			formiga[2] = "L";

		}

		else if (aux_direcao.equals("D") && formiga[2].equals("L")) {

			formiga[2] = "S";

		}

		else if (aux_direcao.equals("D") && formiga[2].equals("S")) {

			formiga[2] = "O";

		}

		else if (aux_direcao.equals("D") && formiga[2].equals("O")) {

			formiga[2] = "N";

		}

		else if (aux_direcao.equals("D") && formiga[2].equals("N")) {

			formiga[2] = "L";

		}

	}

	/*
	 * Enxerga o próximo caractere que está a frente da formiga no tabuleiro.
	 */
	public static void formigaOlha() {

		int proxima_posicao_linha, proxima_posicao_coluna;

		if (formiga[2].equals("L")) {

			proxima_posicao_linha = Integer.parseInt(formiga[0]);
			proxima_posicao_coluna = Integer.parseInt(formiga[1]) + 1;

			formiga[4] = Tabuleiro.tabuleiro[proxima_posicao_linha][proxima_posicao_coluna];

			if (formiga[4].equals(" ")) {

				formiga[4] = "s";

			}
			
			else if (formiga[4].equals("█")) {

				formiga[4] = "b";

			}

			else if (formiga[4].equals("+")) {

				formiga[4] = "1";

			}

		}

		else if (formiga[2].equals("O")) {

			proxima_posicao_linha = Integer.parseInt(formiga[0]);
			proxima_posicao_coluna = Integer.parseInt(formiga[1]) - 1;

			formiga[4] = Tabuleiro.tabuleiro[proxima_posicao_linha][proxima_posicao_coluna];

			if (formiga[4].equals(" ")) {

				formiga[4] = "s";

			}
			
			else if (formiga[4].equals("█")) {

				formiga[4] = "b";

			}

			else if (formiga[4].equals("+")) {

				formiga[4] = "1";

			}

		}

		else if (formiga[2].equals("N")) {

			proxima_posicao_linha = Integer.parseInt(formiga[0]) - 1;
			proxima_posicao_coluna = Integer.parseInt(formiga[1]);

			formiga[4] = Tabuleiro.tabuleiro[proxima_posicao_linha][proxima_posicao_coluna];

			if (formiga[4].equals(" ")) {

				formiga[4] = "s";

			}
			
			else if (formiga[4].equals("█")) {

				formiga[4] = "b";

			}

			else if (formiga[4].equals("+")) {

				formiga[4] = "1";

			}

		}

		else if (formiga[2].equals("S")) {

			proxima_posicao_linha = Integer.parseInt(formiga[0]) + 1;
			proxima_posicao_coluna = Integer.parseInt(formiga[1]);

			formiga[4] = Tabuleiro.tabuleiro[proxima_posicao_linha][proxima_posicao_coluna];

			if (formiga[4].equals(" ")) {

				formiga[4] = "s";

			}
			
			else if (formiga[4].equals("█")) {

				formiga[4] = "b";

			}

			else if (formiga[4].equals("+")) {

				formiga[4] = "1";

			}

		}

	}

	/*
	 * Faz a formiga andar de acordo com a sua orientação cardeal e atualiza a
	 * sua posição atual
	 */

	public static void formigaAnda() {

		int proxima_posicao_linha, proxima_posicao_coluna;

		/* Só andará se encontrar um s (espaço vazio) ou a (açúcar) */
		if (((formiga[4].equals("s") || formiga[4].equals("")) || formiga[4].equals("1")) && (formiga[5].equals("M"))) {
			
			if (formiga[4].equals("")){
				
				acucares_comidos++;
				
			}

			if (formiga[2].equals("L")) {

				proxima_posicao_coluna = Integer.parseInt(formiga[1]) + 1;

				formiga[1] = "" + proxima_posicao_coluna;

			}

			else if (formiga[2].equals("O")) {

				proxima_posicao_coluna = Integer.parseInt(formiga[1]) - 1;

				formiga[1] = "" + proxima_posicao_coluna;

			}

			else if (formiga[2].equals("N")) {

				proxima_posicao_linha = Integer.parseInt(formiga[0]) - 1;

				formiga[0] = "" + proxima_posicao_linha;

			}

			else if (formiga[2].equals("S")) {

				proxima_posicao_linha = Integer.parseInt(formiga[0]) + 1;

				formiga[0] = "" + proxima_posicao_linha;

			}

		}

	}

	/* Faz a leitura do formiga.txt e guarda suas informações em listas */
	public static void leAutomatoFormiga(String txt_formiga)
			throws FileNotFoundException, IOException {

		int cont = 0;
		String linha = null, info = null;

		try {

			/*
			 * Tenta ler o txt recebido pelo teclado, se não encontrar entra no
			 * catch e exibe uma mensagem de erro
			 */

			FileReader arquivo_txt = new FileReader(txt_formiga);
			BufferedReader buffer_linha = new BufferedReader(arquivo_txt);

			/* Lê a primeira linha para análise do conteúdo */

			linha = buffer_linha.readLine();

			while (linha != null) {

				cont++;

				if (cont == 2) {

					linha = linha.replace("{", "").replace("}", "")
							.replace("Q", "").replace("=", "");

					linha = linha.replace(" ", "");

					/*
					 * O while irá percorrer todos os valores da respectiva
					 * linha separando eles pelo token de vírgula
					 */

					StringTokenizer token = new StringTokenizer(linha, ",");

					while (token.hasMoreTokens()) {

						estados.add(token.nextToken());

					}

					info = "\nEstados: " + estados;

				}

				/*
				 * Se o contador de linha é igual a 3, significa que estamos
				 * lendo a linha referente as coordenadas das barreiras
				 */

				else if (cont == 3) {

					/*
					 * O while irá percorrer todos os valores da respectiva
					 * linha separando eles pelo token de vírgula
					 */

					linha = linha.replace("{", "").replace("}", "")
							.replace("A", "").replace("=", "");

					linha = linha.replace(" ", "");

					StringTokenizer token = new StringTokenizer(linha, ",");

					while (token.hasMoreTokens()) {

						alfabeto.add(token.nextToken());

					}

					info += "\nAlfabeto: " + alfabeto;

				}

				/*
				 * Se o contador de linha é igual a 4, significa que estamos
				 * lendo a linha referente as coordenadas dos grãos de açúcar
				 */

				else if (cont == 4) {

					linha = linha.replace("T", "").replace("=", "")
							.replace("{", "").replace("}", "").replace("(", "")
							.replace(")", "").replace("-", ",");

					linha = linha.replace(" ", "");

					/*
					 * O while irá percorrer todos os valores da respectiva
					 * linha separando eles pelo token de vírgula
					 */

					StringTokenizer token = new StringTokenizer(linha, ",");

					while (token.hasMoreTokens()) {

						transicoes.add(token.nextToken());

					}

					info += "\nTransições: " + transicoes;

				}

				else if (cont == 5) {

					/*
					 * O while irá percorrer todos os valores da respectiva
					 * linha separando eles pelo token de espaço
					 */

					linha = linha.replace("{", "").replace("}", "")
							.replace("I", "").replace("=", "");

					linha = linha.replace(" ", "");

					estado_inicial = linha;

					info += "\nEstado Inicial: " + estado_inicial;

				}

				else if (cont == 6) {

					linha = linha.replace("{", "").replace("}", "")
							.replace("F", "").replace("=", "");

					linha = linha.replace(" ", "");

					/*
					 * O while irá percorrer todos os valores da respectiva
					 * linha separando eles pelo token de vírgula
					 */

					StringTokenizer token = new StringTokenizer(linha, ",");

					while (token.hasMoreTokens()) {

						estados_finais.add(token.nextToken());

					}

					info += "\nEstados Finais: " + estados_finais;

				}

				/*
				 * Lê a próxima linha e repete o laço até acabem as linhas
				 */

				linha = buffer_linha.readLine();

			}

			/*
			 * Terminada a leitura, hora de fechar o arquivo
			 */

			buffer_linha.close();
			arquivo_txt.close();

		} catch (IOException e) {

			System.out
					.println("\nERRO! Verifique se o nome e a extensão do txt estão corretos");
			System.exit(1);

		}

		System.out.println(info);

	}

	/*
	 * Interpreta as transições lidas do formiga.txt, e armazena cada transição
	 * de estados em uma sublista. Essas sublistas são armazenadas em uma lista
	 * mãe que contém todas as transições entre os estados do autômato da
	 * formiga
	 */

	public static void setaTransicoes() throws InterruptedException {

		String estado_atual, alfabeto, onde_vai, feromonio, direcao;

		int contador_aux = 1;
		int cont_vetor = 0;

		for (Iterator<String> it = transicoes.iterator(); it.hasNext();) {

			if (contador_aux == 1) {

				estado_atual = it.next();
				transicao_lida.add(estado_atual);

			}

			else if (contador_aux == 2) {

				alfabeto = it.next();
				transicao_lida.add(alfabeto);

			}

			else if (contador_aux == 3) {

				onde_vai = it.next();
				transicao_lida.add(onde_vai);

			}

			else if (contador_aux == 4) {

				feromonio = it.next();
				transicao_lida.add(feromonio);

			}

			else if (contador_aux == 5) {

				direcao = it.next();
				transicao_lida.add(direcao);
				contador_aux = 1;
				proximos_estados.add(cont_vetor, transicao_lida);
				cont_vetor++;
				transicao_lida = new ArrayList<String>();
				continue;

			}

			contador_aux++;

		}

	}

	/*
	 * Onde ocorre as chamadas dos métodos que orientam e a formiga se mover.
	 */
	public static void movimenta() throws InterruptedException {

		for (Iterator<List<String>> it1 = proximos_estados.iterator(); it1
				.hasNext();) {

			List<String> lista_aux;
			lista_aux = it1.next();

			/*
			 * Seleciona a partir do estado atual e do alfabeto lido do
			 * tabuleiro, qual o estado futuro a seguir.
			 */

			if (((formiga[3].equals(lista_aux.get(0))))
					&& ((formiga[4].equals(lista_aux.get(1))))) {

				/* Setando o novo estado do autômato em que a formiga está */
				formiga[3] = lista_aux.get(2);

				/* orientaFormiga indica para onde a formiga está indo */

				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				//System.out.print("\u001b[2J");

				Tabuleiro.tabuleiro[Integer.parseInt(formiga[0])][Integer
						.parseInt(formiga[1])] = "+";

				orientaFormiga(lista_aux.get(4));
				formigaAnda();
				formigaOlha();

				Tabuleiro.tabuleiro[Integer.parseInt(formiga[0])][Integer
						.parseInt(formiga[1])] = "¥";
				
				Tabuleiro.imprimeTabuleiro(Tabuleiro.tabuleiro.length,
						Tabuleiro.tabuleiro[0].length);
				
				if (acucares_comidos == (Tabuleiro.acucares.size() / 2)) {
					
					System.out.println("Todos os grãos de açúcar foram pegos!");
					System.exit(0);
					
				}

				System.out.println("Formiga[0] = " + formiga[0]);
				System.out.println("Formiga[1] = " + formiga[1]);
				System.out.println("Formiga[2] = " + formiga[2]);
				System.out.println("Formiga[3] = " + formiga[3]);
				System.out.println("Formiga[4] = " + formiga[4]);
				System.out.println("Formiga[5] = " + formiga[5]);
				Thread.sleep(100);

			}

		}

	}

}
