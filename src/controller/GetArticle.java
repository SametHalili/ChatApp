package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class GetArticle extends RequestHandler
{
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    {
        String destination = "index.jsp";
        List<String> articles = this.getArticles(5);
        String articleNmbrStr = request.getParameter("articleNmbr");
        int articleNmbr = Integer.parseInt(articleNmbrStr);
        if(!(articles.get(articleNmbr - 1) == null))
        {
            destination = "article" + (articleNmbr) + ".jsp";
        }

        return destination;
    }

    private List<String> getArticles(int length)
    {
        List<String> articles = new ArrayList<>();

        for(int i = 0; i < 5; i++)
        {
            String iStr = "" + i;
            articles.add(iStr);
        }

        return articles;
    }
}
