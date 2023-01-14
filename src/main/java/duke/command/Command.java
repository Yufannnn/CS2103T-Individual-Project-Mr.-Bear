package duke.command;

import duke.display.Ui;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;

import java.io.IOException;

/**
 * An abstract instruction class encapsulating a user input instruction in Duke, which can be extended
 * by more specific input instructions like addToDoInstruction, ExitInstructions, etc.
 */

public abstract class Command {
    public boolean isExit() {
        return false;
    }

    /**
     * Execute the respective instructions.
     *
     * @param tasks The user TaskList that contains all the task to be manipulated
     * @throws DukeException Throws Exceptions when user input invalid instruction
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException;
}