package yg.yg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day {
    private final String dayHead;
    private final List<String> content;

    public static void main(String[] args) {

        Map<String, Long> map = new HashMap();
        System.out.println(
                map.computeIfAbsent("sourcePlayerName", key -> 0L));
        System.out.println(map.get("sourcePlayerName"));
    }

    public Day(String dayHead) {
        this.dayHead = dayHead;
        this.content = new ArrayList<>();
    }

    public void addContent(String content) {
        this.content.add(content);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("\n");
        result.append(dayHead);
        result.append("\n");
        for (String s : content) {
            result.append(s);
            result.append("\n\n");
        }
        return result.toString();
    }

    public void remove(int index) {
        if (content.size() >= (index + 1)) {
            content.remove(index);
        }
    }

    public boolean isEmpty() {
        return content.isEmpty();
    }
}
