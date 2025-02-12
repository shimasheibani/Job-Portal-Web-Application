import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        URL url = new URL("https://semnan.ac.ir/");
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        int ascii = inputStream.read();
        StringBuilder stringBuilder = new StringBuilder();
        while (ascii !=-1){
            stringBuilder.append((char)ascii);
            ascii = inputStream.read();
        }
        inputStream.close();
        System.out.println(stringBuilder);

    }
}