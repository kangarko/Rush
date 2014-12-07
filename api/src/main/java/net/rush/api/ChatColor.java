package net.rush.api;

import java.util.HashMap;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;

/**
 * Color values used to coloring the chat.
 */
public enum ChatColor {
    /**
     * Represents black
     */
    BLACK('0'),
    /**
     * Represents dark blue
     */
    DARK_BLUE('1'),
    /**
     * Represents dark green
     */
    DARK_GREEN('2'),
    /**
     * Represents dark blue (aqua)
     */
    DARK_AQUA('3'),
    /**
     * Represents dark red
     */
    DARK_RED('4'),
    /**
     * Represents dark purple
     */
    DARK_PURPLE('5'),
    /**
     * Represents gold
     */
    GOLD('6'),
    /**
     * Represents gray
     */
    GRAY('7'),
    /**
     * Represents dark gray
     */
    DARK_GRAY('8'),
    /**
     * Represents blue
     */
    BLUE('9'),
    /**
     * Represents green
     */
    GREEN('a'),
    /**
     * Represents aqua
     */
    AQUA('b'),
    /**
     * Represents red
     */
    RED('c'),
    /**
     * Represents light purple
     */
    LIGHT_PURPLE('d'),
    /**
     * Represents yellow
     */
    YELLOW('e'),
    /**
     * Represents white
     */
    WHITE('f'),
    /**
     * Represents characters that change randomly
     */
    MAGIC('k', true),
    /**
     * Makes the text bold
     */
    BOLD('l', true),
    /**
     * Makes a line appear through the text
     */
    STRIKETHROUGH('m', true),
    /**
     * Makes the text underlined.
     */
    UNDERLINE('n', true),
    /**
     * Makes the text italic.
     */
    ITALIC('o', true),
    /**
     * Resets all previous chat colors or formats.
     */
    RESET('r');

    /**
     * The special character which prefixes all chat colour codes. Use this if
     * you need to dynamically convert colour codes from your custom format.
     */
    public static final char COLOR_CHAR = '\u00A7';
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf(COLOR_CHAR) + "[0-9A-FK-OR]");

    private final char code;
    private final boolean isFormat;
    private final static HashMap<Character, ChatColor> BY_CHAR = new HashMap<>();

    private ChatColor(char code) {
        this(code, false);
    }

    private ChatColor(char code, boolean isFormat) {
        this.code = code;
        this.isFormat = isFormat;
    }
    
    @Override
    public String toString() {
	    return new String(new char[] {COLOR_CHAR, code});
    }

    /**
     * Gets the char value associated with this color
     *
     * @return A char value of this color code
     */
    public char getChar() {
        return code;
    }

    /**
     * Checks if this code is a format code as opposed to a color code.
     */
    public boolean isFormat() {
        return isFormat;
    }

    /**
     * Checks if this code is a color code as opposed to a format code.
     */
    public boolean isColor() {
        return !isFormat && this != RESET;
    }

    /**
     * Gets the color represented by the specified color code
     *
     * @param code Code to check
     * @return Associative {@link org.bukkit.ChatColor} with the given code,
     *     or null if it doesn't exist
     */
    public static ChatColor getByChar(char code) {
        return BY_CHAR.get(code);
    }

    /**
     * Gets the color represented by the specified color code
     *
     * @param code Code to check
     * @return Associative {@link org.bukkit.ChatColor} with the given code,
     *     or null if it doesn't exist
     */
    public static ChatColor getByChar(String code) {
        Validate.notNull(code, "Code cannot be null");
        Validate.isTrue(code.length() > 0, "Code must have at least one char");

        return BY_CHAR.get(code.charAt(0));
    }

    /**
     * Strips the given message of all color codes
     *
     * @param input String to strip of color
     * @return A copy of the input string, without any coloring
     */
    public static String stripColor(final String input) {
        if (input == null) {
            return null;
        }

        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

    /**
     * Translates a string using an alternate color code character into a
     * string that uses the internal ChatColor.COLOR_CODE color code
     * character. The alternate color code character will only be replaced if
     * it is immediately followed by 0-9, A-F, a-f, K-O, k-o, R or r.
     *
     * @param altColorChar The alternate color code character to replace. Ex: &
     * @param textToTranslate Text containing the alternate color code character.
     * @return Text containing the ChatColor.COLOR_CODE color code character.
     */
    public static String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i+1]) > -1) {
                b[i] = ChatColor.COLOR_CHAR;
                b[i+1] = Character.toLowerCase(b[i+1]);
            }
        }
        return new String(b);
    }

    /**
     * Gets the ChatColors used at the end of the given input string.
     *
     * @param input Input string to retrieve the colors from.
     * @return Any remaining ChatColors to pass onto the next line.
     */
    public static String getLastColors(String input) {
        String result = "";
        int length = input.length();

        // Search backwards from the end as it is faster
        for (int index = length - 1; index > -1; index--) {
            char section = input.charAt(index);
            if (section == COLOR_CHAR && index < length - 1) {
                char c = input.charAt(index + 1);
                ChatColor color = getByChar(c);

                if (color != null) {
                    result = color.toString() + result;

                    // Once we find a color or reset we can stop searching
                    if (color.isColor() || color.equals(RESET)) {
                        break;
                    }
                }
            }
        }

        return result;
    }

    static {
        for (ChatColor color : values())
            BY_CHAR.put(color.code, color);
    }
}
