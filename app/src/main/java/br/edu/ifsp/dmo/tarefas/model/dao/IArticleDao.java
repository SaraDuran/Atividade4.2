package br.edu.ifsp.dmo.tarefas.model.dao;

import java.util.List;

import br.edu.ifsp.dmo.tarefas.model.entities.Article;
import br.edu.ifsp.dmo.tarefas.model.entities.Tag;

public interface IArticleDao {
    void create(Article article);

    boolean update(String oldTitle, Article article);

    boolean delete(Article article);

    Article findByTitle(String title);

    List<Article> findByTag(Tag tag);

    List<Article> findAll();

    void favorite(Article tarefa);
}
