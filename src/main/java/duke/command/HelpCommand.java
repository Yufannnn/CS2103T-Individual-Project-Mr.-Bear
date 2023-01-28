package duke.command;

import duke.exception.DukeException;
import duke.exception.InvalidInputException;
import duke.parser.ErrorMessage;
import duke.storage.CommandHistory;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

public class HelpCommand extends Command {
    private enum helpType {
        NORMAL, DATE, TIME, DURATION
    }
    private final helpType type;

    public HelpCommand(String information) throws InvalidInputException {
        super();
        try {
            this.type = helpType.valueOf(information.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException(ErrorMessage.INVALID_HELP_COMMAND_ERROR);
        }
    }

    private final static String HEADER = "Here are the available commands and their respective function:";
    private final static String PROMPT = "All the command keywords are case insensitive!";

    private final String VALID_DATE_TIME_FORMAT =
            "Here are all the valid date time format:\n\n" +
            "MMM d yyyy H:mm, MMM d yyyy HHmm\n" +
                    "MMM d yyyy HH:mm, yyyy-MM-d H:mm\n" +
                    "yyyy-MM-d HHmm, yyyy-MM-d HH:mm\n" +
                    "d/MM/yyyy H:mm, d/MM/yyyy HHmm\n" +
                    "d/MM/yyyy HH:mm, yyyy/MM/d H:mm\n" +
                    "yyyy/MM/d HHmm, yyyy/MM/d HH:mm\n" +
                    "yyyy/MM/d'T'Hm, yyyy/MM/d'T'H:mm\n" +
                    "yyyy-MM-d H:mm, yyyy-MM-d HHmm\n" +
                    "yyyy-MM-d HH:mm, d MMM yyyy H:mm\n" +
                    "d MMM yyyy HHmm, d MMM yyyy HH:mm\n" +
                    "MMM d, yyyy H:mm, MMM d, yyyy HHmm\n" +
                    "MMM d, yyyy HH:mm, d-MM-yyyy H:mm";

    private final String VALID_DATE_FORMAT =
            "Here are all the valid date time format:\n\n" +
                    "MMM dd yyyy, yyyy-MM-dd\n" +
                    "dd/MM/yyyy, yyyy/MM/dd\n" +
                    "dd MMM yyyy, MMM dd, yyyy\n" +
                    "dd-mm-yyyy";

    private final String VAILD_DURATION_FORMAT =
            "Here is the correct format to input a duration:\n\n" +
                    "\"PT20.345S\" -- parses as \"20.345 seconds\"\n" +
            "\"PT15M\"     -- parses as \"15 minutes\" (where a minute is 60 seconds)\n" +
            "\"PT10H\"     -- parses as \"10 hours\" (where an hour is 3600 seconds)\n" +
            "\"P2D\"       -- parses as \"2 days\" (where a day is 24 hours or 86400 seconds)\n" +
            "\"P2DT3H4M\"  -- parses as \"2 days, 3 hours and 4 minutes\"\n" +
            "\"P-6H3M\"    -- parses as \"-6 hours and +3 minutes\"\n" +
            "\"-P6H3M\"    -- parses as \"-6 hours and -3 minutes\"\n" +
            "\"-P-6H+3M\"  -- parses as \"+6 hours and -3 minutes\"\n\n" +
            "The letter \"P\" is next in upper or lower case. There are then four sections, " +
            "each consisting of a number and a suffix. The sections have suffixes in ASCII of \"D\", \"H\", " +
            "\"M\" and \"S\" for days, hours, minutes and seconds, accepted in upper or lower case. " +
            "The suffixes must occur in order. The ASCII letter \"T\" must occur before the first occurrence, " +
            "if any, of an hour, minute or second section. At least one of the four sections must be present, " +
            "and if \"T\" is present there must be at least one section after the \"T\". The number part of each " +
            "section must consist of one or more ASCII digits. The number may be prefixed by the ASCII negative" +
            "or positive symbol. The number of days, hours and minutes must parse to an long. The number of " +
            "seconds must parse to an long with optional fraction. The decimal point may be either a dot or a comma. " +
            "The fractional part may have from zero to 9 digits.";

    /**
     * Enum to represent the different types of commands supported by the application
     */
    private enum CommandType {
        BYE("bye", "Exit the program"),
        DEADLINE("deadline [description] /by [date time]", "Add a deadline task with its " +
                "deadline specified, type \"help time\" to check all the available date format"),
        DELETE("delete [taskIndex]", "Delete the task specified by the given index"),
        EVENT("event [description] /by [date time] /from [date time]", "Add a event task " +
                "with its starting and ending date specified, type \"help time\" " +
                "to check all the available date format"),
        FIND("find [keyword]", "List all the events that matches the input keyword. " +
                "(case insensitive)"),
        FIXED("fixed [description] /within [duration]", "Add a fixed duration task with its " +
                "duration specified, type \"help time\" to check the correct format of a duration"),
        Free("free","Find the next free date in the next month"),
        HELP("help", "Show help menu"),
        LIST("list", "Display all tasks in the current Task List"),
        MARK("mark [taskIndex]", "Mark the task specified by the given index as done"),
        MASS_DELETE("massDelete", "Delete all the tasks that have been marked as done"),
        TODO("todo [description]", "Add a todo task"),
        UNMARK("unmark [taskIndex]", "Mark the task specified by the given index as undone"),
        UPDATE("update [taskIndex] [description]", "Update the description of the task specified " +
                "by the given index to be the new description"),
        VIEW("view [date]", "List all the Deadline tasks and Event tasks that takes " +
                "place on the given day, type \"help date\" to check all the available date format");

    private final String command;
        private final String description;

        /**
         * Constructor for the CommandType enum
         *
         * @param command     the command string that the user inputs
         * @param description the description of the command
         */
        CommandType(String command, String description) {
            this.command = command;
            this.description = description;
        }

        /**
         * Gets the command string.
         *
         * @return the command string
         */
        public String getCommand() {
            return command;
        }

        /**
         * Gets the command description.
         *
         * @return the command description
         */
        public String getDescription() {
            return description;
        }
    }

    /**
     * Displays the available commands and their respective function.
     *
     * @param tasks The user TaskList that contains all the task to be manipulated
     * @param ui The ui Object used to display information
     * @param storage The Storage Object used to save and load the TaskList
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage, CommandHistory commandHistory) throws DukeException {
        // Initialize a StringBuilder to store the help message
        if (type == helpType.NORMAL) {
            StringBuilder message = new StringBuilder();
            message.append(HEADER).append("\n\n");

            // Iterate through the CommandType enum and append the command and its description to the message
            for (CommandType commandType : CommandType.values()) {
                message.append("- ").append(commandType.getCommand()).append(" : ")
                        .append(commandType.getDescription()).append("\n\n");
            }

            message.append(PROMPT);
            // Send the message to the UI
            ui.appendResponse(message.toString());
        } if (type == helpType.DATE) {
            ui.appendResponse(VALID_DATE_FORMAT);
        } else if (type == helpType.TIME) {
            ui.appendResponse(VALID_DATE_TIME_FORMAT);
        } else if (type == helpType.DURATION) {
            ui.appendResponse(VAILD_DURATION_FORMAT);
        }
    }
}
