package com.kekshi.baselib.utils;

import android.os.Environment;
import android.text.TextUtils;

import java.io.*;

public class FileUtils {

    public static final String DEVICES_FILE_NAME = ".DEVICES";


    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 得到SD卡根目录.
     */
    public static File getRootPath() {
        File path = null;
        if (sdCardIsAvailable()) {
            path = Environment.getExternalStorageDirectory(); // 取得sdcard文件路径
        } else {
            path = Environment.getDataDirectory();
        }
        return path;
    }

    /**
     * SD卡是否可用.
     */
    public static boolean sdCardIsAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sd = new File(Environment.getExternalStorageDirectory().getPath());
            return sd.canWrite();
        } else {
            return false;
        }
    }

    /**
     * 文件或者文件夹是否存在.
     */
    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 用UTF8保存一个文件.
     */
    public static void saveFileUTF8(String path, String content) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        Writer out = new OutputStreamWriter(fos, "UTF-8");
        out.write(content);
        out.flush();
        out.close();
        fos.flush();
        fos.close();
    }

    /**
     * 用UTF8读取一个文件.
     */
    public static String getFileUTF8(String path) {
        String result = "";
        InputStream fin = null;
        try {
            fin = new FileInputStream(path);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            fin.close();
            result = new String(buffer, "UTF-8");
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 将字符串 保存成 文件
     *
     * @param filePath
     * @param content
     */
    public static void write(String filePath, String content) {
        BufferedWriter bw = null;
        try {
            //根据文件路径创建缓冲输出流
            bw = new BufferedWriter(new FileWriter(filePath));
            // 将内容写入文件中
            bw.write(content);
//            Log.d("M3U8替换", "替换完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    bw = null;
                }
            }
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isFileExists(String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public static File getFileByPath(String filePath) {
        return TextUtils.isEmpty(filePath) ? null : new File(filePath);
    }

    /**
     * 传入文件名以及字符串, 将字符串信息保存到文件中
     *
     * @param dir
     * @param strFilePath
     * @param strBuffer
     */
    public static void TextToFile(String dir, final String strFilePath, final String strBuffer) {
        FileWriter fileWriter = null;
        try {
            // 创建文件对象
            File file = new File(dir, strFilePath);
            if (file.exists()) {
                System.out.println(file.getAbsolutePath());
                System.out.println(file.getName());
                System.out.println(file.length());
            } else {
                file.getParentFile().mkdirs();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
            // 向文件写入对象写入信息
            fileWriter = new FileWriter(file);
            // 写文件
            fileWriter.write(strBuffer);
            // 关闭
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 获取文件名称分割符
     * windows:反斜杠\	linux:正斜杠/
     */
    public static String getFileSeparator() {
        return File.separator;
    }
}
