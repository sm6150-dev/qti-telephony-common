package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class CdmaSubscriptionSource {
    public static final int NV = 1;
    public static final int RUIM_SIM = 0;

    public static final String toString(int o) {
        if (o == 0) {
            return "RUIM_SIM";
        }
        if (o == 1) {
            return "NV";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        sb.append(Integer.toHexString(o));
        return sb.toString();
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        list.add("RUIM_SIM");
        if ((o & 1) == 1) {
            list.add("NV");
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