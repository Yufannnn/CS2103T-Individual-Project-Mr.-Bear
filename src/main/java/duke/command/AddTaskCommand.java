package duke.command;

import duke.exception.StorageFileException;
import duke.storage.CommandHistory;
import duke.storage.Storage;
import duke.task.DukeTask;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * A more specific instruction class that encapsulates the action of adding a task
 * into the given TaskList.
 */

public class AddTaskCommand extends Command {
    private final DukeTask task;
    private final static String ADDED_TASK_MESSAGE = "Got it. I've added this task:\n%s\nNow you have %d tasks in the list.";

    /**
     * The constructor of AddTaskCommand that takes in the task to be added.
     *
     * @param task The task to be added
     */
    public AddTaskCommand(DukeTask task) {
        this.task = task;
    }

    /**
     * Execute the command to add a task to the task list, save the task list to storage, and display a message to the user.
     *
     * @param tasks the task list to add the task to
     * @param ui the user interface to display a message to the user
     * @param storage the storage to save the task list to
     * @param commandHistory the command history to save the previous state of the task list
     * @throws StorageFileException if there is an error saving the task list to storage
     */
    public void execute(TaskList tasks, Ui ui, Storage storage, CommandHistory commandHistory) throws StorageFileException {
        // Save the current state of the task list to the command history
        commandHistory.saveState(tasks);
        // Add the task to the task list
        tasks.addTask(this.task);
        // Save the task list to storage
        storage.saveTaskList(tasks);

        // Display a message to the user that the task was added to the task list
        String message = String.format(ADDED_TASK_MESSAGE, this.task, tasks.getNoOfTasks());
        ui.appendResponse(message);
    }

    /**
     * Compares this object to the specified object.
     *
     * @param obj the object to compare with
     * @return true if the objects are the same; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof AddTaskCommand)) {
            return false;
        }
        AddTaskCommand ddlObj = (AddTaskCommand) obj;

        return this.task.equals(ddlObj.task);
    }

    /**
     * Returns a string representation of the AddTaskCommand in the format "Add Task: task".
     *
     * @return A string representation of the AddTaskCommand
     */
    @Override
    public String toString() {
        return "Add Task: " + this.task;
    }

}
