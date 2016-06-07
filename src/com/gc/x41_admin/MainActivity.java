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
        
        //��ȡ�豸���Է���
        mDPM = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
    
        //�豸�������
        mDeviceAdminSample = new ComponentName(this, AdminReceiver.class);
        
//      //���������ж��Ƿ��Ѽ����豸��Ȼ����������
//		mDPM.lockNow();
//		finish();
   	 
	}

	/**
	 * һ������
	 * @param view
	 */
    public void lockscreen(View view){
    	//�Ƿ��Ѿ������豸
    	if(mDPM.isAdminActive(mDeviceAdminSample)){
    		//��������
    		mDPM.lockNow();
    		//�����������룬�����������룬0�ǲ���������Ӧ���������룩
    		mDPM.resetPassword("123456", 0);
    	}else{
    		Toast.makeText(this, "�����ȼ����豸������", Toast.LENGTH_SHORT).show();
    	}
    	
    }

    /**
     * �����豸������
     * @param view
     */
    public void activeAdmin(View view){
    	 Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
    	
         intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample);
         intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"hello~~,�豸������");
         startActivity(intent);
    }
    
    /**
     * һ��ж��
     * @param view
     */
    public void unInstall(View view){
    	if(mDPM.isAdminActive(mDeviceAdminSample)){
    		
    		//ȡ�������豸������
    		mDPM.removeActiveAdmin(mDeviceAdminSample);
    	}
    	
    	//ж�س���
    	Intent intent = new Intent(Intent.ACTION_DELETE);
    	intent.addCategory(Intent.CATEGORY_DEFAULT);
    	//Ҫж�صĳ���İ���
    	intent.setData(Uri.parse("package:" + getPackageName()));
    	startActivity(intent);
    	
    }
    
    /**
     * �������
     * @param view
     */
    public void clearData(View view){
    	//�Ƿ��Ѿ������豸
    	if(mDPM.isAdminActive(mDeviceAdminSample)){
    		//������ݣ��ָ���������
    		mDPM.wipeData(0);
    	}else{
    		Toast.makeText(this, "�����ȼ����豸������", Toast.LENGTH_SHORT).show();
    	}
    }
}
