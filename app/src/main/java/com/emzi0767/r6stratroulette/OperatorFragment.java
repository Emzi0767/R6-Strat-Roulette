package com.emzi0767.r6stratroulette;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.emzi0767.r6stratroulette.data.RandomizedOperator;
import com.emzi0767.r6stratroulette.models.CtuData;
import com.emzi0767.r6stratroulette.models.RouletteData;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class OperatorFragment extends Fragment {
    private RouletteData rouletteData = null;
    private File assetLocation = null;

    private ImageView image = null;
    private TextView name = null, ctu = null, weapon1 = null, weapon2 = null, gadget = null;

    private RandomizedOperator operator = null;
    private OperatorFragmentType type = OperatorFragmentType.UNKNOWN;

    public OperatorFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainActivity mainActivity = (MainActivity)this.getActivity();
        this.assetLocation = mainActivity.getAssetLocation();
        this.rouletteData = mainActivity.getRouletteData();

        OperatorRandomizerFragment orf = (OperatorRandomizerFragment)this.getParentFragment();
        if (orf == null)
            return;

        if (this.type == OperatorFragmentType.ATTACKER)
            this.setOperator(orf.getAttacker());
        else if (this.type == OperatorFragmentType.DEFENDER)
            this.setOperator(orf.getDefender());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_operator, container, false);

        this.image = v.findViewById(R.id.randomop_img);
        this.name = v.findViewById(R.id.randomop_name);
        this.ctu = v.findViewById(R.id.randomop_ctu);
        this.weapon1 = v.findViewById(R.id.randomop_weapon1);
        this.weapon2 = v.findViewById(R.id.randomop_weapon2);
        this.gadget = v.findViewById(R.id.randomop_gadget);

        return v;
    }

    public void setOperator(@NonNull RandomizedOperator op) {
        this.operator = op;

        File operatorImg = new File(this.assetLocation, op.getOperator().getIcon());
        Bitmap bmp = BitmapFactory.decodeFile(operatorImg.getAbsolutePath());

        CtuData ctu = this.rouletteData.getCtus().get(op.getOperator().getCtu());

        this.image.setImageBitmap(bmp);
        this.name.setText(op.getOperator().getName());
        this.ctu.setText(String.format("%s (%s)", ctu.getName(), ctu.getAbbreviation()));
        this.weapon1.setText(op.getPrimary());
        this.weapon2.setText(op.getSecondary());
        this.gadget.setText(op.getGadget());
    }

    public RandomizedOperator getOperator() {
        return this.operator;
    }

    public OperatorFragmentType getType() {
        return type;
    }

    public void setType(OperatorFragmentType type) {
        this.type = type;
    }

    public enum OperatorFragmentType {
        ATTACKER,
        DEFENDER,
        UNKNOWN
    }
}
