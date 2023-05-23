package br.edu.ifsp.dmo.tarefas.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.dmo.tarefas.model.entities.Article;
import br.edu.ifsp.dmo.tarefas.model.entities.Tag;

public class ArticleDaoSingleton implements IArticleDao{
    private static ArticleDaoSingleton instance = null;
    private List<Article> dataset;

    private ArticleDaoSingleton() {
        dataset = new ArrayList<>();
    }

    public static ArticleDaoSingleton getInstance(){
        if(instance == null)
            instance = new ArticleDaoSingleton();
        return instance;
    }

    @Override
    public void create(Article tarefa) {
        if(tarefa != null){
            dataset.add(tarefa);
        }
    }


    @Override
    public void favorite(Article tarefa) {
        if(tarefa != null){
          Article tAux = dataset.stream().filter(t -> t.getTitle().equals(tarefa.getTitle())).findFirst().get();
            dataset.remove(tAux);
            dataset.add(0, tAux);
        }
    }

    @Override
    public boolean update(String oldTitle, Article article) {
        Article inDataset;
        inDataset = dataset.stream()
                .filter(article1 -> article1.getTitle().equals(oldTitle))
                .findAny()
                .orElse(null);
        if(inDataset != null){
            inDataset.setTitle(article.getTitle());
            inDataset.setUrl(article.getUrl());
            inDataset.setFavorite(article.isFavorite());
            inDataset.getTags().clear();
            inDataset.getTags().addAll(article.getTags());
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Article article) {
        return dataset.remove(article);
    }

    @Override
    public Article findByTitle(String title) {
        return dataset.stream()
                .filter(article -> article.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Article> findByTag(Tag tag) {
        List<Article> selection = new ArrayList<>();
        for(Article a : dataset){
            for(Tag t : a.getTags()){
                if(t.getTagName().equals(tag.getTagName())){
                    selection.add(a);
                }
            }
        }
        return selection;
    }

    @Override
    public List<Article> findAll() {
        return dataset;
    }
}
