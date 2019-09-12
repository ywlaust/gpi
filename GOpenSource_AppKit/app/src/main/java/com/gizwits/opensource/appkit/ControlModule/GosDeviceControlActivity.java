package com.gizwits.opensource.appkit.ControlModule;

import com.gizwits.gizwifisdk.api.GizWifiDevice;
import com.gizwits.gizwifisdk.enumration.GizWifiErrorCode;
import com.gizwits.gizwifisdk.listener.GizWifiDeviceListener;
import com.gizwits.opensource.appkit.R;
import com.gizwits.opensource.appkit.CommonModule.GosBaseActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ConcurrentHashMap;

public class GosDeviceControlActivity extends GosBaseActivity {


	private Button btn_senton;
	private Button btn_sentoff;

	private ConcurrentHashMap<String,Object> map;

	private String sendOff="LED_OnOff";

	private String sendColor="LED_Color";

	private String colorname="黄色33";


	/** The tv MAC */
	private TextView tvMAC;

	/** The GizWifiDevice device */
	private GizWifiDevice device;

	/** The ActionBar actionBar */
	ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gos_device_control);
		initDevice();
		setActionBar(true, true, device.getProductName());
		initView();
	}

	private void initView() {
		btn_sentoff=(Button) findViewById(R.id.btn_lightOff);
		btn_sentoff.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				map=new ConcurrentHashMap<>();
				map.put(sendOff,true);
				device.write(map,0);
				Log.i("Apptest", "1");
			}
		});
		btn_senton=(Button)findViewById(R.id.btn_lightOn);
		btn_senton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				map=new ConcurrentHashMap<>();
				//map.put(sendColor,colorname);
                map.put(sendOff,false);
				device.write(map,0);
				Log.i("Apptest", colorname);
			}
		});

	}

	private void initDevice() {
		Intent intent = getIntent();
		device = (GizWifiDevice) intent.getParcelableExtra("GizWifiDevice");
		Log.i("Apptest", device.getDid());
		device.setListener(mGizWifiDeviceListener);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private GizWifiDeviceListener mGizWifiDeviceListener=new GizWifiDeviceListener(){
		@Override
		public void didReceiveData(GizWifiErrorCode result, GizWifiDevice device, ConcurrentHashMap<String, Object> dataMap, int sn) {
			super.didReceiveData(result, device, dataMap, sn);
			Log.i("ywl","接收到的数据"+dataMap.toString());
		}
	};

	@Override
	protected void onDestroy() {
		device.setListener(null);
		super.onDestroy();
	}
}
