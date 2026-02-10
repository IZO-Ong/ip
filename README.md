# Sappy - User Guide

**Sappy** is a desktop application for managing tasks, optimized for use via a Graphical User Interface (GUI) with a Command Line Interface (CLI) input style.

---

## Quick Start

1. Ensure you have **Java 17** or above installed on your computer.
2. Download the latest `sappy.jar` from the releases page.
3. Copy the file to the folder you want to use as the home folder for your task list.
4. Open a command terminal, `cd` into the folder, and run:  
   `java -jar sappy.jar`
5. A GUI should appear in a few seconds. Type your commands in the text box and press **Enter** to execute.

---

## Features

### Notes about the command format:
* Words in `UPPER_CASE` are the parameters to be supplied by the user.
    * e.g., in `todo DESCRIPTION`, `DESCRIPTION` is a parameter: `todo Buy groceries`.
* Items in square brackets are optional.
    * e.g., `find KEYWORD [MORE_KEYWORDS]` can be used as `find book` or `find book project`.
* Extraneous parameters for commands that do not take in parameters (such as `list` and `bye`) will be ignored.

---

### Adding a ToDo: `todo`
Adds a basic task without any date or time constraints.
* **Format:** `todo DESCRIPTION`
* **Example:** `todo Read textbook chapter 1`

### Adding a Deadline: `deadline`
Adds a task with a specific deadline date.
* **Format:** `deadline DESCRIPTION /by yyyy-mm-dd`
* **Example:** `deadline CS2103T Project /by 2026-02-13`

### Adding an Event: `event`
Adds a task that occurs during a specific time period.
* **Format:** `event DESCRIPTION /from yyyy-mm-dd /to yyyy-mm-dd`
* **Example:** `event Project Workshop /from 2026-12-01 /to 2026-12-05`

### Listing all tasks: `list`
Lists all tasks currently stored in the application.
* **Format:** `list`

### Marking a task: `mark`
Marks a specific task as completed based on its index.
* **Format:** `mark INDEX`
* **Example:** `mark 1`

### Unmarking a task: `unmark`
Reverts a completed task back to an incomplete status.
* **Format:** `unmark INDEX`
* **Example:** `unmark 1`

### Locating tasks by name: `find`
Searches for tasks whose descriptions contain the specified keyword.
* **Format:** `find KEYWORD`
* **Note:** The search is case-insensitive.
* **Example:** `find Tutorial`

### Deleting a task: `remove`
Permanently deletes the specified task from the list.
* **Format:** `remove INDEX`
* **Example:** `remove 2`

### Exiting the program: `bye`
Exits and closes the application.
* **Format:** `bye`

---

## Saving Data

Sappy saves your data automatically to the hard disk after any command that modifies the task list. Manual saving is not required.

### Editing the Data File
Task data is saved automatically as a text file at `[JAR location]/data/sappy.txt`. Advanced users may edit the data file directly.

> **Caution:** If manual edits make the data file format invalid, Sappy will attempt to discard the corrupted lines and load the remaining valid entries.

---

## Command Summary

| Action | Format | Example |
| :--- | :--- | :--- |
| **ToDo** | `todo DESCRIPTION` | `todo Study` |
| **Deadline** | `deadline DESCRIPTION /by yyyy-mm-dd` | `deadline Exam /by 2026-05-01` |
| **Event** | `event DESCRIPTION /from yyyy-mm-dd /to yyyy-mm-dd` | `event Trip /from 2026-06-01 /to 2026-06-05` |
| **List** | `list` | `list` |
| **Mark** | `mark INDEX` | `mark 2` |
| **Unmark** | `unmark INDEX` | `unmark 2` |
| **Find** | `find KEYWORD` | `find lecture` |
| **Remove** | `remove INDEX` | `remove 1` |
| **Exit** | `bye` | `bye` |