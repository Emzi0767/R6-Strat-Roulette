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

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.emzi0767.r6stratroulette.models.*;
import com.emzi0767.r6stratroulette.models.runtime.RouletteRuntimeData;
import com.emzi0767.r6stratroulette.models.runtime.RouletteRuntimeStrategy;
import com.emzi0767.r6stratroulette.models.runtime.RouletteRuntimeStrategyGameMode;
import com.emzi0767.r6stratroulette.models.runtime.RouletteRuntimeStrategySide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class StrategyRouletteFragment extends Fragment {
    private RouletteRuntimeData rouletteData = null;
    private int strategyIndex = -1;
    private RouletteRuntimeStrategySide side = null;
    private RouletteRuntimeStrategyGameMode mode = null;

    private Spinner comboSide = null, comboMode = null;
    private TextView stratName = null, stratDesc = null;

    private final Random rng = new Random();

    public StrategyRouletteFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainActivity ac = (MainActivity)this.getActivity();
        this.rouletteData = ac.getRouletteData();

        List<RouletteRuntimeStrategy> strats = this.rouletteData.getStrategies();
        int stratInd = -1;

        if (savedInstanceState != null && savedInstanceState.containsKey("STRATIND") && savedInstanceState.containsKey("STRATSIDE") && savedInstanceState.containsKey("STRATMODE")) {
            stratInd = savedInstanceState.getInt("STRATIND");
            this.side = RouletteRuntimeStrategySide.fromValueSingle(savedInstanceState.getInt("STRATSIDE"));
            this.mode = RouletteRuntimeStrategyGameMode.fromValueSingle(savedInstanceState.getInt("STRATMODE"));

            switch (this.side) {
                case ATTACKERS:
                    this.comboSide.setSelection(1);
                    break;

                case DEFENDERS:
                    this.comboSide.setSelection(2);
                    break;
            }

            switch (this.mode) {
                case SECURE_AREA:
                    this.comboMode.setSelection(1);
                    break;

                case BOMB:
                    this.comboMode.setSelection(2);
                    break;

                case HOSTAGE:
                    this.comboMode.setSelection(3);
                    break;
            }
        }

        if (stratInd != -1)
            this.setStrategy(stratInd);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_strategy_roulette, container, false);

        this.comboSide = v.findViewById(R.id.strats_side);
        this.comboMode = v.findViewById(R.id.strats_mode);
        this.stratName = v.findViewById(R.id.stratroulette_name);
        this.stratDesc = v.findViewById(R.id.stratroulette_desc);

        Button btn = v.findViewById(R.id.stratroulette_randomize);
        btn.setEnabled(false);
        btn.setOnClickListener(x -> {
            List<RouletteRuntimeStrategy> strats = this.rouletteData.getStrategies();
            ArrayList<RouletteRuntimeStrategy> stratsEligible = new ArrayList<>();
            for (RouletteRuntimeStrategy strat : strats)
                if (strat.getSides().contains(this.side) && strat.getGameModes().contains(this.mode))
                    stratsEligible.add(strat);

            int i = this.rng.nextInt(stratsEligible.size());
            RouletteRuntimeStrategy strat = stratsEligible.get(i);
            i = strats.indexOf(strat);
            this.setStrategy(i);
        });

        ArrayAdapter<String> adapterSide = new ArrayAdapter<String>(this.getContext(), R.layout.spinner_item, this.getResources().getStringArray(R.array.strats_side_options));
        this.comboSide.setAdapter(adapterSide);
        this.comboSide.setSelection(0);
        this.comboSide.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (StrategyRouletteFragment.this.comboSide.getSelectedItemPosition() != 0 && StrategyRouletteFragment.this.comboMode.getSelectedItemPosition() != 0)
                    btn.setEnabled(true);
                else
                    btn.setEnabled(false);

                int item = StrategyRouletteFragment.this.comboSide.getSelectedItemPosition();
                switch (item) {
                    case 0:
                        StrategyRouletteFragment.this.side = null;
                        break;

                    case 1:
                        StrategyRouletteFragment.this.side = RouletteRuntimeStrategySide.ATTACKERS;
                        break;

                    case 2:
                        StrategyRouletteFragment.this.side = RouletteRuntimeStrategySide.DEFENDERS;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapterMode = new ArrayAdapter<String>(this.getContext(), R.layout.spinner_item, this.getResources().getStringArray(R.array.strats_mode_options));
        adapterMode.setDropDownViewResource(R.layout.spinner_item);
        this.comboMode.setAdapter(adapterMode);
        this.comboMode.setSelection(0);
        this.comboMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (StrategyRouletteFragment.this.comboSide.getSelectedItemPosition() != 0 && StrategyRouletteFragment.this.comboMode.getSelectedItemPosition() != 0)
                    btn.setEnabled(true);
                else
                    btn.setEnabled(false);

                int item = StrategyRouletteFragment.this.comboMode.getSelectedItemPosition();
                switch (item) {
                    case 0:
                        StrategyRouletteFragment.this.mode = null;
                        break;

                    case 1:
                        StrategyRouletteFragment.this.mode = RouletteRuntimeStrategyGameMode.SECURE_AREA;
                        break;

                    case 2:
                        StrategyRouletteFragment.this.mode = RouletteRuntimeStrategyGameMode.BOMB;
                        break;

                    case 3:
                        StrategyRouletteFragment.this.mode = RouletteRuntimeStrategyGameMode.HOSTAGE;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.stratName.setText(R.string.strats_nostrat_1);
        this.stratDesc.setText(R.string.strats_nostrat_2);

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (this.strategyIndex != -1 && this.side != null && this.mode != null) {
            outState.putInt("STRATIND", this.strategyIndex);
            outState.putInt("STRATSIDE", this.side.getValue());
            outState.putInt("STRATMODE", this.mode.getValue());
        }

        super.onSaveInstanceState(outState);
    }

    private void setStrategy(int index) {
        this.strategyIndex = index;

        List<RouletteRuntimeStrategy> strats = this.rouletteData.getStrategies();
        RouletteRuntimeStrategy strat = strats.get(index);

        this.stratName.setText(strat.getName());
        this.stratDesc.setText(strat.getDescription());
    }
}
