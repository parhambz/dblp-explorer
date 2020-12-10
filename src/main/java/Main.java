import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
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
    public static Boolean isContainList(String s,List<Object> l){
        for(int i=0;i<l.size();i++){
            if (isContain((String) l.get(i),s)){return true;}
        }
        return false;
    }
}

public class Main {
    public static void main(String[] args) throws IOException{
        int n=5;
        String keyword="parham";


        Supplier<Stream<String>> articlesS
                = () -> {
            try {
                return Files.lines(Path.of(Util.filePath));
            } catch (IOException e) {
                e.printStackTrace();
                return Stream.of("");
            }
        }; ;

        Stream<String> tireStream=articlesS.get().filter(articleStr->Util.isContain(keyword,articleStr))
                .map(s->{ return new JSONObject(s); }).map(ja->(String)ja.get("id"));
        System.out.println("tire 1");

        List<Object> workList=tireStream.collect(Collectors.toList());

        for (int i=2;i<n+1;i++) {
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

            List<Object> finalWorkList = workList;
            List<JSONObject> objs = articlesS.get().filter(s -> Util.isContainList(s, finalWorkList)).map(s -> new JSONObject(s)).collect(Collectors.toList());
            Supplier<Stream<JSONObject>> objsS
                    = () -> {
                return objs.stream();
            };
            FileWriter finalMyWriter = myWriter;
            objsS.get().forEach(j -> {
                try {
                    finalMyWriter.write(j.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            workList = objsS.get().flatMap(j -> {
                JSONArray tempRef=null;
                try{
                tempRef = (JSONArray) j.get("references");}
                catch (JSONException e){}
                if (tempRef != null) {

                    return tempRef.toList().stream();
                }
                return Stream.of();
            }).collect(Collectors.toList());



        }

    }
}
