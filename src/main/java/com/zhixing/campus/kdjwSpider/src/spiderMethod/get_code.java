package spiderMethod;

public class get_code {



    public static String get_encoded(String dataString, String user, String password) {
        // 重写 js加密函数
        String scode = dataString.split("#")[0];
        String sxh = dataString.split("#")[1];
        String code = user + "%%%" + password;
        String encoded = "";
        for (int i = 0; i < code.length(); i++) {
            if (i < 20) {
                String ss = sxh.substring(i, i + 1);
                int s = Integer.parseInt(ss);
                encoded = encoded + code.substring(i, i + 1) + scode.substring(0, s);
                scode = scode.substring(s, scode.length());
            } else {
                encoded = encoded + code.substring(i, code.length());
                i = code.length();
            }
        }
        return encoded;
    }
}
