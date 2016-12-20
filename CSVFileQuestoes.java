package br.gov.serpro.provinha;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.gov.serpro.provinha.model.Alternativa;
import br.gov.serpro.provinha.model.Questao;

public class CSVFileQuestoes {

	
	
	private static final int NUMERO_QUESTAO = 0;
	private static final int ENUNCIADO_PERGUNTA = 1;
	private static final int ALTERNATIVA_A = 2;
	private static final int ALTERNATIVA_B = 3;
	private static final int ALTERNATIVA_C = 4;
	private static final int ALTERNATIVA_D = 5;
	private static final int RESPOSTA = 6;
	BufferedReader bufferedReader;

    public CSVFileQuestoes(BufferedReader bufferedReader){
        this.bufferedReader = bufferedReader;
    }

    public List<Questao> read() throws  ArquivoException {
        List<Questao> resultList = new ArrayList<Questao>();
        BufferedReader reader = bufferedReader;
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
            	
            	if (!isCabecalho(csvLine) && !csvLine.isEmpty()) {
            		String[] row = csvLine.split(",");
            		resultList.add(criarQuestaoProva(row));
            	}
            }
        }
        catch (IOException ex) {
            throw new  ArquivoException("Error in reading CSV file: "+ex);
        }
        finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
                throw new  ArquivoException("Error while closing input stream: "
						+ e);
			}
        }
        return resultList;
    }
    

	private boolean isCabecalho(String csvLine) {
		return csvLine.contains("QUESTAO");
	}

	private Questao  criarQuestaoProva(String[] row) {
		String numeroQuestao = row[NUMERO_QUESTAO].trim();
		String pergunta = row[ENUNCIADO_PERGUNTA].trim();
		String alternativaA = row[ALTERNATIVA_A].trim();
		String alternativaB = row[ALTERNATIVA_B].trim();
		String alternativaC = row[ALTERNATIVA_C].trim();
		String alternativaD = row[ALTERNATIVA_D].trim();
		Alternativa resposta = Alternativa.valueOf(row[RESPOSTA].trim());
		
		Map<Alternativa, CharSequence> alternativasQuestaoUm = new LinkedHashMap<Alternativa, CharSequence>();
		alternativasQuestaoUm.put(Alternativa.A, alternativaA);
		alternativasQuestaoUm.put(Alternativa.B, alternativaB);
		alternativasQuestaoUm.put(Alternativa.C, alternativaC);
		alternativasQuestaoUm.put(Alternativa.D, alternativaD);
		return new Questao(Integer.valueOf(numeroQuestao), pergunta, alternativasQuestaoUm, resposta);
		
	}
		
    
}