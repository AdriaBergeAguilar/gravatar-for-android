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


package com.wareninja.opensource.gravatar4android;

import android.os.AsyncTask;
import android.util.Log;

import com.wareninja.opensource.gravatar4android.common.CONSTANTS;
import com.wareninja.opensource.gravatar4android.common.GenericRequestListener;
import com.wareninja.opensource.gravatar4android.common.Utils;

public class GravatarTask extends AsyncTask<String, Integer, GravatarResponseData> {
	
	protected final static String TAG = "GravatarTask";

	private GenericRequestListener mReqListener;
	
	public void setReqListener(GenericRequestListener mReqListener) {
		this.mReqListener = mReqListener;
	}
		
	@Override
	protected void onPostExecute(GravatarResponseData result) {
		super.onPostExecute(result);
		
    	if(CONSTANTS.DEBUG)Log.d(TAG, "gravatarResponseData->"+result);
        if(result.getStatus()==1) {
            mReqListener.onComplet(result);
        }else {
            mReqListener.onFailure(result.getErrorType(), result.getErrorMessage());
        }
	}
    
	@Override
	protected GravatarResponseData doInBackground(String... params) {
        GravatarResponseData gravatarResponseData = new GravatarResponseData();
				
        String imageUrl = params[0];
        gravatarResponseData.setImageUrl(imageUrl);
        try {
            
            //byte[] imageData = Utils.downloadImage_alternative1(imageUrl);
            byte[] imageData = Utils.downloadImage_alternative2(imageUrl);
            
            gravatarResponseData.setStatus(1);
            gravatarResponseData.setImageData(imageData);
            
        }
        catch (Exception ex) {
            gravatarResponseData.setStatus(0);
            gravatarResponseData.setErrorMessage(ex.toString());
            Log.w(TAG, ex.toString());
        }
    		
		return(gravatarResponseData);
	}
}
