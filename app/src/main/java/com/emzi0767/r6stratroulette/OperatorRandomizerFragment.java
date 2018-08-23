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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.emzi0767.r6stratroulette.data.RandomizedOperator;
import com.emzi0767.r6stratroulette.models.LoadoutData;
import com.emzi0767.r6stratroulette.models.OperatorData;
import com.emzi0767.r6stratroulette.models.OperatorsData;
import com.emzi0767.r6stratroulette.models.RouletteData;

import java.util.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class OperatorRandomizerFragment extends Fragment {
    private RouletteData rouletteData = null;
    private RandomizedOperator opA = null, opD = null;
    private MainActivity mainActivity = null;
    private OperatorFragmentPagerAdapter adapter = null;

    private ViewPager tabPager = null;
    private TabLayout tabs = null;
    private int lastTab = 0;

    private ArrayList<OperatorData> atks = null, defs = null;

    private final Random rng = new Random();

    public OperatorRandomizerFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.adapter = new OperatorFragmentPagerAdapter(this.getChildFragmentManager());
        this.initOperators(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.mainActivity = (MainActivity)this.getActivity();
        this.rouletteData = this.mainActivity.getRouletteData();

        this.initOperators(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_operator_randomizer, container, false);

        this.tabPager = v.findViewById(R.id.randomop_tabs_pager);
        this.tabs = v.findViewById(R.id.randomop_tabs);
        this.tabPager.setAdapter(this.adapter);
        //tabs.setupWithViewPager(this.tabPager);
        this.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                OperatorRandomizerFragment.this.lastTab = tab.getPosition();
                OperatorRandomizerFragment.this.tabPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                OperatorRandomizerFragment.this.lastTab = position;
                OperatorRandomizerFragment.this.tabs.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < this.tabs.getTabCount(); i++) {
            TabLayout.Tab t = this.tabs.getTabAt(i);
            assert t != null;

            t.setCustomView(R.layout.layout_tab);
            if (i == 0) {
                t.setText(R.string.randomop_lbl_attacker);
                t.setIcon(R.drawable.ic_sword);
            } else {
                t.setText(R.string.randomop_lbl_defender);
                t.setIcon(R.drawable.ic_shield);
            }
        }

        Button btn = v.findViewById(R.id.randomop_randomize);
        btn.setOnClickListener(b -> {
            Pair<RandomizedOperator, RandomizedOperator> rops = this.getRandomOperators(this.opA.getOperator(), this.opD.getOperator());
            this.setOperators(rops.first, rops.second);
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        FragmentTransaction ft = this.getChildFragmentManager().beginTransaction();
        for (Fragment f : this.getChildFragmentManager().getFragments())
            ft.remove(f);
        ft.commit();

        if (this.opA != null && this.opD != null) {
            Bundle atkData = new Bundle();
            atkData.putString("NAME", opA.getOperator().getName());
            atkData.putString("WEAPON1", opA.getPrimary());
            atkData.putString("WEAPON2", opA.getSecondary());
            atkData.putString("GADGET", opA.getGadget());

            Bundle defData = new Bundle();
            defData.putString("NAME", opD.getOperator().getName());
            defData.putString("WEAPON1", opD.getPrimary());
            defData.putString("WEAPON2", opD.getSecondary());
            defData.putString("GADGET", opD.getGadget());

            outState.putBundle("ATTACKER", atkData);
            outState.putBundle("DEFENDER", defData);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        this.tabPager.setAdapter(null);
        super.onPause();
    }

    @Override
    public void onResume() {
        this.tabPager.setAdapter(this.adapter);
        this.tabPager.setCurrentItem(this.lastTab);
        super.onResume();
    }

    private void setOperators(RandomizedOperator atk, RandomizedOperator def) {
        this.opA = atk;
        this.opD = def;

        for (Fragment f : this.getChildFragmentManager().getFragments()) {
            if (!(f instanceof OperatorFragment))
                continue;

            OperatorFragment of = (OperatorFragment)f;
            if (of.getType() == OperatorFragment.OperatorFragmentType.ATTACKER)
                of.setOperator(atk);
            else if (of.getType() == OperatorFragment.OperatorFragmentType.DEFENDER)
                of.setOperator(def);
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

        RandomizedOperator atk = null, def = null;

        if (savedInstanceState != null && savedInstanceState.containsKey("ATTACKER") && savedInstanceState.containsKey("DEFENDER")) {
            Bundle atkData = savedInstanceState.getBundle("ATTACKER");
            Bundle defData = savedInstanceState.getBundle("DEFENDER");

            assert atkData != null;
            assert defData != null;

            String atkName = atkData.getString("NAME");
            String defName = defData.getString("NAME");

            OperatorData xopA = null, xopD = null;

            for (OperatorData op : this.atks)
                if (op.getName().equals(atkName)) {
                    xopA = op;
                    break;
                }
            for (OperatorData op : this.defs)
                if (op.getName().equals(defName)) {
                    xopD = op;
                    break;
                }

            if (xopA != null && xopD != null) {
                String atkW1 = atkData.getString("WEAPON1"),
                        atkW2 = atkData.getString("WEAPON2"),
                        atkG = atkData.getString("GADGET"),
                        defW1 = defData.getString("WEAPON1"),
                        defW2 = defData.getString("WEAPON2"),
                        defG = defData.getString("GADGET");

                if (atkW1 != null && atkW2 != null && atkG != null && defW1 != null && defW2 != null && defG != null) {
                    atk = new RandomizedOperator(xopA, atkW1, atkW2, atkG);
                    def = new RandomizedOperator(xopD, defW1, defW2, defG);
                }
            }
        }

        if (atk == null || def == null) {
            Pair<RandomizedOperator, RandomizedOperator> rops = this.getRandomOperators(null, null);
            atk = rops.first;
            def = rops.second;
        }

        this.setOperators(atk, def);
    }

    private Pair<RandomizedOperator, RandomizedOperator> getRandomOperators(@Nullable OperatorData previousAtk, @Nullable OperatorData previousDef) {
        OperatorData opA = null, opD = null;

        while (opA == null || (previousAtk != null && opA.getName().equals(previousAtk.getName()))) {
            opA = Util.randomItem(this.atks, this.rng);
        }

        while (opD == null || (previousDef != null && opD.getName().equals(previousDef.getName()))) {
            opD = Util.randomItem(this.defs, this.rng);
        }

        LoadoutData loadoutA = opA.getLoadout(), loadoutD = opD.getLoadout();
        String aw1 = Util.randomItem(loadoutA.getPrimaryWeapons(), this.rng),
                aw2 = Util.randomItem(loadoutA.getSecondaryWeapons(), this.rng),
                ag = Util.randomItem(loadoutA.getGadgets(), this.rng),
                dw1 = Util.randomItem(loadoutD.getPrimaryWeapons(), this.rng),
                dw2 = Util.randomItem(loadoutD.getSecondaryWeapons(), this.rng),
                dg = Util.randomItem(loadoutD.getGadgets(), this.rng);

        return new Pair<>(new RandomizedOperator(opA, aw1, aw2, ag), new RandomizedOperator(opD, dw1, dw2, dg));
    }

    private class OperatorFragmentPagerAdapter extends FragmentStatePagerAdapter {
        OperatorFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            OperatorFragment opf = new OperatorFragment();

            switch (position) {
                case 0:
                    opf.setType(OperatorFragment.OperatorFragmentType.ATTACKER);
                    break;

                case 1:
                    opf.setType(OperatorFragment.OperatorFragmentType.DEFENDER);
                    break;
            }

            return opf;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return OperatorRandomizerFragment.this.getResources().getString(R.string.randomop_lbl_attacker);

                case 1:
                    return OperatorRandomizerFragment.this.getResources().getString(R.string.randomop_lbl_defender);
            }

            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    RandomizedOperator getAttacker() {
        return this.opA;
    }

    RandomizedOperator getDefender() {
        return this.opD;
    }
}
