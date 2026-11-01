package task1;

public class Main {
    public static void main(String[] args) {

        SuperCustomHashMap<String, Integer> map = new SuperCustomHashMap<>();

        map.put("ты", 5);
        map.put("я", 1007);
        map.put(null, 0);

        System.out.println(map.get("ты"));
        System.out.println(map.get("я"));
        System.out.println(map.get(null));

        Integer oldValue = map.put("ты", 100);
        System.out.println(oldValue);
        System.out.println(map.get("ты"));

        map.remove(null);
        System.out.println(map.get(null));

        System.out.println(map.size());
        System.out.println(map.isEmpty());
    }
}