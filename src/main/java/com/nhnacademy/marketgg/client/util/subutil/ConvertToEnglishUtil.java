package com.nhnacademy.marketgg.client.util.subutil;

/**
 * 알파벳으로 변환
 * 박세완 -> qkrtpdhks, 천재 -> cjswo
 *
 * @version 1.0.0
 */
public class ConvertToEnglishUtil {

    /**
     * 초성 - 가(ㄱ), 날(ㄴ) 닭(ㄷ)
     */
    private final String[] arrChoSungEng;

    /**
     * 중성 - 가(ㅏ), 야(ㅑ), 뺨(ㅑ)
     */
    private final String[] arrJungSungEng;

    /**
     * 종성 - 가(없음), 갈(ㄹ) 천(ㄴ)
     */
    private final String[] arrJongSungEng;

    /**
     * 단일 자음 - ㄱ,ㄴ,ㄷ,ㄹ... (ㄸ,ㅃ,ㅉ은 단일자음(초성)으로 쓰이지만 단일자음으론 안쓰임)
     */
    private final String[] arrSingleJaumEng;

    public ConvertToEnglishUtil() {
        this.arrChoSungEng = new String[]{ "r", "R", "s", "e", "E",
                "f", "a", "q", "Q", "t", "T", "d", "w",
                "W", "c", "z", "x", "v", "g" };

        this.arrJungSungEng = new String[]{ "k", "o", "i", "O",
                "j", "p", "u", "P", "h", "hk", "ho", "hl",
                "y", "n", "nj", "np", "nl", "b", "m", "ml",
                "l" };

        this.arrJongSungEng = new String[]{ "", "r", "R", "rt",
                "s", "sw", "sg", "e", "f", "fr", "fa", "fq",
                "ft", "fx", "fv", "fg", "a", "q", "qt", "t",
                "T", "d", "w", "c", "z", "x", "v", "g" };

        this.arrSingleJaumEng = new String[]{"r", "R", "rt",
                "s", "sw", "sg", "e", "E", "f", "fr", "fa", "fq",
                "ft", "fx", "fv", "fg", "a", "q", "Q", "qt", "t",
                "T", "d", "w", "W", "c", "z", "x", "v", "g"};
    }

    public void combineWord(StringBuilder resultEng, char chars) {
        if (chars <= 11172) {
            /* A-1. 초/중/종성 분리 */
            this.wordSeparation(resultEng, chars);
            /* B. 한글이 아니거나 자음만 있을경우 */
        } else {
            /* 알파벳으로 */
            this.convertToAlphabet(resultEng, chars);
        }
    }

    private void wordSeparation(final StringBuilder resultEng, final char chars) {
        int chosung = chars / (21 * 28);
        int jungsung = chars % (21 * 28) / 28;
        int jongsung = chars % (21 * 28) % 28;

        /* 알파벳으로 */
        resultEng.append(this.arrChoSungEng[chosung])
                 .append(this.arrJungSungEng[jungsung]);
        if (jongsung != 0x0000) {
            /* A-3. 종성이 존재할경우 result 에 담는다 */
            resultEng.append(this.arrJongSungEng[jongsung]);
        }
    }

    private void convertToAlphabet(StringBuilder resultEng, char chars) {
        if (chars >= 34097 && chars <= 34126) {
            /* 단일자음인 경우 */
            int jaum = (chars - 34097);
            resultEng.append(this.arrSingleJaumEng[jaum]);
        } else if (chars >= 34127 && chars <= 34147) {
            /* 단일모음인 경우 */
            int moum = (chars - 34127);
            resultEng.append(this.arrJungSungEng[moum]);
        } else {
            /* 알파벳인 경우 */
            resultEng.append(((char) (chars + 0xAC00)));
        }
    }

}
