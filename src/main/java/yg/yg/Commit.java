package yg.yg;//package com.example.yg;

//import net.mamoe.mirai.event.events.GroupMessageEvent;

import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class Commit {

    public static void main(String[] args) {
        Commit commit = new Commit();
        System.out.println(commit.handle("添加本周比赛 周4 fdszfdszzsfd"));
        System.out.println(commit.handle("比赛"));
        System.out.println(commit.handle("添加本周比赛 周4 fdszfdszzsfd"));
        System.out.println(commit.handle("比赛"));
        System.out.println(commit.handle("添加本周比赛 周4 fdszfdszzsfd"));
        System.out.println(commit.handle("比赛"));
        System.out.println(commit.handle("删除本周比赛  周41s"));
        System.out.println(commit.handle("比赛"));
        System.out.println(commit.handle("删除本周比赛 周4 1fds"));
        System.out.println("------------");
        System.out.println(commit.handle("添加下周比赛 周4 fdszfdszzsfd"));
        System.out.println(commit.handle("删除下周比赛 周4 1fds"));
        System.out.println(commit.handle("下周比赛"));
        System.out.println(commit.handle("本周比赛"));
    }

    private final YG yg = new YG();

    public String handle(String content) {
        yg.checkAndRefresh();
        //添加本周比赛
        String result = addThisWeekend(content);
        if (result != null) {
            return result;
        }
        //添加下周比赛
        result = addNextWeekend(content);
        if (result != null) {
            return result;
        }
        //获得本周比赛
        result = getThisWeekend(content);
        if (result != null) {
            return result;
        }
        //获得下周比赛
        result = getNextWeekend(content);
        if (result != null) {
            return result;
        }
        //删除本周比赛
        result = removeThisWeek(content);
        if (result != null) {
            return result;
        }
        //删除下周比赛
        result = removeNextWeek(content);
        if (result != null) {
            return result;
        }
        //查看指令
        if (content.equals("指令")) {
            return "注意有空格,空格不能多也不能少\n" +
                    "1.添加(下周)比赛 周<周几就填周几> <填内容>\n" +
                    "1.添加(下周)比赛 周x x\n" +
                    "2.删除比赛 周<周几就填周几> <填数字, 要删第几个就填数字几>\n" +
                    "2.删除比赛 周x x\n" +
                    "3.本周比赛 或者 比赛\n";
        }
        return null;
    }

    public String addThisWeekend(String content) {
        boolean a = content.startsWith("添加本周比赛");
        boolean b = content.startsWith("添加比赛");
        //以“添加本周比赛开头”或者以“添加比赛”开头，切长度大于15
        if ((a || b) && content.length() > 15) {
            char[] chars = content.toCharArray();
            int day;
            StringBuilder builder = new StringBuilder();
            for (int i = a ? 6 : 4; i < chars.length; i++) {
                //遍历到周字
                if (chars[i] != '周') {
                    continue;
                }
                //获取周的下一个字, 尝试解析为日期
                day = WeekCaseUtil.weekCaseInt(chars[++i]);
                if (day == 0) {
                    //解析失败, 输入错误
                    return null;
                }
                for (i = i + 1; i < chars.length; i++) {
                    char c = chars[i];
                    //日期解析成功后, 跳过下一个字之间的所有空格
                    if (c != ' ' && c != 13 && c != 10) {
                        //拼接剩余字符
                        for (; i < chars.length; i++) {
                            builder.append(chars[i]);
                        }
                        //保存
                        yg.addThisWeek(builder.toString(), day);
                        return "添加成功";
                    }
                }
            }
        }
        return null;
    }

    public String addNextWeekend(String content) {
        //添加本周比赛
        if (content.startsWith("添加下周比赛") && content.length() > 17) {
            char[] chars = content.toCharArray();
            int day;
            StringBuilder builder = new StringBuilder();
            for (int i = 6; i < chars.length; i++) {
                if (chars[i] == '周') {
                    day = WeekCaseUtil.weekCaseInt(chars[++i]);
                    if (day == 0) {
                        return null;
                    }
                    for (i = i + 1; i < chars.length; i++) {
                        char c = chars[i];
                        System.out.println((int) c);
                        if (c != ' ' && c != 13 && c != 10) {
                            for (; i < chars.length; i++) {
                                builder.append(chars[i]);
                            }
                            yg.addNextWeek(builder.toString(), day);
                            return "添加成功";
                        }
                    }
                }
            }
        }
        return null;
    }

    public String removeThisWeek(String content) {
        boolean b = content.startsWith("删除本周比赛");
        //删除本周比赛
        if ((b || content.startsWith("删除比赛")) && content.length() < 15) {
            char[] chars = content.toCharArray();
            int day;
            for (int i = b ? 6 : 4; i < chars.length; i++) {
                if (chars[i] == '周') {
                    day = WeekCaseUtil.weekCaseInt(chars[++i]);
                    if (day == 0) {
                        return null;
                    }
                    for (i = i + 1; i < chars.length; i++) {
                        if (chars[i] != ' ') {
                            int index = content.charAt(i) - 48 - 1;
                            if (index < 0) {
                                return null;
                            }
                            yg.removeThisWeek(day - 1, index);
                            return "删除成功";
                        }
                    }
                }
            }
        }
        return null;
    }

    public String removeNextWeek(String content) {
        //删除下周周比赛
        if (content.startsWith("删除下周比赛") && content.length() < 17) {
            char[] chars = content.toCharArray();
            int day;
            for (int i = 6; i < chars.length; i++) {
                if (chars[i] == '周') {
                    day = WeekCaseUtil.weekCaseInt(chars[++i]);
                    if (day == 0) {
                        return null;
                    }
                    for (i = i + 1; i < chars.length; i++) {
                        if (chars[i] != ' ') {
                            int index = content.charAt(i) - 48 - 1;
                            if (index < 0) {
                                return null;
                            }
                            yg.removeNextWeek(day - 1, index);
                            return "删除成功";
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获得本周比赛
     *
     * @param content 对话内容
     * @return 比赛内容
     */
    public String getThisWeekend(String content) {
        return "比赛".equals(content) || "本周比赛".equals(content) ? yg.getThisWeekend() : null;
    }

    /**
     * 获得下周比赛
     *
     * @param content 对话内容
     * @return 比赛内容
     */
    public String getNextWeekend(String content) {
        return "下周比赛".equals(content) ? yg.getNextWeekend() : null;
    }
}
