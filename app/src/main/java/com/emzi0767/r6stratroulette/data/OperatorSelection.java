package com.emzi0767.r6stratroulette.data;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.emzi0767.r6stratroulette.R;

import java.util.List;

public final class OperatorSelection {
    private String name, icon;
    private boolean isEnabled, side;
    private IOnEnabledChanged changeListener;

    public OperatorSelection() {
        this.name = null;
        this.icon = null;
        this.isEnabled = false;
        this.changeListener = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;

        if (this.changeListener != null)
            this.changeListener.OnEnabledChanged(this, enabled);
    }

    public boolean getSide() {
        return side;
    }

    public void setSide(boolean side) {
        this.side = side;
    }

    public void setOnEnabledChangedListener(IOnEnabledChanged listener) {
        this.changeListener = listener;
    }

    public static class Adapter extends ArrayAdapter<OperatorSelection> {
        public Adapter(Context context, List<OperatorSelection> items) {
            super(context, 0, items);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            OperatorSelection opSel = this.getItem(position);
            if (convertView == null)
                convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.settings_ops_item, parent, false);

            CheckBox cb = convertView.findViewById(R.id.settings_ops_enabled);
            TextView tv = convertView.findViewById(R.id.settings_ops_name);
            ImageView iv = convertView.findViewById(R.id.settings_ops_icon);

            cb.setTag(opSel.getName());
            cb.setOnCheckedChangeListener((xcb, x) -> opSel.setEnabled(xcb.isChecked()));

            try {
                iv.setImageURI(Uri.parse(opSel.getIcon()));
            } catch (Exception ex) {

            }
            tv.setText(opSel.getName());
            cb.setChecked(opSel.isEnabled);

            return convertView;
        }
    }

    public interface IOnEnabledChanged {
        void OnEnabledChanged(OperatorSelection item, boolean newStatus);
    }
}
