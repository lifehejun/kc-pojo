package com.kc.common.util;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by timi on 2018/5/17.
 */
public class UnicodeReader extends Reader {


    PushbackInputStream internalIn;
    InputStreamReader internalIn2 = null;
    String              defaultEnc;

    private static final int BOM_SIZE = 4;

    public UnicodeReader(){

    }

    public UnicodeReader(InputStream in, String defaultEnc) {
        internalIn = new PushbackInputStream(in, BOM_SIZE);
        this.defaultEnc = defaultEnc;
    }

    public String getDefaultEncoding() {
        return defaultEnc;
    }


    public String getEncoding() {
        if (internalIn2 == null) return null;
        return internalIn2.getEncoding();
    }


    protected void init() throws IOException {
        if (internalIn2 != null) return;

        String encoding;
        byte bom[] = new byte[BOM_SIZE];
        int n, unread;
        n = internalIn.read(bom, 0, bom.length);

        if ( (bom[0] == (byte)0x00) && (bom[1] == (byte)0x00) &&
                (bom[2] == (byte)0xFE) && (bom[3] == (byte)0xFF) ) {
            encoding = "UTF-32BE";
            unread = n - 4;
        } else if ( (bom[0] == (byte)0xFF) && (bom[1] == (byte)0xFE) &&
                (bom[2] == (byte)0x00) && (bom[3] == (byte)0x00) ) {
            encoding = "UTF-32LE";
            unread = n - 4;
        } else if (  (bom[0] == (byte)0xEF) && (bom[1] == (byte)0xBB) &&
                (bom[2] == (byte)0xBF) ) {
            encoding = "UTF-8";
            unread = n - 3;
        } else if ( (bom[0] == (byte)0xFE) && (bom[1] == (byte)0xFF) ) {
            encoding = "UTF-16BE";
            unread = n - 2;
        } else if ( (bom[0] == (byte)0xFF) && (bom[1] == (byte)0xFE) ) {
            encoding = "UTF-16LE";
            unread = n - 2;
        } else {
            // Unicode BOM mark not found, unread all bytes
            encoding = defaultEnc;
            unread = n;
        }
        //System.out.println("read=" + n + ", unread=" + unread);

        if (unread > 0) internalIn.unread(bom, (n - unread), unread);

        // Use given encoding
        if (encoding == null) {
            internalIn2 = new InputStreamReader(internalIn);
        } else {
            internalIn2 = new InputStreamReader(internalIn, encoding);
        }
    }

    @Override
    public void close() throws IOException {
        init();
        internalIn2.close();
    }
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        init();
        return internalIn2.read(cbuf, off, len);
    }

    /**
     * 判断字符是否是中文
     *
     * @param c 字符
     * @return 是否是中文
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否是乱码
     *
     * @param strName 字符串
     * @return 是否是乱码
     */
    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|t*|r*|n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }

    }
}
