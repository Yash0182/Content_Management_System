package com;

import java.util.ArrayList;
import java.util.List;

public class TestList {
	static ArrayList<Integer> list = new ArrayList<Integer>() ;
	static int a=0;
	static int[] p =new int[2];
	private int test(ArrayList<Integer> list, int i)
	{
		System.out.println(i);
		list.add(i);
		if(i==5)
		{return 0;}
		else
		{
			test(list,i+1);
		}
		System.out.println("Inside methid"+list);
		System.out.println(i);
		return i;
	}
	
	public static void main(String args[])
	{
		
		p[0]=2;
		//System.out.println(p[3]);
		String a= "123";
		System.out.println((a.charAt(0)-65));
		
		System.out.println(p[0]);
		System.out.println(list);
		System.out.println(a);
	//new TestList().test(list,a);
	System.out.println(list);
	System.out.println(a);
	}

}
