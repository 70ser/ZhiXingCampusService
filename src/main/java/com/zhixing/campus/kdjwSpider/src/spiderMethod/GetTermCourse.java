package spiderMethod;

import infoClass.termCourse;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// 查询本学期课表
public class GetTermCourse {
    private String url = "http://kdjw.hnust.edu.cn/jsxsd/framework/main_index_loadwdkc.jsp?xnxq01id=2020-2021-1";
    private Connection con;
    private static Map<String, String> cookies;
    private Connection.Response res;

    public GetTermCourse(Map cookies) throws IOException {
        this.cookies = cookies;
    }

    public List<termCourse> getData() throws IOException {
        con = Jsoup.connect(url);
        res = con.followRedirects(false)
                .method(Connection.Method.POST)
                .header("ContentType", "application/x-www-form-urlencoded")
                .cookies(cookies)
                .execute();

        System.out.println(Jsoup.parse(res.body()));
        List<Element> g = Jsoup.parse(res.body()).select("body > ul > li > ul > li");


        List<termCourse> Lc = new ArrayList<>();

        for (Element e : g) {
            Lc.add(new termCourse(spl(e.text())));
        }


//        System.out.println("bvvvvvvvvvvvvvvvvvvvv" + Lc.size());
        for (termCourse e : Lc) {
            System.out.println(e.info);
        }
        return Lc;
    }


    public String spl(String str) {
        StringBuffer s = new StringBuffer(str);
        for (int index = 0; index < s.length(); index++) {
            if (index % 32 == 0) {
                s.insert(index, "\n");
            }
        }
        return s.toString();
    }
}