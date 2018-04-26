package org.wing.jfx.game.core.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

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
public class WLog {
	public static boolean isCanLogInFile = false;

	public static void log(String tag, String content) {
		System.out.println(tag + ":" + content);
		logInFile(tag, content);
	}

	public static void log(String content) {
		System.out.println(content);
		logInFile(content);
	}

	public static void logInFile(String tag, String content) {
		if (isCanLogInFile) {
			try {
				BufferedWriter br = new BufferedWriter(new FileWriter(new File("log.txt"), true));
				br.append(new Date().toString() + "----" + tag + ":" + content + System.getProperty("line.separator"));
				br.flush();
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void logInFile(String content) {
		if (isCanLogInFile) {
			try {
				BufferedWriter br = new BufferedWriter(new FileWriter(new File("log.txt"), true));
				br.append(new Date().toString() + "----" + content + System.getProperty("line.separator"));
				br.flush();
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
