package android.seoulaccord.beacon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by seowok on 2017-05-10.
 */

public class DatabaseConnection {
    /** Database version */
    private static final int DATABASE_VERSION = 2;

    /** Database file name */
    private static final String DATABASE_NAME = "beacons.db";

    private interface Tables {
        /** Sensors table. See {@link BeaconData.Beacon} for column names */
        public static final String REGIONS = "beacons";
    }

    private static final String ID_SELECTION = BeaconData.Beacon._ID + "=?";
    private static final String BEACON_PARAMS_SELECTION = BeaconData.Beacon.UUID + "=? AND " + BeaconData.Beacon.MAJOR + "=? AND " + BeaconData.Beacon.MINOR + "=?";
    private static final String[] BEACON_PROJECTION = new String[] { BeaconData.Beacon._ID, BeaconData.Beacon.NAME, BeaconData.Beacon.UUID, BeaconData.Beacon.MAJOR,
            BeaconData.Beacon.MINOR, BeaconData.Beacon.SIGNAL_STRENGTH, BeaconData.Beacon.EVENT, BeaconData.Beacon.ACTION, BeaconData.Beacon.ACTION_PARAM,
            BeaconData.Beacon.ENABLED };

    private static SQLiteHelper mDatabaseHelper;
    private static SQLiteDatabase mDatabase;
    private String[] mSingleArg = new String[1];
    private String[] mParamsArg = new String[3];

    public DatabaseConnection(final Context context) {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = new SQLiteHelper(context);
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
    }

    /**
     * Adds the new beacon to the database
     *
     * @param beacon
     *            the beacon object
     * @param name
     *            the sensor name
     * @param event
     *            the event that will trigger the action
     * @param action
     *            the action id
     * @param actionParam
     *            optional action parameter, may be <code>null</code>
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long addRegion(final Beacon beacon, final String name, final int event, final int action, final String actionParam) {
        final ContentValues values = new ContentValues();
        values.put(BeaconData.Beacon.NAME, name);
        values.put(BeaconData.Beacon.UUID, beacon.getUuid().toString());
        values.put(BeaconData.Beacon.MAJOR, beacon.getMajor());
        values.put(BeaconData.Beacon.MINOR, beacon.getMinor());
        values.put(BeaconData.Beacon.EVENT, event);
        values.put(BeaconData.Beacon.ACTION, action);
        values.put(BeaconData.Beacon.ACTION_PARAM, actionParam);

        return mDatabase.insert(Tables.REGIONS, null, values);
    }

    /**
     * Removes the region with given ID
     *
     * @param id
     *            the region id in the database
     */
    public void deleteRegion(final long id) {
        mSingleArg[0] = String.valueOf(id);

        mDatabase.delete(Tables.REGIONS, ID_SELECTION, mSingleArg);
    }

    /**
     * Sets the signal strength to 0 for all beacons. Use when exiting from application.
     *
     * @return the number of rows affected
     */
    public int resetSignalStrength() {
        final ContentValues values = new ContentValues();
        values.put(BeaconData.Beacon.SIGNAL_STRENGTH, 0);

        return mDatabase.update(Tables.REGIONS, values, null, null);
    }

    /**
     * Updates the signal strength of a most recent beacon in the region
     *
     * @param id
     *            the region id in the database
     * @param accuracy
     *            the signal strength as a accuracy (distance in meters)
     * @return number of rows affected
     */
    public int updateRegionSignalStrength(final long id, final int accuracy) {
        mSingleArg[0] = String.valueOf(id);

        final ContentValues values = new ContentValues();
        values.put(BeaconData.Beacon.SIGNAL_STRENGTH, accuracy);

        return mDatabase.update(Tables.REGIONS, values, ID_SELECTION, mSingleArg);
    }

    public int updateRegionName(final long id, final String name) {
        mSingleArg[0] = String.valueOf(id);

        final ContentValues values = new ContentValues();
        values.put(BeaconData.Beacon.NAME, name);

        return mDatabase.update(Tables.REGIONS, values, ID_SELECTION, mSingleArg);
    }

    public int updateRegionEvent(final long id, final int event) {
        mSingleArg[0] = String.valueOf(id);

        final ContentValues values = new ContentValues();
        values.put(BeaconData.Beacon.EVENT, event);

        return mDatabase.update(Tables.REGIONS, values, ID_SELECTION, mSingleArg);
    }

    public int updateRegionAction(final long id, final int action) {
        mSingleArg[0] = String.valueOf(id);

        final ContentValues values = new ContentValues();
        values.put(BeaconData.Beacon.ACTION, action);

        return mDatabase.update(Tables.REGIONS, values, ID_SELECTION, mSingleArg);
    }

    public int updateRegionActionParam(final long id, final String param) {
        mSingleArg[0] = String.valueOf(id);

        final ContentValues values = new ContentValues();
        values.put(BeaconData.Beacon.ACTION_PARAM, param);

        return mDatabase.update(Tables.REGIONS, values, ID_SELECTION, mSingleArg);
    }

    public int setRegionEnabled(final long id, final boolean enabled) {
        mSingleArg[0] = String.valueOf(id);

        final ContentValues values = new ContentValues();
        values.put(BeaconData.Beacon.ENABLED, enabled ? 1 : 0);

        return mDatabase.update(Tables.REGIONS, values, ID_SELECTION, mSingleArg);
    }

    /**
     * Searches for a region by matching beacon in the database.
     *
     * @param beacon
     *            the beacon
     * @return cursor with the result
     */
    public Cursor findRegionByBeacon(final Beacon beacon) {
        mParamsArg[0] = beacon.getUuid().toString();
        mParamsArg[1] = String.valueOf(beacon.getMajor());
        mParamsArg[2] = String.valueOf(beacon.getMinor());

        return mDatabase.query(Tables.REGIONS, BEACON_PROJECTION, BEACON_PARAMS_SELECTION, mParamsArg, null, null, null);
    }

    /**
     * Returns the data of a region with specified id.
     *
     * @param id
     *            the region id
     * @return cursor with the result
     */
    public Cursor getRegion(final long id) {
        mSingleArg[0] = String.valueOf(id);

        return mDatabase.query(Tables.REGIONS, BEACON_PROJECTION, ID_SELECTION, mSingleArg, null, null, null);
    }

    public Cursor getAllRegions() {
        return mDatabase.query(Tables.REGIONS, BEACON_PROJECTION, null, null, null, null, null);
    }

    private class SQLiteHelper extends SQLiteOpenHelper {
        /**
         * The SQL code that creates the beacons table:
         *
         * <pre>
         * --------------------------------------------------------------------------------------------------------------------------------------------------------------------
         * |                                                              regions                                                                                             |
         * --------------------------------------------------------------------------------------------------------------------------------------------------------------------
         * | _id (int, pk, auto increment) | name (text) | uuid (text) | major (int) | minor (int) | signal_strength (int) | event (int) | action (int) | action_param (text) |
         * --------------------------------------------------------------------------------------------------------------------------------------------------------------------
         * </pre>
         */
        private static final String CREATE_BEACONS = "CREATE TABLE " + Tables.REGIONS + "(" + BeaconData.Beacon._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BeaconData.Beacon.NAME
                + " TEXT NOT NULL, " + BeaconData.Beacon.UUID + " TEXT NOT NULL, " + BeaconData.Beacon.MAJOR + " INTEGER NOT NULL, " + BeaconData.Beacon.MINOR + " INTEGER NOT NULL, "
                + BeaconData.Beacon.SIGNAL_STRENGTH + " INTEGER, " + BeaconData.Beacon.EVENT + " INTEGER NOT NULL, " + BeaconData.Beacon.ACTION + " INTEGER NOT NULL, "
                + BeaconData.Beacon.ACTION_PARAM + " TEXT, " + BeaconData.Beacon.ENABLED + " INTEGER NOT NULL DEFAULT(1));";

        public SQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(final SQLiteDatabase db) {
            db.execSQL(CREATE_BEACONS);
        }

        private static final String ALTER_REGIONS_ADD_ENABLED = "ALTER TABLE " + Tables.REGIONS + " ADD COLUMN " + BeaconData.Beacon.ENABLED + " INTEGER NOT NULL DEFAULT(1)";

        @Override
        public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
            switch (oldVersion) {
                case 1:
                    db.execSQL(ALTER_REGIONS_ADD_ENABLED);
                    break;
            }
            //			db.execSQL("DROP TABLE IF EXISTS " + Tables.REGIONS);
            //			onCreate(db);
        }

    }
}
