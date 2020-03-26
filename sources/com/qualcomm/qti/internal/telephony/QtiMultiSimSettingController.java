package com.qualcomm.qti.internal.telephony;

import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.util.Log;
import com.android.internal.telephony.GlobalSettingsHelper;
import com.android.internal.telephony.MultiSimSettingController;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.SubscriptionController;
import java.util.List;

public class QtiMultiSimSettingController extends MultiSimSettingController {
    private static final String LOG_TAG = "QtiMultiSimSettingController";

    public static MultiSimSettingController init(Context context, SubscriptionController sc) {
        synchronized (QtiMultiSimSettingController.class) {
            if (sInstance == null) {
                sInstance = new QtiMultiSimSettingController(context, SubscriptionController.getInstance());
            } else {
                Log.wtf(LOG_TAG, "init() called multiple times!  sInstance = " + sInstance);
            }
        }
        return sInstance;
    }

    private QtiMultiSimSettingController(Context context, SubscriptionController sc) {
        super(context, sc);
    }

    public static QtiMultiSimSettingController getInstance() {
        return sInstance;
    }

    /* access modifiers changed from: protected */
    public void disableDataForNonDefaultNonOpportunisticSubscriptions() {
        log("disableDataForNonDefaultNonOpportunisticSubscriptions - do nothing");
    }

    /* access modifiers changed from: protected */
    public synchronized void onUserDataEnabled(int subId, boolean enable) {
        log("onUserDataEnabled");
        setUserDataEnabledForGroup(subId, enable);
    }

    /* access modifiers changed from: protected */
    public synchronized void setUserDataEnabledForGroup(int subId, boolean enable) {
        log("setUserDataEnabledForGroup subId " + subId + " enable " + enable);
        List<SubscriptionInfo> infoList = this.mSubController.getSubscriptionsInGroup(this.mSubController.getGroupUuid(subId), this.mContext.getOpPackageName());
        if (infoList != null) {
            for (SubscriptionInfo info : infoList) {
                int currentSubId = info.getSubscriptionId();
                if (currentSubId != subId) {
                    if (this.mSubController.isActiveSubId(currentSubId)) {
                        Phone phone = PhoneFactory.getPhone(this.mSubController.getPhoneId(currentSubId));
                        if (phone != null) {
                            phone.getDataEnabledSettings().setUserDataEnabled(enable);
                        }
                    } else {
                        GlobalSettingsHelper.setBoolean(this.mContext, "mobile_data", currentSubId, enable);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void updateDefaults(boolean init) {
        log("updateDefaults " + init);
    }

    /* access modifiers changed from: protected */
    public void log(String msg) {
        Log.d(LOG_TAG, msg);
    }
}
