/***
 * 	Copyright (c) 2011 WareNinja.com
 * 	Author: yg@wareninja.com
 *  http://www.WareNinja.net - https://github.com/wareninja	
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
*/

package com.wareninja.opensource.gravatar4android.common;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.util.Log;


public final class Utils {
	
	protected static final String TAG = "Utils";

    public static String hex(byte[] array) {
    	StringBuffer sb = new StringBuffer();
    	for (int i = 0; i < array.length; ++i) {
    		sb.append(Integer.toHexString((array[i]& 0xFF) | 0x100).substring(1,3));        
    	}
    	return sb.toString();
    }
    public static String md5Hex (String message) {
    	try {
    		MessageDigest md = MessageDigest.getInstance("MD5");
    		return hex (md.digest(message.getBytes("CP1252")));
      } catch (NoSuchAlgorithmException e) {
      } catch (UnsupportedEncodingException e) {
      }
      return null;
    }

    public static byte[] downloadImage_alternative2(String url) throws GenericException {
        
		byte[] imageData = null;
        try {
        	
        	URLConnection connection=new URL(url).openConnection();
        	
        	InputStream stream = connection.getInputStream();
        	
			//BufferedInputStream in=new BufferedInputStream(stream);//default 8k buffer
			BufferedInputStream in=new BufferedInputStream(stream, 10240);// 10k=10240, 2x8k=16384
			ByteArrayOutputStream out=new ByteArrayOutputStream(10240);
			int read;
			byte[] b=new byte[4096];
			
			while ((read = in.read(b)) != -1) {
					out.write(b, 0, read);
			}
			
			out.flush();
			out.close();
			
			imageData = out.toByteArray();
        }
	    catch (FileNotFoundException e) {
			return null;
		} 
        catch (Exception e) {

        	Log.w(TAG, "Exc="+e);
        	throw new GenericException(e);
        }
        
        return imageData;
    }
}
