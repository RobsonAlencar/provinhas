package br.gov.serpro.provinha.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.gov.serpro.provinha.model.Prova;
import br.gov.serpro.provinha.R;


/**
 * Created by 82728925534 on 27/09/16.
 */

public class ProvasAdapter extends BaseAdapter {

    private List<Prova> provas;
    private Context context;
    private LayoutInflater layoutInflater;

    public ProvasAdapter(List<Prova> provas, Context context){
        this.provas = provas;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return provas.size();
    }

    @Override
    public Prova getItem(int position) {
        return provas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public long getPosicao(Prova prova) {
        for (Prova p: provas) {
            if (p.equals(prova)) {
                return provas.indexOf(p);
            }
        }
        return -1;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View contentView = layoutInflater.inflate(R.layout.adapter_prova, null);
        TextView nome = (TextView) contentView.findViewById(R.id.tv_adapter_nome);
        TextView disciplina = (TextView) contentView.findViewById(R.id.tv_adapter_disciplina);

        nome.setText(provas.get(position).nome);
        disciplina.setText(provas.get(position).disciplina);

        return contentView;
    }

    public void addProva(Prova prova){
        provas.add(prova);
        notifyDataSetChanged();
    }


    public void updateProduto(Prova prova, Prova provaSelecionada) {
        if(provas.contains(provaSelecionada)) {
            provas.remove(provaSelecionada);
            provas.add(prova);
            notifyDataSetChanged();
        }
    }
}
