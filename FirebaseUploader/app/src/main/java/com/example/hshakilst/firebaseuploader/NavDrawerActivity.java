package com.example.hshakilst.firebaseuploader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FileFragment.OnListFragmentInteractionListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    SharedPreferences preferences;
    private View navHeader;
    private ImageView pImage;
    private TextView name, email;
    private DatabaseReference userData;
    private DatabaseReference fileData;
    private User user;
    private Handler handler;
    private Fragment fragment;
    private UploadFragment uFrag;
    public ArrayList<File> fileList;
    private FileFragment fFrag;
    private ProfileFragment pFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navHeader = navigationView.getHeaderView(0);
        pImage = (ImageView) navHeader.findViewById(R.id.profile_image_view);
        name = (TextView) navHeader.findViewById(R.id.name_text_view);
        email = (TextView) navHeader.findViewById(R.id.email_text_view);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    Intent intent = new Intent(NavDrawerActivity.this, LoginActivity.class);
                    startActivity(intent);
                    NavDrawerActivity.this.finish();
                }
            }
        };

        preferences = getSharedPreferences(getString(R.string.shared_pref_user_info),
                MODE_PRIVATE);

        if(firebaseAuth.getCurrentUser().getUid().equals(preferences.getString("UID", null))){
            String pName = preferences.getString("NAME", null);
            String pEmail = preferences.getString("EMAIL", null);
            TextDrawable drawable = TextDrawable.builder().beginConfig().width(150)
                    .height(150).endConfig().buildRound(pName.substring(0, 1), Color.rgb(228, 63, 63));
            pImage.setImageDrawable(drawable);
            name.setText(pName);
            email.setText(pEmail);
        }
        else{
            userData = FirebaseDatabase.getInstance().getReference("users")
                    .child(firebaseAuth.getCurrentUser().getUid());
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);
                    if(user != null){
                        Context context = NavDrawerActivity.this;
                        SharedPreferences preferences = context
                                .getSharedPreferences(getString(R.string.shared_pref_user_info),
                                        context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putString("UID", firebaseAuth.getCurrentUser().getUid());
                        edit.putString("NAME", user.getName());
                        edit.putString("EMAIL", firebaseAuth.getCurrentUser().getEmail());
                        edit.putString("ADDRESS", user.getAddress());
                        edit.putString("MOBILE", user.getMobile());
                        edit.apply();
                        TextDrawable drawable = TextDrawable.builder().beginConfig().width(150)
                                .height(150).endConfig().buildRound(user.getName().substring(0, 1), Color.rgb(228, 63, 63));
                        pImage.setImageDrawable(drawable);
                        name.setText(user.getName());
                        email.setText(firebaseAuth.getCurrentUser().getEmail());

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(NavDrawerActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }
            };
            userData.addListenerForSingleValueEvent(valueEventListener);
        }

        handler = new Handler();
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out)
                    .add(R.id.root_layout, UploadFragment.newInstance(), "UPLOAD_FRAGMENT")
            .commit();
        }

        fileList = new ArrayList<File>();
        fetchFiles();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if(fragment == null){
            uFrag = UploadFragment.newInstance();
            fFrag = FileFragment.newInstance(1, fileList);
            pFrag = ProfileFragment.newInstance();
        }
        int id = item.getItemId();

        if (id == R.id.nav_upload) {
            fragment = uFrag;
        } else if (id == R.id.nav_files) {
            fragment = fFrag;
        } else if (id == R.id.nav_profile) {
            fragment = pFrag;
        } else if (id == R.id.nav_log_out) {
            firebaseAuth.signOut();
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("UID");
            editor.remove("NAME");
            editor.remove("EMAIL");
            editor.remove("ADDRESS");
            editor.remove("MOBILE");
            editor.apply();
        }

        Runnable mPending = new Runnable() {
            @Override
            public void run() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.root_layout, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        handler.post(mPending);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void fetchFiles(){
        fileData = FirebaseDatabase.getInstance().getReference("files")
                .child(firebaseAuth.getCurrentUser().getUid());
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fileList.add(dataSnapshot.getValue(File.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(NavDrawerActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_LONG)
                        .show();
            }
        };
        fileData.addChildEventListener(childEventListener);
    }

    @Override
    public void onListFragmentDownload(File item) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getFileUrl()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onListFrgmentShare(File item) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, item.getFileUrl());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share this file to"));
    }

    @Override
    public void onListFragmentDelete(final File item) {
        StorageReference delFile = FirebaseStorage.getInstance().getReference().child("files/" +
                firebaseAuth.getCurrentUser().getUid()+"/" + item.getFileName());
        delFile.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Query delFile = FirebaseDatabase.getInstance().getReference().child("files/" +
                firebaseAuth.getCurrentUser().getUid())
                        .orderByChild("fileName").equalTo(item.getFileName());
                delFile.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot fileNameSnapshot: dataSnapshot.getChildren()) {
                            fileNameSnapshot.getRef().removeValue();
                        }
                        Toast.makeText(NavDrawerActivity.this, "Deleted Succesfully!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NavDrawerActivity.this, "Failed to delete file!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
