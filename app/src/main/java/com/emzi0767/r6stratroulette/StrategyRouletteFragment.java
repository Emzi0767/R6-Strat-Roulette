package com.emzi0767.r6stratroulette;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.emzi0767.r6stratroulette.models.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;


/**
 * A simple {@link Fragment} subclass.
 */
public class StrategyRouletteFragment extends Fragment {
    private RouletteData rouletteData = null;
    private int strategyIndex = -1;
    private String side = null, mode = null;

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

        List<StrategyData> strats = this.rouletteData.getStrategies();
        int stratInd = -1;

        if (savedInstanceState != null && savedInstanceState.containsKey("STRATIND") && savedInstanceState.containsKey("STRATSIDE") && savedInstanceState.containsKey("STRATMODE")) {
            stratInd = savedInstanceState.getInt("STRATIND");
            this.side = savedInstanceState.getString("STRATSIDE");
            this.mode = savedInstanceState.getString("STRATMODE");

            switch (this.side) {
                case "atk":
                    this.comboSide.setSelection(1);
                    break;

                case "def":
                    this.comboSide.setSelection(2);
                    break;
            }

            switch (this.mode) {
                case "area":
                    this.comboMode.setSelection(1);
                    break;

                case "bomb":
                    this.comboMode.setSelection(2);
                    break;

                case "hostage":
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
            List<StrategyData> strats = this.rouletteData.getStrategies();
            ArrayList<StrategyData> stratsEligible = new ArrayList<>();
            for (StrategyData strat : strats)
                if (strat.getSides().contains(this.side) && strat.getGameModes().contains(this.mode))
                    stratsEligible.add(strat);

            int i = this.rng.nextInt(stratsEligible.size());
            StrategyData strat = stratsEligible.get(i);
            i = strats.indexOf(strat);
            this.setStrategy(i);
        });

        ArrayAdapter<CharSequence> adapterSide = ArrayAdapter.createFromResource(this.getContext(), R.array.strats_side_options, android.R.layout.simple_spinner_dropdown_item);
        adapterSide.setDropDownViewResource(R.layout.spinner_item);
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
                        StrategyRouletteFragment.this.side = "atk";
                        break;

                    case 2:
                        StrategyRouletteFragment.this.side = "def";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapterMode = ArrayAdapter.createFromResource(this.getContext(), R.array.strats_mode_options, android.R.layout.simple_spinner_dropdown_item);
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
                        StrategyRouletteFragment.this.mode = "area";
                        break;

                    case 2:
                        StrategyRouletteFragment.this.mode = "bomb";
                        break;

                    case 3:
                        StrategyRouletteFragment.this.mode = "hostage";
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
            outState.putString("STRATSIDE", this.side);
            outState.putString("STRATMODE", this.mode);
        }

        super.onSaveInstanceState(outState);
    }

    private void setStrategy(int index) {
        this.strategyIndex = index;

        List<StrategyData> strats = this.rouletteData.getStrategies();
        StrategyData strat = strats.get(index);

        this.stratName.setText(strat.getName());
        this.stratDesc.setText(strat.getDescription());
    }
}
