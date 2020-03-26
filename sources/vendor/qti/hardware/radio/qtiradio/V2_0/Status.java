package vendor.qti.hardware.radio.qtiradio.V2_0;

import java.util.ArrayList;

public final class Status {
    public static final int FAILURE = 0;
    public static final int INVALID = -1;
    public static final int SUCCESS = 1;

    public static final String toString(int o) {
        if (o == -1) {
            return "INVALID";
        }
        if (o == 0) {
            return "FAILURE";
        }
        if (o == 1) {
            return "SUCCESS";
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
        list.add("FAILURE");
        if ((o & 1) == 1) {
            list.add("SUCCESS");
            flipped |= 1;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
