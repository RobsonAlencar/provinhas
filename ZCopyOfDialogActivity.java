package br.gov.serpro.provinha;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.gov.serpro.provinha.model.Alternativa;
import br.gov.serpro.provinha.model.Questao;
import br.gov.serpro.provinha.model.Resposta;

public class ZCopyOfDialogActivity extends Activity {

	private int questaoAtual;
	private final int REQUEST_CODE = 1;
	CharSequence[] items = {"Google", "Apple", "Microsoft"};
	boolean[] checkedItems = new boolean [items.length];
	boolean checkedItem = false;
	Map<Questao, Alternativa> questoesProva = new HashMap<Questao, Alternativa>();
	List<Resposta> respostasAluno;
	int iQuestaoAtual = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prova_main);
		
		definirQuestoesProva();

		/*
		Button btnQuestao = (Button) findViewById(R.id.btn_questao);
		btnQuestao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				questaoAtual = 1;
				fazerProximaPergunta();
			}
		});
		
		

		//Definição de dialog
		Button btn = (Button) findViewById(R.id.btn_dialog);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				   respostasAluno = new ArrayList<Resposta>();
				   iQuestaoAtual = 1;
					showDialog(BIND_IMPORTANT);
			}
		});*/


	}
	
	private void fazerProximaPergunta() {
		Intent intent = new Intent(getBaseContext(), ActivityQuestao.class);
		Bundle extras = new Bundle();
		extras.putString("questao", String.valueOf(questaoAtual));
		intent.putExtras(extras);
		startActivityForResult(intent, REQUEST_CODE);
		
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Toast.makeText(ZCopyOfDialogActivity.this, "Resposta = " + data.getDataString(), 
						Toast.LENGTH_SHORT).show();
		
				if (questaoAtual < 3) {
					questaoAtual++;
					fazerProximaPergunta();
				}
			}
		}
	}

	private void definirQuestoesProva() {
		Map<Alternativa, CharSequence> alternativasQuestaoUm = new LinkedHashMap<Alternativa, CharSequence>();
		alternativasQuestaoUm.put(Alternativa.A, "1");
		alternativasQuestaoUm.put(Alternativa.B, "2");
		alternativasQuestaoUm.put(Alternativa.C, "4");
		alternativasQuestaoUm.put(Alternativa.D, "3");
		Questao questaoUm = new Questao(1, "1 + 3 = ", alternativasQuestaoUm, Alternativa.C);
		
		
		Map<Alternativa, CharSequence> alternativasQuestaoDois = new LinkedHashMap<Alternativa, CharSequence>();
		alternativasQuestaoDois.put(Alternativa.A, "12");
		alternativasQuestaoDois.put(Alternativa.B, "20");
		alternativasQuestaoDois.put(Alternativa.C, "4");
		alternativasQuestaoDois.put(Alternativa.D, "3");
		Questao questaoDois = new Questao(2, "5 - 2 = ", alternativasQuestaoDois, Alternativa.D);
		
		questoesProva.put(questaoUm , null);
		questoesProva.put(questaoDois , null);
	}
	
		
/*
	@Override

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dialog, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	*/
		
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		return criarDialog();
	}

	private Dialog criarDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		final Questao questaoAtual = getQuestao(iQuestaoAtual);
		builder.setTitle(questaoAtual.getPergunta());
		
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (isRespostaQuestaoCerta(questaoAtual)) {
				Toast.makeText(ZCopyOfDialogActivity.this, "Resposta Certa! "+ which, 
						Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(ZCopyOfDialogActivity.this, "Resposta Errada! "+ which, 
							Toast.LENGTH_SHORT).show();
				}
				iQuestaoAtual++;
				showDialog(BIND_IMPORTANT);
			}
		});
/*		builder.setNegativeButton("Cancelar", 
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(DialogActivity.this, "Cancel clicado" + which,
								Toast.LENGTH_SHORT).show();
					}
				});*/
		Collection<CharSequence> values = questaoAtual.getAlternativas().values();
		CharSequence[] array = new CharSequence[values.size()];
		int i = 0;
		for (CharSequence charSequence : values) {
			array[i++] = charSequence;
		}
		builder.setSingleChoiceItems(array, -1,  
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						final Questao questaoAtual = getQuestao(iQuestaoAtual);
						Alternativa respostaAluno = Alternativa.values()[which];
						Resposta resposta = new Resposta();
						resposta.setQuestao(questaoAtual);
						resposta.setRespostaAluno(respostaAluno);
						respostasAluno.add(resposta);
					}
				});
		
		return builder.create();
		
	}
	

	public Questao getQuestao(int numero) {
		Iterator<Questao> iterator = questoesProva.keySet().iterator();
		while (iterator.hasNext()) {
			Questao questao = (Questao) iterator.next();
			if (questao.getNumero() == numero) {
				return questao;
			}
		}
		return null;
	}
	
	
	private boolean isRespostaQuestaoCerta(Questao questao) {
		for (Resposta resposta : respostasAluno) {
			if (resposta.getQuestao().equals(questao)) {
				return resposta.isAcertou();
			}
		}
		return false;
	}
	
}
