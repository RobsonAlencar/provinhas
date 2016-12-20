package br.gov.serpro.provinha.model;

public class Resposta {
	private Questao questao;
	private Alternativa respostaAluno;
	
	public Questao getQuestao() {
		return questao;
	}
	
	public Alternativa getRespostaAluno() {
		return respostaAluno;
	}
	
	public void setQuestao(Questao questao) {
		this.questao = questao;
	}
	
	public void setRespostaAluno(Alternativa respostaAluno) {
		this.respostaAluno = respostaAluno;
	}
	
	public boolean isAcertou() {
		return questao.getRespostaCerta().equals(respostaAluno);
	}
}
