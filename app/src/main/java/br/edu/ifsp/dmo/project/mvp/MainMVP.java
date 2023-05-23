package br.edu.ifsp.dmo.project.mvp;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifsp.dmo.project.model.dao.IArticleDao;
import br.edu.ifsp.dmo.project.model.entities.Article;

public interface MainMVP {

    interface View{
        Context getContext();
    }

    interface Presenter{
        void deatach();

        void openDetails();

        void openDetails(Article article);

        void populateList(RecyclerView recyclerView);

        void favoriteArticle(Article article);

        IArticleDao getDao();
    }
}
