// This file is a part of R6: Strat Roulette project.
//
// Copyright 2018 Emzi0767
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.emzi0767.r6stratroulette;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.emzi0767.r6stratroulette.models.CtuData;
import com.emzi0767.r6stratroulette.models.OperatorData;
import com.emzi0767.r6stratroulette.models.OperatorsData;
import com.emzi0767.r6stratroulette.models.RouletteData;

import java.io.File;
import java.util.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class OperatorRandomizerFragment extends Fragment {
    private RouletteData rouletteData = null;
    private File assetLocation = null;
    private ArrayList<OperatorData> atks = null, defs = null;

    private Bitmap bmpA = null, bmpD = null;
    private OperatorData opA = null, opD = null;

    private ImageView imgAttacker = null, imgDefender = null;
    private TextView nameAttacker = null, nameDefender = null, ctuAttacker = null, ctuDefender = null;

    private MainActivity mainActivity = null;

    private final Random rng = new Random();

    public OperatorRandomizerFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.initOperators(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.mainActivity = (MainActivity)this.getActivity();
        this.rouletteData = this.mainActivity.getRouletteData();
        this.assetLocation = this.mainActivity.getAssetLocation();

        this.initOperators(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_operator_randomizer, container, false);

        this.imgAttacker = v.findViewById(R.id.randomop_attacker_img);
        this.imgDefender = v.findViewById(R.id.randomop_defender_img);
        this.nameAttacker = v.findViewById(R.id.randomop_attacker_name);
        this.nameDefender = v.findViewById(R.id.randomop_defender_name);
        this.ctuAttacker = v.findViewById(R.id.randomop_attacker_ctu);
        this.ctuDefender = v.findViewById(R.id.randomop_defender_ctu);

        Button btn = v.findViewById(R.id.randomop_randomize);
        btn.setOnClickListener(b -> {
            int ra = this.rng.nextInt(this.atks.size());
            int rd = this.rng.nextInt(this.defs.size());

            OperatorData atk = this.atks.get(ra);
            OperatorData def = this.defs.get(rd);

            this.setOperators(atk, def);
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (this.opA != null && this.opD != null) {
            outState.putString("ATTACKER", opA.getName());
            outState.putString("DEFENDER", opD.getName());
        }

        super.onSaveInstanceState(outState);
    }

    private void setOperators(OperatorData atk, OperatorData def) {
        this.opA = atk;
        this.opD = def;

        Map<String, CtuData> ctus = this.rouletteData.getCtus();

        CtuData ctuAtk = ctus.get(atk.getCtu());
        CtuData ctuDef = ctus.get(def.getCtu());

        nameAttacker.setText(atk.getName());
        nameDefender.setText(def.getName());

        ctuAttacker.setText(String.format("%s (%s)", ctuAtk.getName(), ctuAtk.getAbbreviation()));
        ctuDefender.setText(String.format("%s (%s)", ctuDef.getName(), ctuDef.getAbbreviation()));

        File imgAtk = new File(this.assetLocation, atk.getIcon());
        File imgDef = new File(this.assetLocation, def.getIcon());

        this.bmpA = BitmapFactory.decodeFile(imgAtk.getAbsolutePath());
        this.bmpD = BitmapFactory.decodeFile(imgDef.getAbsolutePath());

        imgAttacker.setImageBitmap(bmpA);
        imgDefender.setImageBitmap(bmpD);
    }

    private void initOperators(Bundle savedInstanceState) {
        if (this.mainActivity == null)
            return;

        SharedPreferences prefs = this.mainActivity.getSharedPreferences(this.getString(R.string.settings_filename), Context.MODE_PRIVATE);
        Set<String> disabledOps = prefs.getStringSet(SettingsActivity.DISABLED_OPS_KEY, new HashSet<>());

        OperatorsData ops = this.rouletteData.getOperators();
        List<OperatorData> xatks = ops.getAttackers();
        List<OperatorData> xdefs = ops.getDefenders();
        this.atks = new ArrayList<>();
        this.defs = new ArrayList<>();

        for (OperatorData op : xatks)
            if (!disabledOps.contains(op.getName()))
                this.atks.add(op);
        for (OperatorData op : xdefs)
            if (!disabledOps.contains(op.getName()))
                this.defs.add(op);

        OperatorData atk = null;
        OperatorData def = null;

        if (savedInstanceState != null && savedInstanceState.containsKey("ATTACKER") && savedInstanceState.containsKey("DEFENDER")) {
            String atkN = savedInstanceState.getString("ATTACKER");
            String defN = savedInstanceState.getString("DEFENDER");

            for (OperatorData op : this.atks)
                if (op.getName().equals(atkN)) {
                    atk = op;
                    break;
                }
            for (OperatorData op : this.defs)
                if (op.getName().equals(defN)) {
                    def = op;
                    break;
                }
        }

        if (atk == null || def == null) {
            int ra = this.rng.nextInt(this.atks.size());
            int rd = this.rng.nextInt(this.defs.size());

            atk = this.atks.get(ra);
            def = this.defs.get(rd);
        }

        this.setOperators(atk, def);
    }
}
