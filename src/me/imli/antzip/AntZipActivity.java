package me.imli.antzip;

import me.imli.antzip.zip.UnzipActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
/**
 * 
 *
 * @author <a href="mailto:kris1987@qq.com">Kris.lee</a>
 * @since  2011-10-13  下午6:51:18
 * @version 1.0.0
 */
public class AntZipActivity extends Activity {
	public static final	String	TYPE = "type";
	public static final	int		TYPE_ZIP = -1;
	public static final	int		TYPE_TAR = 1;
	
	public static final String 	SUFFIX_ZIP = ".zip";
	public static final String 	SUFFIX_TAR = ".tar";
    /** Called when the activity is first created. */
	private Button		btnDoCompress;
	private Button		btnDecompress;
	
	private RadioButton	radioZip;
	private RadioButton	radioTar;
	
	private boolean isZip = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.antzip);
        radioZip = (RadioButton)findViewById(R.id.radioZip);
        isZip = true;
        radioZip.setChecked(true);
        radioZip.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				System.out.println("radioZip:"+isChecked);
				if(isChecked)
				{
					isZip = true;
				}
			}
		});
        radioTar = (RadioButton)findViewById(R.id.radioTar);
        radioTar.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				System.out.println("radioTar:"+isChecked);
				if(isChecked)
				{
					isZip = false;
				}
			}
		});
        btnDoCompress = (Button)findViewById(R.id.button1);
        btnDoCompress.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//进入压缩界面　
				Intent i = new Intent(AntZipActivity.this,DozipActivity.class);
				i.putExtra(TYPE, isZip?TYPE_ZIP:TYPE_TAR);
				AntZipActivity.this.startActivity(i);
			}
		});
        btnDecompress = (Button)findViewById(R.id.button2);
        btnDecompress.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//进入解压界面　
				Intent i = new Intent(AntZipActivity.this,UnzipActivity.class);
				i.putExtra(TYPE, isZip?TYPE_ZIP:TYPE_TAR);
				AntZipActivity.this.startActivity(i);
			}
		});
    }
}