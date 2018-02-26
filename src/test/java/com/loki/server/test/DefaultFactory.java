package com.loki.server.test;

public class DefaultFactory extends AbstractFactory{

	@Override
	public Sample createSampleA() {
		
		return new SampleA();
	}

	@Override
	public Sample createSampleB() {
		return new SampleB();
	}

}
