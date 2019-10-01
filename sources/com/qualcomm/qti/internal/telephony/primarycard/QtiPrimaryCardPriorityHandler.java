package com.qualcomm.qti.internal.telephony.primarycard;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.telephony.Rlog;
import com.android.internal.util.XmlUtils;
import com.qualcomm.qti.internal.telephony.primarycard.QtiCardInfoManager.CardInfo;
import java.util.HashMap;
import java.util.regex.Pattern;

public class QtiPrimaryCardPriorityHandler {
    private static final boolean DBG = true;
    private static final int INVALID_NETWORK = -1;
    private static final int INVALID_PRIORITY = -1;
    private static final String LOG_TAG = "QtiPcPriorityHandler";
    private static final boolean VDBG = false;
    private static String packageName = "com.qualcomm.qti.simsettings";
    private HashMap<Integer, PriorityConfig> mAllPriorityConfigs = new HashMap<>();
    private final Context mContext;
    private PriorityConfig[] mCurrPriorityConfigs = null;
    private boolean mLoadingConfigCompleted = VDBG;
    private boolean mLoadingCurrentConfigsDone = VDBG;
    private int mPrefPrimarySlot = -1;
    private int mPriorityCount = 0;

    static class PriorityConfig {
        String cardType;
        String mccmnc;
        int network1 = -1;
        int network2 = -1;
        Pattern pattern;
        int priority = -1;

        PriorityConfig() {
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("PriorityConfig: [priority = ");
            sb.append(this.priority);
            sb.append(", pattern = ");
            sb.append(this.pattern);
            sb.append(", cardType = ");
            sb.append(this.cardType);
            sb.append(", mccmnc = ");
            sb.append(this.mccmnc);
            sb.append(", network1 = ");
            sb.append(this.network1);
            sb.append(", network2 = ");
            sb.append(this.network2);
            sb.append("]");
            return sb.toString();
        }
    }

    QtiPrimaryCardPriorityHandler(Context context) {
        this.mContext = context;
        this.mCurrPriorityConfigs = new PriorityConfig[QtiPrimaryCardUtils.PHONE_COUNT];
        readPriorityConfigFromXml();
    }

    public int[] getNwModesFromConfig(int primarySlotId) {
        int i;
        int defaultNwMode = QtiPrimaryCardUtils.getDefaultNwMode();
        int[] prefNwModes = new int[QtiPrimaryCardUtils.PHONE_COUNT];
        for (int i2 = 0; i2 < QtiPrimaryCardUtils.PHONE_COUNT; i2++) {
            PriorityConfig[] priorityConfigArr = this.mCurrPriorityConfigs;
            if (priorityConfigArr[i2] != null) {
                if (i2 == primarySlotId) {
                    i = priorityConfigArr[i2].network1;
                } else {
                    i = priorityConfigArr[i2].network2;
                }
                prefNwModes[i2] = i;
            } else {
                prefNwModes[i2] = defaultNwMode;
            }
        }
        if (getNumSlotsWithCdma(prefNwModes) > 1) {
            logd("getNwModesFromConfig: More than one slot has CDMA nwMode set non-primary card nwModes to default nwMode");
            for (int i3 = 0; i3 < QtiPrimaryCardUtils.PHONE_COUNT; i3++) {
                StringBuilder sb = new StringBuilder();
                sb.append("getNwModesFromConfig: nwMode from config on slot [");
                sb.append(i3);
                sb.append("] is:");
                sb.append(prefNwModes[i3]);
                logi(sb.toString());
                if (i3 != primarySlotId) {
                    prefNwModes[i3] = QtiPrimaryCardUtils.getDefaultNwMode();
                }
            }
        }
        return prefNwModes;
    }

    private int getNumSlotsWithCdma(int[] prefNwModes) {
        int numSlotsWithCdma = 0;
        for (int i = 0; i < QtiPrimaryCardUtils.PHONE_COUNT; i++) {
            if (QtiPrimaryCardUtils.is3gpp2NwMode(prefNwModes[i])) {
                numSlotsWithCdma++;
            }
        }
        return numSlotsWithCdma;
    }

    public boolean isConfigLoadDone() {
        return this.mLoadingCurrentConfigsDone;
    }

    public void loadCurrentPriorityConfigs(boolean override) {
        if (!this.mLoadingConfigCompleted) {
            logd("getPrefPrimarySlot: All Config Loading not done. EXIT!!!");
            return;
        }
        if (override || !this.mLoadingCurrentConfigsDone) {
            for (int i = 0; i < QtiPrimaryCardUtils.PHONE_COUNT; i++) {
                this.mCurrPriorityConfigs[i] = getPriorityConfig(i);
            }
            this.mLoadingCurrentConfigsDone = DBG;
        }
    }

    public int getPrefPrimarySlot() {
        this.mPrefPrimarySlot = -1;
        logd("getPrefPrimarySlot:  Start!!!");
        if (!this.mLoadingCurrentConfigsDone) {
            logd("getPrefPrimarySlot: Current Config Loading not done. EXIT!!!");
            return this.mPrefPrimarySlot;
        }
        if (areConfigPrioritiesEqual()) {
            this.mPrefPrimarySlot = -2;
        } else {
            this.mPrefPrimarySlot = getMaxPrioritySlot();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("getPrefPrimarySlot: return mPrefPrimarySlot: ");
        sb.append(this.mPrefPrimarySlot);
        logd(sb.toString());
        return this.mPrefPrimarySlot;
    }

    private int getMaxPrioritySlot() {
        int slotId = -1;
        int tempMaxPriority = -1;
        for (int i = 0; i < QtiPrimaryCardUtils.PHONE_COUNT; i++) {
            PriorityConfig[] priorityConfigArr = this.mCurrPriorityConfigs;
            if (priorityConfigArr[i] != null && tempMaxPriority < priorityConfigArr[i].priority) {
                slotId = i;
                tempMaxPriority = this.mCurrPriorityConfigs[i].priority;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("maxPriority: ");
        sb.append(tempMaxPriority);
        sb.append(", maxPrioritySlot:");
        sb.append(slotId);
        logd(sb.toString());
        return slotId;
    }

    private boolean areConfigPrioritiesEqual() {
        for (int i = 0; i < QtiPrimaryCardUtils.PHONE_COUNT; i++) {
            PriorityConfig[] priorityConfigArr = this.mCurrPriorityConfigs;
            if (priorityConfigArr[i] == null || priorityConfigArr[i].priority != this.mCurrPriorityConfigs[0].priority) {
                return VDBG;
            }
        }
        return DBG;
    }

    private PriorityConfig getPriorityConfig(int slotId) {
        int priorityConfigComparator = QtiPrimaryCardUtils.getPriorityConfigComparator();
        CardInfo cardInfo = QtiCardInfoManager.getInstance().getCardInfo(slotId);
        String str = "getPriorityConfig: for slot:";
        if (cardInfo.getIccId() == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(slotId);
            sb.append(": iccid is null, EXIT!!!");
            logd(sb.toString());
            return null;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(slotId);
        sb2.append(" mcc-mnc ");
        sb2.append(cardInfo.getMccMnc());
        sb2.append(", Start!!!");
        logd(sb2.toString());
        int i = 0;
        while (i < this.mPriorityCount) {
            try {
                PriorityConfig pConfig = (PriorityConfig) this.mAllPriorityConfigs.get(Integer.valueOf(i));
                String str2 = ", ";
                String str3 = "getPriorityConfig: Found for slot:";
                if (priorityConfigComparator != 1) {
                    if (priorityConfigComparator != 2) {
                        if (priorityConfigComparator != 3) {
                            continue;
                        } else if (cardInfo.getMccMnc() != null && pConfig.pattern.matcher(cardInfo.getMccMnc()).find() && cardInfo.isCardTypeSame(pConfig.cardType)) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(str3);
                            sb3.append(slotId);
                            sb3.append(str2);
                            sb3.append(pConfig);
                            loge(sb3.toString());
                            return pConfig;
                        }
                    } else if (cardInfo.isCardTypeSame(pConfig.cardType)) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(str3);
                        sb4.append(slotId);
                        sb4.append(str2);
                        sb4.append(pConfig);
                        logd(sb4.toString());
                        return pConfig;
                    }
                } else if (pConfig.pattern.matcher(cardInfo.getIccId()).find() && cardInfo.isCardTypeSame(pConfig.cardType)) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(str3);
                    sb5.append(slotId);
                    sb5.append(str2);
                    sb5.append(pConfig);
                    logd(sb5.toString());
                    return pConfig;
                }
                i++;
            } catch (Exception e) {
                StringBuilder sb6 = new StringBuilder();
                sb6.append("getPriorityConfig:Exception:[");
                sb6.append(slotId);
                sb6.append("] ");
                sb6.append(e.getMessage());
                Rlog.e(LOG_TAG, sb6.toString(), e);
            }
        }
        return null;
    }

    public void reloadPriorityConfig() {
        QtiPrimaryCardUtils.setConfigValue();
        readPriorityConfigFromXml();
    }

    private void readPriorityConfigFromXml() {
        String str = "mAllPriorityConfigs: ";
        XmlResourceParser parser = null;
        try {
            Resources res = this.mContext.getPackageManager().getResourcesForApplication(packageName);
            if (res == null) {
                loge("res is null");
            }
            parser = res.getXml(res.getIdentifier(QtiPrimaryCardUtils.getConfigXml(), "xml", packageName));
            this.mAllPriorityConfigs.clear();
            this.mPriorityCount = 0;
            XmlUtils.beginDocument(parser, "priority_config");
            XmlUtils.nextElement(parser);
            while (parser.getEventType() != 1) {
                savePriorityConfig(parser);
                XmlUtils.nextElement(parser);
            }
            this.mLoadingConfigCompleted = DBG;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(this.mAllPriorityConfigs);
            logi(sb.toString());
        } catch (Exception e) {
            String str2 = LOG_TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Exception while reading priority configs: ");
            sb2.append(e.getMessage());
            Rlog.e(str2, sb2.toString(), e);
            this.mLoadingConfigCompleted = VDBG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(this.mAllPriorityConfigs);
            logi(sb3.toString());
            if (parser == null) {
                return;
            }
        } catch (Throwable th) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str);
            sb4.append(this.mAllPriorityConfigs);
            logi(sb4.toString());
            if (parser != null) {
                parser.close();
            }
            throw th;
        }
        parser.close();
    }

    private void savePriorityConfig(XmlResourceParser parser) throws Exception {
        PriorityConfig pConfig = new PriorityConfig();
        pConfig.priority = Integer.parseInt(parser.getAttributeValue(null, "priority"));
        pConfig.pattern = getPattern(parser);
        pConfig.cardType = parser.getAttributeValue(null, "card_type");
        pConfig.mccmnc = parser.getAttributeValue(null, "mccmnc");
        pConfig.network1 = Integer.parseInt(parser.getAttributeValue(null, "network1"));
        pConfig.network2 = Integer.parseInt(parser.getAttributeValue(null, "network2"));
        this.mAllPriorityConfigs.put(Integer.valueOf(this.mPriorityCount), pConfig);
        StringBuilder sb = new StringBuilder();
        sb.append("Added to mAllPriorityConfigs[");
        sb.append(this.mPriorityCount);
        sb.append("], ");
        sb.append(pConfig);
        logd(sb.toString());
        this.mPriorityCount++;
    }

    private Pattern getPattern(XmlResourceParser parser) throws Exception {
        String regEx = parser.getAttributeValue(null, "iin_pattern");
        if (regEx != null) {
            return Pattern.compile(regEx);
        }
        return null;
    }

    private void logd(String string) {
        Rlog.d(LOG_TAG, string);
    }

    private void logi(String string) {
        Rlog.i(LOG_TAG, string);
    }

    private void loge(String string) {
        Rlog.e(LOG_TAG, string);
    }
}
