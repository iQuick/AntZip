package me.imli.antzip;

import java.io.File;

import me.imli.antzip.zip.TarUtil;
import me.imli.antzip.zip.ZipUtil;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 *
 * @author <a href="mailto:kris1987@qq.com">Kris.lee</a>
 * @since  2011-10-13  下午6:51:46
 * @version 1.0.0
 */
public class DozipActivity extends Activity implements OnClickListener{
	private EditText etPath;
	private EditText etDest;
	private Button	btnDozip;
	private TextView	tvTip;
	
	private String  srcPath;
	private String  zipDest;
	
	private int 	type;
	private String 	suffix;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Ant-压缩");
        type = getIntent().getIntExtra(AntZipActivity.TYPE, AntZipActivity.TYPE_ZIP);
        suffix = type==AntZipActivity.TYPE_ZIP ? AntZipActivity.SUFFIX_ZIP:AntZipActivity.SUFFIX_TAR;
        setContentView(R.layout.dozip);
        //
        etPath = (EditText)findViewById(R.id.editText1);
        etDest = (EditText)findViewById(R.id.editText2);
        //设置一些默认的函数
        etPath.setText("/sdcard/antzip");
        etDest.setText("/sdcard/antzip"+suffix);
        btnDozip = (Button)findViewById(R.id.button);
        tvTip	= (TextView)findViewById(R.id.tv_tip);
        btnDozip.setOnClickListener(this);
    }
	@Override
	public void onClick(View v) {
		srcPath = etPath.getEditableText().toString();
		if(TextUtils.isEmpty(srcPath))
		{
			Toast.makeText(this, "请指定一个路径", Toast.LENGTH_SHORT).show();
			return;
		}
		File srcFile = new File(srcPath);
		if(!srcFile.exists())
		{
			Toast.makeText(this, "指定的压缩包不存在", Toast.LENGTH_SHORT).show();
			return;
		}
		zipDest = etDest.getEditableText().toString();
		if(TextUtils.isEmpty(zipDest))
		{
			//如果用户没有输入目标文件，则生成一个默认的
			zipDest = srcFile.getParent();
		}
		System.out.println("zip name:"+zipDest);
		//如果是以/结尾的，则证明用户输入的是一个目录 ，需要在后面加上文件名
		if(zipDest.endsWith(File.separator))
		{
			zipDest+=srcFile.getName()+suffix;
		}
		else
		{
			//如果压缩文件名不是以zip/tar结尾，则加上后缀后
			if(!zipDest.endsWith(suffix))
			{
				zipDest +=suffix;
			}
		}
		//如果用户选择的是zip，则用 zipUtil进行压缩
		if(type == AntZipActivity.TYPE_ZIP)
		{

			ZipUtil zipp = new ZipUtil();
			zipp.doZip(srcPath, zipDest);
		}
		//如果用户选择的是tar，则用 tarUtil进行压缩
		else
		{
			TarUtil tarr = new TarUtil();
			tarr.doTar(srcPath, zipDest);
		}
		//压缩完成后还是提示用户
		tvTip.setText("压缩文件路径："+zipDest);
		Toast.makeText(this, "压缩完成", Toast.LENGTH_SHORT).show();
	}
}
