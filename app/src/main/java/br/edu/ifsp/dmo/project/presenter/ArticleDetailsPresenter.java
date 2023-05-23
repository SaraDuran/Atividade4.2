package br.edu.ifsp.dmo.project.presenter;

import android.os.Bundle;

import br.edu.ifsp.dmo.project.model.dao.ArticleDaoSingleton;
import br.edu.ifsp.dmo.project.model.dao.IArticleDao;
import br.edu.ifsp.dmo.project.model.entities.Article;
import br.edu.ifsp.dmo.project.mvp.ArticleDetailsMVP;
import br.edu.ifsp.dmo.project.utils.Constant;

public class ArticleDetailsPresenter implements ArticleDetailsMVP.Presenter {

    private ArticleDetailsMVP.View view;
    private Article article;
    private IArticleDao dao;

    public ArticleDetailsPresenter(ArticleDetailsMVP.View view) {
        this.view = view;
        article = null;
        dao = ArticleDaoSingleton.getInstance();
    }

    @Override
    public void deatach() {
        this.view = null;
    }

    @Override
    public void verifyUpdate() {
        String title;
        Bundle bundle = view.getBundle();
        if(bundle != null){
            title = bundle.getString(Constant.ATTR_TITLE);
            article = dao.findByTitle(title);
            view.updateUI(article.getTitle(), article.getUrl());
        }
    }

    @Override
    public void saveArticle(String title, String url) {

        if(article == null){
            //New article
            article = new Article(url, title);
            if(article.getTitle() != null){
                dao.create(article);
                view.showToast("Adicionado com sucesso.");
                view.close();
            }else{
                view.showToast("Erro ao adicionar o elemento.");
            }
        }else{
            //Update a existent article
            String oldTitle = article.getTitle();
            Article newArticle = new Article(url, title, article.isFavorite());
            if(dao.update(oldTitle, newArticle)){
                view.showToast("Atualizado com sucesso.");
                view.close();
            }else{
                view.showToast("Erro ao atualizar o elemento.");
            }
        }
    }
}
