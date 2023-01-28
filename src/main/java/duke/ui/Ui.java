package duke.ui;

/**
 * Customize the conversation interface by changing the length of the horizontal bar
 * and the space of indentation.
 */

public class Ui {
    //Fields
    private final int DEFUALT_LENGTH = 70;
    private final int DEFUALT_SPACE = 4;
    private final String UNDERSCORE = "_";
    private final String SPACE = " ";
    private final String LINE_SEPERATOR = "\n";
    private final int horizontalLineLength;
    private final int indentSpace;
    private StringBuilder response = new StringBuilder();

    //Constructors
    /**
     * Constructor that sets HorizontalLineLength to be 70 and IndentSpace to be 4 by default.
     */
    public Ui() {
        this.horizontalLineLength = DEFUALT_LENGTH;
        this.indentSpace = DEFUALT_SPACE;
    }

    /**
     * Constructor that sets HorizontalLineLength to be the given length
     * and IndentSpace to be the given indent space.
     *
     * @param horizontalLineLength The length of the horizontal line to be set
     * @param indentSpace The length of the indent space to be set
     */
    public Ui(int horizontalLineLength, int indentSpace) {
        this.horizontalLineLength = horizontalLineLength;
        this.indentSpace = indentSpace;
    }

    //Methods
    /**
     * The indent method that places an indentation as specified by the space indent
     * at the start of every line.
     *
     * @param input the text to be indented
     * @return the indented text
     */
    public String indent(String input) {
        String delimiter = this.LINE_SEPERATOR + this.SPACE.repeat(this.indentSpace);
        String[] splitString = input.split(this.LINE_SEPERATOR);

        return this.SPACE.repeat(this.indentSpace) + String.join(delimiter, splitString);
    }

    /**
     * Display the given message between two horizontal line and add the specified indentation.
     *
     * @param message the message to be display
     */
    public void displayWithBar(String message) {
        String bar = this.SPACE.repeat(indentSpace) + this.UNDERSCORE.repeat(this.horizontalLineLength);
        System.out.println(bar + this.LINE_SEPERATOR + indent(message)
                + this.LINE_SEPERATOR + bar + this.LINE_SEPERATOR);
    }

    /**
     * Reset the response StringBuilder.
     */
    public void reset() {
        this.response = new StringBuilder();
    }

    /**
     * Get the final response in String format
     *
     * @return the final response in String format
     */
    public String getResponse() {
        return String.valueOf(this.response);
    }

    /**
     * Appends a message to the existing response.
     *
     * @param message the message to be appended
     */
    public void appendResponse(String message) {
        this.response.append(message);
    }
}
