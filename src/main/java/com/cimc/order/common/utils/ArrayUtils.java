package com.cimc.order.common.utils;

import java.util.Arrays;

public class ArrayUtils   {
    /**
     * 数组逆序
     * @param arr
     */
    public static void adverses(Object[] arr){
        for(int start=0,end=arr.length-1;start<=end;start++,end--){
            Object temp=arr[start];
            arr[start]=arr[end];
            arr[end]=temp;
        }
    }

}
