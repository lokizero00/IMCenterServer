package com.loki.server.utils;

import java.util.Random;

public class CommonUtil {
	private static CommonUtil instance;
	public static CommonUtil getInstance() {
		if(null == instance) {
			instance=new CommonUtil();
		}
		return instance;
	}
	
	/**
     * 随机产生字符串
     * 
     * @param length 字符串长度
     * 
     * @param type 类型 (0: 仅数字; 2:仅字符; 别的数字:数字和字符)
     * @return
     */
    public String getRandomStr(int length, int type)
    {
        String str = "";
        int beginChar = 'a';
        int endChar = 'z';
        // 只有数字
        if (type == 0)
        {
            beginChar = 'z' + 1;
            endChar = 'z' + 10;
        }
        // 只有小写字母
        else if (type == 2)
        {
            beginChar = 'a';
            endChar = 'z';
        }
        // 有数字和字母
        else
        {
            beginChar = 'a';
            endChar = 'z' + 10;
        }
        // 生成随机类
        Random random = new Random();
        for (int i = 0; i < length; i++)
        {
            int tmp = (beginChar + random.nextInt(endChar - beginChar));
            // 大于'z'的是数字
            if (tmp > 'z')
            {
                tmp = '0' + (tmp - 'z');
            }
            str += (char) tmp;
        }
        return str;
    }

}
