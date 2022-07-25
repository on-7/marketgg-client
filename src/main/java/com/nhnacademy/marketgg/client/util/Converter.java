package com.nhnacademy.marketgg.client.util;

import com.nhnacademy.marketgg.client.util.subutil.ConvertToEnglishUtil;
import com.nhnacademy.marketgg.client.util.subutil.ConvertToKoreanUtil;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

/**
 * 받은 검색어의 한=>영, 영=>한 전환을 진행 해줍니다.
 *
 * @version 1.0.0
 */
@Slf4j
public class Converter {
    private final ConvertToEnglishUtil convertToEnglishUtil = new ConvertToEnglishUtil();
    private final ConvertToKoreanUtil convertToKoreanUtil = new ConvertToKoreanUtil();

    static String ignoreChars = "`1234567890-=[]\\;',./~!@#$%^&*()_+{}|:\"<>? ";

    public String converter(final String word) {
        if (Pattern.matches("^[a-zA-Z]*$", word)) {
            return convertToKorean(word);
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

    /**
     * code[6] 의미
     *
     * code[0] - iterator
     * code[1] - initialCode
     * code[2] - medialCode
     * code[3] - finalCode
     * code[4] - tempMedialCode
     * code[5] - tempFinalCode
     */
    private String convertToKorean(final String word) {
        StringBuilder resultKor = new StringBuilder();
        int[] code = new int[6];

        while (code[0] < word.length()) { // 숫자특수문자 처리
            if (ignoreChars.contains(word.substring(code[0], code[0] + 1))) {
                resultKor.append(word.charAt(code[0]));
                code[0]++;
                continue;
            }
            // 초성코드 추출
            code[1] = convertToKoreanUtil.getCode("chosung", word.substring(code[0], code[0] + 1));
            // 다음문자로
            code[0]++;
            // 중성코드 추출
            code[4] = convertToKoreanUtil.getDoubleMedial(code[0], word);
            // 두 자로 이루어진 중성코드 추출
            if (code[4] != -1) {
                code[2] = code[4];
                code[0] += 2;
            } else {
                code[2] = convertToKoreanUtil.getSingleMedial(code[0], word);
                code[0]++;
            }
            // 종성코드 추출
            code[5] = convertToKoreanUtil.getDoubleFinal(code[0], word);
            // 두 자로 이루어진 종성코드 추출
            if (code[5] != -1) {
                // 그 다음의 중성 문자에 대한 코드를 추출한다.
                convertToKoreanUtil.medialCodeExtraction(word, code);
            } else {
                // 코드 값이 없을 경우 ,
                convertToKoreanUtil.noFinalCode(word, code);
            }
            // 추출한 초성 문자 코드,
            // 중성 문자 코드, 종성 문자 코드를 합한 후 변환하여 스트링버퍼에 넘김
            resultKor.append((char) (0xAC00 + code[1] + code[2] + code[3]));
            code[0]++;
        }
        return resultKor.toString();
    }

}
