package spiderMethod;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class getCookies {

    public static String LOGIN_URL = "http://kdjw.hnust.edu.cn/";
    public static String url1 = "http://kdjw.hnust.edu.cn/Logon.do?method=logon&flag=sess";
    public static String url2 = "http://kdjw.hnust.edu.cn/Logon.do?method=logon";
    public static String url3 = "http://kdjw.hnust.edu.cn/jsxsd/xskb/xskb_list.do";
    public static String USER_AGENT = "User-Agent";
    public static String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36";
    public static String user;
    public static String password;
    public static Map<String, String> cookies = new HashMap<>();
    public static int flag; // 标志是否拿取成功,可用于登录密码是否正确，也可直接判断cookies的值


    public getCookies(String user, String password) {
        this.user = user;
        this.password = password;

    }

    public int getFlag() {
        return flag;
    }

    public Map getCookies() throws IOException {
        return log(user, password);
    }

    //获得dataString
    public static String get_dataString(Connection.Response response) throws IOException {

        String s = response.header("Set-Cookie"); //获得Set-Cookie
        String[] array = s.split(";| |,|=|/");
//        System.out.println(s);
        String JSESSIONID = "", SERVERID = "";
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
            if (array[i].equals("SERVERID")) {
                SERVERID = array[i + 1];
                i++;
            }
            if (array[i].equals("JSESSIONID")) {
                JSESSIONID = array[i + 1];
                i++;
            }
        }
        //存入新的cookies中
        cookies.put("JSESSIONID", JSESSIONID);
        cookies.put("SERVERID", SERVERID);
        System.out.println(cookies);

        Connection connection = Jsoup.connect(url1);
        connection.header(USER_AGENT, USER_AGENT_VALUE);
        Connection.Response rs = connection.cookies(cookies).execute();
        Document doc = Jsoup.parse(rs.body());
        System.out.println(doc.select("body").text());
        return doc.select("body").text();
    }




    public static Map log(String user, String password) throws IOException {

        try {
            Connection connection1 = Jsoup.connect(LOGIN_URL);

            //print(LOGIN_URL);
            connection1.header(USER_AGENT, USER_AGENT_VALUE);   // 配置模拟浏览器
            Connection.Response rs = connection1.execute();     // 获取响应

            String dataString = get_dataString(rs);
            String encoded = get_code.get_encoded(dataString, user, password);

            Document d1 = Jsoup.parse(rs.body());       // 通过Jsoup将返回信息转换为Dom树
            List<Element> eleList = d1.select("form");  // 获取提交form表单，可以通过查看页面源码代码得知
            // 获取cooking和表单属性
            Map<String, String> datas = new HashMap<>();
            for (int i = 0; i < eleList.size(); i++) {
                for (Element e : eleList.get(i).getAllElements()) {
                    // 设置用户名
                    if (e.attr("name").equals("userAccount")) {
                        e.attr("value", user);
                    }
                    // 设置用户密码
                    if (e.attr("name").equals("userPassword")) {
                        e.attr("value", "");
                    }
                    if (e.attr("name").equals("encoded")) {
                        e.attr("value", encoded);
                    }
                    // 排除空值表单属性
                    if (e.attr("name").length() > 0 && !e.attr("name").equals("loginForm")) {
                        datas.put(e.attr("name"), e.attr("value"));
                    }
                }
            }
            Connection con2 = Jsoup.connect(url2);
            con2.header(USER_AGENT, USER_AGENT_VALUE);
            System.out.println(cookies);
            // 设置cookie和post上面的map数据
            Connection.Response login = con2.followRedirects(false)
                    .method(Connection.Method.POST)
                    .data(datas).cookies(cookies)
                    .header("ContentType", "application/x-www-form-urlencoded")
                    .execute();
//        System.out.println(login.body());
//        System.out.println(login.cookies());
            String location = login.header("Location");//空
            System.out.println(location);
            Connection connection3 = Jsoup.connect(location);
            Connection.Response cdx = connection3.followRedirects(false)
                    .method(Connection.Method.GET)
                    .header("ContentType", "application/x-www-form-urlencoded")
                    .cookies(cookies)
                    .execute();
            // 打印，登陆成功后的信息
            if (cdx.statusCode() != 302)
                System.out.println("登陆失败");
            else {
                System.out.println(cdx.cookies());
                Map<String, String> map = cdx.cookies();
                for (String s : map.keySet()) {
                    System.out.println(s + " : " + map.get(s));
                }
                Connection con7 = Jsoup.connect(url3);
                cdx.cookies().put("SERVERID", cookies.get("SERVERID"));
            }

            System.out.println(cdx.cookies());
            flag = 1;
            return cdx.cookies();
        } catch (Exception e) {
            Map<String, String> st = new HashMap<String, String>();
            flag = 0;
            return st;
        }
    }
}

