package com.example.jmcghee.movementpatterns.activities;

import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.jmcghee.movementpatterns.R;
import com.example.jmcghee.movementpatterns.adapters.MovementAdapter;
import com.example.jmcghee.movementpatterns.data.Movement;
import com.example.jmcghee.movementpatterns.data.MovementDBHelper;
import com.example.jmcghee.movementpatterns.fragments.CategoryDialogFragment;
import com.example.jmcghee.movementpatterns.fragments.MovementDialogFragment;
import com.example.jmcghee.movementpatterns.fragments.MovementIndexFragment;

import java.util.List;


public class MovementIndexActivity extends AppCompatActivity implements
        MovementDialogFragment.MovementDataPasser,
        CategoryDialogFragment.CategoryDataPasser,
        MovementAdapter.MovementOptionsClickListener {

    private static final String MOVEMENT_DIALOG_TAG = "movement_dialog_tag";
    public static final String CATEGORY_TAG = "category_tag";

    private SQLiteDatabase mDb;
    private List<String> categories;
    private CategoryPagerAdapter pagerAdapter;
    private ViewPager viewPager;


    public interface DataUpdateListener {
        void onDataUpdated(List<Movement> movementList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement_index);

        // Get the data
        MovementDBHelper dbHelper = new MovementDBHelper(this);
        mDb = dbHelper.getWritableDatabase();
        categories = getAllCategories();

        // Set up the ViewPager
        viewPager = findViewById(R.id.view_pager);
        pagerAdapter = new CategoryPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        // Set up the tab layout
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Set up the FAB
        FloatingActionButton fab = findViewById(R.id.fab_new_movement);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                DialogFragment dialogFragment = new MovementDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), MOVEMENT_DIALOG_TAG);
            }
        });
    }

    private class CategoryPagerAdapter extends FragmentStatePagerAdapter {
        public CategoryPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            List<Movement> movementList = getAllMovementsFromCategory(categories.get(position));
            MovementIndexFragment movementIndexFragment = MovementIndexFragment.init(movementList);

            return movementIndexFragment;
        }

        @Override
        public int getCount() {
            return categories.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return categories.get(position);
        }

        public void updateCategories() {
            categories = getAllCategories();
            notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.index_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_category) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Movement> getAllMovementsFromCategory(String category) {

        String query = "SELECT * FROM "
                + MovementDBHelper.TABLE_MOVEMENT + " tm, "
                + MovementDBHelper.TABLE_CATEGORY + " tc, "
                + MovementDBHelper.TABLE_MOVEMENT_CATEGORIES + " tmc "
                + "WHERE tc." + MovementDBHelper.KEY_CATEGORY_NAME + " = '" + category + "' "
                + "AND tc." + MovementDBHelper.KEY_ID + " = tmc." + MovementDBHelper.KEY_CATEGORY_ID
                + " AND tm." + MovementDBHelper.KEY_ID + " = tmc." + MovementDBHelper.KEY_MOVEMENT_ID;

        Cursor cursor = mDb.rawQuery(query, null);

        return MovementDBHelper.makeMovementList(cursor);
    }

    private List<String> getAllCategories() {
        Cursor cursor = mDb.query(
                MovementDBHelper.TABLE_CATEGORY,
                null,
                null,
                null,
                null,
                null,
                MovementDBHelper.KEY_CATEGORY_NAME
        );

        cursor.close();

        return MovementDBHelper.makeCategoryList(cursor);
    }


    @Override
    public void onMovementDataPassed(ContentValues cv) {
        // Insert movement into movement table
        long movementId = mDb.insert(MovementDBHelper.TABLE_MOVEMENT, null, cv);

        // Insert movement into movement/category linking table
        Cursor cursor = mDb.query(
                MovementDBHelper.TABLE_CATEGORY,
                new String[]{MovementDBHelper.KEY_MOVEMENT_ID},
                MovementDBHelper.KEY_CATEGORY_NAME + "=?",
                new String[]{categories.get(viewPager.getCurrentItem())},
                null,
                null,
                null
                );
        long categoryId = cursor.getLong(cursor.getColumnIndex(MovementDBHelper.KEY_MOVEMENT_ID));
        ContentValues movementCategories = new ContentValues();
        movementCategories.put(MovementDBHelper.KEY_MOVEMENT_ID, movementId);
        movementCategories.put(MovementDBHelper.KEY_CATEGORY_ID, categoryId);
        mDb.insert(MovementDBHelper.TABLE_MOVEMENT_CATEGORIES, null, movementCategories);

        // Update the recycler view fragment in the tab that is shown
        DataUpdateListener dataUpdateListener = (DataUpdateListener) this;
        dataUpdateListener.onDataUpdated(getAllMovementsFromCategory(categories.get(viewPager.getCurrentItem())));

    }

    @Override
    public void onCategoryDataPassed(ContentValues cv) {
        mDb.insert(MovementDBHelper.TABLE_CATEGORY, null, cv);
        pagerAdapter.updateCategories();
    }

    @Override
    public void editMovement(Movement movement) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        DialogFragment dialogFragment = new MovementDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), MOVEMENT_DIALOG_TAG);
    }

    @Override
    public void deleteMovement(Movement movement) {
        mDb.delete(MovementDBHelper.TABLE_MOVEMENT, MovementDBHelper.KEY_MOVEMENT_NAME + "='" + movement.getName() + "'", null);
     //   adapter.updateCursor(getAllMovements();
    }
}
