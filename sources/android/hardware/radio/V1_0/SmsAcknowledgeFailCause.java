package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class SmsAcknowledgeFailCause {
    public static final int MEMORY_CAPACITY_EXCEEDED = 211;
    public static final int UNSPECIFIED_ERROR = 255;

    public static final String toString(int o) {
        if (o == 211) {
            return "MEMORY_CAPACITY_EXCEEDED";
        }
        if (o == 255) {
            return "UNSPECIFIED_ERROR";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        sb.append(Integer.toHexString(o));
        return sb.toString();
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        if ((o & MEMORY_CAPACITY_EXCEEDED) == 211) {
            list.add("MEMORY_CAPACITY_EXCEEDED");
            flipped = 0 | MEMORY_CAPACITY_EXCEEDED;
        }
        if ((o & 255) == 255) {
            list.add("UNSPECIFIED_ERROR");
            flipped |= 255;
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
