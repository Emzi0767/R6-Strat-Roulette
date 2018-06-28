package com.emzi0767.r6stratroulette;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

    private OperatorData[] opsA = null, opsD = null;

    private ImageView[] imgAttackers = null, imgDefenders = null;
    private TextView[] nameAttackers = null, nameDefenders = null;

    private final Random rng = new Random();

    public TeamRandomizerFragment() {
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

        OperatorData[] atk = null;
        OperatorData[] def = null;

        if (savedInstanceState != null && savedInstanceState.containsKey("ATTACKERS") && savedInstanceState.containsKey("DEFENDERS")) {
            List<String> atkN = Arrays.asList(savedInstanceState.getStringArray("ATTACKERS"));
            List<String> defN = Arrays.asList(savedInstanceState.getStringArray("DEFENDERS"));

            atk = new OperatorData[5];
            def = new OperatorData[5];

            for (int i = 0; i < atks.size(); i++) {
                OperatorData opT = atks.get(i);
                int j = atkN.indexOf(opT.getName());
                if (j != -1)
                    atk[j] = opT;
            }
            for (int i = 0; i < defs.size(); i++) {
                OperatorData opT = defs.get(i);
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
        } else {
            atk = new OperatorData[5];
            def = new OperatorData[5];
            this.randomizeOperators(atk, def);
        }

        if (atk != null && def != null)
            this.setOperators(atk, def);
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
            ArrayList<OperatorData> atks = new ArrayList<>(ops.getAttackers());
            ArrayList<OperatorData> defs = new ArrayList<>(ops.getDefenders());

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
        ArrayList<OperatorData> atks = new ArrayList<>(ops.getAttackers());
        ArrayList<OperatorData> defs = new ArrayList<>(ops.getDefenders());

        for (int i = 0; i < 5; i++) {
            int ra = this.rng.nextInt(atks.size());
            int rd = this.rng.nextInt(defs.size());

            atk[i] = atks.get(ra);
            def[i] = defs.get(rd);

            atks.remove(ra);
            defs.remove(rd);
        }
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
