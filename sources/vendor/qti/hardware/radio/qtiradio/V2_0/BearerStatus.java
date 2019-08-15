package vendor.qti.hardware.radio.qtiradio.V2_0;

import java.util.ArrayList;

public final class BearerStatus {
    public static final int ALLOCATED = 1;
    public static final int INVALID = -1;
    public static final int NOT_ALLOCATED = 0;

    public static final String toString(int o) {
        if (o == -1) {
            return "INVALID";
        }
        if (o == 0) {
            return "NOT_ALLOCATED";
        }
        if (o == 1) {
            return "ALLOCATED";
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
        list.add("NOT_ALLOCATED");
        if ((o & 1) == 1) {
            list.add("ALLOCATED");
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
