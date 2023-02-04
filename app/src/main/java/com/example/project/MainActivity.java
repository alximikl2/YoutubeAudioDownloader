package com.example.project;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.project.databinding.ActivityMainBinding;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        binds(); //TODO: move to fragment

        setContentView(view);

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        Thread thread = new Thread(() -> { //TODO: move to fragment
            StaticFields.onActivityCreate(view);
            if(StaticFields.isDataCorrect()) {
                ViewUpdateAction viewUpdate = new ViewUpdateAction(this);
                viewUpdate.viewUpdate();
            }
        });
        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void binds(){
        Button searchButton = view.findViewById(R.id.buttonSearch);
        EditText editText = view.findViewById(R.id.videoEditText);

        searchButton.setOnClickListener(view1 -> {
            String search = String.valueOf(editText.getText());
            try {
                SearchAction action = new SearchAction(view, search, this);
                Thread thread = new Thread(action);
                thread.start();
            } catch (NullPointerException e) {
                Log.e("Search Timeout", e.getMessage());
                Toast.makeText(this.getApplicationContext(), "Search Timeout",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}