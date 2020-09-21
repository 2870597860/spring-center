package com.xd.batch;

import java.util.Arrays;

public class QuanPaiLie {

    public QuanPaiLie() {
    }

    public static void permute(int[] array, boolean[] use,int start) {
        if (start == array.length) {
            System.out.println(Arrays.toString(array));
        } else {
            for(int i = start; i < array.length; ++i) {
                if((i > 0 && array[i]==array[i-1] && !use[i-1])){
                    continue;
                }
                swap(array, start, i);
                use[i] = true;
                permute(array, use,start + 1);
                use[i]=false;
                swap(array, start, i);
            }
        }

    }

    private static void swap(int[] array, int s, int i) {
        int t = array[s];
        array[s] = array[i];
        array[i] = t;
    }

    public static void main(String[] args) {
        int[] array = new int[]{1, 1, 3};
        boolean[] use = new boolean[array.length];
        permute(array, use,0);
    }
}
