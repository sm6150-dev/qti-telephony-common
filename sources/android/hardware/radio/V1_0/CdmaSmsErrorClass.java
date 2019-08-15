package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class CdmaSmsErrorClass {
    public static final int ERROR = 1;
    public static final int NO_ERROR = 0;

    public static final String toString(int o) {
        if (o == 0) {
            return "NO_ERROR";
        }
        if (o == 1) {
            return "ERROR";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        sb.append(Integer.toHexString(o));
        return sb.toString();
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        list.add("NO_ERROR");
        if ((o & 1) == 1) {
            list.add("ERROR");
            flipped = 0 | 1;
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
