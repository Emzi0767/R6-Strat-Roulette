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
import com.emzi0767.r6stratroulette.data.RandomizedOperator;
import com.emzi0767.r6stratroulette.models.*;

import java.io.File;
import java.util.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttackerRandomizerFragment extends Fragment {
    private RouletteData rouletteData = null;
    private File assetLocation = null;
    private ArrayList<OperatorData> ops = null;

    private RandomizedOperator op = null;

    private ImageView img = null;
    private TextView name = null, ctu = null, weapon1 = null, weapon2 = null, gadget = null;

    private MainActivity mainActivity = null;

    private final Random rng = new Random();

    public AttackerRandomizerFragment() {
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
        View v = inflater.inflate(R.layout.fragment_attacker_randomizer, container, false);

        this.img = v.findViewById(R.id.randomatk_img);
        this.name = v.findViewById(R.id.randomatk_name);
        this.ctu = v.findViewById(R.id.randomatk_ctu);
        this.weapon1 = v.findViewById(R.id.randomop_atk_weapon1);
        this.weapon2 = v.findViewById(R.id.randomop_atk_weapon2);
        this.gadget = v.findViewById(R.id.randomop_atk_gadget);

        Button btn = v.findViewById(R.id.randomatk_randomize);
        btn.setOnClickListener(b -> {
            RandomizedOperator op = this.getRandomOperator(this.op.getOperator());
            this.setOperator(op);
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (this.op != null) {
            Bundle savedOp = new Bundle();

            savedOp.putString("NAME", op.getOperator().getName());
            savedOp.putString("WEAPON1", op.getPrimary());
            savedOp.putString("WEAPON2", op.getSecondary());
            savedOp.putString("GADGET", op.getGadget());

            outState.putBundle("ATKOP", savedOp);
        }

        super.onSaveInstanceState(outState);
    }

    private void setOperator(RandomizedOperator op) {
        this.op = op;

        Map<String, CtuData> ctus = this.rouletteData.getCtus();

        CtuData ctu = ctus.get(op.getOperator().getCtu());

        this.name.setText(op.getOperator().getName());
        this.ctu.setText(String.format("%s (%s)", ctu.getName(), ctu.getAbbreviation()));
        this.weapon1.setText(op.getPrimary());
        this.weapon2.setText(op.getSecondary());
        this.gadget.setText(op.getGadget());

        File img = new File(this.assetLocation, op.getOperator().getIcon());
        Bitmap bmp = BitmapFactory.decodeFile(img.getAbsolutePath());
        this.img.setImageBitmap(bmp);
    }

    private void initOperators(Bundle savedInstanceState) {
        if (this.mainActivity == null)
            return;

        SharedPreferences prefs = this.mainActivity.getSharedPreferences(this.getString(R.string.settings_filename), Context.MODE_PRIVATE);
        Set<String> disabledOps = prefs.getStringSet(SettingsActivity.DISABLED_OPS_KEY, new HashSet<>());

        OperatorsData ops = this.rouletteData.getOperators();
        List<OperatorData> xops = ops.getAttackers();
        this.ops = new ArrayList<>();

        for (OperatorData op : xops)
            if (!disabledOps.contains(op.getName()))
                this.ops.add(op);

        RandomizedOperator rop = null;
        OperatorData op = null;

        if (savedInstanceState != null && savedInstanceState.containsKey("ATKOP")) {
            Bundle savedOp = savedInstanceState.getBundle("ATKOP");
            String opName = savedOp.getString("NAME");

            for (OperatorData xop : this.ops)
                if (xop.getName().equals(opName)) {
                    op = xop;
                    break;
                }

            if (op != null) {
                String w1 = savedOp.getString("WEAPON1");
                String w2 = savedOp.getString("WEAPON2");
                String g = savedOp.getString("GADGET");

                if (w1 != null && w2 != null && g != null)
                    rop = new RandomizedOperator(op, w1, w2, g);
            }
        }

        if (rop == null) {
            rop = this.getRandomOperator(null);
        }

        this.setOperator(rop);
    }

    private RandomizedOperator getRandomOperator(@Nullable OperatorData previous) {
        OperatorData op = null;
        while (op == null || (previous != null && op.getName().equals(previous.getName()))) {
            int ro = this.rng.nextInt(this.ops.size());
            op = this.ops.get(ro);
        }

        LoadoutData loadout = op.getLoadout();
        int w1i = this.rng.nextInt(loadout.getPrimaryWeapons().size()),
                w2i = this.rng.nextInt(loadout.getSecondaryWeapons().size()),
                gi = this.rng.nextInt(loadout.getGadgets().size());

        String w1 = loadout.getPrimaryWeapons().get(w1i),
                w2 = loadout.getSecondaryWeapons().get(w2i),
                g = loadout.getGadgets().get(gi);

        return new RandomizedOperator(op, w1, w2, g);
    }
}
