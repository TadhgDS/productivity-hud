import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.security.auth.login.Configuration;
import java.io.IOException;
import java.util.ArrayList;


public class fiskArticle{

    ArrayList<String> getURLs(){
        ArrayList<String> articleURLs = new ArrayList<String>();
        try{
            Document doc = Jsoup.connect("http://www.independent.co.uk/biography/robert-fisk").get();

            //<div class="article news" >
            Elements elements = doc.body().select("div.article.news");

            Elements articleLinks = elements.select("a");
            for (Element link : articleLinks) {
                String absHref = link.attr("abs:href"); // "http://jsoup.org/"
                articleURLs.add(absHref);

            }
        } catch (IOException e){e.printStackTrace();}

        return articleURLs;
    }

    /*Article[]*/void getArticlesFromURLs(ArrayList<String> urls){
        for(String url : urls){
            try{
                Document doc = Jsoup.connect(url).get();
                System.out.println(url);

              //  String url = urls.get(0);





                Element body = doc.body();
                System.out.println("1");
                String htmlArticle = doc.select("p").toString();
                //int location = htmlArticle.indexOf()
                Elements elements = doc.body().select("p.error");



                String stripped = htmlArticle.substring(htmlArticle.indexOf("dateline"));
                if(htmlArticle.indexOf("orangeFullstop") > 0){
                    stripped = htmlArticle.substring(0, htmlArticle.indexOf("orangeFullstop"));
                }
                //String moreStripped = stripped.substring(htmlArticle.indexOf("</p>"));
                //stripped = stripped.substring(htmlArticle.indexOf("<p>") - 4);


                System.out.println("3");
                String moreStripped = stripped.replace(elements.toString(),"");
                for(Element elem : elements){
                    moreStripped = moreStripped.replace(elem.toString(),"");
                }

                System.out.println("4");
                System.out.println("Here !!!!!!! ----->>> " + moreStripped.indexOf("a href"));

              //  while(moreStripped.indexOf("a href") > 0){
               //     //write this
              //  }

/*
                if(moreStripped.indexOf("a href") > 0
                        && moreStripped.indexOf("a href") < moreStripped.indexOf("<span class")){
                    moreStripped = moreStripped.substring(0,moreStripped.indexOf("a href"));
                }
*/
                /*
                moreStripped = moreStripped.substring(0,moreStripped.indexOf("<span class"));
                moreStripped = moreStripped.substring(moreStripped.indexOf(">"));
                moreStripped = moreStripped.replace("<p>","");
                moreStripped = moreStripped.replace("</p>","");
                */
                System.out.println(moreStripped);



            }
            catch (IOException e){e.printStackTrace();}

        }
    }


    public static void main(String[] args) {

        fiskArticle anArticle = new fiskArticle();

        System.out.println(anArticle.getURLs());
        anArticle.getArticlesFromURLs(anArticle.getURLs());



    }

}