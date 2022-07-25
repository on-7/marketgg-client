package com.nhnacademy.marketgg.client.util.subutil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConvertToKoreanUtil {

    public void medialCodeExtraction(String word, int[] code) {
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

    public void noFinalCode(String word, int[] code) {
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
     * 해당 문자에 따른 코드를 추출한다.
     *
     * @param type 초성 : chosung, 중성 : jungsung, 종성 : jongsung 구분
     * @param word 해당 문자
     */
    public int getCode(String type, String word) {
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
                for (int i = 0; i < init.length; i++) {
                    if (init[i].equals(word)) {
                        return i * 21 * 28;
                    }
                }
                break;
            case "jungsung":
                for (int i = 0; i < mid.length; i++) {
                    if (mid[i].equals(word)) {
                        return i * 28;
                    }
                }
                break;
            case "jongsung":
                for (int i = 0; i < fin.length; i++) {
                    if (fin[i].equals(word)) {
                        return i + 1;
                    }
                }
                break;
            default:
                log.error("잘못된 타입 입니다");
        }
        return -1;
    }

    // 한 자로 된 중성값을 리턴한다
    // 인덱스를 벗어낫다면 -1을 리턴
    public int getSingleMedial(int i, String eng) {
        if ((i + 1) <= eng.length()) {
            return getCode("jungsung", eng.substring(i, i + 1));
        }
        return -1;
    }

    // 두 자로 된 중성을 체크하고, 있다면 값을 리턴한다.
    // 없으면 리턴값은 -1
    public int getDoubleMedial(int i, String eng) {
        if ((i + 2) > eng.length()) {
            return -1;
        }
        return getCode("jungsung", eng.substring(i, i + 2));
    }

    // 두 자로된 종성을 체크하고, 있다면 값을 리턴한다.
    // 없으면 리턴값은 -1
    public int getDoubleFinal(int i, String eng) {
        if ((i + 2) > eng.length()) {
            return -1;
        }
        return getCode("jongsung", eng.substring(i, i + 2));
    }

    // 한 자로된 종성값을 리턴한다
    // 인덱스를 벗어낫다면 -1을 리턴
    private int getSingleFinal(int i, String eng) {
        if ((i + 1) <= eng.length()) {
            return getCode("jongsung", eng.substring(i, i + 1));
        }
        return -1;
    }

}
