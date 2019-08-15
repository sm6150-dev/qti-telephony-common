package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class LceStatus {
    public static final int ACTIVE = 2;
    public static final int NOT_SUPPORTED = 0;
    public static final int STOPPED = 1;

    public static final String toString(int o) {
        if (o == 0) {
            return "NOT_SUPPORTED";
        }
        if (o == 1) {
            return "STOPPED";
        }
        if (o == 2) {
            return "ACTIVE";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        sb.append(Integer.toHexString(o));
        return sb.toString();
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        list.add("NOT_SUPPORTED");
        if ((o & 1) == 1) {
            list.add("STOPPED");
            flipped = 0 | 1;
        }
        if ((o & 2) == 2) {
            list.add("ACTIVE");
            flipped |= 2;
        }
        if (o != flipped) {
            StringBuilder sb = new StringBuilder();
            sb.append("0x");
            sb.append(Integer.toHexString((~flipped) & o));
            list.add(sb.toString());
        }
        return String.join(" | ", list);
    }
}
