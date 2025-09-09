package py660.pyMC;

import java.util.TreeMap;

public class RomanNumeral {

    private final static TreeMap<Integer, String> map = new TreeMap<>();

    static {

        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

    }

    public static String toRoman(int number) {
        if (number == 0) {
            return "∅";
        } else if (number < 0) {
            number *= -1;
            return "-" + toRomanButPositiveNumbersOnly(number);
        }
        return toRomanButPositiveNumbersOnly(number);
    }

    private static String toRomanButPositiveNumbersOnly(int number) {
        int l = map.floorKey(number);
        if (number == l) {
            return map.get(number);
        }
        return map.get(l) + toRomanButPositiveNumbersOnly(number - l);
    }

}