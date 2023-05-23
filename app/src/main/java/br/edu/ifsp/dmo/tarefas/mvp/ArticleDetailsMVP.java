package br.edu.ifsp.dmo.tarefas.mvp;

import android.os.Bundle;

public interface ArticleDetailsMVP {

    interface View{
        void updateUI(String title, String url);

        Bundle getBundle();

        void showToast(String message);

        void close();
    }

    interface Presenter{
        void deatach();

        void verifyUpdate();

        void saveArticle(String title, String url);
    }
}
