package duke.storage;

import duke.exception.InvalidInputException;
import duke.exception.StorageFileIOException;
import duke.task.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * A Storage object that handles the saving and loading of the TaskList.
 * Writes the TaskList in an external file whenever it is updated. Load the existing
 * file when the user opens the Duke program.
 */

public class Storage {
    public final String filePath;
    public final Path folder;
    public final File storageFile;


    /**
     * Constructor of that takes a path of the file and specify the file for
     * storage of the given path.
     *
     * @param filePath The path of the storage file
     */
    public Storage(String filePath) {
        String rootPath = Paths.get("").toAbsolutePath().toString();
        this.filePath = Paths.get(rootPath, filePath).toString();

        Path path = Paths.get(filePath);
        int len = path.getNameCount();

        this.folder = Paths.get(rootPath, path.subpath(0, len-1).toString());
        this.storageFile = new File(this.filePath);
    }

    public TaskList load() throws InvalidInputException, StorageFileIOException {
        TaskList list = new TaskList();
        if (!storageFile.exists()) {
            return list;
        }
        try {
            Scanner sc = new Scanner(storageFile);
            while (sc.hasNextLine()) {
                String instruction = sc.nextLine().strip();
                String[] information = instruction.split("\\s\\|\\s");

                String taskTag = information[0];
                boolean isDone = information[1].equals("[X]");
                String description = information[2];

                if (taskTag.equals("[T]")) {
                    TodoTask todo = new TodoTask(description);
                    if (isDone) {
                        todo.markAsDone();
                    }
                    list.addTask(todo);
                } else if (taskTag.equals("[D]")) {
                    String date = information[3];
                    DeadlineTask deadline = new DeadlineTask(description, LocalDate.parse(date));
                    if (isDone) {
                        deadline.markAsDone();
                    }
                    list.addTask(deadline);
                } else {
                    String from = information[3];
                    String to = information[4];
                    EventTask event = new EventTask(description, LocalDate.parse(from), LocalDate.parse(to));
                    if (isDone) {
                        event.markAsDone();
                    }
                    list.addTask(event);
                }
            }
            return list;
        } catch (FileNotFoundException e) {
            throw new StorageFileIOException("☹ OOPS!!! There's something wrong " +
                    "when reading the Storage list");
        }
    }

    //@@author Yufannnn-reused
    //https://nus-cs2103-ay2223s2.github.io/website/schedule/week3/topics.html#W3-4c
    //with minor modification, nice and concise function to overwrite text to a given file.
    public void writeToFile(String filePath, String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(textToAdd);
        fw.close();
    }
    //@@author

    public void save(TaskList list) throws IOException {
        if (!this.folder.toFile().exists()) {
            folder.toFile().mkdirs();
        }

        StringBuilder record = new StringBuilder();
        for (int i = 0; i < list.remainingTasks(); i++) {
            DukeTask task = list.getTask(i);
            record.append(task.storageString()).append(System.lineSeparator());
        }
        writeToFile(filePath, record.toString());
    }
}
