package br.gov.serpro.provinha;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import br.gov.serpro.provinha.model.Alternativa;
import br.gov.serpro.provinha.model.Questao;

public class ActivityQuestao extends Activity {

	Questao questaoObj;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questao);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			questaoObj = (Questao) getIntent().getSerializableExtra("questaoObject");
		}

		TextView textView = (TextView) findViewById(R.id.textView1);
		CharSequence charPergunta = questaoObj.getPergunta();
		textView.setText(charPergunta);
		
		String numero =  "Questão " + String.valueOf(questaoObj.getNumero());
		setTitle(numero);
		
		setarAlternativas();
		
		Button btn = (Button) findViewById(R.id.btn_OK);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				   Intent data = getResposta();
				   setResult(RESULT_OK, data);
				   finish();
			}

			private Intent getResposta() {
				Intent data = new Intent();
				RadioButton rdbA = (RadioButton) findViewById(R.id.radioAlternativaA);
				if (rdbA.isChecked()) {
					data.setData(Uri.parse("A"));
				}
				RadioButton rdbB = (RadioButton) findViewById(R.id.radioAlternativaB);
				if (rdbB.isChecked()) {
					data.setData(Uri.parse("B"));
				}
				RadioButton rdbC = (RadioButton) findViewById(R.id.radioAlternativaC);
				if (rdbC.isChecked()) {
					data.setData(Uri.parse("C"));
				}
				RadioButton rdbD = (RadioButton) findViewById(R.id.radioAlternativaD);
				if (rdbD.isChecked()) {
					data.setData(Uri.parse("D"));
				}

				return data;
			}
		});
		
	}

	private void setarAlternativas() {
		RadioButton rdbA = (RadioButton) findViewById(R.id.radioAlternativaA);
		rdbA.setText(questaoObj.getAlternativas().get(Alternativa.A));
		RadioButton rdbB = (RadioButton) findViewById(R.id.radioAlternativaB);
		rdbB.setText(questaoObj.getAlternativas().get(Alternativa.B));
		RadioButton rdbC = (RadioButton) findViewById(R.id.radioAlternativaC);
		rdbC.setText(questaoObj.getAlternativas().get(Alternativa.C));
		RadioButton rdbD = (RadioButton) findViewById(R.id.radioAlternativaD);
		rdbD.setText(questaoObj.getAlternativas().get(Alternativa.D));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	//	getMenuInflater().inflate(R. menu.activity_pergunta1, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
	//	if (id == R.id.action_settings) {
	//		return true;
//		}
		return super.onOptionsItemSelected(item);
	}
}
