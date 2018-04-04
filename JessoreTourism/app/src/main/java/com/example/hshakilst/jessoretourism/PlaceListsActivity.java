package com.example.hshakilst.jessoretourism;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PlaceListsActivity extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        listView = (ListView) findViewById(R.id.list_view);
        final ArrayList<Places> list = new ArrayList<Places>();
        list.add(new Places(R.mipmap.dhopakhola, "DhopaKhola", "Dhopakhola village is situated in " +
                "the Jessore Sadar Upozilla."));
        list.add(new Places(R.mipmap.balia_vakutia, "Balia Vakutia", "Village named Balia Vakutia is" +
                " famed for its minor intertwining production."));
        list.add(new Places(R.mipmap.rampur, "Rampur", "Rampur village has turned out to be a tourist " +
                "charm intended for its island-like appearance."));
        list.add(new Places(R.mipmap.abhaynagar, "Abhaynagar Upazila", "There a small village named " +
                "Dhoolgaam once had a wonderful multifaceted."));
        list.add(new Places(R.mipmap.sagardari, "Sagardari", "In Sagardari village of Jessore, the house of Michael " +
                "Madhusudan Dutta is situated."));
        list.add(new Places(R.mipmap.vaatnogor, "Vaatnagar", "In Vaatnogor, relics of eleven sanctuaries " +
                "multifaceted."));
        list.add(new Places(R.mipmap.shahi_mosque, "Shahi Mosque", "it is situated in Hamidpur village " +
                "which is an illustration of Muslim Sultani era."));
        list.add(new Places(R.mipmap.imam_bara, "Imam Bara", "It is an ancient building which was made " +
                "by Hazi Muhammad Mohsin."));
        list.add(new Places(R.mipmap.airport, "Jessore Airport", "Jessore airport is an international airport"));
        list.add(new Places(R.mipmap.benapole, "Benapole Border Gate", "Benapole is a boundary city " +
                "in Bangladesh, the furthermost frequently used border ."));
        list.add(new Places(R.mipmap.mallinath, "Mallinath", "A terracotta inscription bearing the " +
                "spitting image of Mallinath, a woman tirthankar (saint)."));
        PlaceAdapter adapter = new PlaceAdapter(this, R.layout.custom_list, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Places places = list.get(i);
                Intent intent = new Intent(getApplicationContext(), PlaceDeatailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Name", places.getName());
                switch (places.getName()){
                    case "DhopaKhola":
                        bundle.putInt("Desc", R.string.desc_dhopakhola);
                        bundle.putInt("Image", R.drawable.dhopakhola);
                        break;
                    case "Balia Vakutia":
                        bundle.putInt("Desc", R.string.desc_balia_vakutia);
                        bundle.putInt("Image", R.drawable.balia_vakutia);
                        break;
                    case "Rampur":
                        bundle.putInt("Desc", R.string.desc_rampur);
                        bundle.putInt("Image", R.drawable.rampur);
                        break;
                    case "Abhaynagar Upazila":
                        bundle.putInt("Desc", R.string.desc_abhaynagar);
                        bundle.putInt("Image", R.drawable.abhaynagar);
                        break;
                    case "Sagardari":
                        bundle.putInt("Desc", R.string.desc_sagardari);
                        bundle.putInt("Image", R.drawable.sagardari);
                        break;
                    case "Vaatnagar":
                        bundle.putInt("Desc", R.string.desc_vaatnogor);
                        bundle.putInt("Image", R.drawable.vaatnogor);
                        break;
                    case "Shahi Mosque":
                        bundle.putInt("Desc", R.string.desc_shahi_mosque);
                        bundle.putInt("Image", R.drawable.shahi_mosque);
                        break;
                    case "Imam Bara":
                        bundle.putInt("Desc", R.string.desc_imam_bara);
                        bundle.putInt("Image", R.drawable.imam_bara);
                        break;
                    case "Jessore Airport":
                        bundle.putInt("Desc", R.string.desc_airport);
                        bundle.putInt("Image", R.drawable.airport);
                        break;
                    case "Benapole Border Gate":
                        bundle.putInt("Desc", R.string.desc_benapole);
                        bundle.putInt("Image", R.drawable.benapole);
                        break;
                    case "Mallinath":
                        bundle.putInt("Desc", R.string.desc_mallinath);
                        bundle.putInt("Image", R.drawable.mallinath);
                        break;
                    default:
                        break;
                }
                intent.putExtra("Detail", bundle);
                startActivity(intent);
            }
        });
    }
}
