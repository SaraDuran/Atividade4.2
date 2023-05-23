package br.edu.ifsp.dmo.tarefas.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifsp.dmo.tarefas.R;
import br.edu.ifsp.dmo.tarefas.model.entities.Article;
import br.edu.ifsp.dmo.tarefas.mvp.MainMVP;
import br.edu.ifsp.dmo.tarefas.view.RecyclerViewItemClickListener;

public class ItemPocketRecyclerAdapter extends RecyclerView.Adapter<ItemPocketRecyclerAdapter.ViewHolder>{

    private Context context;
    private MainMVP.Presenter presenter;
    private List<Article> data;
    private static RecyclerViewItemClickListener clickListener;

    public ItemPocketRecyclerAdapter(Context context, List<Article> data, MainMVP.Presenter presenter){
        this.context = context;
        this.presenter = presenter;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article tarefa = data.get(position);
        holder.titleTextView.setText(tarefa.getTitle());
        holder.urlTextView.setText(tarefa.getUrl());
        holder.btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getDao().delete(tarefa);
                notifyDataSetChanged();
            }
        });

        holder.btnFavoritar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getDao().favorite(tarefa);
                notifyDataSetChanged();
            }
        });

        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.openDetails(tarefa);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setClickListener(RecyclerViewItemClickListener listener){
        clickListener = listener;
    }

    private void heartClick(Article tarefa){
        presenter.favoriteArticle(tarefa);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView titleTextView;
        public TextView urlTextView;

        public ImageButton btnEditar;
        public ImageButton btnDeletar;
        public ImageButton btnFavoritar;

        public ViewHolder(View itemView){
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titulo_tarefa);
            urlTextView = itemView.findViewById(R.id.descricao_tarefa);
            btnDeletar = itemView.findViewById(R.id.buttonDelete);
            btnEditar = itemView.findViewById(R.id.buttonEdit);
            btnFavoritar = itemView.findViewById(R.id.buttonFav);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null){
                clickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
