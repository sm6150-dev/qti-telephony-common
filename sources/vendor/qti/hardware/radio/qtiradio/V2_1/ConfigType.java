package vendor.qti.hardware.radio.qtiradio.V2_1;

import java.util.ArrayList;

public final class ConfigType {
    public static final int INVALID = -1;
    public static final int NSA_CONFIGURATION = 0;
    public static final int SA_CONFIGURATION = 1;

    public static final String toString(int o) {
        if (o == -1) {
            return "INVALID";
        }
        if (o == 0) {
            return "NSA_CONFIGURATION";
        }
        if (o == 1) {
            return "SA_CONFIGURATION";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        sb.append(Integer.toHexString(o));
        return sb.toString();
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        if ((o & -1) == -1) {
            list.add("INVALID");
            flipped = 0 | -1;
        }
        list.add("NSA_CONFIGURATION");
        if ((o & 1) == 1) {
            list.add("SA_CONFIGURATION");
            flipped |= 1;
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
