package duke.storage;

import duke.task.DukeTask;
import duke.task.TaskList;

import java.util.ArrayList;
import java.util.Stack;

/**
 * The CommandHistory class that keeps track of the previous states of the TaskList.
 */
public class CommandHistory {
    private Stack<TaskList> undoStack;

    /**
     * Constructs a CommandHistory object.
     */
    public CommandHistory() {
        this.undoStack = new Stack<>();
    }

    /**
     * Push the current state of TaskList to undo stack
     *
     * @param tasks The current TaskList
     */
    public void saveState(TaskList tasks) {
        TaskList taskListCopy = new TaskList(tasks.getTasks());
        undoStack.push(taskListCopy);
    }

    /**
     * Check if the undo stack is empty.
     *
     * @return true if the undo stack is empty, false otherwise
     */
    public boolean isEmpty() {
        return undoStack.isEmpty();
    }

    /**
     * Pop the last state of TaskList from the undo stack.
     *
     * @return the last state of TaskList from the undo stack
     */
    public TaskList pop() {
        return undoStack.pop();
    }
}