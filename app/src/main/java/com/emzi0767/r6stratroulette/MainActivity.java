package com.emzi0767.r6stratroulette;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import com.emzi0767.r6stratroulette.data.URLs;
import com.emzi0767.r6stratroulette.models.RouletteData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private int mActiveView = 0;
    private int grantedPermissions = 0;
    private ProgressDialog progressDialog;
    private RouletteData rouletteData = null;
    private File assetLocation = null;

    private final CountDownLatch permissionSemaphore = new CountDownLatch(1);
    private final ThreadPoolExecutor threadPool = (ThreadPoolExecutor)Executors.newFixedThreadPool(2);

    private Fragment home = null,
        about = null,
        randomOperator = null,
        strategyRoulette = null,
        randomTeam = null,
        randomRecruit = null;

    private static final int PERMISSION_INTERNET = 32;
    private static final int PERMISSION_READ_STORAGE = 64;
    private static final int PERMISSION_WRITE_STORAGE = 128;
    private static final int PERMISSION_NETWORK_STATE = 256;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Pass to superclass and initialize
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        // Restore fragments
        List<Fragment> frags = this.getSupportFragmentManager().getFragments();
        for (Fragment f : frags) {
            if (f instanceof HomeFragment)
                this.home = f;
            else if (f instanceof OperatorRandomizerFragment)
                this.randomOperator = f;
            else if (f instanceof TeamRandomizerFragment)
                this.randomTeam = f;
            else if (f instanceof RecruitRandomizerFragment)
                this.randomRecruit = f;
            else if (f instanceof StrategyRouletteFragment)
                this.strategyRoulette = f;
            else if (f instanceof AboutFragment)
                this.randomOperator = f;
        }

        if (this.home == null)
            this.home = new HomeFragment();
        if (this.about == null)
            this.about = new AboutFragment();
        if (this.randomOperator == null)
            this.randomOperator = new OperatorRandomizerFragment();
        if (this.strategyRoulette == null)
            this.strategyRoulette = new StrategyRouletteFragment();
        if (this.randomTeam == null)
            this.randomTeam = new TeamRandomizerFragment();
        if (this.randomRecruit == null)
            this.randomRecruit = new RecruitRandomizerFragment();

        // Set action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        // Enable menu button
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        // Get the nav drawer
        this.mDrawerLayout = this.findViewById(R.id.drawer_layout);

        // Set nav drawer callback
        NavigationView navigationView = this.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(x -> {
            x.setChecked(true);
            mDrawerLayout.closeDrawers();

            int vid = x.getItemId();
            this.mActiveView = vid != R.id.nav_drawer_exit ? vid : this.mActiveView;
            this.setView(vid, actionBar);

            return true;
        });

        // Check if we have necessary permissions
        ArrayList<String> reqPerms = new ArrayList<>();
        String[] perms = new String[] {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED)
                reqPerms.add(perm);
            else
                this.updatePermissions(perm);
        }

        if (!reqPerms.isEmpty()) {
            final String[] perms2 = reqPerms.toArray(new String[0]);
            AlertDialog.Builder adb = new AlertDialog.Builder(this, this.getAppropriateTheme());
            AlertDialog ad = adb.setTitle(R.string.activity_permissions_title)
                    .setMessage(R.string.activity_permissions)
                    .setCancelable(false)
                    .setPositiveButton(R.string.activity_permissions_affirmative, (d, i) -> {
                        d.dismiss();
                        this.requestPermissions(perms2);
                    })
                    .setNegativeButton(R.string.activity_permissions_negative, (d, i) -> this.finish())
                    .create();
            ad.show();
        } else {
            this.permissionSemaphore.countDown();
        }

        // Initiate asset check and download
        threadPool.submit(this::checkAndDownloadAssets);

        // Load data, if possible
        if (this.rouletteData == null) {
            try {
                File[] caches = ContextCompat.getExternalCacheDirs(this);
                if (caches.length == 0) {
                    this.showErrorDialog(new Exception("No external storage available"));
                    return;
                }
                File sdcard = new File(caches[0], "assets");
                this.assetLocation = sdcard;

                Gson gson = new Gson();
                File data = new File(this.assetLocation, "data.json");
                if (data.exists()) {
                    String dataJson = FileUtils.readFileToString(data, "UTF-8");
                    this.rouletteData = gson.fromJson(dataJson, RouletteData.class);
                }
            } catch (Exception ex) {
                this.showErrorDialog(ex);
            }
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Set main view to home
        ActionBar actionBar = this.getSupportActionBar();
        this.setView(this.mActiveView, actionBar);
    }

    private void setView(int id, ActionBar actionBar) {
        FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();

        switch (id) {
            case 0:
            case R.id.nav_drawer_home:
                if (this.home.isDetached())
                    t.attach(this.home);
                actionBar.setTitle(R.string.frag_home);
                t.replace(R.id.content_frame, this.home);
                break;

            case R.id.nav_drawer_random_op:
                if (this.randomOperator.isDetached())
                    t.attach(this.randomOperator);
                actionBar.setTitle(R.string.frag_random_op);
                t.replace(R.id.content_frame, this.randomOperator);
                break;

            case R.id.nav_drawer_random_team:
                if (this.randomTeam.isDetached())
                    t.attach(this.randomTeam);
                actionBar.setTitle(R.string.frag_random_team);
                t.replace(R.id.content_frame, this.randomTeam);
                break;

            case R.id.nav_drawer_recruit_builder:
                if (this.randomRecruit.isDetached())
                    t.attach(this.randomRecruit);
                actionBar.setTitle(R.string.frag_recruit_builder);
                t.replace(R.id.content_frame, this.randomRecruit);
                break;

            case R.id.nav_drawer_strat_roulette:
                if (this.strategyRoulette.isDetached())
                    t.attach(this.strategyRoulette);
                actionBar.setTitle(R.string.frag_strat_roulette);
                t.replace(R.id.content_frame, this.strategyRoulette);
                break;

            case R.id.nav_drawer_about:
                if (this.about.isDetached())
                    t.attach(this.about);
                actionBar.setTitle(R.string.frag_about);
                t.replace(R.id.content_frame, this.about);
                break;

            case R.id.nav_drawer_exit:
                t.commit();
                this.finish();
                return;
        }

        t.commit();
    }

    private void showErrorDialog(Exception ex) {
        Log.e("R6Strats", "Unhandled exception", ex);

        AlertDialog.Builder adb = new AlertDialog.Builder(this, this.getAppropriateTheme());

        AlertDialog ad = adb.setTitle(R.string.activity_nointernet_title)
                .setMessage(R.string.activity_nointernet)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.activity_nointernet_affirmative, (d, i) -> this.finish())
                .create();

        ad.show();
    }

    private void showNoInternetDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this, this.getAppropriateTheme());

        AlertDialog ad = adb.setTitle(R.string.activity_error_title)
                .setMessage(R.string.activity_error)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.activity_error_affirmative, (d, i) -> this.finish())
                .create();

        ad.show();
    }

    private void showWorkingDialog() {
        this.progressDialog = new ProgressDialog(this, this.getAppropriateTheme());
        this.progressDialog.setMessage(this.getResources().getString(R.string.activity_loading_assets));
        this.progressDialog.setCancelable(false);
        this.progressDialog.setIndeterminate(true);
        this.progressDialog.show();
    }

    private void dismissWorkingDialog() {
        this.progressDialog.dismiss();
    }

    private void releaseSemaphoreIfPermissionsGranted() {
        if (this.grantedPermissions == (PERMISSION_INTERNET | PERMISSION_NETWORK_STATE | PERMISSION_READ_STORAGE | PERMISSION_WRITE_STORAGE))
            this.permissionSemaphore.countDown();
    }

    private void updatePermissions(String perm) {
        switch (perm) {
            case Manifest.permission.INTERNET:
                this.grantedPermissions |= PERMISSION_INTERNET;
                break;

            case Manifest.permission.ACCESS_NETWORK_STATE:
                this.grantedPermissions |= PERMISSION_NETWORK_STATE;
                break;

            case Manifest.permission.READ_EXTERNAL_STORAGE:
                this.grantedPermissions |= PERMISSION_READ_STORAGE;
                break;

            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                this.grantedPermissions |= PERMISSION_WRITE_STORAGE;
                break;
        }
    }

    private void requestPermissions(String[] perms) {
        ActivityCompat.requestPermissions(this, perms, 0);
    }

    private int getAppropriateTheme() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1)
            return android.R.style.Theme_DeviceDefault_Dialog_NoActionBar;
        else
            return android.support.v7.appcompat.R.style.Base_Theme_AppCompat_Dialog;
    }

    private void checkAndDownloadAssets() {
        try {
            this.checkAndDownloadAssetsInner();
        } catch (Exception ex) {
            this.runOnUiThread(() -> this.showErrorDialog(ex));
        }
    }

    private void checkAndDownloadAssetsInner() throws Exception {
        this.permissionSemaphore.await();

        // show working dialog
        this.runOnUiThread(this::showWorkingDialog);

        // check for internet
        boolean hasInternet = false;
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            hasInternet = ni == null || ni.isConnected();
        }

        // get the obb dir
        File[] caches = ContextCompat.getExternalCacheDirs(this);
        if (caches.length == 0) {
            this.showErrorDialog(new Exception("No external storage available"));
            return;
        }
        File sdcard = caches[0];
        if (!this.checkDirectory(sdcard, hasInternet)) return;

        // get asset dir and validate it
        sdcard = new File(sdcard, "assets");
        if (!this.checkDirectory(sdcard, hasInternet)) return;
        this.assetLocation = sdcard;

        // create GSON reader
        Gson gson = new Gson();

        // load remote asset registry
        String assetsCurrent = null;
        Map<String, String> assetsMapCurrent = new HashMap<>();
        Type type = new TypeToken<HashMap<String, String>>() { }.getType();
        if (hasInternet) {
            URL assetsUrl = URLs.getAssetUrl("assets.json");
            assetsCurrent = IOUtils.toString(assetsUrl, "UTF-8");
            assetsMapCurrent = gson.fromJson(assetsCurrent, type);
        }

        // check asset registry
        File assets = new File(sdcard, "assets.json");
        HashMap<String, String> assetsMapLocal = new HashMap<>();
        if (!assets.exists() && !hasInternet) {
            this.runOnUiThread(this::showNoInternetDialog);
            return;
        } else if (assets.exists()) {
            String assetsJson = FileUtils.readFileToString(assets, "UTF-8");
            Map<String, String> tmp = gson.fromJson(assetsJson, type);
            for (String key : tmp.keySet())
                assetsMapLocal.put(key, tmp.get(key));
        }

        // see and update what's missing
        for (String s : assetsMapCurrent.keySet()) {
            File asset = new File(sdcard, s);
            if (!assetsMapLocal.containsKey(s) || !assetsMapLocal.get(s).equals(assetsMapCurrent.get(s)) || !asset.exists()) {
                URL assetUrl = URLs.getAssetUrl(s);
                FileUtils.copyURLToFile(assetUrl, asset);
                Log.i("R6Strats", String.format("Downloaded asset '%s' with a hash of '%s'", s, assetsMapCurrent.get(s)));
            }
        }

        // write the current registry
        if (assetsCurrent != null)
            FileUtils.writeStringToFile(assets, assetsCurrent, "UTF-8");

        // load roulette data
        File data = new File(sdcard, "data.json");
        String dataJson = FileUtils.readFileToString(data, "UTF-8");
        this.rouletteData = gson.fromJson(dataJson, RouletteData.class);

        // dismiss working dialog
        this.runOnUiThread(this::dismissWorkingDialog);
    }

    public boolean checkDirectory(File dir, boolean hasInternet) {
        if ((!dir.isDirectory() || !dir.exists()) && !hasInternet) {
            this.runOnUiThread(this::showNoInternetDialog);
            return false;
        }
        else if (!dir.isDirectory() || !dir.exists())
            dir.mkdir();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("active_view", this.mActiveView);

        super.onSaveInstanceState(outState);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && outPersistentState != null)
            outPersistentState.putInt("active_view", this.mActiveView);
        else
            outState.putInt("active_view", this.mActiveView);

        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("active_view"))
            this.mActiveView = savedInstanceState.getInt("active_view");

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && persistentState != null) {
            if (persistentState.containsKey("active_view"))
                this.mActiveView = persistentState.getInt("active_view");
        }
        else {
            if (savedInstanceState.containsKey("active_view"))
                this.mActiveView = savedInstanceState.getInt("active_view");
        }

        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            int result = grantResults[i];

            if (result != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.activity_permissions_denied, Toast.LENGTH_SHORT).show();

                this.finish();
                break;
            }

            this.updatePermissions(perm);
        }

        this.releaseSemaphoreIfPermissionsGranted();
    }

    public RouletteData getRouletteData() {
        return this.rouletteData;
    }

    public File getAssetLocation() {
        return this.assetLocation;
    }
}
