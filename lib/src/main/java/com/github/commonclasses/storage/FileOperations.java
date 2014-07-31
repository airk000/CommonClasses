/*
 * This source is part of the CommonClasses repository.
 *
 * Copyright 2014 Kevin Liu (airk908@gmail.com)
 *
 * CommonClasses is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CommonClasses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CommonClasses.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.commonclasses.storage;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import com.github.commonclasses.logwrapper.LogWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * File Operations Utils
 * <p/>
 * External storage in this class means application's private files, while application
 * is removed, these files will be deleted even though they are in the external storage
 * <p/>
 * Permission Needed
 * <p/>
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 */
public class FileOperations {

    /**
     * Check if external storage of this mobile is available
     *
     * @return true or false
     */
    public static boolean isExternalStorageAvailable() {
        boolean available = Environment.getExternalStorageState()
                .equalsIgnoreCase(Environment.MEDIA_MOUNTED);
        if (available) {
            LogWrapper.d("External storage is available!!!");
        }
        return available;
    }

    /**
     * Check if external storage of the mobile is read only
     *
     * @return true for read only
     */
    public static boolean isExternalStorageOnlyReadable() {
        String state = Environment.getExternalStorageState();
        boolean readOnly = state.equalsIgnoreCase(Environment.MEDIA_MOUNTED_READ_ONLY);
        if (readOnly) {
            LogWrapper.e("External storage is read only!");
        }
        return readOnly;
    }

    /**
     * Get the OutputStream of a internal storage file for writing
     *
     * @param context  Context
     * @param filename Filename you want it in internal storage
     * @return OutputStream of this file
     * @throws FileNotFoundException
     */
    public static OutputStream writeToInternalStorage(Context context, String filename) throws FileNotFoundException {
        return context.openFileOutput(filename, Context.MODE_PRIVATE);
    }

    /**
     * Get a file object in internal storage, will be created if it doesn't exist.
     *
     * @param context  Context
     * @param filename Filename you want get(create) in internal storage
     * @return the file object
     */
    public static File getInternalStorageFile(Context context, String filename) {
        return new File(context.getFilesDir(), filename);
    }

    /**
     * Get the InputStream of a internal storage file for reading
     *
     * @param context  Context
     * @param filename the filename you wanna read in internal storage
     * @return the InputStream of this file
     * @throws FileNotFoundException
     */
    public static InputStream readFromInternalStorage(Context context, String filename) throws FileNotFoundException {
        return context.openFileInput(filename);
    }

    /**
     * Delete a file in internal storage
     *
     * @param context  Context
     * @param filename the file's name you wanna delete it
     * @return true for success
     */
    public static boolean deleteInternalStorageFile(Context context, String filename) {
        if (context.fileList() != null && context.fileList().length > 0) {
            for (String file : context.fileList()) {
                if (file.equals(filename)) {
                    return context.deleteFile(filename);
                }
            }
        }
        return false;
    }

    /**
     * Get a file object in external storage, will be created if it doesn't exist
     *
     * @param context  Context
     * @param filename Filename you want get(create) in external storage
     * @return file object
     */
    public static File getExternalStorageFile(Context context, String filename) {
        return new File(context.getExternalFilesDir(null), filename);
    }

    /**
     * Delete a file in external storage
     *
     * @param context  Context
     * @param filename Filename you want delete in external storage
     * @return true for success
     */
    public static boolean deleteExternalStorageFile(Context context, String filename) {
        File file = new File(context.getExternalFilesDir(null), filename);
        return file.exists() && file.delete();
    }


    /**
     * Gets the content:// URI  from the given corresponding path to a file
     *
     * @param context Context
     * @param file    File Object
     * @return content Uri
     */
    public static Uri getUriFromFile(Context context, File file) {
        String filePath = file.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (file.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * Gets the corresponding path to a file from the given content:// URI
     *
     * @param uri             The content:// URI to find the file path from
     * @param contentResolver The content resolver to use to perform the query.
     * @return the file path as a string
     */
    public static String getFilePathFromUri(Uri uri,
                                            ContentResolver contentResolver) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};

        Cursor cursor = contentResolver.query(uri, filePathColumn, null, null, null);

        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }
}
