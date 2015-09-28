package hiteshdua1.codescripter.sf;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import hiteshdua1.codescripter.sf.NavigationDrawer.DrawerItemCustomAdapter;
import hiteshdua1.codescripter.sf.NavigationDrawer.ObjectDrawerItem;

public class MainActivity extends AppCompatActivity {

    /* Navigation Drawer */
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Toolbar toolbar;
    ActionBarDrawerToggle mDrawerToggle;
    /* Navigation Drawer */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.relTopNavbar);
        if(toolbar!=null){
            setSupportActionBar(toolbar);

            toolbar.setNavigationOnClickListener( new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Log.d("D", "Clicked");
                }
            });

//            homeTitle = (TextView) findViewById(R.id.HomeTitle);
//            homeTitle.setTypeface(font.ptSanRegular(), Typeface.NORMAL);

            //toolbar.getMenu().clear();

        /* Drawer layout */
            mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerList = (ListView) findViewById(R.id.left_drawer);

            ObjectDrawerItem[] drawerItems = new ObjectDrawerItem[1];
            drawerItems[0] = new ObjectDrawerItem(R.drawable.ic_menu, "Any Label 1");

            DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.navigation_item_row, drawerItems);
            mDrawerList.setAdapter(adapter);


            mDrawerToggle = new ActionBarDrawerToggle(
                    this,
                    mDrawerLayout,
                    toolbar,
                    R.string.drawer_open,
                    R.string.drawer_close
            ) {
                /// Called when a drawer has settled in a completely closed state.
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                    getSupportActionBar().setTitle("Opened");
                }

                /// Called when a drawer has settled in a completely open state.
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    getSupportActionBar().setTitle("Closed");
                }
            };

            mDrawerLayout.setDrawerListener(mDrawerToggle);

            final ActionBar ab = getSupportActionBar();
            ab.setHomeAsUpIndicator(R.drawable.ic_drawer);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);

            mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            mDrawerLayout.closeDrawer(GravityCompat.START);
//                            Intent i = new Intent(Home.this, ContactUs.class);
//                            startActivity(i);
                            break;
                    }
                }
            });

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
