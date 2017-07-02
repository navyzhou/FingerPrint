package com.yc.fingerprint;

import java.io.File;
import java.io.IOException;

public class Test {
	public static void main(String[] args) {
		try {
			Runtime mt =Runtime.getRuntime();
			//找到相对应的绝对路径。
			File  myfile =new File("Demo.exe");
			mt.exec(myfile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}   
	}
}
