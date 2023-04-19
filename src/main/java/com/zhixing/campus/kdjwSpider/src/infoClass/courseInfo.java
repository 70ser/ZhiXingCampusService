package infoClass;
// 课表
public class courseInfo {

    // 结果很长 分为两部分
    public int x; //  节次
    public int y; //  周次
    public String info1; // 查询结果1
    public String info2; // 查询结果2

    public courseInfo(int x, int y, String info1, String info2) {
        this.x = x;
        this.y = y;
        this.info1 = info1;
        this.info2 = info2;

    }
}
