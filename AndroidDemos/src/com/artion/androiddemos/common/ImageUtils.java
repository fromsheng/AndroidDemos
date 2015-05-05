package com.artion.androiddemos.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.MediaColumns;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.artion.androiddemos.utils.DebugTool;
import com.nostra13.universalimageloader.cache.disc.DiscCacheAware;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.DiscCacheUtils;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

/**
 * 图片相关常用操作类
 * @author jinsheng_cai
 * @since 2014-12-02
 */
public class ImageUtils {
	static FileNameGenerator md5FileNameGenerator = new Md5FileNameGenerator();

	/**
	 * @param url 目前需要解析以下几个类型的地址：
	 * 用户头像：http://t.kingdee.com/space/c/photo/load?id=4e9d12d09cba0c1d41000002&spec=180
	 * 群组头像：http://t.kingdee.com/space/c/photo/load?type=group&spec=80&id=
	 * 图片文件：http://t.kingdee.com/microblog/filesvr/4ea797bfcce7850f1a159bb3
	 * 或者          ：http://t.kingdee.com/microblog/filesvr/4ea797bfcce7850f1a159bb3?thumbnail 
	 * 补充          ：新浪同步回来的图片大中小格式如下：
	 *  http://ww2.sinaimg.cn/thumbnail/4bba2f06jw1dqtxv35scyj.jpg
		http://ww2.sinaimg.cn/bmiddle/4bba2f06jw1dqtxv35scyj.jpg
		http://ww2.sinaimg.cn/large/4bba2f06jw1dqtxv35scyj.jpg
	 */
	public static String getFileNameFromUrlUseFileID(String imageUrl){
		if(TextUtils.isEmpty(imageUrl))return "";
		String[] array = imageUrl.split("\\/");
		String temp = array[array.length-1];
		//先处理新浪的图片
		String tempPrefix = array[array.length-2];
		if(tempPrefix.equals("thumbnail") || tempPrefix.equals("bmiddle") || tempPrefix.equals("large")|| tempPrefix.equals("big")){
			return tempPrefix +"_"+temp;
		}
		
		//解释图片文件的URL
		int indexThumbnail = temp.indexOf("?thumbnail");
		if(indexThumbnail>-1){
			return temp.substring(0,indexThumbnail)+"_thumbnail";
		}
		int indexOriginal = temp.indexOf("?original");
		if(indexOriginal>-1){
			return temp.substring(0,indexOriginal)+"_original";
		}
		int indexBig = temp.indexOf("?big");
		if(indexBig>-1){
			return temp.substring(0,indexBig)+"_big";
		}
		//解析用户头像的URL
		int begin = temp.indexOf("id=");
		int end = temp.indexOf("&");
		if(begin>-1){
			return (end>-1 && begin<end)?temp.substring(begin+3,end):temp.substring(begin+3);
		}
		//解析缩略图,原始图的URL
		end = temp.indexOf("?");
		if(end>-1){
			return temp.substring(0,end);
		}
		return temp;
	}

	public static String getFileNameFromUrl(String imageUrl){
		if(TextUtils.isEmpty(imageUrl))return "";
		//文件名由取原来的url的hashCode改成去url的MD5
//		String fileName = String.valueOf(imageUrl.hashCode());
//		fileName = fileName.replaceAll("-", "f");
		String fileName = md5FileNameGenerator.generate(imageUrl);
		return fileName;
	}
	
	
	/**
	 * 获取最大加载进内存的缩略图对象
	 */
	public static Bitmap getSuitableThumbBitmap(Context context,String imagePath) {
		if (TextUtils.isEmpty(imagePath) || !new File(imagePath).exists() || new File(imagePath).isDirectory()) {
			return null;
		}
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		int maxNumOfPixels = displayMetrics.widthPixels*displayMetrics.heightPixels/4;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imagePath, opts);
		opts.inSampleSize = computeInitialSampleSize(opts, -1, maxNumOfPixels);
		opts.inJustDecodeBounds = false;
		try {
			return BitmapFactory.decodeFile(imagePath, opts);
		} catch (OutOfMemoryError err) {
		}
		return null;
	}	
	
	/**
	 * 获取最大加载进内存的大图对象
	 */
	public static Bitmap getSuitableBigBitmap(Context context,String imagePath) {
		if (TextUtils.isEmpty(imagePath) || !new File(imagePath).exists() || new File(imagePath).isDirectory()) {
			return null;
		}
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		int maxNumOfPixels = Math.min(MAX_SIZE_LOADINMEMORY, displayMetrics.widthPixels*displayMetrics.heightPixels);
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imagePath, opts);
		opts.inSampleSize = computeInitialSampleSize(opts, -1, maxNumOfPixels);
		opts.inJustDecodeBounds = false;
		try {
			return BitmapFactory.decodeFile(imagePath, opts);
		} catch (OutOfMemoryError err) {
			return null;
		}
	}
	/**
	 * 获取图片本地路径
	 * 
	 * @param context
	 * @param pathName
	 * @return
	 */
	public static String getPath(Context context, String pathName)
	{
		if (pathName.startsWith(ContentResolver.SCHEME_CONTENT))
		{
			String[] proj = { MediaColumns.DATA };
			Uri pUri = Uri.parse(pathName);
			Cursor actualimagecursor = context.getContentResolver().query(pUri,
					proj, null, null, null);
			int actual_image_column_index = actualimagecursor
					.getColumnIndexOrThrow(MediaColumns.DATA);
			actualimagecursor.moveToFirst();
			pathName = actualimagecursor.getString(actual_image_column_index);
		} else if (pathName.startsWith(ContentResolver.SCHEME_FILE))
		{
			try
			{
				pathName = new File(new URI(pathName)).getAbsolutePath();
			} catch (URISyntaxException e)
			{
				e.printStackTrace();
			}
		}

		return pathName;
	}

	/**
	 * 根据图片宽高和最长边的值计算解码采样值
	 * 
	 * @param context
	 * @param path
	 * @param maxwh
	 * @return
	 */
	public static int getScaleDecodeFile(int bitmapW, int bitmapH, int maxwh)
	{
		int scale;
		if (bitmapW > bitmapH)
		{
			scale = bitmapH / maxwh;
		} else
		{
			scale = bitmapW / maxwh;
		}
		if (scale < 2)
		{
			return 1;
		} else if (scale < 4)
		{
			return 2;
		} else if (scale < 8)
		{
			return 4;
		} else
		{
			return 8;
		}
	}
	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.round(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}
	
	
	/**
	 * 圆角化图片处理，原图bitmap不会被释放，外部调用注意处理bitmap
	 * @param bitmap
	 * @param pixels
	 * @return
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) { 
		Bitmap output = null;
        try{
	        output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888); 
	        android.graphics.Canvas canvas = new android.graphics.Canvas(output); 
	        final int color = 0xff424242; 
	        final android.graphics.Paint paint = new android.graphics.Paint(); 
	        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()); 
	        final android.graphics.RectF rectF = new android.graphics.RectF(rect); 
	        final float roundPx = pixels; 
	        paint.setAntiAlias(true); 
	        canvas.drawARGB(0, 0, 0, 0); 
	        paint.setColor(color); 
	        canvas.drawRoundRect(rectF, roundPx, roundPx, paint); 
	        paint.setXfermode(new android.graphics.PorterDuffXfermode(Mode.SRC_IN)); 
	        canvas.drawBitmap(bitmap, rect, rect, paint); 
        }catch (Exception e) {
			//Log.e("", "", e);
		}
        return output; 
	}
	
	/**
	 * 裁剪缩略图中间的resultWidth*resultHeight区域，得到为方形
	 * 	bmp原图会被释放
	 * @param bmp 原图
	 * @param resultWith 结果图的宽
	 * @param resultHeight 结果图的高
	 * @return 
	 */
	public static Bitmap cutBmp(Bitmap bmp, int resultWidth, int resultHeight) {
		if(bmp == null){
			return null;
		}
		Bitmap result;
		int width = bmp.getWidth();// 输入长方形宽
		int height = bmp.getHeight();// 输入长方形高
		if( width < resultWidth || height <resultHeight || (width == resultWidth && height == resultHeight)){
			return bmp;
		}
		result = Bitmap.createBitmap(bmp, (width - resultWidth) / 2, (height - resultHeight) / 2, resultWidth, resultHeight);
		bmp.recycle();
		return result;
	}
	

	/** 
	 * 读取图片属性：旋转的角度 
	 * @param path 图片绝对路径 
	 * @return degree旋转的角度 
	 */  
   public static int readPictureDegree(String path) {  
       int degree  = 0;  
       try {  
               ExifInterface exifInterface = new ExifInterface(path);  
               int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);  
               switch (orientation) {  
               case ExifInterface.ORIENTATION_ROTATE_90:  
                       degree = 90;  
                       break;  
               case ExifInterface.ORIENTATION_ROTATE_180:  
                       degree = 180;  
                       break;  
               case ExifInterface.ORIENTATION_ROTATE_270:  
                       degree = 270;  
                       break;  
               }  
       } catch (IOException e) {  
               e.printStackTrace();  
       }  
       return degree;  
   }  
   
   
	/**
	 * 设置图片文件Exif角度信息
	 * 
	 * @param filepath 文件路径
	 * @param orientation 角度 为正数 90 * i, i >= 0
	 */
	public static void savePictureDegree(String filepath, int orientation)
	{
		ExifInterface exif = null;
		try
		{
			exif = new ExifInterface(filepath);
			switch (orientation)
			{
				case 0:
				{
					exif.setAttribute(ExifInterface.TAG_ORIENTATION,
							String.valueOf(ExifInterface.ORIENTATION_NORMAL));
				}
					break;
				case 90:
				{
					exif.setAttribute(ExifInterface.TAG_ORIENTATION,
							String.valueOf(ExifInterface.ORIENTATION_ROTATE_90));
				}
					break;
				case 180:
				{
					exif.setAttribute(ExifInterface.TAG_ORIENTATION, String
							.valueOf(ExifInterface.ORIENTATION_ROTATE_180));
				}
					break;
				case 270:
				{
					exif.setAttribute(ExifInterface.TAG_ORIENTATION, String
							.valueOf(ExifInterface.ORIENTATION_ROTATE_270));
				}
					break;
				default:
				{
					exif.setAttribute(ExifInterface.TAG_ORIENTATION,
							String.valueOf(ExifInterface.ORIENTATION_NORMAL));
				}
			}
			exif.saveAttributes();
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
	/**
	 * 图片旋转
	 * 原图b会被释放
	 * @param b
	 * @param degrees
	 * @return
	 */
	public static Bitmap rotate(int degrees,Bitmap b)
	{
		if (degrees != 0 && b != null)
		{
			Matrix m = new Matrix();

			if (degrees == 180)// 某些系统中旋转180无效,通过两次旋转90度达到180效果
			{
				for (int i = 0; i < 2; i++)
				{
					m.setRotate(90, (float) b.getWidth() / 2,
							(float) b.getHeight() / 2);
					try
					{
						Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
								b.getHeight(), m, true);
						if (b != b2)
						{
							b.recycle();
							b = b2;
						}
					} catch (OutOfMemoryError ex)
					{
						return b;
					}
				}
			} else
			{
				m.setRotate(degrees, (float) b.getWidth() / 2,
						(float) b.getHeight() / 2);
				try
				{
					Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
							b.getHeight(), m, true);
					if (b != b2)
					{
						b.recycle();
						b = b2;
					}
				} catch (OutOfMemoryError ex)
				{
					return b;
				}
			}
		}
		return b;
	}
   
	public static String saveLocalPic(String detailName,int quality,
			Bitmap bm) {
		String path = FileUtils.getImageCacheDir(AndroidUtils.appContext());
		String path_img = path + detailName;

		FileOutputStream fos = null;

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		File file_img = new File(path_img);
		try {
			file_img.createNewFile();
			fos = new FileOutputStream(file_img);
			if (bm != null && fos != null) {
				bm.compress(Bitmap.CompressFormat.JPEG, quality, fos);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return path_img;
	}
   
   public static String getSuitablePath(String imagePath, int reqWith, int reqHeight) {
		if (TextUtils.isEmpty(imagePath) || !new File(imagePath).exists() || new File(imagePath).isDirectory()) {
			return null;
		}
		String fileName = FileUtils.getMD5Hash(imagePath) + "_" + reqWith + "X" + reqHeight;
		String cacheFilePath = FileUtils.getImageCacheDir(AndroidUtils.appContext())  + fileName;
		File cacheFile = new File(cacheFilePath);
		if(cacheFile.exists()){
			return cacheFilePath;
		}
		int maxNumOfPixels = reqWith*reqHeight;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imagePath, opts);
		opts.inSampleSize = computeInitialSampleSize(opts, -1, maxNumOfPixels);
		opts.inJustDecodeBounds = false;
		try {
			Bitmap bm = BitmapFactory.decodeFile(imagePath, opts);
			int degree = ImageUtils.readPictureDegree(imagePath);
			if(degree>0){
				bm = ImageUtils.rotate(degree, bm);
			}
			saveLocalPic(fileName,90, bm);
			bm.recycle();
			return cacheFilePath;
		} catch (OutOfMemoryError err) {
		}
		return null;
	}
   
   public static String getSuitablePath(String imagePath) {
	   return getSuitablePath(imagePath,800,600);
   }
   
   public static String moveToMyAlbum(Context context,String imagePath) {
		File toCamera = new File(Environment.getExternalStorageDirectory() + File.separator + "DCIM" + File.separator
				+ "Camera" + File.separator, getPhotoFileName());
		File sourceFile = new File(imagePath);
		FileUtils.moveFile(sourceFile, toCamera);
//		boolean isRenameSuccess = sourceFile.renameTo(toCamera);
//		if(!isRenameSuccess){
//			return "";
//		}
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri = Uri.fromFile(toCamera);
		intent.setData(uri);
		context.sendBroadcast(intent);
		return toCamera.getAbsolutePath();
	}
   
   public static String moveToAlbum(Context context,String imagePath) {
		File toCamera = new File(Environment.getExternalStorageDirectory() + File.separator + "DCIM" + File.separator
				+ "Camera" + File.separator, getPhotoFileName());
		File sourceFile = new File(imagePath);
		sourceFile.renameTo(toCamera);
		ContentValues values = new ContentValues(8);
		values.put(MediaColumns.MIME_TYPE, "image/jpeg");
		values.put(MediaColumns.DATA, toCamera.getAbsolutePath());
		values.put(Images.Media.ORIENTATION,ImageUtils.readPictureDegree(toCamera.getAbsolutePath()));
		context.getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI, values);
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
		return toCamera.getAbsolutePath();
	}
   
   public static String copyToAlbum(Context context,String imagePath) {
		File toCamera = new File(Environment.getExternalStorageDirectory() + File.separator + "DCIM" + File.separator
				+ "Camera" + File.separator, getPhotoFileName());
		FileUtils.copyFile(new File(imagePath), toCamera);
		ContentValues values = new ContentValues(8);
		values.put(MediaColumns.MIME_TYPE, "image/jpeg");
		values.put(MediaColumns.DATA, toCamera.getAbsolutePath());
		values.put(Images.Media.ORIENTATION,ImageUtils.readPictureDegree(toCamera.getAbsolutePath()));
		context.getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI, values);
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
		return toCamera.getAbsolutePath();
	}
	
   public static Bitmap decodeStream(InputStream is,int size) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		options.inSampleSize = size;
		return BitmapFactory.decodeStream(is, null, options);
	}
   
   public final static int MAX_SIZE_LOADINMEMORY = 480*480;
   public final static int MAX_QUALITY_IN_2G = 80;
   public final static int MAX_QUALITY_IN_3G = 80;
   public final static int MAX_QUALITY_IN_WIFI = MAX_QUALITY_IN_3G;
   public final static int MAX_SIZE_IN_2G = 720;//720P
   public final static int MAX_SIZE_IN_3G = ((Runtime.getRuntime().maxMemory() / (float) (1024 * 1024)))<=64?1024:1280;//1080P
   public final static int MAX_SIZE_IN_WIFI = MAX_SIZE_IN_3G;
   
   public static String savePhotoFileInWiFi(String source){
	   return saveImageFile(true, source);
   }
   public static String savePhotoFileIn2G(String source){
	   return saveImageFile(false, source);
   }
   
   public static String saveImageDataInWiFi(byte[] data,int angle,int quality){
	   return saveImageData(true, data, angle, quality);
   }
   public static String saveImageDataIn2G(byte[] data,int angle,int quality){
	   return saveImageData(false, data, angle, quality);
   }
   public static String saveImageBitmapInWiFi(Bitmap bm,int angle,int quality){
		String fileName = FileUtils.getMD5Hash(String.valueOf(System.currentTimeMillis())) + "_" + bm.getWidth() + "X" + bm.getHeight();
		String cacheFilePath = FileUtils.getImageCacheDir(AndroidUtils.appContext())  + fileName;
		File cacheFile = new File(cacheFilePath);
		if(cacheFile.exists()){
			return cacheFilePath;
		}
		if(saveImageBitmap(true, bm, angle,cacheFilePath,quality)){
			return cacheFilePath;
		}
		else {
			return null;
		}
   }
   public static String saveImageBitmapIn2G(Bitmap bm,int angle,int quality){
		String fileName = FileUtils.getMD5Hash(String.valueOf(System.currentTimeMillis())) + "_" + bm.getWidth() + "X" + bm.getHeight();
		String cacheFilePath = FileUtils.getImageCacheDir(AndroidUtils.appContext())  + fileName;
		File cacheFile = new File(cacheFilePath);
		if(cacheFile.exists()){
			return cacheFilePath;
		}
		if(saveImageBitmap(false, bm, angle,cacheFilePath,quality)){
			return cacheFilePath;
		}
		else {
			return null;
		}
   }

   public static String saveImageFile(boolean wifi,String sourcePath){
       return saveImageFile(wifi, sourcePath, true);
   }

	/**
	 *
	 * @param wifi
	 * @param sourcePath
	 * @param md5 false则为webview上传图片文件
	 * @return
	 */
	public static String saveImageFile(boolean wifi,String sourcePath, boolean md5){
		return saveImageFile(wifi, sourcePath, md5, -1, null);
	}

    /**
     *
     * @param wifi
     * @param sourcePath
     * @param md5 false则为webview上传图片文件
	 * @param quality 转换图片质量0-100，0最小100最大，-1则不用此参数根据参数wifi设置
     * @return
     */
   public static String saveImageFile(boolean wifi,String sourcePath, boolean md5, int quality, final String TAG){
//	   	GJLog.PrintMemoryError("saveImageFile1");
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(sourcePath, opts);
		int reqWith,reqHeight;
		if(opts.outWidth<opts.outHeight)//竖图
		{
			reqHeight = wifi?MAX_SIZE_IN_WIFI:MAX_SIZE_IN_2G;
			reqWith = opts.outWidth*reqHeight/opts.outHeight;
		}
		else//横图 
		{
			reqWith = wifi?MAX_SIZE_IN_WIFI:MAX_SIZE_IN_2G;
			reqHeight = opts.outHeight*reqWith/opts.outWidth;
		}
		File sourceFile = new File(sourcePath);
		String fileName = null;
       if(!md5){
           String type = sourceFile.getName();
           if(TAG != null) {
        	   if(TextUtils.isEmpty(type)){
        		   fileName = TAG + FileUtils.getMD5Hash(sourcePath+"_"+opts.outWidth+"X"+opts.outHeight+"_"+sourceFile.lastModified()) + ".jpg";
        	   }else{
        		   if(type.startsWith(TAG)){
        			   fileName = type;
        		   }else{
        			   fileName = TAG + type;
        		   }
        	   }
           } else {
        	   fileName = FileUtils.getMD5Hash(sourcePath+"_"+opts.outWidth+"X"+opts.outHeight+"_"+sourceFile.lastModified()) + ".jpg";
           }
       }else{
           fileName = FileUtils.getMD5Hash(sourcePath+"_"+opts.outWidth+"X"+opts.outHeight+"_"+sourceFile.lastModified()) + "_" + reqWith + "X" + reqHeight;
       }
		String cacheFilePath = FileUtils.getImageCacheDir(AndroidUtils.appContext())  + fileName;
		File cacheFile = new File(cacheFilePath);
		if(cacheFile.exists()){
			FileUtils.copyFile(sourceFile, cacheFile);///覆盖老的缓存
			return cacheFilePath;
		}
		int maxNumOfPixels = reqWith*reqHeight;
		int orgNumOfPixels = opts.outHeight*opts.outWidth;
		if(maxNumOfPixels*10>orgNumOfPixels*11)//如果最大尺寸像素点小于原图尺寸像素点1.1倍就不做处理
		{
			if(sourcePath.startsWith(FileUtils.getImageCacheDir(AndroidUtils.appContext())))//如果是在缓存里面的就直接返回原路径
			{
				cacheFilePath = sourcePath;
			}
			else {//如果是其他路径的就拷贝一份文件
				opts.inJustDecodeBounds = false;
//				Bitmap bm = BitmapFactory.decodeFile(sourcePath, opts);
				Bitmap bm = getBitmapByOptions(sourcePath, opts);
				int angle = readPictureDegree(sourcePath);
				if(bm!=null)
				{
					if(angle%360!=0){
						bm = rotate(angle, bm);
						angle = 0;
					}
					
					if(!saveImageBitmap(wifi, bm, angle, cacheFilePath, quality))
					{
						cacheFilePath = null;
					}
					bm.recycle();
				}
				else {
					cacheFilePath = null;
				}
			}
		}
		else {
//			opts.inSampleSize = getScaleDecodeFile(opts.outWidth, opts.outHeight, Math.max(reqHeight, reqWith));
			opts.inSampleSize = calculateInSampleSize(opts, reqWith, reqHeight);
			
			opts.inJustDecodeBounds = false;
			
//			Bitmap bm = BitmapFactory.decodeFile(sourcePath, opts);
			Bitmap bm = getBitmapByOptions(sourcePath, opts);
			if(bm != null){
				int h = bm.getHeight();
				int w = bm.getWidth();
				if(w*h>maxNumOfPixels){//如果采样后的图片仍然大于目标尺寸就要做缩放处理
					Rect Rctdst = new Rect(0, 0, reqWith, reqHeight);
					Rect RctSrc = new Rect(0, 0, w ,h);
					
					w = RctSrc.right - RctSrc.left;
					h = RctSrc.bottom - RctSrc.top;
					if (w * reqHeight > h * reqWith)
					{
						w = h * reqWith / reqHeight;
					} else
					{
						h = w * reqHeight / reqWith;
					}
					
					RctSrc.top = (bm.getHeight() - h) / 2;
					RctSrc.left = (bm.getWidth() - w) / 2;
					RctSrc.right = RctSrc.left + w;
					RctSrc.bottom = RctSrc.top + h;
					
					try {
						Bitmap tempBitmap = Bitmap.createBitmap(reqWith, reqHeight,Config.ARGB_8888);
						Canvas canvas = new Canvas(tempBitmap);
						Paint paint = new Paint();
						paint.setAntiAlias(true);
						paint.setFilterBitmap(true);
						canvas.drawBitmap(bm, RctSrc, Rctdst, paint);
						bm.recycle();
						bm = tempBitmap;
					} catch (OutOfMemoryError e) {
						e.printStackTrace();
					}
				}
			}
			int angle = readPictureDegree(sourcePath);
			if(bm!=null)
			{
				if(angle%360!=0){
					bm = rotate(angle, bm);
					angle = 0;
				}
				
				if(!saveImageBitmap(wifi, bm, angle, cacheFilePath, quality))
				{
					cacheFilePath = null;
				}
				bm.recycle();
			}
			else {
				cacheFilePath = null;
			}
		}
//		GJLog.PrintMemoryError("saveImageFile3");
		return cacheFilePath;
   }
   
   /**
    * 获取bitmap，节省内存，替代decodeFile或decodeStream方法
    * @param sourcePath 图片路径
    * @param options 
    * @return
    */
	public static Bitmap getBitmapByOptions(String sourcePath, Options options) {
		FileInputStream fs = null;
		Bitmap bm = null;
		try {
			fs = new FileInputStream(sourcePath);
		} catch (FileNotFoundException fnfException) {
			fnfException.printStackTrace();
		}
		if (fs != null) {
			try {
				bm = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, options);
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}catch (OutOfMemoryError e) {
				e.printStackTrace();
			}finally{
				try {
					fs.close();
					fs = null;
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		}
		DebugTool.info("ImageUtils", bm != null ? "not null" : "null");
		return bm;
	}

	/**
	 *
	 * @param quality 转换图片质量0-100，0最小100最大，-1则不用此参而根据参数wifi设置
	 */
   public static boolean saveImageBitmap(boolean wifi,Bitmap bitmap,int angle,String path, int quality){
	   boolean result = false;
		try {
			File file = new File(path);
			FileOutputStream outputStream = new FileOutputStream(file);
			if (bitmap != null)
			{
				if(quality >= 0){
					if (bitmap.compress(CompressFormat.JPEG, quality, outputStream))
					{
						savePictureDegree(path, angle);
						result = true;
					}
				}else{
					if (bitmap.compress(CompressFormat.JPEG, wifi?MAX_QUALITY_IN_WIFI:MAX_QUALITY_IN_2G, outputStream))
					{
						savePictureDegree(path, angle);
						result = true;
					}
				}

			}
			outputStream.flush();
			outputStream.close();
		} catch (OutOfMemoryError err) {
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return result;
   }
   
   public static String saveImageData(boolean wifi, byte[] data,int angle, int quality){
	   
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, 0, data.length, opts);
		int reqWith,reqHeight;
		if(opts.outWidth<opts.outHeight)//竖图
		{
			reqHeight = wifi?MAX_SIZE_IN_WIFI:MAX_SIZE_IN_2G;
			reqWith = opts.outWidth*reqHeight/opts.outHeight;
		}
		else//横图 
		{
			reqWith = wifi?MAX_SIZE_IN_WIFI:MAX_SIZE_IN_2G;
			reqHeight = opts.outHeight*reqWith/opts.outWidth;
		}
	   
		String fileName = FileUtils.getMD5Hash(String.valueOf(System.currentTimeMillis())) + "_" + reqWith + "X" + reqHeight;
		String cacheFilePath = FileUtils.getImageCacheDir(AndroidUtils.appContext())  + fileName;
		File cacheFile = new File(cacheFilePath);
		if(cacheFile.exists()){
			return cacheFilePath;
		}

		opts.inSampleSize = getScaleDecodeFile(opts.outWidth, opts.outHeight, Math.max(reqHeight, reqWith));
		opts.inJustDecodeBounds = false;
		
		Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
		if(bm!=null)
		{
			if(!saveImageBitmap(wifi, bm, angle, cacheFilePath,quality))
			{
				cacheFilePath = null;
			}
			bm.recycle();
		}
		else {
			cacheFilePath = null;
		}
		return cacheFilePath;
   }
   
   public static Bitmap createVideoThumbnail(String filePath, int kind)
   {
	   Bitmap thumb = ThumbnailUtils.createVideoThumbnail(
			   filePath, kind);
	   if(thumb!=null)
	   {
		   thumb = cutBmp(thumb,Math.min(thumb.getWidth(), thumb.getHeight())
				   ,Math.min(thumb.getWidth(), thumb.getHeight()));
	   }
	   return thumb;
   }
   /** 
    * 保存缓存文件 
    * @throws IOException 
    */  
	public static void saveCacheFile(String orgPath,String original_pic,String bmiddle_pic,String thumb_pic){ 
		String tempPath = ImageUtils.saveImageFile(NetworkUtils.isWifiNetConnect(AndroidUtils.appContext()),orgPath);
		File orgFile = new File(tempPath);
		if(!TextUtils.isEmpty(original_pic)){
			saveFileInDiskCache(original_pic, orgFile);
		}
		if(!TextUtils.isEmpty(bmiddle_pic)){
			saveFileInDiskCache(bmiddle_pic, orgFile);
		}
		if(!TextUtils.isEmpty(thumb_pic)){
			saveFileInDiskCache(thumb_pic, orgFile);
		}
	}
	
	
	
	public static Bitmap getBackgroundBitmap(Context context, int resourceId) {
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		int maxNumOfPixels = displayMetrics.widthPixels
				* displayMetrics.heightPixels;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), resourceId, opts);
		opts.inSampleSize = computeInitialSampleSize(opts, -1, maxNumOfPixels) + 1;
		opts.inJustDecodeBounds = false;
		Bitmap bm = null;
		try {
			bm = BitmapFactory.decodeResource(context.getResources(),
					resourceId, opts);
		} catch (OutOfMemoryError err) {
		}
		return bm;
	}

    public static String spec180(String url){
        if(url == null)
            return null;
        StringBuilder sb = new StringBuilder(url);
        if(url.contains("?")){
            sb.append("&spec=180");
        }else{
            sb.append("?spec=180");
        }
        return sb.toString();
    }
    
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
    	final int height = options.outHeight;
    	final int width = options.outWidth;
    	int inSampleSize = 1;
    	
    	if(height > reqHeight || width > reqWidth) {
    		final int heightRatio = Math.round((float) height / (float) reqHeight);
    		final int widthRatio = Math.round((float) width / (float) reqWidth);
    		inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
    		if(inSampleSize <= 0) {
    			inSampleSize = 1;
    		}
    	}
    	return inSampleSize;
    }
    
    /**
     * 取当前时间给取得的图片命名
     * @return
     */
    public static String getPhotoFileName() {
    	Date date = new Date(System.currentTimeMillis());
    	SimpleDateFormat sdf = new SimpleDateFormat("'VIDEO'_yyyyMMdd_HHmmss");
    	return sdf.format(date) + ".jpg";
    }
    
    public static File createFilePhoto() {
    	File file = new File(FileUtils.getImageCacheDir(AndroidUtils.appContext()), getPhotoFileName());
    	if(file.exists()) {
    		file.delete();
    	}
    	try {
			file.createNewFile();
		} catch (IOException e) {
//			e.printStackTrace();
		}
    	return file;
    }
    
    /****************************************************************/
    /****************************************************************/
    /****************************************************************/
    public static Bitmap findBitmapByUri(String uri) {
		List<Bitmap> datas = MemoryCacheUtils.findCachedBitmapsForImageUri(uri,
				ImageLoader.getInstance().getMemoryCache());
		return datas != null && !datas.isEmpty() ? datas.get(0) : null;
	}
	
	public static void initImageLoader(Context context){
		int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 8);

		MemoryCacheAware memoryCache;
		if (Build.VERSION.SDK_INT >= 9) { 
			memoryCache = new LruMemoryCache(memoryCacheSize);
		} else {
			memoryCache = new LRULimitedMemoryCache(memoryCacheSize);
		}
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCache(memoryCache)
				.discCache(initImageLoaderDiskCache(context))
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(diskCacheFileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.FIFO)
//				.imageDownloader(new com.kdweibo.android.image.KdImageDownloader(context))
				.build();
		ImageLoader.getInstance().init(config);
		L.enableLogging();
	}
	
	public static FileNameGenerator diskCacheFileNameGenerator(){
		return new Md5FileNameGenerator();
	}
	
	public static ImageLoader getImageLoader(){
		return ImageLoader.getInstance();
	}
	
	public static void saveFileInDiskCache(String imageUri, File file){
		getImageLoader().getDiscCache().put(imageUri, file);
	}
	
	
	private static DiscCacheAware initImageLoaderDiskCache(Context context){
		File imgDir = FileUtils.getAppCacheDirByTypeChild(context, ".image");
		int initSize;
		if(FileUtils.isSDReady(context)){
			initSize = 1024 * 1024 * 300;//SDcard上
		}else{
			initSize = 1024 * 1024 * 10;//内存
		}
		return new LimitedAgeDiscCache(imgDir, diskCacheFileNameGenerator(), initSize);
	}
	
	/**
	 * 更新缓存中imageUri对应的bitmap
	 * @param imageUri
	 * @param bmp 新的bitmap
	 */
	public static void putBitmapInMemoryCache(String imageUri, Bitmap bmp){
		List<String> keys = MemoryCacheUtils.findCacheKeysForImageUri(imageUri, ImageLoader.getInstance().getMemoryCache());
		if(keys!=null && !keys.isEmpty()){
			ImageLoader.getInstance().getMemoryCache().put(keys.get(0), bmp);
		}
	}
	
	/**
	 * 获取bitmap在memoryCache对应的key值
	 * @param imageUri
	 * @return
	 */
	public static String getBitmpaKey(String imageUri){
		List<String> keys = MemoryCacheUtils.findCacheKeysForImageUri(imageUri, ImageLoader.getInstance().getMemoryCache());
		if(keys != null && !keys.isEmpty()){
			return keys.get(0);
		}
		return null;
	}
	
	public static Bitmap getMemroyCacheBM(String imageUri) {
		return (Bitmap) getMemroyCache().get(imageUri);
	}
	
	public static MemoryCacheAware getMemroyCache() {
		return ImageLoader.getInstance().getMemoryCache();
	}
	
	public static DiscCacheAware getCurrenyDiskCache() {
		return ImageLoader.getInstance().getDiscCache();
	}
	
	public static boolean isExistedInDiskCache(String keyUrl) {
		File file = getFileInDiskCache(keyUrl);
		if(file == null) {
			return false;
		}
		
		return file.exists();
	}
	
	public static File getFileInDiskCache(String keyUrl) {
		return DiscCacheUtils.findInCache(keyUrl, getCurrenyDiskCache());
	}	
}
