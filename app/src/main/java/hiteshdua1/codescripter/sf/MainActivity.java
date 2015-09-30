package hiteshdua1.codescripter.sf;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import hiteshdua1.codescripter.sf.Fragments.fragment_map;
import hiteshdua1.codescripter.sf.NavigationDrawer.FragmentDrawer;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {


    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment f = new fragment_map();
        ft.replace(R.id.container_body, f);
        getSupportActionBar().setTitle("Volunteer Programmes");
        ft.commit();

    }


    @Override
    public void onDrawerItemSelected(View view, int position) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                Fragment f1 = new fragment_map();
                ft.replace(R.id.container_body, f1);
                getSupportActionBar().setTitle("Centres");
                ft.commit();
                break;
            case 1:
                Fragment f2 = new fragment_map();
                ft.replace(R.id.container_body, f2);
                getSupportActionBar().setTitle("Centres");
                ft.commit();
                break;

            case 2:
//                ListFragment f3 = new fragment_events();
//                ft.replace(R.id.container_body, f3);
//                getSupportActionBar().setTitle("Events ");
                ft.commit();
                break;

            case 3:
//                SharedPreferences s  = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//                SharedPreferences.Editor e = s.edit();
//                e.putString("id","null");
//                e.commit();
//                Intent i = new Intent(MainActivity.this,LoginLogout.class);
//                startActivity(i);
//                finish();
                break;


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
