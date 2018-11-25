package edu.ua.cs.cs495.caladrius.android;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import edu.ua.cs.cs495.caladrius.android.graphData.GraphContract.GraphEntry;

/**
 * This is use for add or edit graph setting page.
 *
 * @author Hansheng Li
 */
public class GraphEditorActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    /** Identifier for the graph data loader */
    private static final int EXISTING_GRAPH_LOADER = 0;

    /** Content URI for the existing graph (null if it's a new graph) */
    private Uri mCurrentGraphUri;

    /** EditText field to enter the graph's Time Range */
    private Spinner mTimeRangeSpinner;

    /** EditText field to enter the graph's Type */
    private Spinner mTypeSpinner;

    /** EditText field to enter the graph's Stats */
    private Spinner mStatsSpinner;

    /** EditText field to enter the graph's Color */
    private Spinner mColorSpinner;

    /** EditText field to enter the graph's Title */
    private EditText mTitleEditText;

    private LinearLayout mSecondGraphLinearLayout;

    /** EditText field to enter the graph2's Type */
    private Spinner mType2Spinner;

    /** EditText field to enter the graph2's Stats */
    private Spinner mStats2Spinner;

    /** EditText field to enter the graph2's Color */
    private Spinner mColor2Spinner;

    private Switch mSecondGraphSwitch;

    private RadioGroup mTimeRangeTypeRadioGroup;

    private String mStartDate = "N/A";
    private String mEndDate = "N/A";


    private TextView mStartDateTextView;
    private TextView mEndDateTextView;

    private boolean mGraphHasChanged = false;

    private int mTimeRange = GraphEntry.TIME_RANGE_TODAY;
    private int mType = GraphEntry.BAR_GRAPH;
    private int mStats = GraphEntry.STATS_BPM;
    private int mColor = GraphEntry.COLOR_BLACK;
    private int mType2 = GraphEntry.BAR_GRAPH;
    private int mStats2 = GraphEntry.STATS_BPM;
    private int mColor2 = GraphEntry.COLOR_BLACK;
    private int mNumberOfGraph = GraphEntry.GRAPH_NUMBER_ONE;
    private int mTimeRangeType = GraphEntry.TIME_RANGE_TYPE_SINGLE;
    
    // OnTouchListener that listens for any user touches on a View, implying that they are modifying
    // the view, and we change the mGraphHasChanged boolean to true.

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mGraphHasChanged = true;
            return false;
        }
    };

    static String getMonthForInt(int m)
    {
        List<String> monthStr = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        return monthStr.get(m);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_activity_editor);

        Toolbar myToolbar = findViewById(R.id.graph_editor_toolbar);
        setSupportActionBar(myToolbar);

        mSecondGraphLinearLayout = findViewById(R.id.second_graph_layout);

        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new graph or editing an existing one.
        Intent intent = getIntent();
        mCurrentGraphUri = intent.getData();

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // If the intent DOES NOT contain a graph content URI, then we know that we are
        // creating a new graph.
        if (mCurrentGraphUri == null) {
            // This is a new graph, so change the app bar to say "Add a Graph"
            Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.editor_activity_title_new_graph));

            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a graph that hasn't been created yet.)
            invalidateOptionsMenu();
        } else {
            // Otherwise this is an existing graph, so change app bar to say "Edit Graph"
            Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.editor_activity_title_edit_graph));

            // Initialize a loader to read the graph data from the database
            // and display the current values in the editor
            getLoaderManager().initLoader(EXISTING_GRAPH_LOADER, null, this);
        }


        // Find all relevant views that we will need to read user input from
        mTimeRangeSpinner = findViewById(R.id.spinner_time_range);
        mTypeSpinner = findViewById(R.id.spinner_graph_type);
        mStatsSpinner = findViewById(R.id.spinner_graph_stats);
        mColorSpinner = findViewById(R.id.spinner_graph_color);
        mType2Spinner = findViewById(R.id.spinner_graph2_type);
        mStats2Spinner = findViewById(R.id.spinner_graph2_stats);
        mColor2Spinner = findViewById(R.id.spinner_graph2_color);
        mTitleEditText = findViewById(R.id.edit_graph_title);
        mStartDateTextView = findViewById(R.id.start_date);
        mEndDateTextView = findViewById(R.id.end_date);

        mTimeRangeSpinner.setOnTouchListener(mTouchListener);
        mTypeSpinner.setOnTouchListener(mTouchListener);
        mStatsSpinner.setOnTouchListener(mTouchListener);
        mColorSpinner.setOnTouchListener(mTouchListener);
        mType2Spinner.setOnTouchListener(mTouchListener);
        mStats2Spinner.setOnTouchListener(mTouchListener);
        mColor2Spinner.setOnTouchListener(mTouchListener);
        mTitleEditText.setOnTouchListener(mTouchListener);

        setupSpinner();

        mSecondGraphSwitch = findViewById(R.id.second_graph_switch);
        mSecondGraphSwitch.setOnTouchListener(mTouchListener);

        mTimeRangeTypeRadioGroup = findViewById(R.id.time_range_type);
        mTimeRangeTypeRadioGroup.setOnTouchListener(mTouchListener);

        mTimeRangeTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.Single_day:
                        mTimeRangeType = GraphEntry.TIME_RANGE_TYPE_SINGLE;
                        break;
                    case R.id.several_days:
                        mTimeRangeType = GraphEntry.TIME_RANGE_TYPE_SEVERAL;
                        break;
                    case R.id.relative_days:
                        mTimeRangeType = GraphEntry.TIME_RANGE_TYPE_RELATIVE;
                        break;
                    default:
                        mTimeRangeType = GraphEntry.TIME_RANGE_TYPE_SINGLE;
                        break;
                }
            }
        });

        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        String date = getMonthForInt(month) + " " + day + " " + year;
        mStartDate = date;
        mStartDateTextView.setText(String.format("%sth%s",
                date.substring(0, date.length() - 5),
                date.substring(date.length() - 5, date.length())));

        CalendarView mCalendarView = findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int i1, int i2)
            {
                Integer month = i1;
                String day;
                if (i2 < 10) {
                    day = " " + String.valueOf(i2);
                } else {
                    day = String.valueOf(i2);
                }
                String date = getMonthForInt(month) + " " + day + " " + year;
                String dateShow = getMonthForInt(month) + " " + day + "th " + year;

                if (mTimeRangeTypeRadioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Please select dateType",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // get selected radio button from radioGroup
                    int selectedId = mTimeRangeTypeRadioGroup.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    String dateType = selectedRadioButton.getText()
                            .toString();

                    if (dateType.equals(getString(R.string.single_day))) {
                        mStartDateTextView.setText(dateShow);
                        mStartDate = date;
                    } else if (dateType.equals(getString(R.string.several_days))) {
                        mEndDateTextView.setText(dateShow);
                        mEndDate = date;
                    }
                }
            }
        });
    }

    /**
     * Setup the dropdown spinner that allows the user to select of the graph.
     */
    private void setupSpinner() {
        
        /*
          Time Range Spinner
         */
        
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter timeRangeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_time_range_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        timeRangeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mTimeRangeSpinner.setAdapter(timeRangeSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mTimeRangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.time_range_today))) {
                        mTimeRange = GraphEntry.TIME_RANGE_TODAY;
                    } else if (selection.equals(getString(R.string.time_range_week))) {
                        mTimeRange = GraphEntry.TIME_RANGE_WEEK;
                    } else if (selection.equals(getString(R.string.time_range_month))) {
                        mTimeRange = GraphEntry.TIME_RANGE_MONTH;
                    } else {
                        mTimeRange = GraphEntry.TIME_RANGE_YEAR;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mTimeRange = GraphEntry.TIME_RANGE_TODAY;
            }
        });

        
        /*
          Graph Type Spinner
         */
        
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter graphTypeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_graph_type_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        graphTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mTypeSpinner.setAdapter(graphTypeSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.type_bar))) {
                        mType = GraphEntry.BAR_GRAPH;
                    } else if (selection.equals(getString(R.string.type_points))) {
                        mType = GraphEntry.POINTS_GRAPH;
                    } else {
                        mType = GraphEntry.LINE_GRAPH;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mType = GraphEntry.TIME_RANGE_TODAY;
            }
        });
        
                
        /*
          Graph Stats Spinner
         */

        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter StatsSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_graph_stats_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        StatsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mStatsSpinner.setAdapter(StatsSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mStatsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.stats_bpm))) {
                        mStats = GraphEntry.STATS_BPM;
                    } else if (selection.equals(getString(R.string.stats_steps))) {
                        mStats = GraphEntry.STATS_STEPS;
                    } else if (selection.equals(getString(R.string.stats_caloric))) {
                        mStats = GraphEntry.STATS_CALORIC;
                    } else {
                        mStats = GraphEntry.STATS_BPM;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mStats = GraphEntry.STATS_BPM;
            }
        });

        
        /*
          Graph Color Spinner
         */

        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter colorSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_graph_color_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        colorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mColorSpinner.setAdapter(colorSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.color_black))) {
                        mColor = GraphEntry.COLOR_BLACK;
                    } else if (selection.equals(getString(R.string.color_blue))) {
                        mColor = GraphEntry.COLOR_BLUE;
                    } else if (selection.equals(getString(R.string.color_cyan))) {
                        mColor = GraphEntry.COLOR_CYAN;
                    } else if (selection.equals(getString(R.string.color_gray))) {
                        mColor = GraphEntry.COLOR_GRAY;
                    } else if (selection.equals(getString(R.string.color_green))) {
                        mColor = GraphEntry.COLOR_GREEN;
                    } else if (selection.equals(getString(R.string.color_red))) {
                        mColor = GraphEntry.COLOR_RED;
                    } else if (selection.equals(getString(R.string.color_yellow))) {
                        mColor = GraphEntry.COLOR_YELLOW;
                    } else {
                        mColor = GraphEntry.COLOR_BLACK;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mColor = GraphEntry.COLOR_BLACK;
            }
        });



        /*
          Graph2 Type Spinner
         */

        // Apply the adapter to the spinner
        mType2Spinner.setAdapter(graphTypeSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mType2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.type_bar))) {
                        mType2 = GraphEntry.BAR_GRAPH;
                    } else if (selection.equals(getString(R.string.type_points))) {
                        mType2 = GraphEntry.POINTS_GRAPH;
                    } else {
                        mType2 = GraphEntry.LINE_GRAPH;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mType2 = GraphEntry.TIME_RANGE_TODAY;
            }
        });


        /*
          Graph2 Stats Spinner
         */

        // Apply the adapter to the spinner
        mStats2Spinner.setAdapter(StatsSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mStats2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.stats_bpm))) {
                        mStats2 = GraphEntry.STATS_BPM;
                    } else if (selection.equals(getString(R.string.stats_steps))) {
                        mStats2 = GraphEntry.STATS_STEPS;
                    } else if (selection.equals(getString(R.string.stats_caloric))) {
                        mStats2 = GraphEntry.STATS_CALORIC;
                    } else {
                        mStats2 = GraphEntry.STATS_BPM;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mStats2 = GraphEntry.STATS_BPM;
            }
        });


        /*
          Graph2 Color Spinner
         */

        // Apply the adapter to the spinner
        mColor2Spinner.setAdapter(colorSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mColor2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.color_black))) {
                        mColor2 = GraphEntry.COLOR_BLACK;
                    } else if (selection.equals(getString(R.string.color_blue))) {
                        mColor2 = GraphEntry.COLOR_BLUE;
                    } else if (selection.equals(getString(R.string.color_cyan))) {
                        mColor2 = GraphEntry.COLOR_CYAN;
                    } else if (selection.equals(getString(R.string.color_gray))) {
                        mColor = GraphEntry.COLOR_GRAY;
                    } else if (selection.equals(getString(R.string.color_green))) {
                        mColor2 = GraphEntry.COLOR_GREEN;
                    } else if (selection.equals(getString(R.string.color_red))) {
                        mColor2 = GraphEntry.COLOR_RED;
                    } else if (selection.equals(getString(R.string.color_yellow))) {
                        mColor2 = GraphEntry.COLOR_YELLOW;
                    } else {
                        mColor2 = GraphEntry.COLOR_BLACK;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mColor2 = GraphEntry.COLOR_BLACK;
            }
        });

    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the graph.
                deleteGraph();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the graph.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the graph in the database.
     */
    private void deleteGraph() {

        // Only perform the delete if this is an existing graph.
        if (mCurrentGraphUri != null) {
            int rowsDeleted = getContentResolver().delete(
                    mCurrentGraphUri, null, null);
            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_graph_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_graph_successful),
                        Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

    /**
     * Get user input from editor and save new graph into database.
     */
    private void saveGraph() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String TitleString = mTitleEditText.getText().toString().trim();
        String StartDateString = mStartDate;
        String EndDateString = mEndDate;

        if (mCurrentGraphUri == null &&
                mColor == 0 && mTimeRange == 0 && mType == 0 && mStats == 0
                && TextUtils.isEmpty(TitleString)) {return;}

        // Create a ContentValues object where column names are the keys,
        // and graph attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(GraphEntry.COLUMN_GRAPH_TIME_RANGE, mTimeRange);
        values.put(GraphEntry.COLUMN_GRAPH_TYPE, mType);
        values.put(GraphEntry.COLUMN_GRAPH_STATS, mStats);
        values.put(GraphEntry.COLUMN_GRAPH_COLORS, mColor);
        values.put(GraphEntry.COLUMN_GRAPH_TITLE, TitleString);

        values.put(GraphEntry.COLUMN_GRAPH_TIME_RANGE_TYPE, mTimeRangeType);
        values.put(GraphEntry.COLUMN_GRAPH2_TYPE, mType2);
        values.put(GraphEntry.COLUMN_GRAPH2_STATS, mStats2);
        values.put(GraphEntry.COLUMN_GRAPH2_COLORS, mColor2);
        values.put(GraphEntry.COLUMN_NUMBER_OF_GRAPH, mNumberOfGraph);
        values.put(GraphEntry.COLUMN_GRAPH_START_TIME, StartDateString);
        values.put(GraphEntry.COLUMN_GRAPH_END_TIME, EndDateString);

        if (mCurrentGraphUri == null) {

            // Insert a new graph into the provider, returning the content URI for the new graph.
            Uri newUri = getContentResolver().insert(GraphEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_graph_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_graph_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING graph, so update the graph with content URI: mCurrentGraphUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentGraphUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(
                    mCurrentGraphUri, values, null, null);
            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.editor_update_graph_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_update_graph_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new graph, hide the "Delete" menu item.
        if (mCurrentGraphUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_graph_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save graph to database
                saveGraph();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the graph hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!mGraphHasChanged) {
                    NavUtils.navigateUpFromSameTask(GraphEditorActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(GraphEditorActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Since the editor shows all graph attributes, define a projection that contains
        // all columns from the graph table
        String[] projection = {
                GraphEntry._ID,
                GraphEntry.COLUMN_GRAPH_TIME_RANGE,
                GraphEntry.COLUMN_GRAPH_TITLE,
                GraphEntry.COLUMN_GRAPH_TYPE,
                GraphEntry.COLUMN_GRAPH_STATS,
                GraphEntry.COLUMN_GRAPH_COLORS,
                GraphEntry.COLUMN_GRAPH_TIME_RANGE_TYPE,
                GraphEntry.COLUMN_GRAPH2_COLORS,
                GraphEntry.COLUMN_GRAPH2_STATS,
                GraphEntry.COLUMN_GRAPH2_TYPE,
                GraphEntry.COLUMN_NUMBER_OF_GRAPH,
                GraphEntry.COLUMN_GRAPH_START_TIME,
                GraphEntry.COLUMN_GRAPH_END_TIME};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentGraphUri,         // Query the content URI for the current graph
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of graph attributes that we're interested in
            int titleColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_TITLE);
            int timeRangeColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_TIME_RANGE);
            int typeColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_TYPE);
            int statsColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_STATS);
            int colorColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_COLORS);

            int numGraphColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_NUMBER_OF_GRAPH);
            int type2ColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH2_TYPE);
            int stats2ColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH2_STATS);
            int color2ColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH2_COLORS);
            int timeRangeTypeColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_TIME_RANGE_TYPE);
            int startTimeColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_START_TIME);
            int endTimeColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_END_TIME);

            int timeRange = cursor.getInt(timeRangeColumnIndex);
            int type = cursor.getInt(typeColumnIndex);
            int stats = cursor.getInt(statsColumnIndex);
            int color = cursor.getInt(colorColumnIndex);
            String title = cursor.getString(titleColumnIndex);

            int numGraph = cursor.getInt(numGraphColumnIndex);
            int type2 = cursor.getInt(type2ColumnIndex);
            int stats2 = cursor.getInt(stats2ColumnIndex);
            int color2 = cursor.getInt(color2ColumnIndex);
            int timeRangeType = cursor.getInt(timeRangeTypeColumnIndex);
            String startTime = cursor.getString(startTimeColumnIndex);
            String endTime = cursor.getString(endTimeColumnIndex);

            // Update the views on the screen with the values from the database
            mTitleEditText.setText(title);

            mStartDateTextView.setText(startTime);
            mEndDateTextView.setText(endTime);
            mNumberOfGraph = numGraph;

            switch (numGraph) {
                case GraphEntry.GRAPH_NUMBER_ONE:
                    mSecondGraphSwitch.setChecked(false);
                    break;
                case GraphEntry.GRAPH_NUMBER_TWO:
                    mSecondGraphSwitch.setChecked(true);
                    mSecondGraphLinearLayout.setVisibility(View.VISIBLE);
                    break;
                default:
                    mSecondGraphSwitch.setChecked(false);
                    break;
            }

            switch (timeRangeType) {
                case GraphEntry.TIME_RANGE_TYPE_SINGLE:
                    ((RadioButton)mTimeRangeTypeRadioGroup.getChildAt(0)).setChecked(true);
                    break;
                case GraphEntry.TIME_RANGE_TYPE_SEVERAL:
                    ((RadioButton)mTimeRangeTypeRadioGroup.getChildAt(1)).setChecked(true);
                    break;
                case GraphEntry.TIME_RANGE_TYPE_RELATIVE:
                    ((RadioButton)mTimeRangeTypeRadioGroup.getChildAt(2)).setChecked(true);
                    break;
                default:
                    ((RadioButton)mTimeRangeTypeRadioGroup.getChildAt(0)).setChecked(true);
                    break;
            }

            switch (timeRange) {
                case GraphEntry.TIME_RANGE_TODAY:
                    mTimeRangeSpinner.setSelection(0);
                    break;
                case GraphEntry.TIME_RANGE_WEEK:
                    mTimeRangeSpinner.setSelection(1);
                    break;
                case GraphEntry.TIME_RANGE_MONTH:
                    mTimeRangeSpinner.setSelection(2);
                    break;
                case GraphEntry.TIME_RANGE_YEAR:
                    mTimeRangeSpinner.setSelection(3);
                    break;
                default:
                    mTimeRangeSpinner.setSelection(0);
                    break;
            }


            switch (type2) {
                case GraphEntry.BAR_GRAPH:
                    mType2Spinner.setSelection(0);
                    break;
                case GraphEntry.POINTS_GRAPH:
                    mType2Spinner.setSelection(1);
                    break;
                case GraphEntry.LINE_GRAPH:
                    mType2Spinner.setSelection(2);
                    break;
                default:
                    mType2Spinner.setSelection(0);
                    break;
            }


            switch (stats2) {
                case GraphEntry.STATS_BPM:
                    mStats2Spinner.setSelection(0);
                    break;
                case GraphEntry.STATS_STEPS:
                    mStats2Spinner.setSelection(1);
                    break;
                case GraphEntry.STATS_CALORIC:
                    mStats2Spinner.setSelection(2);
                    break;
                default:
                    mStats2Spinner.setSelection(0);
                    break;
            }


            switch (color2) {
                case GraphEntry.COLOR_BLACK:
                    mColor2Spinner.setSelection(0);
                    break;
                case GraphEntry.COLOR_BLUE:
                    mColor2Spinner.setSelection(1);
                    break;
                case GraphEntry.COLOR_CYAN:
                    mColor2Spinner.setSelection(2);
                    break;
                case GraphEntry.COLOR_GRAY:
                    mColor2Spinner.setSelection(3);
                    break;
                case GraphEntry.COLOR_GREEN:
                    mColor2Spinner.setSelection(4);
                    break;
                case GraphEntry.COLOR_RED:
                    mColor2Spinner.setSelection(5);
                    break;
                case GraphEntry.COLOR_YELLOW:
                    mColor2Spinner.setSelection(6);
                    break;
                default:
                    mColor2Spinner.setSelection(0);
                    break;
            }


            switch (type) {
                case GraphEntry.BAR_GRAPH:
                    mTypeSpinner.setSelection(0);
                    break;
                case GraphEntry.POINTS_GRAPH:
                    mTypeSpinner.setSelection(1);
                    break;
                case GraphEntry.LINE_GRAPH:
                    mTypeSpinner.setSelection(2);
                    break;
                default:
                    mTypeSpinner.setSelection(0);
                    break;
            }


            switch (stats) {
                case GraphEntry.STATS_BPM:
                    mStatsSpinner.setSelection(0);
                    break;
                case GraphEntry.STATS_STEPS:
                    mStatsSpinner.setSelection(1);
                    break;
                case GraphEntry.STATS_CALORIC:
                    mStatsSpinner.setSelection(2);
                    break;
                default:
                    mStatsSpinner.setSelection(0);
                    break;
            }


            switch (color) {
                case GraphEntry.COLOR_BLACK:
                    mColorSpinner.setSelection(0);
                    break;
                case GraphEntry.COLOR_BLUE:
                    mColorSpinner.setSelection(1);
                    break;
                case GraphEntry.COLOR_CYAN:
                    mColorSpinner.setSelection(2);
                    break;
                case GraphEntry.COLOR_GRAY:
                    mColorSpinner.setSelection(3);
                    break;
                case GraphEntry.COLOR_GREEN:
                    mColorSpinner.setSelection(4);
                    break;
                case GraphEntry.COLOR_RED:
                    mColorSpinner.setSelection(5);
                    break;
                case GraphEntry.COLOR_YELLOW:
                    mColorSpinner.setSelection(6);
                    break;
                default:
                    mColorSpinner.setSelection(0);
                    break;
            }


        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        mTitleEditText.setText("");
        mTimeRangeSpinner.setSelection(0);
        mTypeSpinner.setSelection(0);
        mStatsSpinner.setSelection(0);
        mColorSpinner.setSelection(0);

        ((RadioButton)mTimeRangeTypeRadioGroup.getChildAt(0)).setChecked(true);
        mType2Spinner.setSelection(0);
        mStats2Spinner.setSelection(0);
        mColor2Spinner.setSelection(0);
        mSecondGraphSwitch.setChecked(false);
        mStartDateTextView.setText("N/A");
        mStartDateTextView.setText("N/A");
    }

    /**
     * This method is called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        // If the graph hasn't changed, continue with handling back button press
        if (!mGraphHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {

        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the graph.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void secondGraphClicked(View view) {

        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);

        //code to check if this checkbox is checked!
        Switch secondGraphSwitch = (Switch)view;
        if(secondGraphSwitch.isChecked()){
            mSecondGraphLinearLayout.setVisibility(View.VISIBLE);
            mNumberOfGraph = GraphEntry.GRAPH_NUMBER_TWO;
            mSecondGraphLinearLayout.startAnimation(slideUp);
        } else {
            mSecondGraphLinearLayout.setVisibility(View.INVISIBLE);
            mNumberOfGraph = GraphEntry.GRAPH_NUMBER_ONE;
            mSecondGraphLinearLayout.startAnimation(slideDown);
        }
    }
}