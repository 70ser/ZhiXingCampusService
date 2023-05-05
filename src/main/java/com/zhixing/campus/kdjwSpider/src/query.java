import spiderMethod.getCookies;
import spiderMethod.GetCourseGrade;
import spiderMethod.GetTermCourse;
import spiderMethod.GetLevelRes;
import spiderMethod.GetExamArrange;
import spiderMethod.GetCourseInfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class query {

    private static String username;
    private static String password;
    private static Map<String, String> cookies = new HashMap<String, String>();

    public static void main(String[] args) throws IOException {
        Scanner cin = new Scanner(System.in);
        username = cin.next();
        password = cin.next();
        // 获取 cookies
        getCookies gc = new getCookies(username, password);

        cookies = gc.getCookies();

        if (gc.getFlag() == 0) {
            System.out.println("账户或者密码错误");
        } else {
// 使用 getData 获取爬取的数据，具体传入的数据要结合不同类使用
//            GetExamArrange GEA = new GetExamArrange(cookies);
//            GetTermCourse GTC = new GetTermCourse(cookies);
//            GetLevelRes GLR = new GetLevelRes(cookies);
//            GetCourseGrade GCG = new GetCourseGrade(cookies);
//            GetCourseInfo GCI = new GetCourseInfo(cookies);
//            System.out.println(GCI.getData( "7", "2019-2020-1"));
//            System.out.println(GLR.getData());
            System.out.println("爬取成功");
        }
    }
}
