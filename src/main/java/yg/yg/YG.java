package yg.yg;

import java.time.LocalDate;

public class YG {

    //本周末
    private LocalDate weekend;
    //下周末
    private LocalDate nextWeekend;

    public YG() {
        this.thisDays = new Day[7];
        this.nextDays = new Day[7];
        checkAndRefresh();
    }

    private String thisHead;
    private String nextHead;
    private Day[] thisDays;

    private Day[] nextDays;

    public void checkAndRefresh() {
        LocalDate now = LocalDate.now();
        checkAndRefresh(now);
    }
    public void checkAndRefresh(LocalDate now) {
        int offset = now.getDayOfWeek().getValue();
        if (weekend == null || (offset >= 1 && weekend.isBefore(now))) {
            System.out.println(now);
            System.out.println(offset);
            LocalDate thisStartDate = now.minusDays(offset - 1);
            System.out.println(thisStartDate);
            LocalDate thisEndDate = now.plusDays(7 - offset);
            String thisStartDay = getFormatterDate(thisStartDate);
            String thisEndDay = getFormatterDate(thisEndDate);
            this.thisHead =  thisStartDay + " ~ " + thisEndDay + "   国服本周比赛\n" + "截至发布前\n";

            LocalDate nextStartDate = now.plusDays(8 - offset);
            LocalDate nextEndDate = now.plusDays(14 - offset);
            String nextStartDay = getFormatterDate(nextStartDate);
            String nextEndDay = getFormatterDate(nextEndDate);
            this.nextHead = nextStartDay + " ~ " + nextEndDay + "   国服下周比赛\n" + "截至发布前\n";
            this.thisDays = nextDays;
            this.nextDays = new Day[7];
            weekend = now.plusDays(7 - offset);
            nextWeekend = weekend.plusDays(7);
        }
    }
    public String getFormatterDate(LocalDate date) {
        int month = date.getMonth().getValue();
        int day = date.getDayOfMonth();
        return month + "." + day;
    }
    public String getThisWeekend() {
        return get(thisDays, thisHead);
    }

    public String getNextWeekend() {
        return get(nextDays, nextHead);
    }
    public String get(Day[] days, String head) {
        StringBuilder result = new StringBuilder(head);
        for (Day d : days) {
            if (d != null) {
                result.append(d);
            }
        }
        return result.toString();
    }

    public void removeThisWeek(int day, int index) {
        remove(day, index, thisDays);
    }
    public void removeNextWeek(int day, int index) {
        remove(day, index, nextDays);
    }


    public void remove(int day, int index, Day[] days) {
        Day d = days[day];
        if (d != null) {
            d.remove(index);
            if (d.isEmpty()) {
                days[day] = null;
            }
        }
    }

    public void addNextWeek(String content, int day) {
        add(content, day, nextDays, nextWeekend);
    }

    public void addThisWeek(String content, int day) {
        add(content, day, thisDays, weekend);
    }

    private void add(String content, int day, Day[] days, LocalDate weekend) {
        Day d = days[day - 1];
        if (d == null) {
            LocalDate date = weekend.minusDays(7 - day);
            String headDay = date.getMonth().getValue() + "." + date.getDayOfMonth() + " 周" +  WeekCaseUtil.weekCaseString(day);
            d = new  Day(headDay);
            days[day - 1] = d;
        }
        d.addContent(content);
    }

}
