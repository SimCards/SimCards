package io.github.simcards.simcards.client.graphics;

import android.content.res.Resources;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Retrieves resources, distinguishing between Android and desktop environments.
 */
public class ResourceUtil {

    /** The resources for the app. */
    public static Resources resources;

    /**
     * Reads a text resource into a stream.
     * @param id The text resource to read.
     * @return An InputStream with the contents of the text resource.
     * @throws FileNotFoundException If the resource does not exist.
     */
    public static InputStream openRawResource(int id) throws FileNotFoundException {
            return resources.openRawResource(id);
    }
}
