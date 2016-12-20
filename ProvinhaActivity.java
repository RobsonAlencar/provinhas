package br.gov.serpro.provinha;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.gov.serpro.provinha.adapter.ProvasAdapter;
import br.gov.serpro.provinha.fragment.AvisoFragment;
import br.gov.serpro.provinha.fragment.ListaProvasFragment;
import br.gov.serpro.provinha.model.Prova;
import br.gov.serpro.provinha.adapter.ProvasAdapter;
import br.gov.serpro.provinha.fragment.AvisoFragment;
import br.gov.serpro.provinha.fragment.ListaProvasFragment;
import br.gov.serpro.provinha.model.Alternativa;
import br.gov.serpro.provinha.model.Prova;
import br.gov.serpro.provinha.model.Questao;
import br.gov.serpro.provinha.model.Resposta;

public class ProvinhaActivity extends AppCompatActivity {

	private ProvasAdapter provasAdapter;

	private List<Prova> provas;
	private ListView listView;
	Prova provaSelecionada;


	private int questaoAtual;
	private final int REQUEST_CODE = 1;
	CharSequence[] items = {"Google", "Apple", "Microsoft"};
	boolean[] checkedItems = new boolean [items.length];
	boolean checkedItem = false;
	Map<Questao, Alternativa> questoesProva = new HashMap<Questao, Alternativa>();
	List<Resposta> respostasAluno;
	int iQuestaoAtual = 0;
	
	private Spinner SpnListarArquivosProvas;
	private Map<String, File> arquivos = new HashMap<String, File>(); 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prova_main);
		//SpnListarArquivosProvas = (Spinner)  findViewById(R.id.spListaProvas);


		consultarProvasExternalStorage();

		Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
		setSupportActionBar(myToolbar);

		setTitle("Lista de Provas");

		provas = new ArrayList<Prova>();
		provas.add(0, new Prova("Coletivos", "Portugues"));

		listView = (ListView) findViewById(R.id.lv_provas);

		provasAdapter = new ProvasAdapter(provas, this);

		listView.setAdapter(provasAdapter);



		
		/*Button btnQuestao = (Button) findViewById(R.id.btn_questao);
		btnQuestao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					lerArquivoProvas();
				} catch (FileNotFoundException e) {
					exibirMensagem(e.getMessage());
				}
				questaoAtual = 1;
				fazerProximaPergunta();
			}
		}); */
		
	}
	
	private void lerArquivoProvas() throws FileNotFoundException  {
		String prova = SpnListarArquivosProvas.getSelectedItem().toString();
		
		File fileProva = arquivos.get(prova);
		
		FileReader arq = new FileReader(fileProva.getAbsolutePath());
	    BufferedReader lerArq = new BufferedReader(arq);
	    questoesProva.clear();
	    CSVFileQuestoes csvFile = new CSVFileQuestoes(lerArq);
        try {
            List<Questao> read = csvFile.read();
            for (Questao questao : read) {
                questoesProva.put(questao, null);
            }
        } catch (ArquivoException ae) {
            Toast.makeText(ProvinhaActivity.this, "Erro no arquivo: " + ae, Toast.LENGTH_SHORT).show();
            throw  new RuntimeException("Provinha com problemas!!!");
        }

		
	}
	
	private void consultarProvasExternalStorage() {
		File diretorio = new File(ObterDiretorio(null));
		File[] arquivosDir = diretorio.listFiles();

		if (arquivosDir != null) {
			int length = arquivosDir.length;
			for (int i = 0; i < length; ++i) {
				File f = arquivosDir[i];
				if (f.isFile()) {
					String nameFile = f.getName().replace(".csv", "");
					arquivos.put(nameFile, f);
				}
			}

			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, 
					new ArrayList<String>(arquivos.keySet()));
			arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			SpnListarArquivosProvas.setAdapter(arrayAdapter);
		}

	}
	
	private void exibirMensagem(String msg)
	{
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	} 

	/**
	 * método nos retorna o diretório de armazenamento externo.
	 */
	private String ObterDiretorio(String pathToFolder)
	{

        //String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        //return baseDir;


	    File root = android.os.Environment.getExternalStorageDirectory();
	    return root.toString() + "/Provinhas";
	} 
	

	private void fazerProximaPergunta() {
		Intent intent = new Intent(getBaseContext(), ActivityQuestao.class);

		/*Bundle extras = new Bundle();
		extras.putString("questao", String.valueOf(questaoAtual));
		String perguntaAtual = String.valueOf(getPerguntaAtual(questaoAtual));
		extras.putString("pergunta", perguntaAtual);
		intent.putExtras(extras);*/
		
		intent.putExtra("questaoObject", getQuestaoAtual(questaoAtual));
		
		startActivityForResult(intent, REQUEST_CODE);

	}

	private Questao getQuestaoAtual(int findQuestaoAtual) {
		for (Questao questao : questoesProva.keySet()) {
			if (questao.getNumero() == findQuestaoAtual) {
				return questao;
			}
		}
		return null;
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				String resposta = data.getDataString();
				if (getQuestaoAtual(questaoAtual).isRespostaCerta(resposta)) {
					resposta = "Resposta certa!!!"; 
				} else {
					resposta = "Resposta Errada!!!"; 
				}
				Toast.makeText(ProvinhaActivity.this, resposta, 
						Toast.LENGTH_SHORT).show();
				if (questaoAtual < questoesProva.size()) {
					questaoAtual++;
					fazerProximaPergunta();
				}
			}
		}
	}


	@Override
	protected void onResume() {
		super.onResume();


		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		AvisoFragment avisoFragment = (AvisoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
		ListaProvasFragment listaFragment = (ListaProvasFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_lista);

		if(provasAdapter.getCount() > 0){
			fragmentTransaction.hide(avisoFragment);
			fragmentTransaction.show(listaFragment);
		} else {
			fragmentTransaction.show(avisoFragment);
			fragmentTransaction.hide(listaFragment);
		}

		fragmentTransaction.commit();
	}
}
