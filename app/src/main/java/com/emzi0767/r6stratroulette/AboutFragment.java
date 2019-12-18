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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (this.getActivity().getPackageName().endsWith(".debug")) {
            View v = this.getView();
            TextView tv = v.findViewById(R.id.about_name);
            tv.setText("DEBUG BUILD - DO NOT USE IN PROD");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about, container, false);

        // Button supportPP = v.findViewById(R.id.btn_about_support_paypal);
        // supportPP.setOnClickListener(x -> {
        //     Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.getString(R.string.about_paypal_url)));
        //     this.startActivity(browserIntent);
        // });
        //
        // Button supportPT = v.findViewById(R.id.btn_about_support_patreon);
        // supportPT.setOnClickListener(x -> {
        //     Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.getString(R.string.about_patreon_url)));
        //     this.startActivity(browserIntent);
        // });

        return v;
    }

}
