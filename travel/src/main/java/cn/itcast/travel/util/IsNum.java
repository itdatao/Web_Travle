package cn.itcast.travel.util;

public class IsNum {
    public static boolean isNumeric(String str){
        try {
            if (str != null && str.length() > 0) {
                for (int i = 0; i < str.length(); i++) {
                    System.out.println(str.charAt(i));
                    if (!Character.isDigit(str.charAt(i))) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }

    }
}
