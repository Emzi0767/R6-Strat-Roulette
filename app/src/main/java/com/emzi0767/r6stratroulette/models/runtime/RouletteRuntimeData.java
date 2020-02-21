package com.emzi0767.r6stratroulette.models.runtime;

import android.os.Parcel;
import android.os.Parcelable;
import com.emzi0767.r6stratroulette.models.StringLanguage;
import com.emzi0767.r6stratroulette.models.json.RouletteJsonCTU;
import com.emzi0767.r6stratroulette.models.json.RouletteJsonOperator;
import com.emzi0767.r6stratroulette.models.json.RouletteJsonRoot;
import com.emzi0767.r6stratroulette.models.json.RouletteJsonStrategy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouletteRuntimeData implements Parcelable {
    private RouletteRuntimeVersion version;
    private List<RouletteRuntimeOperator> operatorAttackers;
    private List<RouletteRuntimeOperator> operatorDefenders;
    private RouletteRuntimeRecruitLoadout recruitAttacker;
    private RouletteRuntimeRecruitLoadout recruitDefender;
    private List<RouletteRuntimeStrategy> strategies;

    public static final Creator<RouletteRuntimeData> CREATOR = new Creator<RouletteRuntimeData>() {
        @Override
        public RouletteRuntimeData createFromParcel(Parcel in) {
            return new RouletteRuntimeData(in);
        }

        @Override
        public RouletteRuntimeData[] newArray(int size) {
            return new RouletteRuntimeData[size];
        }
    };

    private RouletteRuntimeData(
            RouletteRuntimeVersion version,
            List<RouletteRuntimeOperator> operatorAttackers,
            List<RouletteRuntimeOperator> operatorDefenders,
            RouletteRuntimeRecruitLoadout recruitAttacker,
            RouletteRuntimeRecruitLoadout recruitDefender,
            List<RouletteRuntimeStrategy> strategies) {
        this.version = version;
        this.operatorAttackers = operatorAttackers;
        this.operatorDefenders = operatorDefenders;
        this.recruitAttacker = recruitAttacker;
        this.recruitDefender = recruitDefender;
        this.strategies = strategies;
    }

    protected RouletteRuntimeData(Parcel in) {
        this.version = in.readParcelable(RouletteRuntimeVersion.class.getClassLoader());
        this.operatorAttackers = in.createTypedArrayList(RouletteRuntimeOperator.CREATOR);
        this.operatorDefenders = in.createTypedArrayList(RouletteRuntimeOperator.CREATOR);
        this.recruitAttacker = in.readParcelable(RouletteRuntimeRecruitLoadout.class.getClassLoader());
        this.recruitDefender = in.readParcelable(RouletteRuntimeRecruitLoadout.class.getClassLoader());
        this.strategies = in.createTypedArrayList(RouletteRuntimeStrategy.CREATOR);
    }

    public RouletteRuntimeVersion getVersion() {
        return this.version;
    }

    public List<RouletteRuntimeOperator> getOperatorAttackers() {
        return this.operatorAttackers;
    }

    public List<RouletteRuntimeOperator> getOperatorDefenders() {
        return this.operatorDefenders;
    }

    public RouletteRuntimeRecruitLoadout getRecruitAttacker() {
        return this.recruitAttacker;
    }

    public RouletteRuntimeRecruitLoadout getRecruitDefender() {
        return this.recruitDefender;
    }

    public List<RouletteRuntimeStrategy> getStrategies() {
        return this.strategies;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.version, flags);
        dest.writeTypedList(this.operatorAttackers);
        dest.writeTypedList(this.operatorDefenders);
        dest.writeParcelable(this.recruitAttacker, flags);
        dest.writeParcelable(this.recruitDefender, flags);
        dest.writeTypedList(this.strategies);
    }

    public static RouletteRuntimeData fromJson(RouletteJsonRoot jsonRoot, StringLanguage lang) throws IllegalArgumentException {
        if (lang == StringLanguage.UNKNOWN)
            throw new IllegalArgumentException("Invalid language specified.");

        Map<String, RouletteJsonCTU> ctusRaw = jsonRoot.getCTUs();
        HashMap<String, RouletteRuntimeCTU> ctus = new HashMap<>();
        for (String ctuId : ctusRaw.keySet()) {
            RouletteJsonCTU ctu = ctusRaw.get(ctuId);
            ctus.put(ctuId, RouletteRuntimeCTU.fromJson(ctu, ctuId));
        }

        List<RouletteJsonOperator> opsAtkRaw = jsonRoot.getOperatorSets().getAttackers();
        RouletteRuntimeOperator[] opsAtk = new RouletteRuntimeOperator[opsAtkRaw.size()];
        for (int i = 0; i < opsAtk.length; i++)
            opsAtk[i] = RouletteRuntimeOperator.fromJson(opsAtkRaw.get(i), ctus, jsonRoot.getGadgets(), lang);

        List<RouletteJsonOperator> opsDefRaw = jsonRoot.getOperatorSets().getDefenders();
        RouletteRuntimeOperator[] opsDef = new RouletteRuntimeOperator[opsDefRaw.size()];
        for (int i = 0; i < opsDef.length; i++)
            opsDef[i] = RouletteRuntimeOperator.fromJson(opsDefRaw.get(i), ctus, jsonRoot.getGadgets(), lang);

        List<RouletteJsonStrategy> stratsRaw = jsonRoot.getStrategies();
        RouletteRuntimeStrategy[] strats = new RouletteRuntimeStrategy[stratsRaw.size()];
        for (int i = 0; i < strats.length; i++)
            strats[i] = RouletteRuntimeStrategy.fromJson(stratsRaw.get(i), lang);

        return new RouletteRuntimeData(
                RouletteRuntimeVersion.fromJson(jsonRoot.getVersion()),
                Arrays.asList(opsAtk),
                Arrays.asList(opsDef),
                RouletteRuntimeRecruitLoadout.fromJson(jsonRoot.getRecruits().getAttacker(), jsonRoot.getGadgets(), lang),
                RouletteRuntimeRecruitLoadout.fromJson(jsonRoot.getRecruits().getDefender(), jsonRoot.getGadgets(), lang),
                Arrays.asList(strats)
        );
    }
}
