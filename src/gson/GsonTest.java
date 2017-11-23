package gson;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by jiang on 17/10/17.
 */
public class GsonTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        JsonObject json = new JsonObject();
        json.addProperty("a",1);

        JsonObject json2 =new JsonObject();
        json2.addProperty("cc",33);

        json.addProperty("data","%7b%22cc%22%3a33%7d");
        json.add("data2",json2);

        String str = json.toString();
        byte[] byteStr = str.getBytes();
        JsonObject result = new JsonParser().parse(new String(byteStr)).getAsJsonObject();
        String decodeStr =URLDecoder.decode(result.get("data").toString(),"utf-8");
        System.out.println(decodeStr.replaceAll("\"",""));
        decodeStr = decodeStr.replaceAll("\"","");
        JsonReader reader = new JsonReader(new StringReader(decodeStr));
        System.out.println("out:"+new JsonParser().parse(reader).getAsJsonObject());
        // System.out.println(new JsonParser().parse());
        System.out.println(result);
    }
}
