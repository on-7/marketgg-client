package com.nhnacademy.marketgg.client.util.subutil;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

/**
 * 영어 검색어를 키보드 배열에 맞는 한국어로 전환해줍니다.
 *
 * @version 1.0.0
 */
@Slf4j
public class ConvertToKoreanUtil {

    /**
     * 두 글자로 이루어진 종성코드를 추출한 후, 중성 문자에 대한 코드를 추출하는 메소드입니다.
     *
     * @param word - 검색어입니다.
     * @param code - code[0] - iterator 반복자입니다.
     *               code[1] - initialCode 초성입니다.
     *               code[2] - medialCode 중성입니다.
     *               code[3] - finalCode 종성입니다.
     *               code[4] - tempMedialCode 임시 중성입니다.
     *               code[5] - tempFinalCode 임시 종성입니다.
     * @since 1.0.0
     */
    public void medialCodeExtraction(final String word, int[] code) {
        code[3] = code[5];
        code[4] = this.getSingleMedial(code[0] + 2, word);

        if (code[4] == -1) {
            code[0]++;
        } else {
            // 코드 값이 있을 경우
            code[3] = this.getSingleFinal(code[0], word);
            // 종성 코드 값을 저장한다.
        }
    }

    /**
     * 두 글자로 이루어진 종성코드를 추출한 후, 코드 값이 없을 경우 해당 메소드를 실행합니다.
     *
     * @param word - 검색어입니다.
     * @param code - code[0] - iterator 반복자입니다.
     *               code[1] - initialCode 초성입니다.
     *               code[2] - medialCode 중성입니다.
     *               code[3] - finalCode 종성입니다.
     *               code[4] - tempMedialCode 임시 중성입니다.
     *               code[5] - tempFinalCode 임시 종성입니다.
     * @since 1.0.0
     */
    public void noFinalCode(final String word, int[] code) {
        code[4] = this.getSingleMedial(code[0] + 1, word);
        // 그 다음의 중성 문자에 대한 코드 추출.
        if (code[4] != -1) {
            // 그 다음에 중성 문자가 존재할 경우,
            code[3] = 0; // 종성 문자는 없음.
            code[0]--;
        } else {
            code[3] = this.getSingleFinal(code[0], word);
            // 종성 문자 추출
            if (code[3] == -1) {
                code[3] = 0;
                code[0]--;
                // 초성,중성 + 숫자,특수문자,
                // 기호가 나오는 경우 index 를 줄임.
            }
        }
    }

    /**
     * 해당 문자에 따른 코드를 추출합니다.
     *
     * @param type - 초성 : chosung, 중성 : jungsung, 종성 : jongsung 구분
     * @param word - 검색어입니다.
     * @since 1.0.0
     */
    public int getCode(final String type, final String word) {
        // 초성
        String[] init =
                { "r", "R", "s", "e", "E", "f", "a", "q", "Q", "t", "T", "d", "w", "W", "c", "z",
                        "x", "v", "g" };
        // 중성
        String[] mid =
                { "k", "o", "i", "O", "j", "p", "u", "P", "h", "hk", "ho", "hl", "y", "n", "nj",
                        "np", "nl", "b", "m", "ml", "l" };
        // 종성
        String[] fin =
                { "r", "R", "rt", "s", "sw", "sg", "e", "f", "fr", "fa", "fq", "ft", "fx", "fv",
                        "fg", "a", "q", "qt", "t", "T", "d", "w", "c", "z", "x", "v", "g" };

        switch (type) {
            case "chosung":
                return Arrays.asList(init).indexOf(word) * 21 * 28;
            case "jungsung":
                return Arrays.asList(mid).indexOf(word) * 28;
            case "jongsung":
                return Arrays.asList(fin).indexOf(word) + 1;
            default:
                log.error("잘못된 타입 입니다");
        }
        return -1;
    }

    /**
     * 한 글자로 된 종성값을 반환해줍니다.
     *
     * @param i - 반복자입니다.
     * @param eng - 검색어입니다.
     * @return 한 글자로 된 중성값을 반환합니다. 인덱스를 벗어나면 -1 을 반환합니다.
     * @since 1.0.0
     */
    public int getSingleMedial(final Integer i, final String eng) {
        if ((i + 1) <= eng.length()) {
            return getCode("jungsung", eng.substring(i, i + 1));
        }
        return -1;
    }

    /**
     * 두 글자로 된 중성을 체크하고 값을 반환합니다.
     *
     * @param i - 반복자입니다.
     * @param eng - 검색어입니다.
     * @return 두 글자로 된 중성이 있으면 중성값을 반환하고, 인덱스를 벗어나면 -1 을 반환합니다.
     * @since 1.0.0
     */
    public int getDoubleMedial(final Integer i, final String eng) {
        if ((i + 2) > eng.length()) {
            return -1;
        }
        return getCode("jungsung", eng.substring(i, i + 2));
    }

    /**
     * 두 글자로 된 종성을 체크하고 값을 반환합니다.
     *
     * @param i - 반복자입니다.
     * @param eng - 검색어입니다.
     * @return 두 글자로 된 종성이 있다면 종성값을 반환하고, 인덱스를 벗어나면 -1 을 반환합니다.
     * @since 1.0.0
     */
    public int getDoubleFinal(final Integer i, final String eng) {
        if ((i + 2) > eng.length()) {
            return -1;
        }
        return getCode("jongsung", eng.substring(i, i + 2));
    }

    /**
     * 한 글자로 된 종성값을 반환해줍니다.
     *
     * @param i - 반복자입니다.
     * @param eng - 검색어입니다.
     * @return 한 글자로 된 종성값을 반환합니다. 인덱스를 벗어나면 -1 을 반환합니다.
     * @since 1.0.0
     */
    private int getSingleFinal(final Integer i, final String eng) {
        if ((i + 1) <= eng.length()) {
            return getCode("jongsung", eng.substring(i, i + 1));
        }
        return -1;
    }

}
