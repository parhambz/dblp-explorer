
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Util{
    static String basePath="C:\\Users\\bghpa\\Desktop\\New folder\\dblp-explorer";
    static String filePath=basePath+"\\src\\main\\java\\dblp_papers_v11.txt";
    static String resPath=basePath;
    public static Boolean isContain(String keyword,String article){
        return article.contains(keyword);
    }
    public static Stream<Object> getReferencesStream(JSONObject j){
        System.out.println(j.get("id"));
        JSONArray tempRef=null;
        try {
            tempRef=(JSONArray) j.get("references");

        }catch (JSONException e){}
        if(tempRef==null){return Stream.of();}
    return tempRef.toList().stream();
    }

}

public class Main {

    public static JSONObject findArticle(String id,Stream<String> articles){

        List<JSONObject> res = articles.map(s->{ return new JSONObject(s); })
                .filter(j->{
                        if (j.get("id")==id){return true;}else{return false;}
                        }).collect(Collectors.toList());
        return res.get(0);
    }
    public static void main(String[] args) throws IOException{
        int n=5;
        String keyword="parham";



        ArrayList<Stream<Object> > ids = new ArrayList<Stream<Object> >(n);

        Supplier<Stream<String>> articlesS
                = () -> {
            try {
                return Files.lines(Path.of(Util.filePath));
            } catch (IOException e) {
                e.printStackTrace();
                return Stream.of("");
            }
        }; ;
        Stream<String> articles = Files.lines(Path.of(Util.filePath));
        Stream<Object> tireStream=articlesS.get().filter(articleStr->Util.isContain(keyword,articleStr))
                .map(s->{ return new JSONObject(s); }).map(ja->ja.get("id"));
        System.out.println("tire 1");


                //.flatMap(art-> Util.getReferencesStream(art));

                /**/

        Stream<Object>workStream=tireStream.collect(Collectors.toList()).stream();
        IntStream.range(2,n+1).forEach(i-> {
            System.out.print("tire");
            System.out.println(i);

                    String resPath = Util.resPath + "\\tire" + String.valueOf(i) + ".txt";
                    File fileObj = new File(resPath);
            try {
                fileObj.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileWriter myWriter = null;
            try {
                myWriter = new FileWriter(resPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileWriter finalMyWriter = myWriter;

            List<Object> next=workStream
                            .flatMap(parentId -> Util.getReferencesStream(findArticle((String) parentId, articlesS.get()))).collect(Collectors.toList());
            System.out.println(next);
            next.stream().forEach(artId -> {
                        JSONObject artJ = findArticle((String) artId, articlesS.get());
                        try {
                            finalMyWriter.write(artJ.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    });

                });

                    /*.forEach(id-> {
                        JSONObject art = findArticle((String) id,articles);
                        try {
                            myWriter.write(art.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Stream<Object> tempRef= Util.getReferencesStream(art);
                        if(tempRef!=null){
                        tempRef.forEach(refId->{
                                tempIdNext.add((String) refId);
                        });

                        }
                        }
                        );
            tempId= tempIdNext.stream();*/


    }
}
