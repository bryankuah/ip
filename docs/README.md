# NIG User Guide

NIG is a **personal task manager** chatbot that helps you track todos, deadlines,
and events — all from the command line.

---

## Quick Start

1. Ensure **Java 17** is installed.
2. Download the latest `nig.jar` from the [Releases](../../releases) page.
3. Open a terminal in the folder containing `nig.jar`.
4. Run: `java -jar nig.jar`
5. Type a command and press **Enter**.

---

## Features

### `todo` — Add a Todo

Adds a simple task with no date/time attached.

**Format:** `todo DESCRIPTION`

**Example:** `todo read book`

```
____________________________________________________________
 Got it. I've added this task:
   [T][ ] read book
 Now you have 1 tasks in the list.
____________________________________________________________
```

---

### `deadline` — Add a Deadline

Adds a task that must be done by a specific date/time.

**Format:** `deadline DESCRIPTION /by DATE`

**Example:** `deadline submit assignment /by Friday 11pm`

```
____________________________________________________________
 Got it. I've added this task:
   [D][ ] submit assignment (by: Friday 11pm)
 Now you have 2 tasks in the list.
____________________________________________________________
```

---

### `event` — Add an Event

Adds a task that spans a time range.

**Format:** `event DESCRIPTION /from START /to END`

**Example:** `event project meeting /from Mon 2pm /to Mon 4pm`

```
____________________________________________________________
 Got it. I've added this task:
   [E][ ] project meeting (from: Mon 2pm to: Mon 4pm)
 Now you have 3 tasks in the list.
____________________________________________________________
```

---

### `list` — List All Tasks

Displays all tasks with their index, type, status, and description.

**Format:** `list`

```
____________________________________________________________
 Here are the tasks in your list:
 1.[T][ ] read book
 2.[D][ ] submit assignment (by: Friday 11pm)
 3.[E][ ] project meeting (from: Mon 2pm to: Mon 4pm)
____________________________________________________________
```

---

### `find` — Find Tasks by Keyword

Searches for tasks whose description contains the given keyword.

**Format:** `find KEYWORD`

**Example:** `find book`

```
____________________________________________________________
 Here are the matching tasks in your list:
 1.[T][ ] read book
____________________________________________________________
```

---

### `mark` — Mark as Done

Marks a task as completed.

**Format:** `mark INDEX`

**Constraints:** `INDEX` must be a positive integer matching a task in the list.

**Example:** `mark 1`

```
____________________________________________________________
 Nice! I've marked this task as done:
   [X] read book
____________________________________________________________
```

---

### `unmark` — Mark as Not Done

Reverts a completed task to not done.

**Format:** `unmark INDEX`

**Example:** `unmark 1`

```
____________________________________________________________
 OK, I've marked this task as not done yet:
   [ ] read book
____________________________________________________________
```

---

### `delete` — Delete a Task

Permanently removes a task from the list.

**Format:** `delete INDEX`

**Constraints:** `INDEX` must be a positive integer matching a task in the list.

**Example:** `delete 2`

```
____________________________________________________________
 Noted. I've removed this task:
   [D][ ] submit assignment (by: Friday 11pm)
 Now you have 2 tasks in the list.
____________________________________________________________
```

---

### `bye` — Exit

Exits the NIG application. All tasks are automatically saved everytime a change is made.

**Format:** `bye`

```
____________________________________________________________
 Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Data Storage

- Tasks are automatically saved to `data/nig.txt` after every change.
- Data is reloaded automatically on the next startup — no manual saving needed.

---

## Command Summary

| Command    | Format                                         | Example                                   |
|------------|------------------------------------------------|-------------------------------------------|
| `todo`     | `todo DESCRIPTION`                             | `todo read book`                          |
| `deadline` | `deadline DESCRIPTION /by DATE`                | `deadline submit report /by Friday 11pm`  |
| `event`    | `event DESCRIPTION /from START /to END`        | `event meeting /from Mon 2pm /to Mon 4pm` |
| `list`     | `list`                                         | `list`                                    |
| `find`     | `find KEYWORD`                                 | `find book`                               |
| `mark`     | `mark INDEX`                                   | `mark 1`                                  |
| `unmark`   | `unmark INDEX`                                 | `unmark 1`                                |
| `delete`   | `delete INDEX`                                 | `delete 2`                                |
| `bye`      | `bye`                                          | `bye`                                     |
