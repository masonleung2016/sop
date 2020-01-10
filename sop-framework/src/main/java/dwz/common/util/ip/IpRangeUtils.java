package dwz.common.util.ip;

/**
 * @Author: LCF
 * @Date: 2020/1/8 15:35
 * @Package: dwz.common.util.ip
 */

public class IpRangeUtils {

    private static final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";
    private static final String REGX_IPB = REGX_IP + "\\-" + REGX_IP;

    /**
     * 判断IP是否在指定范围；
     */
    public static boolean ipIsValid(String ipSection, String ip) {
        if (ipSection == null)
            throw new NullPointerException("IP段不能为空！");
        if (ip == null)
            throw new NullPointerException("IP不能为空！");
        ipSection = ipSection.trim();
        ip = ip.trim();

        //单IP匹配
        if (ipSection.matches(REGX_IP)) {
            return ipSection.equals(ip);
        }

        //IP段匹配 102.168.1.1-192.168.1.100
        if (!ipSection.matches(REGX_IPB) || !ip.matches(REGX_IP))
            return false;

        int idx = ipSection.indexOf('-');
        String[] sips = ipSection.substring(0, idx).split("\\.");
        String[] sipe = ipSection.substring(idx + 1).split("\\.");
        String[] sipt = ip.split("\\.");
        long ips = 0L, ipe = 0L, ipt = 0L;
        for (int i = 0; i < 4; ++i) {
            ips = ips << 8 | Integer.parseInt(sips[i]);
            ipe = ipe << 8 | Integer.parseInt(sipe[i]);
            ipt = ipt << 8 | Integer.parseInt(sipt[i]);
        }
        if (ips > ipe) {
            long t = ips;
            ips = ipe;
            ipe = t;
        }
        return ips <= ipt && ipt <= ipe;
    }

    /**
     * @param ipRangeContent:192.168.1.1-192.168.1.100 过个IP段用换行风格
     * @return
     */
    public static String fixIpRanges(String ipRangeContent) {
        StringBuilder sb = new StringBuilder();
        if (ipRangeContent != null && ipRangeContent.length() > 0) {
            String[] rows = ipRangeContent.split("(\\r\\n|\\n)");

            for (String row : rows) {
                if (row.matches(REGX_IPB) || row.matches(REGX_IP)) {
                    sb.append(row).append("\n");
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        if (ipIsValid("192.168.1.1-192.168.1.100", "192.168.1.1")) {
            System.out.println("ip属于该网段");
        } else
            System.out.println("ip不属于该网段");

        System.out.println(ipIsValid("192.168.1.1", "192.168.1.1"));

    }
}
