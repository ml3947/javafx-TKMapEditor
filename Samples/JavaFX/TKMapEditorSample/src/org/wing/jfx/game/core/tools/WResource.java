package org.wing.jfx.game.core.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * Copyright 2013
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @project wjfx-game-simple
 * @author wing mei
 * @email：wingfourever@gmail.com
 */
public class WResource {

	/*
	 * URI Path
	 **/
	public static String getResourceInRes(String filename){
		return Thread.currentThread().getContextClassLoader().getResource("res/" + filename).toString();
	}
	
	public static String getResourcePathInRes(String filename){
		return Thread.currentThread().getContextClassLoader().getResource("res/" + filename).getPath();
	}
	
	public static InputStream getResourceAsStreamInRes(String filename){
		return Thread.currentThread().getContextClassLoader().getResourceAsStream("res/" + filename);
	}
	
	public static String loadScript(String path){
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(path)));
			String line = br.readLine();
			while(line != null){
				sb.append(line);
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String loadScriptFromRes(String path){
		StringBuffer sb = new StringBuffer();
		try {
			//String pStr = getResourcePathInRes(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(getResourceAsStreamInRes(path)));
			String line = br.readLine();
			while(line != null){
				sb.append(line + System.getProperty("line.separator"));
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String loadScriptFromRes(String path ,String charset){
		StringBuffer sb = new StringBuffer();
		try {
			//String pStr = getResourcePathInRes(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(getResourceAsStreamInRes(path),charset));
			String line = br.readLine();
			while(line != null){
				sb.append(line + System.getProperty("line.separator"));
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		WLog.logInFile(sb.toString());
		return sb.toString();
	}
	
	public static String loadScriptFromInputStream(InputStream is){
		StringBuffer sb = new StringBuffer();
		try {
			//String pStr = getResourcePathInRes(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = br.readLine();
			while(line != null){
				sb.append(line + System.getProperty("line.separator"));
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String loadScriptFromInputStream(InputStream is,String charset){
		StringBuffer sb = new StringBuffer();
		try {
			//String pStr = getResourcePathInRes(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = br.readLine();
			while(line != null){
				sb.append(new String(line.getBytes(), charset) + System.getProperty("line.separator"));
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	
}
