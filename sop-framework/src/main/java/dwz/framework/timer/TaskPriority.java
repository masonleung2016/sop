package dwz.framework.timer;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:12
 * @Package: dwz.framework.timer
 */

public enum TaskPriority {
    high(2), normal(1), low(0);

    private int value;

    private TaskPriority(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
