package com.gc.x41_admin;

import com.gc.x41_admin.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    private DevicePolicyManager mDPM;
	private ComponentName mDeviceAdminSample;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //获取设备策略服务
        mDPM = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
    
        //设备管理组件
        mDeviceAdminSample = new ComponentName(this, AdminReceiver.class);
        
//      //（可以先判断是否已激活设备，然后）立刻锁屏
//		mDPM.lockNow();
//		finish();
   	 
	}

	/**
	 * 一键锁屏
	 * @param view
	 */
    public void lockscreen(View view){
    	//是否已经激活设备
    	if(mDPM.isAdminActive(mDeviceAdminSample)){
    		//立刻锁屏
    		mDPM.lockNow();
    		//重置锁屏密码，（参数：密码，0是不允许其他应用重置密码）
    		mDPM.resetPassword("123456", 0);
    	}else{
    		Toast.makeText(this, "必须先激活设备！！！", Toast.LENGTH_SHORT).show();
    	}
    	
    }

    /**
     * 激活设备管理器
     * @param view
     */
    public void activeAdmin(View view){
    	 Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
    	
         intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample);
         intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"hello~~,设备管理器");
         startActivity(intent);
    }
    
    /**
     * 一键卸载
     * @param view
     */
    public void unInstall(View view){
    	if(mDPM.isAdminActive(mDeviceAdminSample)){
    		
    		//取消激活设备管理器
    		mDPM.removeActiveAdmin(mDeviceAdminSample);
    	}
    	
    	//卸载程序
    	Intent intent = new Intent(Intent.ACTION_DELETE);
    	intent.addCategory(Intent.CATEGORY_DEFAULT);
    	//要卸载的程序的包名
    	intent.setData(Uri.parse("package:" + getPackageName()));
    	startActivity(intent);
    	
    }
    
    /**
     * 清除数据
     * @param view
     */
    public void clearData(View view){
    	//是否已经激活设备
    	if(mDPM.isAdminActive(mDeviceAdminSample)){
    		//清除数据，恢复出厂设置
    		mDPM.wipeData(0);
    	}else{
    		Toast.makeText(this, "必须先激活设备！！！", Toast.LENGTH_SHORT).show();
    	}
    }
}
