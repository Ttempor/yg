package yg.yg;

public class WeekCaseUtil {
    public static String weekCaseString(int day) {
        switch (day) {
            case 1 : {
                return "一";
            }
            case 2 : {
                return "二";
            }
            case 3 : {
                return "三";
            }
            case 4 : {
                return "四";
            }
            case 5 : {
                return "五";
            }
            case 6 : {
                return "六";
            }
            case 7 : {
                return "日";
            }
            default : {
                return "null";
            }
        }
    }

    public static int weekCaseInt(char day) {
        switch (day) {
            case '一' : {
                return 1;
            }
            case '二' : {
                return 2;
            }
            case '三' : {
                return 3;
            }
            case '四' : {
                return 4;
            }
            case '五' : {
                return 5;
            }
            case '六' : {
                return 6;
            }
            case '日' : {
                return 7;
            }
            case '1' : {
                return 1;
            }
            case '2' : {
                return 2;
            }
            case '3' : {
                return 3;
            }
            case '4' : {
                return 4;
            }
            case '5' : {
                return 5;
            }
            case '6' : {
                return 6;
            }
            case '7' : {
                return 7;
            }
            default : {
                return 0;
            }
        }
    }

}
