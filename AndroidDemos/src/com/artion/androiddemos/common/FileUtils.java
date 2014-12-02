package com.artion.androiddemos.common;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;

import android.content.Context;

/**
 * 文件相关常用操作类
 * @author jinsheng_cai
 * @since 2014-12-01
 */
public class FileUtils {
	
	public static final String FILE_SIZE_GB = " GB";
	public static final String FILE_SIZE_MB = " MB";
	public static final String FILE_SIZE_KB = " KB";
	public static final String FILE_SIZE_B = " B";
	
	public static boolean checkMkdirs(String filePath) {
		if(filePath == null) {
			return false;
		}
		if(isFileExists(filePath)) {
			return true;
		}
		File file = new File(filePath);
		return file.mkdirs();
	}
	
	public static boolean isFileExists(String filePath) {
		if(filePath == null) {
			return false;
		}
		File file = new File(filePath);
		return file.exists();
	}
	
	public static void delDirOrFile(File dir) {
		if(dir == null) {
			return ;
		}
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isFile()) {
					file.delete();
				} else {
					delDirOrFile(file);
				}
			}
		} else {
			dir.delete();
		}
	}
	
	public static String getFormatSize(long size) {
		String result = "";
		if ((size / 1024) >= 1) {
			size /= 1024;
			if (size / 1024 >= 1) {
				// 解决当最后一位为0时，未显示出来的问题
				double value = size * 1.0 / 1024;
				DecimalFormat df = new DecimalFormat("#.00");
				result = df.format(value) + FILE_SIZE_MB;
				// result=new
				// BigDecimal(fileSize*1.0/1024).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue()+" MB";
			} else {
				result = size + FILE_SIZE_KB;
			}
		} else {
			result = size + FILE_SIZE_B;
		}
		return result;
	}
	
	public static long getDirOrFileSize(String filePath, boolean isRecursion) {
		if(filePath == null || isFileExists(filePath)) {
			return 0;
		}
		File dir = new File(filePath);
		long size = 0;
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isFile()) {
					size += dir.length();
				} else {
					if(isRecursion) {
						size += getDirOrFileSize(file.getAbsolutePath(), isRecursion);
					}
				}
			}
		} else {
			size += dir.length();
		}
		return size;
	}
	
	/*************************************************************************************/
	/***内部文件操作**************************************************************************/
	/*************************************************************************************/
	/**
	 * 将对象写入内部文件
	 * @param ctx
	 * @param fileName
	 * @param obj
	 */
	public static boolean saveInfosToFile(Context ctx, String fileName, Object obj) {
		if(fileName == null || obj == null){
			return false;
		}
		
		byte[] infos = objectToByteArray(obj);
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			fos = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
			bos = new BufferedOutputStream(fos);
			bos.write(infos);
			
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(bos != null) {
				try {
					bos.close();
					bos = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	/**
	 * 从内部文件中读取后得到对象
	 * @param ctx
	 * @param fileName
	 * @return
	 */
	public static Object getInfosFromFile(Context ctx, String fileName) {
		if(fileName == null){
			return null;
		}
		FileInputStream fis = null;
		try {
			fis = ctx.openFileInput(fileName);
			int len = fis.available();
			byte[] infos = new byte[len];
			byte[] tmpBytes = new byte[100];
			int byteRead = 0;
			int readLen = 0;
			while((byteRead = fis.read(tmpBytes)) != -1){
				System.arraycopy(tmpBytes, 0, infos, readLen, byteRead);
				readLen += byteRead;
			}
			
			return byteArraytoObject(infos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fis != null) {
				try {
					fis.close();
					fis = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 删除内部文件
	 * @param ctx
	 * @param fileName
	 */
	public static boolean deleteSaveFile(Context ctx, String fileName) {
		if(fileName == null){
			return false;
		}
		
		return ctx.deleteFile(fileName);
	}
	
	/**
	 * 对象转数组
	 * @param obj
	 * @return
	 */
	public static byte[] objectToByteArray (Object obj) {   
		byte[] bytes = null;   
		ByteArrayOutputStream bos = new ByteArrayOutputStream();  
		ObjectOutputStream oos = null;
		try {     
			oos = new ObjectOutputStream(bos);      
			oos.writeObject(obj);     
			oos.flush();      
			bytes = bos.toByteArray ();   
		} catch (IOException ex) {     
			ex.printStackTrace();
		} finally {
			if(oos != null) {
				try {
					oos.close();
					oos = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(bos != null) {
				try {
					bos.close();
					bos = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bytes; 
	}
	
	/**
	 * 数组转对象
	 * @param bytes
	 * @return
	 */
	public static Object byteArraytoObject (byte[] bytes) {   
		Object obj = null;   
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {     
			bis = new ByteArrayInputStream (bytes);     
			ois = new ObjectInputStream (bis);     
			obj = ois.readObject();   
		} catch (IOException ex) {     
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {     
			ex.printStackTrace();
		} finally {
			if(ois != null) {
				try {
					ois.close();
					ois = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(bis != null) {
				try {
					bis.close();
					bis = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return obj; 
	}
	/*************************************************************************************/
	/*************************************************************************************/
	/*************************************************************************************/
}
