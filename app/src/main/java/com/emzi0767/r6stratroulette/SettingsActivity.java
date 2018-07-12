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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.*;
import com.emzi0767.r6stratroulette.data.OperatorSelection;
import com.emzi0767.r6stratroulette.models.OperatorData;
import com.emzi0767.r6stratroulette.models.RouletteData;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class SettingsActivity extends AppCompatActivity {

    private RouletteData rouletteData = null;
    private File assetLocation = null;
    private HashSet<String> disabledOps;
    private ArrayList<OperatorSelection> opSelections;
    private OperatorSelection.Adapter opSelAdapter;
    private boolean settingsSaved = false;

    private final ThreadPoolExecutor threadPool = (ThreadPoolExecutor)Executors.newFixedThreadPool(2);

    public static final String DISABLED_OPS_KEY = "disabled-ops";

    public SettingsActivity() {
        this.disabledOps = new HashSet<>();
        this.opSelections = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_settings);

        // get data
        Bundle b = this.getIntent().getExtras();
        this.rouletteData = b.getParcelable("rouletteData");
        this.assetLocation = new File(b.getString("assetLocation"));

        // get disabled list
        SharedPreferences prefs = this.getSharedPreferences(this.getString(R.string.settings_filename), Context.MODE_PRIVATE);
        Set<String> dops = prefs.getStringSet(DISABLED_OPS_KEY, null);
        if (dops != null)
            this.disabledOps.addAll(dops);

        // enable action bar
        Toolbar toolbar = this.findViewById(R.id.toolbar_settings);
        this.setSupportActionBar(toolbar);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        // populate list view
        ListView lv = this.findViewById(R.id.settings_ops);
        this.opSelAdapter = new OperatorSelection.Adapter(this, this.opSelections);
        lv.setAdapter(this.opSelAdapter);
        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            CheckBox cb = view.findViewById(R.id.settings_ops_enabled);
            cb.setChecked(!cb.isChecked());
        });
        this.threadPool.submit(this::loadOperatorSettings);
    }

    @Override
    public void onBackPressed() {
        this.saveSettings();
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.saveSettings();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        this.saveSettings();
        super.onDestroy();
    }

    private void loadOperatorSettings() {
        RouletteData rd = this.rouletteData;
        File f = this.assetLocation;

        for (OperatorData opd : rd.getOperators().getAttackers())
            this.prepareOperator(opd, f, true);
        for (OperatorData opd : rd.getOperators().getDefenders())
            this.prepareOperator(opd, f, false);

        this.opSelAdapter.notifyDataSetChanged();
    }

    private void prepareOperator(OperatorData opd, File f, boolean side) {
        File icon = new File(f, opd.getIcon());
        OperatorSelection opSel = new OperatorSelection();
        opSel.setName(opd.getName());
        opSel.setIcon(icon.toURI().toString());
        opSel.setEnabled(!this.disabledOps.contains(opd.getName()));
        opSel.setSide(side);
        this.opSelections.add(opSel);

        opSel.setOnEnabledChangedListener((x, s) -> {
            if (!s)
                this.disabledOps.add(x.getName());
            else
                this.disabledOps.remove(x.getName());
        });
    }

    private void saveSettings() {
        if (this.settingsSaved)
            return;

        this.settingsSaved = true;
        int atkC = 0, defC = 0;

        for (OperatorSelection opSel : this.opSelections) {
            atkC += opSel.getSide() && opSel.isEnabled() ? 1 : 0;
            defC += !opSel.getSide() && opSel.isEnabled() ? 1 : 0;
        }

        if (atkC < 5 || defC < 5) {
            Toast.makeText(this, this.getString(R.string.settings_ops_need_at_least_one), Toast.LENGTH_LONG).show();
        } else {
            SharedPreferences prefs = this.getSharedPreferences(this.getString(R.string.settings_filename), Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putStringSet(DISABLED_OPS_KEY, this.disabledOps);
            edit.apply();
        }

        this.setResult(1);
    }
}
