package com.ruthbeeler.possibleapplication;

public class SharedPreferences {
    private final SharedPreferences SharedPreferences;

    private final String KEY = "input";

    public SharedPreferences(SharedPreferences sharedPreferences) {
        SharedPreferences = sharedPreferences;
    }
    /*
    public boolean saveEntry(String entry) {
        SharedPreferences.Editor mockEditor = SharedPreferences.edit();
        mockEditor.putString(KEY, entry);
        return mockEditor.commit();
    }

    public String getEntry() {
        return SharedPreferences.getString(KEY, "");
    }
}
*/

}
