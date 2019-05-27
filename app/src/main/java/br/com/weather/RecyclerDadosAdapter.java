package br.com.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import br.com.weather.model.Dados;

public class RecyclerDadosAdapter extends RecyclerView.Adapter<RecyclerDadosAdapter.RecyclerDadosViewHolder>{
    Context ctx;
    private List<Dados> mList;
    protected ItemListener mListener;

    public RecyclerDadosAdapter(Context ctx, List<Dados> list, ItemListener listener) {
        this.ctx = ctx;
        this.mList = list;
        this.mListener = listener;
    }

    @Override
    public RecyclerDadosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view_dados, viewGroup, false);
        return new RecyclerDadosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerDadosViewHolder viewHolder, int i) {
        Dados dados = mList.get(i);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        viewHolder.viewDia.setText(dateFormat.format(dados.getDia()));
        viewHolder.viewCidade.setText(dados.getName());
        viewHolder.viewLatitude.setText(dados.getCoord().getLat());
        viewHolder.viewLongitude.setText(dados.getCoord().getLon());
        viewHolder.viewTemperatura.setText(getTextCelius(Double.toString(dados.getMain().getTemp())));
        viewHolder.viewUmidade.setText(Double.toString(dados.getMain().getHumidity()));
        viewHolder.viewPressao.setText(Double.toString(dados.getMain().getPressure()));
        viewHolder.viewTempMax.setText(getTextCelius(Double.toString(dados.getMain().getTemp_max())));
        viewHolder.viewTempMin.setText(getTextCelius(Double.toString(dados.getMain().getTemp_min())));
    }

    private String getTextCelius(String valor){
        return valor + "°C";
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    protected class RecyclerDadosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView viewDia;
        protected TextView viewCidade;
        protected TextView viewLatitude;
        protected TextView viewLongitude;
        protected TextView viewTemperatura;
        protected TextView viewPressao;
        protected TextView viewUmidade;
        protected TextView viewTempMax;
        protected TextView viewTempMin;

        public RecyclerDadosViewHolder(final View itemView) {
            super(itemView);
            viewDia = (TextView) itemView.findViewById(R.id.textDiaConsultado);
            viewCidade = (TextView) itemView.findViewById(R.id.textListCidade);
            viewLatitude = (TextView) itemView.findViewById(R.id.textListLatitude);
            viewLongitude = (TextView) itemView.findViewById(R.id.textListLongitude);
            viewTemperatura = (TextView) itemView.findViewById(R.id.textListTemperatura);
            viewUmidade = (TextView) itemView.findViewById(R.id.textListUmidade);
            viewPressao = (TextView) itemView.findViewById(R.id.textListPressao);
            viewTempMax = (TextView) itemView.findViewById(R.id.textListTempMax);
            viewTempMin = (TextView) itemView.findViewById(R.id.textListTempMin);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface ItemListener {
        void onItemClick(Dados item);
    }
}


