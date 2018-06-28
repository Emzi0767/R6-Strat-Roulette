package com.emzi0767.r6stratroulette;

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
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class OperatorRandomizerFragment extends Fragment {
    private RouletteData rouletteData = null;
    private File assetLocation = null;

    private Bitmap bmpA = null, bmpD = null;
    private OperatorData opA = null, opD = null;

    private ImageView imgAttacker = null, imgDefender = null;
    private TextView nameAttacker = null, nameDefender = null, ctuAttacker = null, ctuDefender = null;

    private final Random rng = new Random();

    public OperatorRandomizerFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainActivity ac = (MainActivity)this.getActivity();
        this.rouletteData = ac.getRouletteData();
        this.assetLocation = ac.getAssetLocation();

        OperatorsData ops = this.rouletteData.getOperators();
        List<OperatorData> atks = ops.getAttackers();
        List<OperatorData> defs = ops.getDefenders();

        OperatorData atk = null;
        OperatorData def = null;

        if (savedInstanceState != null && savedInstanceState.containsKey("ATTACKER") && savedInstanceState.containsKey("DEFENDER")) {
            String atkN = savedInstanceState.getString("ATTACKER");
            String defN = savedInstanceState.getString("DEFENDER");

            for (OperatorData op : atks)
                if (op.getName().equals(atkN)) {
                    atk = op;
                    break;
                }
            for (OperatorData op : defs)
                if (op.getName().equals(defN)) {
                    def = op;
                    break;
                }
        } else {
            int ra = this.rng.nextInt(atks.size());
            int rd = this.rng.nextInt(defs.size());

            atk = atks.get(ra);
            def = defs.get(rd);
        }

        if (atk != null && def != null)
            this.setOperators(atk, def);
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
            OperatorsData ops = this.rouletteData.getOperators();
            List<OperatorData> atks = ops.getAttackers();
            List<OperatorData> defs = ops.getDefenders();

            int ra = this.rng.nextInt(atks.size());
            int rd = this.rng.nextInt(defs.size());

            OperatorData atk = atks.get(ra);
            OperatorData def = defs.get(rd);

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
}
