package sop.utils;

import java.text.DecimalFormat;

/**
 * 把表示美元的阿拉伯数字转换为英文大写的格式。
 *
 * @Author: LCF
 * @Date: 2020/1/9 11:27
 * @Package: sop.utils
 */

public class DollarNumberFormat {
    private static final String[] MAJORS = {" ", " Thousand ", " Million ",
            " Billion ", " Trillion ", " Quadrillion ", " Quintillion "};

    private static final String[] TENS = {" ", " Ten ", " Twenty ", " Thirty ",
            " Forty ", " Fifty ", " Sixty ", " Seventy ", " Eighty ",
            " Ninety "};

    private static final String[] NUMBERS = {" ", " One ", " Two ", " Three ",
            " Four ", " Five ", " Six ", " Seven ", " Eight ", " Nine ",
            " Ten ", " Eleven ", " Twelve ", " Thirteen ", " Fourteen ",
            " Fifteen ", " Sixteen ", " Seventeen ", " Eighteen ", " Nineteen "};

    /**
     * 把阿拉伯数字转换为英文大写格式。<br>
     * 例如：123.01：One Hundred Twenty Three Dollars And One Cent
     *
     * @param value
     * @return
     */
    public String format(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        double number = Double.parseDouble(df.format(value));
        // 小数点保留两位
        String result = getDollarPart(number) + getCentPart(number);
        return result.replace("  ", " ").toUpperCase();
        // 去掉多余的空格，并转为大写
    }

    // 获取整数部分的转换
    private String getDollarPart(double value) {
        int number = (int) value;
        String dollars = formatDigit(number);
        if (dollars.trim().equals(""))
            return "No Dollars";
        else if (dollars.trim().equals("One"))
            return "One Dollar";
        else
            return dollars + " Dollars";

    }

    // 获取小数部分的转换
    private String getCentPart(double number) {
        int cents = (int) (Math.round(number * 100) - ((int) number) * 100);
        String centsPart = formatDigit(cents);

        if (centsPart.trim().equals(""))
            return " And No Cents";
        else if (centsPart.trim().equals("One"))
            return " And One Cent";
        else
            return " And " + centsPart + " Cents";
    }

    private String formatDigit(int value) {
        int number = value;
        String prefix = "";
        // 正负号前缀。

        if (number < 0) {
            number = -number;
            prefix = "Negative ";
        }

        String result = "";
        int index = 0;
        do {
            int n = number % 1000;
            if (n != 0) {
                result = formatLessThanOneThousand(n) + MAJORS[index] + result;
            }
            index++;
            number /= 1000;
        } while (number > 0);

        return (prefix + result).trim();
    }

    private String formatLessThanOneThousand(int value) {
        int number = value;
        String result = "";
        if (number % 100 < 20) {
            // 前两位数的值在20以内。
            result = NUMBERS[number % 100];
            number /= 100;
        } else {// 前两位数的值在20以外。
            result = NUMBERS[number % 10];
            // 个位
            number /= 10;

            result = TENS[number % 10] + result;
            // 十位&个位
            number /= 10;
        }
        if (number == 0)
            return result;
        else
            return NUMBERS[number] + " Hundred " + result;
        // 百位&十位&个位。
    }

}
