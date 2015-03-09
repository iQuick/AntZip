package me.imli.antzip.zip;

import java.io.File;

import me.imli.antzip.AntZipActivity;
import me.imli.antzip.R;
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
 * @since 2011-10-13 下午6:51:50
 * @version 1.0.0
 */
public class UnzipActivity extends Activity implements OnClickListener {
	private EditText etPath;
	private EditText etDest;
	private Button btnDozip;
	private TextView tvTip;

	private String srcPath;
	private String destPath;
	private int type;
	private String suffix;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Ant-解压");
		type = getIntent().getIntExtra(AntZipActivity.TYPE, AntZipActivity.TYPE_ZIP);
		suffix = type == AntZipActivity.TYPE_ZIP ? AntZipActivity.SUFFIX_ZIP : AntZipActivity.SUFFIX_TAR;
		setContentView(R.layout.unzip);
		etPath = (EditText) findViewById(R.id.editText1);
		etDest = (EditText) findViewById(R.id.editText2);
		etPath.setText("/sdcard/antzip" + suffix);
		etDest.setText("/sdcard/antzip");
		btnDozip = (Button) findViewById(R.id.button);
		tvTip = (TextView) findViewById(R.id.tv_tip);
		btnDozip.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		srcPath = etPath.getEditableText().toString();
		if (TextUtils.isEmpty(srcPath)) {
			Toast.makeText(this, "请指定一个zip文件路径", Toast.LENGTH_SHORT).show();
			return;
		}

		File srcFile = new File(srcPath);
		if (!srcFile.exists()) {
			Toast.makeText(this, "指定的压缩文件不存在", Toast.LENGTH_SHORT).show();
			return;
		}

		destPath = etDest.getEditableText().toString();
		if (TextUtils.isEmpty(destPath)) {
			destPath = srcPath;
		}
		destPath.replace(suffix, "");// 去掉后面的zip，以便组成解压的目录。

		File destFile = new File(destPath);
		// 如果用户指定的解压目录不存在，则先创建
		if (!destFile.exists()) {
			destFile.mkdirs();
		}
		// 如果用户指定的解压目录是一个已经存在的文件，则提示用户
		if (destFile.isFile()) {
			Toast.makeText(this, "重新输入解压路径，已经有一个相同的文件了，而不是一个目录",
					Toast.LENGTH_SHORT).show();
			return;
		}
		// 如果用户选择的是zip，则用 zipUtil进行解压
		if (type == AntZipActivity.TYPE_ZIP) {
			ZipUtil zipp = new ZipUtil();
			zipp.unZip(srcPath, destPath);
		}
		// 如果用户选择的是tar，则用 tarUtil进行解压
		else {
			TarUtil tarr = new TarUtil();
			tarr.unTar(srcPath, destPath);
		}

		tvTip.setText("解压目录路径：" + destPath);

		Toast.makeText(this, "解压完成", Toast.LENGTH_SHORT).show();
	}
}
