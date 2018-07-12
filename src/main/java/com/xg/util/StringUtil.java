package com.xg.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具
 *
 * @author guzhen
 * @date 2018/7/12
 */
public class StringUtil extends StringUtils{

  /**
   * 通过正则表达式获取内容
   *
   * @param regex		正则表达式
   * @param from		原字符串
   * @return
   */
  public static String[] regex(String regex, String from){
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(from);
    List<String> results = new ArrayList<String>();
    while(matcher.find()){
      for (int i = 0; i < matcher.groupCount(); i++) {
        results.add(matcher.group(i+1));
      }
    }
    return results.toArray(new String[]{});
  }

}
