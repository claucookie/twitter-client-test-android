package es.claucookie.twitterclient.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by claucookie on 05/10/15.
 */
public class FontFactory {
    private static FontFactory instance = new FontFactory();
    private Map<String, Typeface> fontCache = new HashMap<String, Typeface>();

    private FontFactory() {

    }

    public static FontFactory getInstance() {
        return instance;
    }

    public synchronized Typeface getTypeface(Context context, String fontName) {

        if (!fontCache.containsKey(fontName)) {
            AssetManager manager = context.getAssets();

            String fontNameWithExtension = new String();
            try {
                String fileList[] = manager.list("fonts");
                for (String fileName : fileList) {
                    if (fontName.equals(fileName.substring(0, fileName.lastIndexOf('.')))) {
                        // File found
                        fontNameWithExtension = fileName;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Typeface tf;
            if (fontNameWithExtension.length() > 0) {
                tf = Typeface.createFromAsset(manager, "fonts/" + fontNameWithExtension);
            } else {
                // Font not found
                tf = Typeface.DEFAULT;
            }
            fontCache.put(fontName, tf);
        }
        return fontCache.get(fontName);
    }
}
