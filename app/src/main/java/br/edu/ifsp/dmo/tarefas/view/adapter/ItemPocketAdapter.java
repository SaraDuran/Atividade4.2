package br.edu.ifsp.dmo.tarefas.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import br.edu.ifsp.dmo.tarefas.R;
import br.edu.ifsp.dmo.tarefas.model.entities.Article;
import br.edu.ifsp.dmo.tarefas.mvp.MainMVP;

public class ItemPocketAdapter extends ArrayAdapter {

    private LayoutInflater inflater;
    private MainMVP.Presenter presenter;

    public ItemPocketAdapter(Context context, List<Article> data, MainMVP.Presenter presenter) {
        super(context, R.layout.listview_item, data);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_item, null);
            holder = new ViewHolder();
            holder.titleTextView = convertView.findViewById(R.id.titulo_tarefa);
            holder.urlTextView = convertView.findViewById(R.id.descricao_tarefa);
            holder.btnDeletar = convertView.findViewById(R.id.buttonDelete);
            holder.btnEditar = convertView.findViewById(R.id.buttonEdit);
            holder.btnFavoritar = convertView.findViewById(R.id.buttonFav);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Article tarefa = (Article) getItem(position);
        holder.titleTextView.setText(tarefa.getTitle());
        holder.urlTextView.setText(tarefa.getUrl());

        return convertView;
    }

    private void heartClick(int position) {
        Article article = (Article) getItem(position);
        presenter.favoriteArticle(article);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView titleTextView;
        public TextView urlTextView;
        public ImageButton btnEditar;
        public ImageButton btnDeletar;
        public ImageButton btnFavoritar;
    }
}
