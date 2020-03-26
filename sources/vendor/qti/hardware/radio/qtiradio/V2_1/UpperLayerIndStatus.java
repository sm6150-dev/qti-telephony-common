package vendor.qti.hardware.radio.qtiradio.V2_1;

import java.util.ArrayList;

public final class UpperLayerIndStatus {
    public static final int AVAILABLE = 1;
    public static final int INVALID = -1;
    public static final int UNAVAILABLE = 0;

    public static final String toString(int o) {
        if (o == -1) {
            return "INVALID";
        }
        if (o == 0) {
            return "UNAVAILABLE";
        }
        if (o == 1) {
            return "AVAILABLE";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        if ((o & -1) == -1) {
            list.add("INVALID");
            flipped = 0 | -1;
        }
        list.add("UNAVAILABLE");
        if ((o & 1) == 1) {
            list.add("AVAILABLE");
            flipped |= 1;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
