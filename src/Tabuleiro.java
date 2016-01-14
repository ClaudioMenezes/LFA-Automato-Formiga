import java.io.*;
import java.util.*;

public class Tabuleiro {
	
	public static List<String> barreiras = new ArrayList<String>();
	public static List<String> acucares = new ArrayList<String>();
	public static List<String> posicao_inicial = new ArrayList<String>();
	public static String[][] tabuleiro = null;
	
	public static String[][] criaTabuleiro(int linha, int coluna) {

		String tabuleiro[][] = new String[linha][coluna];

		for (int i = 0; i < linha; i++) {

			for (int j = 0; j < coluna; j++) {

				tabuleiro[i][j] = " ";

			}

		}

		for (int i = 0; i < linha; i++) {

			for (int j = 0; j < coluna; j++) {

				if (i == 0 || i == (linha - 1)) {

					tabuleiro[i][j] = "█";

				}

				if (j == 0 || j == (coluna - 1)) {

					tabuleiro[i][j] = "█";

				}

			}

		}

		return tabuleiro;

	}

	public static void inserirNoTabuleiro(int linha1, int linha2, int coluna1,
			int coluna2, String obj) {

		for (int i = linha1; i <= linha2; i++) {

			for (int j = coluna1; j <= coluna2; j++) {

				tabuleiro[i][j] = obj;

			}
		}
	}

	public static void imprimeTabuleiro(int linha, int coluna) {

		for (int i = 0; i < linha; i++) {

			for (int j = 0; j < coluna; j++) {

				System.out.print(tabuleiro[i][j] + " ");

			}

			System.out.println();

		}

		System.out.println();

	}

	public static void leTabuleiro(String txt_tabuleiro)
			throws FileNotFoundException, IOException {

		int cont = 0, i = 0;
		int largura_altura[] = new int[2];
		String direcao_inicial = null, linha = null, info = null;

		try {

			/*
			 * Tenta ler o txt recebido pelo teclado, se não encontrar entra no
			 * catch e exibe uma mensagem de erro
			 */

			FileReader arquivo_txt = new FileReader(txt_tabuleiro);
			BufferedReader buffer_linha = new BufferedReader(arquivo_txt);

			/* Lê a primeira linha para análise do conteúdo */

			linha = buffer_linha.readLine();

			while (linha != null) {

				cont++;

				if (cont == 2) {

					/*
					 * Se a linha lida é a linha 2, então estamos lendo as
					 * dimensões da matriz
					 */

					StringTokenizer token = new StringTokenizer(linha, " ");
					i = 0;

					while (token.hasMoreTokens()) {

						try {

							largura_altura[i] = Integer.parseInt(token
									.nextToken()) + 2;
							i++;

						}

						catch (Exception e) {

							// Não é inteiro referente a dimensão da matriz

						}

					}

					/*
					 * Cria a martriz passando os valores de altura e largura
					 * definindo a dimensão dela
					 */

					info = "\nDimensão da matriz é: " + (largura_altura[0] - 2)
							+ " x " + (largura_altura[1] - 2);

					tabuleiro = criaTabuleiro(largura_altura[0],
							largura_altura[1]);

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

					linha = linha.replace("B", "").replace("=", "")
							.replace("{", "").replace("}", "").replace("(", "")
							.replace(")", "").replace("-", ",");

					linha = linha.replace(" ", "");

					StringTokenizer token = new StringTokenizer(linha, ",");

					while (token.hasMoreTokens()) {

						barreiras.add(token.nextToken());

					}

					// Preenche barreiras

					int linha1 = -1, linha2 = -1, coluna1 = -1, coluna2 = -1;

					int contador_aux = 1;

					for (Iterator<String> it = barreiras.iterator(); it
							.hasNext();) {

						if (contador_aux == 1) {

							linha1 = Integer.parseInt(it.next());

						}

						else if (contador_aux == 2) {

							coluna1 = Integer.parseInt(it.next());

						}

						else if (contador_aux == 3) {

							linha2 = Integer.parseInt(it.next());

						}

						else if (contador_aux == 4) {

							coluna2 = Integer.parseInt(it.next());

							inserirNoTabuleiro(linha1, linha2, coluna1,
									coluna2, "█");

							contador_aux = 1;
							continue;

						}

						contador_aux++;

					}

					info += "\nBarreiras: " + barreiras;

				}

				/*
				 * Se o contador de linha é igual a 4, significa que estamos
				 * lendo a linha referente as coordenadas dos grãos de açúcar
				 */

				else if (cont == 4) {

					linha = linha.replace("A", "").replace("=", "")
							.replace("{", "").replace("}", "").replace("(", "")
							.replace(")", "").replace("-", ",");

					linha = linha.replace(" ", "");

					/*
					 * O while irá percorrer todos os valores da respectiva
					 * linha separando eles pelo token de vírgula
					 */

					StringTokenizer token = new StringTokenizer(linha, ",");

					while (token.hasMoreTokens()) {

						acucares.add(token.nextToken());

					}

					// Preenche açúcares

					int linha_acucar = -1, coluna_acucar = -1;

					int contador_aux = 1;

					// exibe os itens da lista usando um Iterator
					for (Iterator<String> it = acucares.iterator(); it
							.hasNext();) {

						if (contador_aux == 1) {

							linha_acucar = Integer.parseInt(it.next());
							contador_aux++;

						}

						else if (contador_aux == 2) {

							coluna_acucar = Integer.parseInt(it.next());
							tabuleiro[linha_acucar][coluna_acucar] = "";
							contador_aux = 1;
						}

					}

					info += "\nAçúcares: " + acucares;

				}

				else if (cont == 5) {

					/*
					 * O while irá percorrer todos os valores da respectiva
					 * linha separando eles pelo token de espaço
					 */

					linha = linha.replace("{", "").replace("}", "")
							.replace("(", "").replace(")", "").replace("P", "")
							.replace("=", "");

					linha = linha.replace(" ", "");

					StringTokenizer token = new StringTokenizer(linha, ",");

					while (token.hasMoreTokens()) {

						posicao_inicial.add(token.nextToken());

					}

					if (linha.contains("D")) {

						direcao_inicial = "D";

					}

					else if (linha.contains("E")) {

						direcao_inicial = "E";

					}

					else {

						direcao_inicial = "M";

					}
					
					tabuleiro[Integer.parseInt(posicao_inicial.get(0))][Integer
					                    								.parseInt(posicao_inicial.get(1))] = "o";

					info += "\nPosição Inicial: " + posicao_inicial;
					info += "\nDireção Inicial: " + direcao_inicial + "\n";

					/*
					 * Incrementa o contador de linha indicando que mais uma
					 * linha foi lida, é hora de ler a próxima linha
					 */

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

		imprimeTabuleiro(largura_altura[0], (largura_altura[1]));

	}
}
