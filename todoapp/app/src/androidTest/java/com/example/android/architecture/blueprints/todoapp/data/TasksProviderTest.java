package com.example.android.architecture.blueprints.todoapp.data;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;

import com.example.android.architecture.blueprints.todoapp.data.source.TasksProvider;
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksPersistenceContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TasksProviderTest extends AndroidTestCase {

    @Before
    public void setUp() throws Exception {
        setContext(InstrumentationRegistry.getTargetContext());
        super.setUp();
    }

    /*
    This test checks to make sure that the content provider is registered correctly.
    */
    @Test
    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        // We define the component name based on the package name from the context and the
        // TasksPersistenceContract class.
        ComponentName componentName = new ComponentName(mContext.getPackageName(), TasksProvider.class.getName());
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: TasksProvider registered with authority: " + providerInfo.authority +
                                 " instead of authority: " + TasksPersistenceContract.CONTENT_AUTHORITY,
                         providerInfo.authority, TasksPersistenceContract.CONTENT_AUTHORITY
            );
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: TasksProvider not registered at " + mContext.getPackageName(), false);
        }
    }

    /*
         This test doesn't touch the database.  It verifies that the ContentProvider returns
         the correct type for each type of URI that it can handle.
      */
    @Test
    public void testGetType() {
        // content://com.example.android.architecture.blueprints.todoapp/task
        String type = mContext.getContentResolver().getType(TasksPersistenceContract.TaskEntry.buildTasksUri());
        // vnd.android.cursor.dir/com.example.android.architecture.blueprints.todoapp/task
        assertEquals("Error: the TaskEntry CONTENT_URI should return TasksPersistenceContract.CONTENT_TASK_TYPE",
                     TasksPersistenceContract.CONTENT_TASK_TYPE, type
        );

        long testTask = 1L;
        // content://com.example.android.architecture.blueprints.todoapp/task/1
        type = mContext.getContentResolver().getType(
                TasksPersistenceContract.TaskEntry.buildTasksUriWith(testTask));
        // vnd.android.cursor.item/com.example.android.sunshine.app/weather
        assertEquals("Error: the WeatherEntry CONTENT_URI with location should return TasksPersistenceContract.CONTENT_TASK_ITEM_TYPE",
                     TasksPersistenceContract.CONTENT_TASK_ITEM_TYPE, type
        );
    }

}
