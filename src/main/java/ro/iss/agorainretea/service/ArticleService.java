package ro.iss.agorainretea.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import ro.iss.agorainretea.domain.Article;
import ro.iss.agorainretea.domain.ArticleDto;
import ro.iss.agorainretea.domain.User;
import ro.iss.agorainretea.exceptions.ServiceException;
import ro.iss.agorainretea.repository.ArticleRepository;
import ro.iss.agorainretea.repository.UserRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    public Optional<Article> findById(int id) {
        return articleRepository.findById(id);
    }

    public List<Article> getAllPublishedArticles() {
        return articleRepository.findAllByIsPublished(true);
    }

    public List<Article> getArticlesByUser(long userId) {
        return articleRepository.findAllByAuthor_Id(userId);
    }

    public List<Article> getArticlesByGroup(int groupId) {
        List<User> allAuthors = userRepository.findAllByTeam_Id(groupId);

        List<Article> allArticles = new ArrayList<>();
        for(User u: allAuthors) {
            allArticles.addAll(articleRepository.findAllByAuthor_IdAndIsPublished(u.getId(), true));
        }

        return allArticles;
    }

    public ArticleDto getArticleContent(int id) {
        var article = articleRepository.findById(id).orElse(null);

        if(article == null)
            return null;

        File file = new File(article.getFilePath());
        try {
            StringBuilder content = new StringBuilder();
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                content.append(data);
            }
            ArticleDto res = new ArticleDto(article.getId(), article.getTitle(), article.getAuthor().getId(), article.getIsPublished(), content.toString());

            return res;
        }
        catch(IOException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    private void publishArticle(Article article, boolean isPublished) {
        article.setIsPublished(isPublished);
        articleRepository.save(article);
    }

    public void updateArticle(int id, String newTitle, String newText, boolean isPublished) {
        var article = articleRepository.findById(id).orElse(null);

        if(article == null)
            throw new ServiceException("Article not found!");

        //daca vrem sa publicam, doar vom publica si aia e
        if(isPublished != article.getIsPublished()) {
            publishArticle(article, isPublished);
            return;
        }
        //teoretic ar trebui sa verif sa nu existe 2 articole cu acelasi nume sau sa modif cum le tinem minte
        //|---> facem niste schimbari <->numele fisierului cu art o sa fie titlu_authorId - asa, singura pb o sa fie daca
        //un user are 2 articole cu acelasi nume

        String newFilePath = "src/main/resources/static/articles/" + newTitle + "_" + article.getAuthor().getId() + ".txt";
        File file = new File(newFilePath);
        try {
            if(file.exists()) {
                if(!file.delete()) {
                    throw new ServiceException("Error on file handling!");
                }
            }
            if(file.createNewFile()) {
                try(FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write(newText);
                }
            }
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }

        article.setTitle(newTitle);
        article.setFilePath(newFilePath);
        //adaugare validare daca e in starea pe care o voiam
        articleRepository.save(article);
    }

    public Article saveArticle(long authorId, String title, String text) {
        var authorOpt = userRepository.findById(authorId);

        if(authorOpt.isEmpty()) {
            throw new ServiceException("Author can't be found!");
        }

        var author = authorOpt.get();
        //adaugare validare
        var article = new Article(null, author, title, text, false);
        String filePath = "src/main/resources/static/articles/" + title + "_" + authorId + ".txt";
        File file = new File(filePath);

        if(file.exists()) {
            throw new ServiceException("Article titles for one user must be unique!");
        }

        try {
            if(file.createNewFile()) {
                try(FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write(text);
                }
            }
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }

        article.setFilePath(filePath);
        articleRepository.save(article);
        return article;
    }
}
