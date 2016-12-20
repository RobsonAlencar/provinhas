package br.gov.serpro.provinha.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Questao implements Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 3157787618319101684L;

	private int numero;
	
	private String pergunta;
	
	
	private Map<Alternativa, CharSequence> alternativas =
			new HashMap<Alternativa, CharSequence>();
	
	private Alternativa respostaCerta;
	
	public Questao(int numero, String pergunta, Map<Alternativa, CharSequence> alternativas,
			Alternativa respostaCerta) {
		this.setNumero(numero);
		this.setPergunta(pergunta);
		this.setAlternativas(alternativas);
		this.setRespostaCerta(respostaCerta);
	}

	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public int getNumero() {
		return numero;
	}

	public String getPergunta() {
		return pergunta;
	}


	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	
	public Alternativa getRespostaCerta() {
		return respostaCerta;
	}

	public boolean isRespostaCerta(String resposta) {
		return respostaCerta.name().equals(resposta);
	}
	
	
	public void setRespostaCerta(Alternativa respostaCerta) {
		this.respostaCerta = respostaCerta;
	}


	public Map<Alternativa, CharSequence> getAlternativas() {
		return alternativas;
	}


	public void setAlternativas(Map<Alternativa, CharSequence> alternativas) {
		this.alternativas = alternativas;
	}
}
