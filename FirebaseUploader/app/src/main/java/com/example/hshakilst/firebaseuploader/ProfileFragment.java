package com.example.hshakilst.firebaseuploader;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

public class ProfileFragment extends Fragment {
    private ImageView pImage;
    private TextView name;
    private TextView email;
    private TextView phone;
    private TextView address;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        pImage = (ImageView) view.findViewById(R.id.profile_frag_img);
        name = (TextView) view.findViewById(R.id.profile_frag_name);
        email = (TextView) view.findViewById(R.id.profile_frag_email);
        phone = (TextView) view.findViewById(R.id.profile_frag_phone);
        address = (TextView) view.findViewById(R.id.profile_frag_address);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.shared_pref_user_info),
                getContext().MODE_PRIVATE);
        String sName = sharedPreferences.getString("NAME", "NAME");
        String sEmail = sharedPreferences.getString("EMAIL", "EMAIL");
        String sPhone = sharedPreferences.getString("MOBILE", "MOBILE");
        String sAddress = sharedPreferences.getString("ADDRESS", "ADDRESS");
        TextDrawable drawable = TextDrawable.builder().beginConfig().width(250)
                .height(250).endConfig().buildRound(sName.substring(0, 1), Color.rgb(204,29,29));
        pImage.setImageDrawable(drawable);
        name.setText("Name: "+sName);
        email.setText("Email: "+sEmail);
        phone.setText("Phone: +880"+sPhone);
        address.setText("Address: "+sAddress);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
