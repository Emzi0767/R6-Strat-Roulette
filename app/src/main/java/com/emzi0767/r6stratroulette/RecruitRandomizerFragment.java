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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.emzi0767.r6stratroulette.data.RandomizedRecruit;
import com.emzi0767.r6stratroulette.models.*;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecruitRandomizerFragment extends Fragment {
    private RouletteData rouletteData = null;
    private File assetLocation = null;

    private RandomizedRecruit atk = null, def = null;

    private ImageView imgCtuAtk = null, imgCtuDef = null;
    private TextView atkWeapon1 = null, atkWeapon2 = null, atkGadget1 = null, atkGadget2 = null,
            defWeapon1 = null, defWeapon2 = null, defGadget1 = null, defGadget2 = null,
            atkCtu = null, defCtu = null;

    private final Random rng = new Random();

    public RecruitRandomizerFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainActivity ac = (MainActivity)this.getActivity();
        this.rouletteData = ac.getRouletteData();
        this.assetLocation = ac.getAssetLocation();

        RandomizedRecruit atk = new RandomizedRecruit();
        RandomizedRecruit def = new RandomizedRecruit();

        if (savedInstanceState != null && savedInstanceState.containsKey("ATTACKER") && savedInstanceState.containsKey("DEFENDER")) {
            String[] atkP = savedInstanceState.getStringArray("ATTACKER");
            String[] defP = savedInstanceState.getStringArray("DEFENDER");

            atk.setCtu(atkP[0]);
            atk.setPrimaryWeapon(atkP[1]);
            atk.setSecondaryWeapon(atkP[2]);
            atk.setPrimaryGadget(atkP[3]);
            atk.setSecondaryGadget(atkP[4]);

            def.setCtu(defP[0]);
            def.setPrimaryWeapon(defP[1]);
            def.setSecondaryWeapon(defP[2]);
            def.setPrimaryGadget(defP[3]);
            def.setSecondaryGadget(defP[4]);
        } else {
            this.randomizeRecruits(atk, def);
        }

        this.setRecruits(atk, def);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recruit_randomizer, container, false);

        this.atkCtu = v.findViewById(R.id.randomrecruit_attacker_ctu);
        this.defCtu = v.findViewById(R.id.randomrecruit_defender_ctu);
        this.atkWeapon1 = v.findViewById(R.id.randomrecruit_atk_weapon1);
        this.atkWeapon2 = v.findViewById(R.id.randomrecruit_atk_weapon2);
        this.atkGadget1 = v.findViewById(R.id.randomrecruit_atk_gadget1);
        this.atkGadget2 = v.findViewById(R.id.randomrecruit_atk_gadget2);
        this.defWeapon1 = v.findViewById(R.id.randomrecruit_def_weapon1);
        this.defWeapon2 = v.findViewById(R.id.randomrecruit_def_weapon2);
        this.defGadget1 = v.findViewById(R.id.randomrecruit_def_gadget1);
        this.defGadget2 = v.findViewById(R.id.randomrecruit_def_gadget2);
        this.imgCtuAtk = v.findViewById(R.id.randomrecruit_attacker_img);
        this.imgCtuDef = v.findViewById(R.id.randomrecruit_defender_img);

        Button btn = v.findViewById(R.id.randomrecruit_randomize);
        btn.setOnClickListener(x -> {
            RandomizedRecruit atkT = new RandomizedRecruit(), defT = new RandomizedRecruit();
            this.randomizeRecruits(atkT, defT);
            this.setRecruits(atkT, defT);
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (this.atk != null && this.def != null) {
            outState.putStringArray("ATTACKER", new String[] { atk.getCtu(), atk.getPrimaryWeapon(), atk.getSecondaryWeapon(), atk.getPrimaryGadget(), atk.getSecondaryGadget() });
            outState.putStringArray("DEFENDER", new String[] { def.getCtu(), def.getPrimaryWeapon(), def.getSecondaryWeapon(), def.getPrimaryGadget(), def.getSecondaryGadget() });
        }

        super.onSaveInstanceState(outState);
    }

    private void setRecruits(RandomizedRecruit atk, RandomizedRecruit def) {
        this.atk = atk;
        this.def = def;

        Map<String, CtuData> ctus = this.rouletteData.getCtus();
        CtuData ctuAtk = ctus.get(atk.getCtu());
        CtuData ctuDef = ctus.get(def.getCtu());

        File imgAtk = new File(this.assetLocation, ctuAtk.getIcon());
        File imgDef = new File(this.assetLocation, ctuDef.getIcon());

        Bitmap imgAtkBmp = BitmapFactory.decodeFile(imgAtk.getAbsolutePath());
        Bitmap imgDefBmp = BitmapFactory.decodeFile(imgDef.getAbsolutePath());

        this.imgCtuAtk.setImageBitmap(imgAtkBmp);
        this.imgCtuDef.setImageBitmap(imgDefBmp);

        this.atkCtu.setText(ctuAtk.getAbbreviation());
        this.defCtu.setText(ctuDef.getAbbreviation());

        this.atkWeapon1.setText(atk.getPrimaryWeapon());
        this.atkWeapon2.setText(atk.getSecondaryWeapon());
        this.atkGadget1.setText(atk.getPrimaryGadget());
        this.atkGadget2.setText(atk.getSecondaryGadget());
        this.defWeapon1.setText(def.getPrimaryWeapon());
        this.defWeapon2.setText(def.getSecondaryWeapon());
        this.defGadget1.setText(def.getPrimaryGadget());
        this.defGadget2.setText(def.getSecondaryGadget());
    }

    private void randomizeRecruits(RandomizedRecruit atk, RandomizedRecruit def) {
        RecruitsData recruits = this.rouletteData.getRecruits();
        RecruitData[] atks = recruits.getAttackers().toArray(new RecruitData[0]);
        RecruitData[] defs = recruits.getDefenders().toArray(new RecruitData[0]);

        RecruitData atkR = atks[rng.nextInt(atks.length)];
        RecruitData defR = defs[rng.nextInt(defs.length)];

        atk.setCtu(atkR.getCtu());
        def.setCtu(defR.getCtu());

        List<String> tmp = atkR.getPrimaryWeapons();
        atk.setPrimaryWeapon(tmp.get(rng.nextInt(tmp.size())));
        tmp = atkR.getSecondaryWeapons();
        atk.setSecondaryWeapon(tmp.get(rng.nextInt(tmp.size())));
        tmp = atkR.getPrimaryGadgets();
        atk.setPrimaryGadget(tmp.get(rng.nextInt(tmp.size())));
        tmp = atkR.getSecondaryGadgets();
        atk.setSecondaryGadget(tmp.get(rng.nextInt(tmp.size())));

        tmp = defR.getPrimaryWeapons();
        def.setPrimaryWeapon(tmp.get(rng.nextInt(tmp.size())));
        tmp = defR.getSecondaryWeapons();
        def.setSecondaryWeapon(tmp.get(rng.nextInt(tmp.size())));
        tmp = defR.getPrimaryGadgets();
        def.setPrimaryGadget(tmp.get(rng.nextInt(tmp.size())));
        tmp = defR.getSecondaryGadgets();
        def.setSecondaryGadget(tmp.get(rng.nextInt(tmp.size())));
    }
}
