package witness.host;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;

import dalvik.system.DexClassLoader;
import plugin.apis.IPlugin;
import witness.ho1st.R;

/**
 * Created by Administrator on 2015/3/26.
 */
public class MainActivity extends Activity {

    private static final String TAG = "host";
    TextView ip, get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ip = (TextView) findViewById(R.id.ip);
        get = (TextView) findViewById(R.id.get);

        get.setOnClickListener(onClickGet);

    }

    View.OnClickListener onClickGet = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String fn = "dexed.jar";
            String executeFile = getFilesDir() + "/aa" + fn;
            if (!writeAssetToFile(fn, executeFile)) {
                Log.e(TAG, "write file fail:" + executeFile);
                return;
            }

            LoadAPK(executeFile, getFilesDir().getAbsolutePath());

        }
    };


    void showToast(String str) {
//        Toast.makeText()
    }

    private boolean writeAssetToFile(String assetFile, String extFile) {
        // 读取assets文件
        InputStream is = null;
        FileOutputStream fos = null;
        boolean success = false;
        try {
            is = getResources().getAssets().open(assetFile);
            int len = is.available();
            byte[] buffer = new byte[10 * 1024];
//

            File outFile = new File(extFile);
            if (outFile.exists() && outFile.length() != len) {
                outFile.delete();
            }

            fos = new FileOutputStream(new File(extFile));

            int readBytes = 0;
            do {
                readBytes = is.read(buffer);
                if (readBytes > 0) {
                    fos.write(buffer, 0, readBytes);
                }
            } while (readBytes > 0);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
//                    e.printStackTrace();
                    //should not happen
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
//                    e.printStackTrace();
                    //should not happen
                }
            }
        }

        return success;
    }

    public void LoadAPK(String dexpath, String dexoutputpath) {
//        ClassLoader localClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader localClassLoader = this.getClassLoader();
//        VMStack.getCallingClassLoader();


        DexClassLoader localDexClassLoader = new DexClassLoader(dexpath, dexoutputpath, null, localClassLoader);
        try {
//            PackageInfo plocalObject = getPackageManager().getPackageArchiveInfo(dexpath, 1);

            String clsName = "plugin.apis.PluginApi";
            Log.d("sys", "activityname = " + clsName);

            Class<?> localClass = localDexClassLoader.loadClass(clsName);//结果："com.example.fragmentproject.FristActivity"
            Constructor localConstructor = localClass.getConstructor(new Class[]{});

            Object instance = localConstructor.newInstance(new Object[]{});
            Log.d("sys", "instance = " + instance);

            IPlugin plugin = (IPlugin) instance;
            String ip = plugin.parseIP("aask223.3.5fjaspfj1.02.3.4kjkl69.0.54.35jlkjn");
            Log.d("sys", "ip = " + ip);

            Log.d("sys", "isValidIp = " + plugin.isValidIp("195.5.5.5"));
            Log.d("sys", "isValidIp = " + plugin.isValidIp("1915.5.5.5"));
            Log.d("sys", "isValidIp = " + plugin.isValidIp("195.05.5.5"));
            Log.d("sys", "isValidIp = " + plugin.isValidIp("195.5.5.-5"));


//            Class[] cArg = new Class[1];
//            cArg[0] = String.class;
//
//            Method des = localClass.getMethod("parseIP", cArg);
//            String ip = (String) des.invoke(instance, "aask223.3.5fjaspfj1.02.3.4kjkl69.0.54.35jlkjn");
//
//            Log.d("sys", "ip = " + ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}