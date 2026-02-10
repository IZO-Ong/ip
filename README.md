# Sappy

Sappy is a desktop application for managing tasks, optimized for use via a Graphical User Interface (GUI) while maintaining the efficiency of a Command Line Interface (CLI). It allows users to manage ToDos, Deadlines, and Events through simple text commands.

---

## Quick Start

1. Ensure you have **Java 17** or above installed on your computer.
2. Download the latest `sappy.jar` from [here](https://github.com/izo-ong/ip/releases).
3. Copy the file to the folder you want to use as the home folder for your task list.
4. Open a command terminal, `cd` into the folder where the jar file is located, and run the command:
   `java -jar sappy.jar`
5. The GUI should appear in a few seconds.

---

## Documentation

* **[User Guide](docs/README.md)**: Detailed instructions on command syntax and application features.

---

## Features

* **Task Management**: Support for three distinct task types: ToDo, Deadline, and Event.
* **Persistence**: Automated saving and loading of tasks via a local text file.
* **Keyword Search**: Built-in support for locating tasks using case-insensitive search logic.
* **Robust Logic**: Input validation and error handling to ensure application stability.

---

## Acknowledgements

Sappy is a project developed as part of the [SE-EDU initiative](https://se-education.org) and is based on the [Duke](https://github.com/se-edu/duke) project template.

**Warning:** Keep the `src/main/java` folder as the root folder for Java files. Do not rename those folders or move Java files to another folder outside of this path, as this is the default location tools (e.g., Gradle) expect to find Java files.