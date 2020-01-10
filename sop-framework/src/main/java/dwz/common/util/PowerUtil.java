package dwz.common.util;

import java.math.BigInteger;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:06
 * @Package: dwz.common.util
 */

public class PowerUtil {
    /**
     * totalPurview总权限 optPurview是一个操作要求的权限为一个整数（没有经过权的！）
     *
     * @param totalPurview
     * @param optPurview
     * @return
     */
    public static boolean checkPower(int totalPurview, int optPurview) {
        long purviewValue = (long) Math.pow(2, optPurview);
        return (totalPurview & purviewValue) == purviewValue;
    }

    public static int addPower(int totalPurview, int optPurview) {
        return totalPurview | (int) Math.pow(2, optPurview);
    }

    public static int removePower(int totalPurview, int optPurview) {
        return totalPurview & ~(int) Math.pow(2, optPurview);
    }

    public static boolean checkPower(long totalPurview, int optPurview) {
        long purviewValue = (long) Math.pow(2, optPurview);
        return (totalPurview & purviewValue) == purviewValue;
    }

    public static long addPower(long totalPurview, int optPurview) {
        return totalPurview | (long) Math.pow(2, optPurview);
    }

    public static long removePower(long totalPurview, int optPurview) {
        return totalPurview & ~(long) Math.pow(2, optPurview);
    }

    public static boolean checkPower(BigInteger totalPurview, int optPurview) {
        BigInteger purviewValue = getPurviewValue(optPurview);
        return totalPurview.and(purviewValue).compareTo(purviewValue) == 0;
    }

    public static BigInteger addPower(BigInteger totalPurview, int optPurview) {
        BigInteger purviewValue = getPurviewValue(optPurview);
        return totalPurview.or(purviewValue);
    }

    public static BigInteger removePower(BigInteger totalPurview, int optPurview) {
        BigInteger purviewValue = getPurviewValue(optPurview);
        return totalPurview.andNot(purviewValue);
    }

    private static BigInteger getPurviewValue(int optPurview) {
        return BigInteger.valueOf(2).pow(optPurview);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        // purview =2^2+2^3+2^4＝28
        for (int i = 0; i <= 5; i++) {
            System.out.println("checkPower(28, " + i + ")" + checkPower(28, i));
            System.out.println(addPower(28, i));
            System.out.println(removePower(28, i));
        }

        System.out.println("BigInteger test...");
        BigInteger totalPurview = BigInteger.valueOf(28);
        for (int i = 0; i <= 5; i++) {
            System.out.println("totalPurview=" + totalPurview + "optPurview=" + i + ": " + checkPower(totalPurview, i));
            System.out.println(addPower(totalPurview, i));
            System.out.println(removePower(totalPurview, i));
        }
    }
}
