package code.guide.utils;

public class Handler {

    public static void handle() throws Exception {
    }


    public static int countVariants(int n) {
        int sum = 0;
        for(int i = 1; i <=n; i++) {
            sum += factorial(n)/factorial(i)/factorial(n - i);
        }
        return sum;
    }

    public static int factorial(int n) {
        if(n < 2) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }



}
