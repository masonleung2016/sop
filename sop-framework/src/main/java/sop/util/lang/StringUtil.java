package sop.util.lang;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:18
 * @Package: sop.util.lang
 */

public class StringUtil {

    public final static String EMPTY_STR = "";
    public static String EN = "en";
    public static String CH = "ch";
    public static String SQL_ESCAPE_CHAR = "/";
    private static String COLON = ":";

    /**
     * Creates new TextProcessor
     */
    private StringUtil() {
    }

    public static byte[] hexToBytes(String hexStr) throws Exception {

        if (hexStr == null) {
            return null;
        }

        if (hexStr.length() % 2 != 0) {
            throw new Exception("Length of data is not equal to even number");
        }

        byte[] rtnBytes = new byte[hexStr.length() / 2];

        for (int i = 0; i < hexStr.length() / 2; i++) {
            rtnBytes[i] = (byte) Integer.parseInt(hexStr.substring(i * 2,
                    i * 2 + 2), 16);
        }

        return rtnBytes;
    }

    /**
     * Convert a long value in millisec to hh:MM:ss
     */
    public static String longToHHMMSS(long someTime) {

        long totalSec = someTime / 1000;
        long sec = totalSec % 60;

        String ss = leftPad(Long.toString(sec), 2, '0');

        long min = ((totalSec - sec) / 60) % 60;
        String mm = leftPad(Long.toString(min), 2, '0');

        String hh = Long.toString((totalSec - sec) / 3600);

        return new StringBuffer().append(hh).append(':').append(mm).append(':')
                .append(ss).toString();
    }

    public static String leftPad(String inStr, int length, char c) {
        if (inStr.length() == length) {
            return inStr;
        }

        StringBuffer outStr = new StringBuffer();
        for (int i = inStr.length(); i < length; i++) {
            outStr.append(c);
        }
        outStr.append(inStr);

        return outStr.toString();
    }

    public static String rightPad(String inStr, int length, char c) {
        if (inStr.length() == length) {
            return inStr;
        }

        StringBuffer outStr = new StringBuffer();
        outStr.append(inStr);

        for (int i = inStr.length(); i < length; i++) {
            outStr.append(c);
        }

        return outStr.toString();
    }

    /**
     * Tokenizes a given string according to the specified delimiters. The
     * characters in the delim argument are the delimiters for separating
     * tokens. Delimiter characters themselves will not be treated as tokens.
     *
     * @param str   A string to be parsed.
     * @param delim The delimiters.
     * @return The tokens in a String array.
     */
    public static String[] tokenize(String str, String delim) {
        String[] strs = null;
        if (str != null) {
            StringTokenizer tokens;
            if (delim == null) {
                tokens = new StringTokenizer(str);
            } else {
                tokens = new StringTokenizer(str, delim);
            }
            strs = new String[tokens.countTokens()];
            for (int i = 0; i < strs.length && tokens.hasMoreTokens(); i++) {
                strs[i] = tokens.nextToken();
            }
        }
        return strs;
    }

    /**
     * Tokenizes a given string according to a fixed length. If the last token's
     * length is less than the fixed length specified, it will be ignored.
     *
     * @param str         A string to be parsed.
     * @param fixedLength The fixed length.
     * @return The tokens in a String array.
     */
    public static String[] tokenize(String str, int fixedLength) {
        String[] strs = null;
        if (str != null && fixedLength > 0) {
            Vector v = new Vector();
            for (int i = 0; i < str.length(); i += fixedLength) {
                int next = i + fixedLength;
                if (next > str.length()) {
                    next = str.length();
                }
                v.addElement(str.substring(i, next));
            }
            strs = (String[]) v.toArray(new String[]{});
        }
        return strs;
    }

    /**
     * Concatenates a String array (String tokens) into a String with the
     * specified delimiter String.
     *
     * @param tokens A String array to be concatenated.
     * @param delim  The delimiter.
     * @return The concatenated String.
     */
    public static String concat(String[] tokens, String delim) {
        return concat(tokens, EMPTY_STR, EMPTY_STR, delim);
    }

    /**
     * Concatenates a String array (String tokens) into a String with the
     * specified delimiter String, token's prefix, and token's suffix.
     *
     * @param tokens      A String array to be concatenated.
     * @param tokenPrefix The token's prefix to be concatenated.
     * @param tokenSuffix The token's suffix to be concatenated.
     * @param delim       The delimiter.
     * @return The concatenated String.
     */
    public static String concat(String[] tokens, String tokenPrefix,
                                String tokenSuffix, String delim) {
        StringBuffer s = new StringBuffer();
        if (tokens != null) {
            if (tokenPrefix == null) {
                tokenPrefix = EMPTY_STR;
            }
            if (tokenSuffix == null) {
                tokenSuffix = EMPTY_STR;
            }
            if (delim == null) {
                delim = EMPTY_STR;
            }
            for (int i = 0; i < tokens.length; i++) {
                // s += tokenPrefix+tokens[i]+tokenSuffix;
                // if (i+1<tokens.length) s += delim;
                s.append(tokenPrefix).append(tokens[i]).append(tokenSuffix);
                if (i + 1 < tokens.length) {
                    s.append(delim);
                }
            }
        }
        return s.toString();
    }

    public static String concatIgnoreNull(String str1, String str2) {
        if (null != str1 && null != str2) {
            return str1.concat(str2);
        }

        if (null != str1) {
            return str1;
        }

        if (null != str2) {
            return str2;
        }

        return "";
    }

    /**
     * Checks if a given String array contains the specified search String.
     *
     * @param tokens A String array to be searched.
     * @param target The target search String.
     * @return <PRE>
     * true
     * </PRE>
     * <p>
     * if the given String array contains the specified search String,
     *
     * <PRE>
     * false
     * </PRE>
     * <p>
     * otherwise.
     */
    public static boolean contains(String[] tokens, String target) {
        if (tokens != null) {
            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i] == null) {
                    if (target == null) {
                        return true;
                    }
                } else {
                    if (tokens[i].equals(target)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    /**
     * Repeats a given String in the specified number of times, then
     * concatenates and returns it.
     *
     * @param s      A String to be repeated and concatenated.
     * @param occurs The number of times of the given String to be repeated.
     * @return The concatenated String.
     */
    public static String repeatString(String s, int occurs) {
        StringBuffer result = new StringBuffer();
        if (s != null && occurs > 0) {
            for (int i = 0; i < occurs; i++) {
                result.append(s);
            }
        }
        return result.toString();
    }

    /**
     * Checks if a given String contains only digits.
     *
     * @param s A String to be checked.
     * @return <PRE>
     * true
     * </PRE>
     * <p>
     * if the given String contains only digits,
     *
     * <PRE>
     * false
     * </PRE>
     * <p>
     * otherwise.
     */
    public static boolean isAllDigit(String s) {
        if (s == null || s.equals(EMPTY_STR)) {
            return false;
        } else {
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static boolean isAllAlphanumeric(String s) {

        if (s == null || s.equals(EMPTY_STR)) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[a-zA-Z]*");

        Matcher matcher = pattern.matcher(s);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isAllLetter(String s) {

        if (s == null || s.equals(EMPTY_STR)) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]*");

        Matcher matcher = pattern.matcher(s);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isAllCapitalAlphanumeric(String s) {

        if (s == null || s.equals(EMPTY_STR)) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[A-Z]*");

        Matcher matcher = pattern.matcher(s);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Formats a given double into a String according to the specified pattern.
     *
     * @param d       The double to be formatted.
     * @param pattern The pattern to be followed in formatting.
     * @return The formatted String of the double.
     */
    public static String formatDecimal(double d, String pattern) {
        return new DecimalFormat(pattern).format(d);
    }

    public static String formatDecimal(BigDecimal b, String pattern) {
        String result = new DecimalFormat(pattern).format(b);
        StringBuffer str = new StringBuffer();
        while (result.startsWith("0")) {
            result = result.substring(1, result.length());
            str.append(" ");
        }

        return str.toString() + result;
    }

    /**
     * Formats a given java.util.Date into a String according to the specified
     * pattern.
     *
     * @param date    The date to be formatted.
     * @param pattern The pattern to be followed in formatting.
     * @return The formatted String of the date.
     */
    public static String formatDate(java.util.Date date, String pattern) {
        return formatDate(date, pattern, null);
    }

    /**
     * Formats a given java.util.Date into a String according to the specified
     * pattern.
     *
     * @param locale  The locale used in formatting.
     * @param date    The date to be formatted.
     * @param pattern The pattern to be followed in formatting.
     * @return The formatted String of the date.
     */
    public static String formatDate(java.util.Date date, String pattern,
                                    Locale locale) {
        if (date == null)
            return null;
        try {
            if (locale == null) {
                return new SimpleDateFormat(pattern).format(date);
            } else {
                return new SimpleDateFormat(pattern, locale).format(date);
            }
        } catch (Exception e) {
            return date.toString();
        }
    }

    /**
     * Parses a date string and returns a java.util.Date object.
     *
     * @param date    The date string to be parsed.
     * @param pattern The pattern of the date string.
     * @return A java.util.Date object that represents the given date string.
     */
    public static java.util.Date parseDate(String date, String pattern) {
        return parseDate(date, pattern, null);
    }

    /**
     * Parses a date string and returns a java.util.Date object.
     *
     * @param date    The date string to be parsed.
     * @param pattern The pattern of the date string.
     * @param locale  The locale used in parsing the date string.
     * @return A java.util.Date object that represents the given date string.
     */
    public static java.util.Date parseDate(String date, String pattern,
                                           Locale locale) {
        try {
            SimpleDateFormat dateFormatter;
            if (locale == null) {
                dateFormatter = new SimpleDateFormat(pattern);
            } else {
                dateFormatter = new SimpleDateFormat(pattern, locale);
            }
            dateFormatter.setLenient(false);
            return dateFormatter.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Adds leading zeros to the given String to the specified length. Nothing
     * will be done if the length of the given String is equal to or greater
     * than the specified length.
     *
     * @param s   The source string.
     * @param len The length of the target string.
     * @return The String after adding leading zeros.
     */
    public static String addLeadingZero(String s, int len) {
        return addLeadingCharacter(s, '0', len);
    }

    /**
     * Adds leading spaces to the given String to the specified length. Nothing
     * will be done if the length of the given String is equal to or greater
     * than the specified length.
     *
     * @param s   The source string.
     * @param len The length of the target string.
     * @return The String after adding leading spaces.
     */
    public static String addLeadingSpace(String s, int len) {
        return addLeadingCharacter(s, ' ', len);
    }

    /**
     * Adds specified leading characters to the specified length. Nothing will
     * be done if the length of the given String is equal to or greater than the
     * specified length.
     *
     * @param s   The source string.
     * @param c   The leading character(s) to be added.
     * @param len The length of the target string.
     * @return The String after adding the specified leading character(s).
     */
    public static String addLeadingCharacter(String s, char c, int len) {
        if (s != null) {
            StringBuffer sb = new StringBuffer();
            int count = len - s.length();
            for (int i = 0; i < count; i++) {
                sb.append(c);
            }
            sb.append(s);
            return sb.toString();
        } else {
            return null;
        }
    }

    /**
     * Removes leading zeros from the given String, if any.
     *
     * @param s The source string.
     * @return The String after removing leading zeros.
     */
    public static String removeLeadingZero(String s) {
        return removeLeadingCharacter(s, '0');
    }

    /**
     * Removes leading spaces from the given String, if any.
     *
     * @param s The source string.
     * @return The String after removing leading spaces.
     */
    public static String removeLeadingSpace(String s) {
        return removeLeadingCharacter(s, ' ');
    }

    /**
     * Removes specified leading characters from the given String, if any.
     *
     * @param s The source string.
     * @param c The leading character(s) to be removed.
     * @return The String after removing the specified leading character(s).
     */
    public static String removeLeadingCharacter(String s, char c) {
        if (s != null) {
            int len = s.length();
            int i = 0;
            for (i = 0; i < len; i++) {
                if (s.charAt(i) != c) {
                    break;
                }
            }
            if (i > 0) {
                return s.substring(i);
            } else {
                return s;
            }

        } else {
            return null;
        }
    }

    /**
     * Appends zeros to the given String to the specified length. Nothing will
     * be done if the length of the given String is equal to or greater than the
     * specified length.
     *
     * @param s   The source string.
     * @param len The length of the target string.
     * @return The String after appending zeros.
     */
    public static String appendZero(String s, int len) {
        return appendCharacter(s, '0', len);
    }

    /**
     * Appends spaces to the given String to the specified length. Nothing will
     * be done if the length of the given String is equal to or greater than the
     * specified length.
     *
     * @param s   The source string.
     * @param len The length of the target string.
     * @return @return The String after appending spaces.
     */
    public static String appendSpace(String s, int len) {
        return appendCharacter(s, ' ', len);
    }

    /**
     * Appends specified characters to the given String to the specified length.
     * Nothing will be done if the length of the given String is equal to or
     * greater than the specified length.
     *
     * @param s   The source string.
     * @param c   The character(s) to be appended.
     * @param len The length of the target string.
     * @return @return The String after appending the specified character(s).
     */
    public static String appendCharacter(String s, char c, int len) {
        if (s != null) {
            StringBuffer sb = new StringBuffer().append(s);
            while (sb.length() < len) {
                sb.append(c);
            }
            return sb.toString();
        } else {
            return null;
        }
    }

    public static String appendCharacter(String s, String appendStr, int len) {
        if (s != null) {
            StringBuffer sb = new StringBuffer().append(s);
            while (sb.length() < len) {
                sb.append(appendStr);
            }
            return sb.toString();
        } else {
            return null;
        }
    }

    /**
     * Checks if a given string is null or empty after trimmed.
     *
     * @param s The String to be checked.
     * @return <PRE>
     * true
     * </PRE>
     * <p>
     * if the given string is null or empty after trimmed,
     *
     * <PRE>
     * false
     * </PRE>
     * <p>
     * otherwise.
     */
    public static boolean isEmptyString(String s) {
        return (s == null || (s.trim()).equals(EMPTY_STR));
    }

    public static boolean isEmpty(String[] s) {
        if (null == s || 0 == s.length) {
            return true;
        }

        for (int i = 0; i < s.length; ++i) {
            if (!isEmptyString(s[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns a String representation of the given object, empty String if it
     * is null.
     *
     * @param obj The Object for getting its String representation.
     * @return A String represenation of the given Object.
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return EMPTY_STR;
        } else {
            return obj.toString();
        }
    }

    /**
     * Trims a given String. An empty String will be returned if the given
     * String is null.
     *
     * @param s The String to be Trimmed.
     * @return The String trimmed.
     */
    public static String trim(String s) {
        if (s == null) {
            return EMPTY_STR;
        } else {
            return s.trim();
        }
    }

    /**
     * Trims a given String. An empty String will be returned if the given
     * String is null.
     * It will remove all whiteSpace.
     *
     * @param s The String to be Trimmed.
     * @return The String trimmed.
     */
    public static String trimAll(String s) {
        if (s == null) {
            return EMPTY_STR;
        } else {
            return s.replaceAll("[\u0020|\u3000]", "");
        }
    }

    /**
     * Trims a given String. An empty String will be returned if the given
     * String is null.
     * It will replace all whiteSpace to be one space.
     *
     * @param s The String to be Trimmed.
     * @return The String trimmed.
     */
    public static String replaceMutliSpace(String s) {
        if (s == null) {
            return EMPTY_STR;
        } else {
            return s.replaceAll("[\u0020|\u3000]{2,}", " ");
        }
    }

    /**
     * Trims a given String and then verifies its size against the specified
     * size. If the sizes do not match, null will be returned.
     *
     * @param s    The String to be trimmed and verified.
     * @param size The size for the verification.
     * @return The trimmed String or null if the size verification failed.
     */
    public static String trimAndVerifySize(String s, int size) {

        s = trim(s);

        if (s.length() != size) {
            return null;
        } else {
            return s;
        }
    }

    /**
     * Remove empty element in the String[].
     *
     * @param s The String[] to be operated.
     * @return The String[] after be removed empty element.
     */
    public static String[] removeEmptyElement(String[] s) {

        if (!isEmpty(s)) {
            List<String> strList = new ArrayList<String>();
            if (null != s && s.length != 0) {
                for (int i = 0; i < s.length; i++) {
                    if (!isEmptyString(s[i])) {
                        strList.add(s[i]);
                    }
                }

                String[] notEmptyElement = new String[strList.size()];
                for (int j = 0; j < strList.size(); j++) {
                    notEmptyElement[j] = strList.get(j);
                }

                return notEmptyElement;
            }
        }
        return s;
    }

    /**
     * Remove same element in the String[].
     *
     * @param s The String[] to be operated.
     * @return The String[] after be removed same element.
     */
    public static String[] removeSameElement(String[] s) {
        if (!isEmpty(s)) {
            Set<String> strSet = new HashSet<String>();
            for (int i = 0; i < s.length; i++) {
                strSet.add(s[i]);
            }

            return strSet.toArray(new String[strSet.size()]);
        }
        return s;
    }

    /**
     * Remove element element in the String[].
     *
     * @param s The String[] to be operated.
     * @return The String[] after be removed needRemove element.
     */
    public static String[] removeElement(String[] s, String element) {

        List<String> strList = new ArrayList<String>();
        if (null != s && s.length != 0) {
            for (int i = 0; i < s.length; i++) {
                strList.add(s[i]);
            }

            List<String> notNeedRemoveElement = new ArrayList<String>();
            for (int j = 0; j < strList.size(); j++) {
                if (!strList.get(j).equals(element)) {
                    notNeedRemoveElement.add(strList.get(j));
                }
            }

            return notNeedRemoveElement.toArray(new String[notNeedRemoveElement.size()]);
        } else {
            return null;
        }

    }

    /**
     * Replaces all the occurences of a search string in a given String with a
     * specified substitution.
     *
     * @param text The String to be searched.
     * @param src  The search String.
     * @param tar  The replacement String.
     * @return The result String after replacing.
     */
    public static String replace(String text, String src, String tar) {
        StringBuffer sb = new StringBuffer();

        if (text == null || src == null || tar == null) {
            return text;
        } else {
            int size = text.length();
            int gap = src.length();

            for (int start = 0; start >= 0 && start < size; ) {
                int i = text.indexOf(src, start);
                if (i == -1) {
                    sb.append(text.substring(start));
                    start = -1;
                } else {
                    sb.append(text.substring(start, i)).append(tar);
                    start = i + gap;
                }
            }
            return sb.toString();
        }
    }

    /**
     * To judge all the characters if are ascii
     *
     * @param str The String to be judged.
     * @return judged result true or false
     */
    public static boolean isAllAscii(String str) {
        boolean flag = true;
        for (int i = 0; i < str.length(); i++) {
            int code = str.charAt(i);
            if (code < 255 && code > -1) {
            } else {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * @param in
     * @return
     */
    public static String toHtmlString(String in) {
        if (null != in) {
            in = in.replaceAll("<", "&lt;");
            in = in.replaceAll(">", "&gt;");
            in = in.replaceAll((new Character((char) 9))
                    .toString(), "&nbsp;&nbsp;&nbsp;&nbsp; ");
            in = in.replaceAll("  ", "&nbsp;&nbsp; ");
            in = in.replaceAll(" 1�7", "'");
            in = in.replaceAll((new Character((char) 10))
                    .toString(), "<br>");
        } else {
            in = "";
        }
        return in;
    }

    /**
     * Replace '%' for SQL parameter
     *
     * @param in
     * @return
     */
    public static String toSqlParamString(String in) {
        if (null != in) {
            in = in.replaceAll("%", SQL_ESCAPE_CHAR + "%");
            in = in.replaceAll("＄1�7", SQL_ESCAPE_CHAR + "%");
        } else {
            in = "";
        }
        return in;
    }

    public static String getStr(String enStr, String chStr, String language) {
        if (EN.equals(language)) {
            if (!StringUtil.isEmptyString(enStr)) {
                return enStr;
            } else {
                return chStr;
            }
        }

        if (CH.equals(language)) {
            if (!StringUtil.isEmptyString(chStr)) {
                return chStr;
            } else {
                return enStr;
            }
        }

        return "";
    }

    public static String removeTailZero(String s) {
        return removeTailCharacter(s, '0');
    }

    public static String removeTailCharacter(String s, char c) {
        if (s != null) {
            int len = s.length();
            int i = len - 1;
            for (i = len - 1; i >= 0; i--) {
                if (s.charAt(i) != c) {
                    break;
                }
            }
            if (i >= 0) {
                return s.substring(0, i + 1);
            } else {
                return null;
            }

        } else {
            return null;
        }
    }

    public static String doubleToString(Double d) {
        if (d == null) {
            return "";
        }
        DecimalFormat format = new DecimalFormat("0.###");
        return format.format(d.doubleValue());
    }

    /**
     * like c string.h strncasecmp
     *
     * @param str1
     * @param str2
     * @param length
     * @return
     */
    public static boolean equalsIgnoreCase(String str1, String str2, int length) {
        if (str1 == null
                && str2 == null) {
            return true;
        } else if (str1 == null
                && str2 != null) {
            return false;
        } else if (str1 != null
                && str2 == null) {
            return false;
        } else {
            String _str1 = str1;
            String _str2 = str2;

            if (str1.length() >= length) {
                _str1 = str1.substring(0, length - 1);
            }
            if (str2.length() >= length) {
                _str2 = str2.substring(0, length - 1);
            }

            return StringUtils.equalsIgnoreCase(_str1, _str2);
        }
    }

    public static String getPrefixSubstring(int length, String str) {
        if (str == null) {
            return null;
        }
        if (length < 0) {
            return str;
        }
        if (length > str.length()) {
            return str;
        }
        String result = str.substring(0, length);

        return result;

    }

    public static String strchr(String g_sInFileName, char indexStr) {
        if (g_sInFileName == null) {
            return null;
        }
        int index = g_sInFileName.indexOf(indexStr);
        if (index == -1) {
            return null;
        } else {
            return g_sInFileName.substring(index);
        }
    }

    /**
     * @param str1
     * @param str2
     * @return if true, str1 is different with str2, false is equal
     */
    public static boolean strcmp(String str1, String str2) {
        if (str1 == null) {
            if (str2 == null) {
                return false;
            } else {
                return true;
            }
        } else {
            if (str2 == null) {
                return true;
            }
        }
        if (str1.compareTo(str2) == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param str1
     * @param str2
     * @return if false, str1 is different with str2, true is equal
     */
    public static boolean strcmp2(String str1, String str2) {
        if (str1 == null) {
            if (str2 == null) {
                return false;
            } else {
                return true;
            }
        } else {
            if (str2 == null) {
                return true;
            }
        }
        if (str1.compareTo(str2) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isBlank(String str) {
        return org.apache.commons.lang.StringUtils.isBlank(str);
    }

    public static boolean isHKID(String idNo, String chkDig) {

        if (isEmptyString(idNo) || isEmptyString(chkDig)) {
            return false;
        }

        int idLen = idNo.length();

        if (idLen < 7) {
            return false;
        }

        String prefix = idNo.substring(0, idLen - 6).toUpperCase();
        String num = idNo.substring(idLen - 6);


        if (prefix.length() == 2) {
            if (1 == prefix.indexOf(" ")) {
                return false;
            } else {
                prefix = prefix.trim();
            }
        }
        String regPrefix = "[A-Z]{1,2}";
        String regNum = "[0-9]{6}";
        String regChkDig = "[A0-9]{1}";

        if (prefix.matches(regPrefix) && num.matches(regNum) && chkDig.toUpperCase().matches(regChkDig)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isGreaterThanZero(String ser) {

        if (isEmptyString(ser)) {
            return false;
        }

        try {
            Integer serInt = Integer.parseInt(ser);
            if (serInt > 0) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String handleNull(String str) {
        return str != null ? str : EMPTY_STR;
    }

    /**
     * Test driver.
     *
     * @param args The arguments.
     */
    public static void main(String[] args) {

//		System.out.println(StringUtil.leftPad("1", 2, '0'));
//		System.out.println(StringUtil.addLeadingZero("55", 1));
//		System.out.println(StringUtil.addLeadingZero("56", 2));
//		System.out.println(StringUtil.addLeadingZero("5", 3));
//		System.out.println(StringUtil.addLeadingZero("0", 0));
//		System.out.println(StringUtil.addLeadingZero("", -1));
//
//		System.out.println(StringUtil.removeLeadingZero("00055"));
//		System.out.println(StringUtil.removeLeadingZero("56"));
//		System.out.println(StringUtil.removeLeadingZero("5"));
//		System.out.println(StringUtil.removeLeadingZero("0"));
//		System.out.println(StringUtil.removeLeadingZero(""));
//		System.out.println(StringUtil.isAllLetter("8"));
//		String[] s = new String[]{"1","","2","","4","1","2","3"};
//		s = StringUtil.removeSameElement(s);
        double originalWeight = 1.3, partialWeight = 1.2;
        System.out.println(doubleToString(originalWeight - partialWeight));

        System.out.println(StringUtil.equalsIgnoreCase("", null, 3));

    }


}
