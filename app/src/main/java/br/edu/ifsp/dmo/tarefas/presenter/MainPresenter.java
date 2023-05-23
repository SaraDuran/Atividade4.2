package br.edu.ifsp.dmo.tarefas.presenter;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifsp.dmo.tarefas.model.dao.ArticleDaoSingleton;
import br.edu.ifsp.dmo.tarefas.model.dao.IArticleDao;
import br.edu.ifsp.dmo.tarefas.model.entities.Article;
import br.edu.ifsp.dmo.tarefas.mvp.MainMVP;
import br.edu.ifsp.dmo.tarefas.utils.Constant;
import br.edu.ifsp.dmo.tarefas.view.ArticleDetailsActivity;
import br.edu.ifsp.dmo.tarefas.view.RecyclerViewItemClickListener;
import br.edu.ifsp.dmo.tarefas.view.adapter.ItemPocketRecyclerAdapter;

public class MainPresenter implements MainMVP.Presenter {

    private MainMVP.View view;
    private IArticleDao dao;
    Article article;

    public MainPresenter(MainMVP.View view) {
        this.view = view;
        dao = ArticleDaoSingleton.getInstance();
    }

    @Override
    public void deatach() {
        view = null;
    }

    @Override
    public void openDetails() {
        Intent intent = new Intent(view.getContext(), ArticleDetailsActivity.class);
        view.getContext().startActivity(intent);
    }

    @Override
    public void openDetails(Article tarefa) {
        Intent intent = new Intent(view.getContext(), ArticleDetailsActivity.class);
        intent.putExtra(Constant.ATTR_TITLE, tarefa.getTitle());
        view.getContext().startActivity(intent);
    }

    @Override
    public void populateList(RecyclerView recyclerView) {
        /*ArrayAdapter<Article> adapter = new ArrayAdapter<>(
                view.getContext(),
                android.R.layout.simple_list_item_1,
                dao.findAll());*/
        /*ItemPocketAdapter adapter = new ItemPocketAdapter(
                view.getContext(),
                dao.findAll(),
                this);
        listView.setAdapter(adapter);*/
        ItemPocketRecyclerAdapter adapter = new
                ItemPocketRecyclerAdapter(view.getContext(), dao.findAll(), this);


        adapter.setClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                article = dao.findAll().get(position);
                openDetails(article);
            }


        });

        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void favoriteArticle(Article tarefa) {
        tarefa.setFavorite(!tarefa.isFavorite());
        dao.update(tarefa.getTitle(), tarefa);
    }

    public IArticleDao getDao(){
        return this.dao;
    }
}
