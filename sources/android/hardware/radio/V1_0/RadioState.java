package android.hardware.radio.V1_0;

import java.util.ArrayList;

public final class RadioState {
    public static final int OFF = 0;
    public static final int ON = 10;
    public static final int UNAVAILABLE = 1;

    public static final String toString(int o) {
        if (o == 0) {
            return "OFF";
        }
        if (o == 1) {
            return "UNAVAILABLE";
        }
        if (o == 10) {
            return "ON";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        sb.append(Integer.toHexString(o));
        return sb.toString();
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        list.add("OFF");
        if ((o & 1) == 1) {
            list.add("UNAVAILABLE");
            flipped = 0 | 1;
        }
        if ((o & 10) == 10) {
            list.add("ON");
            flipped |= 10;
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
