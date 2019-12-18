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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.emzi0767.r6stratroulette.models.OperatorData;
import com.emzi0767.r6stratroulette.models.OperatorsData;
import com.emzi0767.r6stratroulette.models.RouletteData;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamRandomizerFragment extends Fragment {
    private RouletteData rouletteData = null;
    private File assetLocation = null;
    private ArrayList<OperatorData> atks = null, defs = null;

    private OperatorData[] opsA = null, opsD = null;

    private ImageView[] imgAttackers = null, imgDefenders = null;
    private TextView[] nameAttackers = null, nameDefenders = null;

    private MainActivity mainActivity = null;

    private final Random rng = new Random();

    public TeamRandomizerFragment() {
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
        View v = inflater.inflate(R.layout.fragment_team_randomizer, container, false);

        this.imgAttackers = new ImageView[5];
        this.imgDefenders = new ImageView[5];
        this.nameAttackers = new TextView[5];
        this.nameDefenders = new TextView[5];

        for (int i = 0; i < 5; i++) {
            this.imgAttackers[i] = v.findViewById(this.getResourceId(String.format(Locale.US, "randomteam_attacker%d_img", i), R.id.class));
            this.nameAttackers[i] = v.findViewById(this.getResourceId(String.format(Locale.US, "randomteam_attacker%d_name", i), R.id.class));
            this.imgDefenders[i] = v.findViewById(this.getResourceId(String.format(Locale.US, "randomteam_defender%d_img", i), R.id.class));
            this.nameDefenders[i] = v.findViewById(this.getResourceId(String.format(Locale.US, "randomteam_defender%d_name", i), R.id.class));
        }

        Button btn = v.findViewById(R.id.randomteam_randomize);
        btn.setOnClickListener(x -> {
            OperatorsData ops = this.rouletteData.getOperators();

            OperatorData[] atk = new OperatorData[5];
            OperatorData[] def = new OperatorData[5];

            this.randomizeOperators(atk, def);
            this.setOperators(atk, def);
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (this.opsA != null && this.opsD != null) {
            String[] atks = new String[5];
            String[] defs = new String[5];

            for (int i = 0; i < 5; i++) {
                atks[i] = opsA[i].getName();
                defs[i] = opsD[i].getName();
            }

            outState.putStringArray("ATTACKERS", atks);
            outState.putStringArray("DEFENDERS", defs);
        }

        super.onSaveInstanceState(outState);
    }

    private void setOperators(OperatorData[] atk, OperatorData[] def) {
        this.opsA = atk;
        this.opsD = def;

        for (int i = 0; i < 5; i++) {
            OperatorData atkT = atk[i];
            OperatorData defT = def[i];

            this.nameAttackers[i].setText(atkT.getName());
            this.nameDefenders[i].setText(defT.getName());

            File imgAtk = new File(this.assetLocation, atkT.getIcon());
            File imgDef = new File(this.assetLocation, defT.getIcon());

            Bitmap bmpAtk = BitmapFactory.decodeFile(imgAtk.getAbsolutePath());
            Bitmap bmpDef = BitmapFactory.decodeFile(imgDef.getAbsolutePath());

            this.imgAttackers[i].setImageBitmap(bmpAtk);
            this.imgDefenders[i].setImageBitmap(bmpDef);
        }
    }

    private void randomizeOperators(OperatorData[] atk, OperatorData[] def) {
        OperatorsData ops = this.rouletteData.getOperators();
        ArrayList<OperatorData> atks = new ArrayList<>(this.atks);
        ArrayList<OperatorData> defs = new ArrayList<>(this.defs);

        for (int i = 0; i < 5; i++) {
            int ra = this.rng.nextInt(atks.size());
            int rd = this.rng.nextInt(defs.size());

            atk[i] = atks.get(ra);
            def[i] = defs.get(rd);

            atks.remove(ra);
            defs.remove(rd);
        }
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

        OperatorData[] atk = null;
        OperatorData[] def = null;

        if (savedInstanceState != null && savedInstanceState.containsKey("ATTACKERS") && savedInstanceState.containsKey("DEFENDERS")) {
            List<String> atkN = Arrays.asList(savedInstanceState.getStringArray("ATTACKERS"));
            List<String> defN = Arrays.asList(savedInstanceState.getStringArray("DEFENDERS"));

            atk = new OperatorData[5];
            def = new OperatorData[5];

            for (int i = 0; i < this.atks.size(); i++) {
                OperatorData opT = this.atks.get(i);
                int j = atkN.indexOf(opT.getName());
                if (j != -1)
                    atk[j] = opT;
            }
            for (int i = 0; i < this.defs.size(); i++) {
                OperatorData opT = this.defs.get(i);
                int j = defN.indexOf(opT.getName());
                if (j != -1)
                    def[j] = opT;
            }

            for (int i = 0; i < 5; i++) {
                if (atk[i] == null || def[i] == null) {
                    atk = null;
                    def = null;
                    break;
                }
            }
        }

        if (atk == null || def == null) {
            atk = new OperatorData[5];
            def = new OperatorData[5];
            this.randomizeOperators(atk, def);
        }

        this.setOperators(atk, def);
    }

    private int getResourceId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception ex) {
            return -1;
        }
    }
}
