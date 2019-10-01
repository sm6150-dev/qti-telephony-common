package vendor.qti.hardware.radio.qtiradio.V1_0;

import java.util.ArrayList;

public final class QtiRadioIndicationType {
    public static final int UNSOLICITED = 0;
    public static final int UNSOLICITED_ACK_EXP = 1;

    public static final String toString(int o) {
        if (o == 0) {
            return "UNSOLICITED";
        }
        if (o == 1) {
            return "UNSOLICITED_ACK_EXP";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        sb.append(Integer.toHexString(o));
        return sb.toString();
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        list.add("UNSOLICITED");
        if ((o & 1) == 1) {
            list.add("UNSOLICITED_ACK_EXP");
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
