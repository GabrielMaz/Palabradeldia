package com.palabradeldia.palabradeldia;

public class Calculate {

    public static int calculatePercentage(int likes, int dislikes) {
        int total = likes + dislikes;
        if (total != 0) {
            return likes * 100 / total;
        }

        return 0;
    }
}
