package com.nhnacademy.marketgg.client.util;

import com.nhnacademy.marketgg.client.util.subutil.ConvertToEnglishUtil;
import java.util.regex.Pattern;

/**
 * 받은 검색어의 한=>영 전환을 진행 해줍니다.
 *
 * @version 1.0.0
 */
public class Converter {
    private final ConvertToEnglishUtil convertToEnglishUtil = new ConvertToEnglishUtil();

    static String ignoreChars = "`1234567890-=[]\\;',./~!@#$%^&*()_+{}|:\"<>? ";

    public String converter(final String word) {
        if (Pattern.matches("^[a-zA-Z]*$", word)) {
            return word;
        }
        return convertToEnglish(word);
    }

    private String convertToEnglish(final String word) {
        StringBuilder resultEng = new StringBuilder();
        int i = 0;

        while (i < word.length()) {
            if (!ignoreChars.contains(word.substring(i, i + 1))) {/*  한글자씩 읽어들인다. */
                char chars = (char) (word.charAt(i) - 0xAC00);
                /* A. 자음과 모음이 합쳐진 글자인경우 */
                convertToEnglishUtil.combineWord(resultEng, chars);
            } else {
                resultEng.append(word.charAt(i));
            }
            i++;
        }
        return resultEng.toString();
    }

}
