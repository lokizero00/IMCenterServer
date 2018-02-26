package com.loki.server.test;

public class BasicTest {
	
	
	public static void main(String[] args) {
		AbstractFactory f=new DefaultFactory();
		SampleA a=(SampleA) f.createSampleA();
		SampleB b=(SampleB) f.createSampleB();
		a.methodA();
		b.methodB();
	}
}
