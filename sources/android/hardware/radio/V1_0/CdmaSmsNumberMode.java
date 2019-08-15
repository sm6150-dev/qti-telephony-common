package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class CdmaSmsNumberMode {
    public static final int DATA_NETWORK = 1;
    public static final int NOT_DATA_NETWORK = 0;

    public static final String toString(int o) {
        if (o == 0) {
            return "NOT_DATA_NETWORK";
        }
        if (o == 1) {
            return "DATA_NETWORK";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        sb.append(Integer.toHexString(o));
        return sb.toString();
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        list.add("NOT_DATA_NETWORK");
        if ((o & 1) == 1) {
            list.add("DATA_NETWORK");
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
