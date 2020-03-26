package com.qualcomm.qti.internal.telephony.primarycard;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.telephony.Rlog;
import com.android.internal.util.XmlUtils;
import com.qualcomm.qti.internal.telephony.primarycard.QtiCardInfoManager;
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
            return "PriorityConfig: [priority = " + this.priority + ", pattern = " + this.pattern + ", cardType = " + this.cardType + ", mccmnc = " + this.mccmnc + ", network1 = " + this.network1 + ", network2 = " + this.network2 + "]";
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
                logi("getNwModesFromConfig: nwMode from config on slot [" + i3 + "] is:" + prefNwModes[i3]);
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
        } else if (override || !this.mLoadingCurrentConfigsDone) {
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
        logd("getPrefPrimarySlot: return mPrefPrimarySlot: " + this.mPrefPrimarySlot);
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
        logd("maxPriority: " + tempMaxPriority + ", maxPrioritySlot:" + slotId);
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
        QtiCardInfoManager.CardInfo cardInfo = QtiCardInfoManager.getInstance().getCardInfo(slotId);
        if (cardInfo.getIccId() == null) {
            logd("getPriorityConfig: for slot:" + slotId + ": iccid is null, EXIT!!!");
            return null;
        }
        logd("getPriorityConfig: for slot:" + slotId + " mcc-mnc " + cardInfo.getMccMnc() + ", Start!!!");
        int i = 0;
        while (i < this.mPriorityCount) {
            try {
                PriorityConfig pConfig = this.mAllPriorityConfigs.get(Integer.valueOf(i));
                if (priorityConfigComparator != 1) {
                    if (priorityConfigComparator != 2) {
                        if (priorityConfigComparator != 3) {
                            continue;
                        } else if (cardInfo.getMccMnc() != null && pConfig.pattern.matcher(cardInfo.getMccMnc()).find() && cardInfo.isCardTypeSame(pConfig.cardType)) {
                            loge("getPriorityConfig: Found for slot:" + slotId + ", " + pConfig);
                            return pConfig;
                        }
                    } else if (cardInfo.isCardTypeSame(pConfig.cardType)) {
                        logd("getPriorityConfig: Found for slot:" + slotId + ", " + pConfig);
                        return pConfig;
                    }
                } else if (pConfig.pattern.matcher(cardInfo.getIccId()).find() && cardInfo.isCardTypeSame(pConfig.cardType)) {
                    logd("getPriorityConfig: Found for slot:" + slotId + ", " + pConfig);
                    return pConfig;
                }
                i++;
            } catch (Exception e) {
                Rlog.e(LOG_TAG, "getPriorityConfig:Exception:[" + slotId + "] " + e.getMessage(), e);
            }
        }
        return null;
    }

    public void reloadPriorityConfig() {
        QtiPrimaryCardUtils.setConfigValue();
        readPriorityConfigFromXml();
    }

    private void readPriorityConfigFromXml() {
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
            logi("mAllPriorityConfigs: " + this.mAllPriorityConfigs);
        } catch (Exception e) {
            Rlog.e(LOG_TAG, "Exception while reading priority configs: " + e.getMessage(), e);
            this.mLoadingConfigCompleted = VDBG;
            logi("mAllPriorityConfigs: " + this.mAllPriorityConfigs);
            if (parser == null) {
                return;
            }
        } catch (Throwable th) {
            logi("mAllPriorityConfigs: " + this.mAllPriorityConfigs);
            if (parser != null) {
                parser.close();
            }
            throw th;
        }
        parser.close();
    }

    private void savePriorityConfig(XmlResourceParser parser) throws Exception {
        PriorityConfig pConfig = new PriorityConfig();
        pConfig.priority = Integer.parseInt(parser.getAttributeValue((String) null, "priority"));
        pConfig.pattern = getPattern(parser);
        pConfig.cardType = parser.getAttributeValue((String) null, "card_type");
        pConfig.mccmnc = parser.getAttributeValue((String) null, "mccmnc");
        pConfig.network1 = Integer.parseInt(parser.getAttributeValue((String) null, "network1"));
        pConfig.network2 = Integer.parseInt(parser.getAttributeValue((String) null, "network2"));
        this.mAllPriorityConfigs.put(Integer.valueOf(this.mPriorityCount), pConfig);
        logd("Added to mAllPriorityConfigs[" + this.mPriorityCount + "], " + pConfig);
        this.mPriorityCount = this.mPriorityCount + 1;
    }

    private Pattern getPattern(XmlResourceParser parser) throws Exception {
        String regEx = parser.getAttributeValue((String) null, "iin_pattern");
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
