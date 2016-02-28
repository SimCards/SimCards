package io.github.simcards.simcards.client.graphics;

import android.content.res.Resources;

import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import io.github.simcards.simcards.R;

/**
 * Retrieves resources, distinguishing between Android and desktop environments.
 */
public class ResourceUtil {

    /** The resources for the app. */
    public static Resources resources;
    /** The path to the resources folder. */
    private static final String resourcePath = "app/src/main/res/";

    /**
     * Reads a text resource into a stream.
     * @param id The text resource to read.
     * @return An InputStream with the contents of the text resource.
     * @throws FileNotFoundException If the resource does not exist.
     */
    public static InputStream openRawResource(int id) throws FileNotFoundException {
        if (resources == null) {
            File initialFile = new File(getResourcePath(id));
            return new FileInputStream(initialFile);
        } else {
            return resources.openRawResource(id);
        }
    }

    /**
     * Loads a texture from a resource.
     * @param resourceId The image resource to load.
     * @return The loaded image texture.
     * @throws GLException
     * @throws IOException
     */
    public static TextureData loadTexture(int resourceId) throws GLException, IOException {
        return TextureIO.newTextureData(GLProfile.get(GLProfile.GL2ES2), openRawResource(resourceId), false, null);
    }

    /**
     * Gets the path to a resource from its resource ID.
     * @param id The ID of the resource.
     * @return The path of the resource with the given ID.
     */
    private static String getResourcePath(int id) {
        return resourcePath + getResourceName(id);
    }

    /**
     * Gets the name of a resource from its resource ID.
     * @param id The ID of the resource.
     * @return The name of the resource with the given ID.
     */
    private static String getResourceName(int id) {
        switch(id) {
            case R.drawable.ace_of_clubs: return "drawable/ace_of_clubs.png";
            case R.drawable.ace_of_diamonds: return "drawable/ace_of_diamonds.png";
            case R.drawable.ace_of_hearts: return "drawable/ace_of_hearts.png";
            case R.drawable.ace_of_spades: return "drawable/ace_of_spades.png";
            case R.drawable.black_joker: return "drawable/black_joker.png";
            case R.drawable.eight_of_clubs: return "drawable/eight_of_clubs.png";
            case R.drawable.eight_of_diamonds: return "drawable/eight_of_diamonds.png";
            case R.drawable.eight_of_hearts: return "drawable/eight_of_hearts.png";
            case R.drawable.eight_of_spades: return "drawable/eight_of_spades.png";
            case R.drawable.face_down: return "drawable/face_down.png";
            case R.drawable.five_of_clubs: return "drawable/five_of_clubs.png";
            case R.drawable.five_of_diamonds: return "drawable/five_of_diamonds.png";
            case R.drawable.five_of_hearts: return "drawable/five_of_hearts.png";
            case R.drawable.five_of_spades: return "drawable/five_of_spades.png";
            case R.drawable.four_of_clubs: return "drawable/four_of_clubs.png";
            case R.drawable.four_of_diamonds: return "drawable/four_of_diamonds.png";
            case R.drawable.four_of_hearts: return "drawable/four_of_hearts.png";
            case R.drawable.four_of_spades: return "drawable/four_of_spades.png";
            case R.drawable.jack_of_clubs: return "drawable/jack_of_clubs.png";
            case R.drawable.jack_of_diamonds: return "drawable/jack_of_diamonds.png";
            case R.drawable.jack_of_hearts: return "drawable/jack_of_hearts.png";
            case R.drawable.jack_of_spades: return "drawable/jack_of_spades.png";
            case R.drawable.king_of_clubs: return "drawable/king_of_clubs.png";
            case R.drawable.king_of_diamonds: return "drawable/king_of_diamonds.png";
            case R.drawable.king_of_hearts: return "drawable/king_of_hearts.png";
            case R.drawable.king_of_spades: return "drawable/king_of_spades.png";
            case R.drawable.nine_of_clubs: return "drawable/nine_of_clubs.png";
            case R.drawable.nine_of_diamonds: return "drawable/nine_of_diamonds.png";
            case R.drawable.nine_of_hearts: return "drawable/nine_of_hearts.png";
            case R.drawable.nine_of_spades: return "drawable/nine_of_spades.png";
            case R.drawable.queen_of_clubs: return "drawable/queen_of_clubs.png";
            case R.drawable.queen_of_diamonds: return "drawable/queen_of_diamonds.png";
            case R.drawable.queen_of_hearts: return "drawable/queen_of_hearts.png";
            case R.drawable.queen_of_spades: return "drawable/queen_of_spades.png";
            case R.drawable.red_joker: return "drawable/red_joker.png";
            case R.drawable.seven_of_clubs: return "drawable/seven_of_clubs.png";
            case R.drawable.seven_of_diamonds: return "drawable/seven_of_diamonds.png";
            case R.drawable.seven_of_hearts: return "drawable/seven_of_hearts.png";
            case R.drawable.seven_of_spades: return "drawable/seven_of_spades.png";
            case R.drawable.six_of_clubs: return "drawable/six_of_clubs.png";
            case R.drawable.six_of_diamonds: return "drawable/six_of_diamonds.png";
            case R.drawable.six_of_hearts: return "drawable/six_of_hearts.png";
            case R.drawable.six_of_spades: return "drawable/six_of_spades.png";
            case R.drawable.ten_of_clubs: return "drawable/ten_of_clubs.png";
            case R.drawable.ten_of_diamonds: return "drawable/ten_of_diamonds.png";
            case R.drawable.ten_of_hearts: return "drawable/ten_of_hearts.png";
            case R.drawable.ten_of_spades: return "drawable/ten_of_spades.png";
            case R.drawable.three_of_clubs: return "drawable/three_of_clubs.png";
            case R.drawable.three_of_diamonds: return "drawable/three_of_diamonds.png";
            case R.drawable.three_of_hearts: return "drawable/three_of_hearts.png";
            case R.drawable.three_of_spades: return "drawable/three_of_spades.png";
            case R.drawable.two_of_clubs: return "drawable/two_of_clubs.png";
            case R.drawable.two_of_diamonds: return "drawable/two_of_diamonds.png";
            case R.drawable.two_of_hearts: return "drawable/two_of_hearts.png";
            case R.drawable.two_of_spades: return "drawable/two_of_spades.png";
            case R.drawable.font: return "drawable/font.png";
            case R.raw.default_frag: return "raw/default_frag.frag";
            case R.raw.default_vert: return "raw/default_vert.vert";
            default: return "";
        }
    }
}
