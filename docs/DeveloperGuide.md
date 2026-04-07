---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# AB-3 Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This project evolved from the [AddressBook-Level3 project](https://github.com/se-edu/addressbook-level3) created by the [SE-EDU initiative](https://se-education.org).

--------------------------------------------------------------------------------------------------------------------

## **Setting Up, Getting Started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

The sequence diagram below illustrates the interactions within the `UI` component when the window is resized, to dynamically change the number of displayed columns of contacts.

<puml src="diagrams/ResizingSequenceDiagram.puml" alt="Interactions within the UI component during a window resizing" />

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

###  Undo feature

The `undo` mechanism is implemented within ModelManager. It allows the user to restore the address book to its immediate previous state after a data-modifying command, but only once.

#### Implementation

The `undo` mechanism is facilitated by ModelManager storing a ReadOnlyAddressBook named previousAddressBook. It implements the following operations:

* `ModelManager#commitAddressBook()` — Saves a copy of the current address book state before a data-modifying command executes.
* `ModelManager#undoAddressBook()` — Restores the address book state from the stored backup and clears the backup to ensure only one undo is possible.
* `ModelManager#canUndo()` — Checks if a backup state exists.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#canUndo()` respectively.

Given below is an example usage scenario and how the `undo` mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `ModelManager` is initialized with the initial address book state. `previousAddressBook` is `null`.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes the `delete 5` command. The command calls `Model#commitAddressBook()`, which saves the state before the deletion into `previousAddressBook`. The command then deletes the person.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, overwriting `previousAddressBook` with the state that included everyone except the 5th person. The command then adds David.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, preserving the existing undo state.
**Note:** Non-data-modifying commands like `sort`, `list` and `find` will not call `Model#commitAddressBook()`, preserving the existing undo state.

</box>

Step 4. The user decides adding David was a mistake and executes `undo`. The UndoCommand calls `Model#undoAddressBook()`, which restores the `Model` to the state saved in `previousAddressBook` (the state after the deletion but before the add). `previousAddressBook` is then set back to `null`.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />

<box type="info" seamless>

**Note:** If `previousAddressBook` is `null`, the user cannot perform an `undo`. UndoCommand uses `Model#canUndo()` to verify state before attempting the restoration.

</box>

The following sequence diagram shows how an `undo` operation goes through the `Logic` and `Model` components:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

###  \[Proposed\] Multiple Undo Feature

The current single-state backup in `ModelManager` can only support undoing a maximum of one command. To support a multiple `undo` feature, the current single-state backup in `ModelManager` would need to be upgraded to a state history list (similar to the original AB3 proposal). This would involve:

1. Replacing `previousAddressBook` with an `addressBookStateList` and a `currentStatePointer`.
2. Changing the implementation of `Model#undoAddressBook()`, to move the pointer backward in the history list to restore a previous state.
3. Updating the "Purge" logic: If a new data-modifying command is executed after an `undo`, all "redoable" states at the end of the list must be deleted.

#### Design Considerations:

**Aspect: How undo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement and less prone to bugs when complex fields (like certificates) are modified.
  * Cons: Higher memory usage since a full snapshot is taken for every modifying command

* **Alternative 2:** Individual commands know how to reverse themselves (eg an AddCommand does `undo` by performing a delete).
  * Pros: Very memory efficient.
  * Cons: Highly complex to implement correctly, especially for commands that modify multiple internal states simultaneously.

Due to time constraints, Alternative 1 was implemented.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, Logging, Testing, Configuration, Dev-Ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product Scope

**Target user profile**:

* are HR/Admin personnel of a small startup
* manages a significant number of employee information (~50 employees)
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage critical employee information faster than a typical mouse/GUI driven app

The app does not support managing larger workgroups beyond the average startup size (~50 people)
and will only manage critical employee-related information such as contact details, departments,
team structures and salaries.

### User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                                                       | I want to …​                                                                                                       | So that I can…​                                                                                               |
|----------|-------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|
| `* * *`  | user with internet access                                                     | see usage instructions in online User Guide                                                                        | refer to instructions when I learn or forget how to use the app                                               |
| `* * *`  | user without internet access                                                  | view an in-app help menu                                                                                           | learn basic usages of the app even when I am offline                                                          |
| `* * *`  | user                                                                          | add a new employee                                                                                                 | I can record details about an employee                                                                        |
| `* * *`  | user                                                                          | delete an employee                                                                                                 | ensure data privacy by removing past employees                                                                |
| `* * *`  | user                                                                          | find employee(s) by specific details                                                                               | locate details of employees without having to go through the entire list                                      |
| `* * `   | forgetful user                                                                | find employee(s) by partial words                                                                                  | locate details of employees without having to go through the entire list or remembering full specific details |
| `* * *`  | returning user                                                                | save and load contacts via a file                                                                                  | quickly restore data from a past session                                                                      |
| `* * `   | user who prefers some flexibility                                             | be warned instead of blocked when I make duplicate errors                                                          | personally decide who belongs in my contact list                                                              |
| `* * `   | user who wants to minimise inputs of wrongly formatted data                   | be able to use the app's in-built input validation feature                                                         | use the data with ease later                                                                                  |
| `* * `   | user who handles company compliance risks                                     | add and find certificates in employee contacts                                                                     | send employees for training or retraining, before they are assigned specialised roles                         |
| `* * `   | user who handles employee qualifications                                      | edit and delete certificates in employee contacts                                                                  | update my employees' qualifications when there is a change                                                    |
| `* * `   | user who handles employee qualifications                                      | add and edit certificates without an expiry date                                                                   | record lifetime employee qualifications that never expire                                                     |
| `* * `   | user who works with employee salaries                                         | add and edit salaries in employee contacts                                                                         | use salary details without depending on another app                                                           |
| `* * `   | user who likes categorising details                                           | add and delete tags to employee contacts                                                                           | organise employee records by tags                                                                             |
| `* * `   | user who likes categorising details                                           | colour my employees' tags                                                                                          | organise employee records visually by coloured tags                                                           |
| `*  `    | busy user who likes categorising details                                      | mass add and remove tags from specific persons displayed contact list                                              | efficiently organise employee records by tags                                                                 |
| `* *`    | user who may not have all details at the moment                               | add a new employee with partial details                                                                            | at least create a simple record in the contact list, to be updated later                                      |
| `* *`    | user who is prone to typing wrongly                                           | be able to view my employee contact list when I type a command wrongly                                             | refer to the employee details when I want to fix my errors                                                    |
| `* *`    | busy user                                                                     | edit specific employee details                                                                                     | can be more efficient by not having to delete existing contacts and adding updated ones                       |
| `* *`    | user who is used to alphabetical ordering                                     | sort employees by alphabetical ordering, prioritising capital letters when there is a mix of small and big letters | quickly view or gather information about employees in alphabetical order                                      |
| `* *`    | user who dislikes a small screen                                              | be able to enlarge my screen                                                                                       | view more employee contacts                                                                                   |
| `* *`    | user who is prone to typos                                                    | be able to undo my immediate previous command                                                                      | efficiently restore immediate past details without having to check what they orginally were                   |
| `* *`    | user who is prone to typos                                                    | be able to fix most of my typos, using the app's trimming utility features                                         | efficiently use the app without losing much momentum                                                          |
| `* * `   | user who may be working with different amounts of contact details per contact | be able to scroll each individual contact box                                                                      | view overall contacts without it being too cluttered or spaced-out                                            |
| `* `     | user who prefers typing on the keyboard                                       | close pop-up windows with keyboard keys                                                                            | conveniently use the app without having to use a mouse or a trackpad often                                    |
| `* `     | user who prefers typing on the keyboard                                       | close the app just by typing                                                                                       | conveniently use the app without having to use a mouse or a trackpad often                                    |
| `* `     | new user who is replacing the past HR manager                                 | upload file to add a group of employees at once                                                                    | be able to not add employees one by one                                                                       |
| `* `     | user who wants to check employees with missing details                        | view highlighted missing details of employees                                                                      | efficiently see missing information that I have to fill in                                                    |
| `* `     | user who is dealing with employee qualifications                              | filter out "No Expiry" certificates during an expiry search                                                        | only see employees whose certifications are actually past a deadline                                          |

### Use Cases

(For all use cases below, the **System** is the `Big Brother` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC1 Add a person**

**MSS**

1. User requests to add a person
2. Big Brother indicates that a person is added

   Use case ends.

**Extensions**

* 1a. User inputs an invalid command
    * 1a1. Big Brother shows an error message

       Use case resumes at step 1.

**Use case: UC2 Add a tag to a person**

**MSS**

1.  User requests to list persons
2.  Big Brother shows a list of persons
3.  User requests to add a tag to a specific person in the list
4.  Big Brother adds the tag to the person

    Use case ends.

**Extensions**

* 1a. The list is empty

  Use case ends.

* 3a. The given person's index is invalid

    * 3a1. Big Brother shows an error message

      Use case resumes at step 2.

**Use case: UC3 Delete a person**

**MSS**

1.  User requests to list persons
2.  Big Brother shows a list of persons
3.  User requests to delete a specific person in the list
4.  Big Brother deletes the person

    Use case ends.

**Extensions**

* 1a. The list is empty

  Use case ends.

* 3a. The given person's index is invalid

    * 3a1. Big Brother shows an error message

      Use case resumes at step 2.

**Use case: UC4 Delete a tag**

**MSS**

1.  User requests to list persons
2.  Big Brother shows a list of persons
3.  User requests to delete a tag from a specific person in the list
4.  Big Brother deletes the tag from the person

    Use case ends.

**Extensions**

* 1a. The list is empty

  Use case ends.

* 3a. The given person's index is invalid

    * 3a1. Big Brother shows an error message

      Use case resumes at step 2.

* 3b. The tag specified cannot be found

    * 3b1. Big Brother shows an error message

      Use case resumes at step 2.

**Use case: UC5 Find a person**

**MSS**

1.  User requests to find a specific person
2.  Big Brother displays the details of the specific person

    Use case ends.

**Extensions**

* 1a. Person specified does not exist
    * 1a1. Big Brother shows an error message

      Use case ends.


*{More to be added}*

### Non-Functional Requirements

#### Environment
1.  Should work on any _mainstream OS_ as long as it has Java `17` installed.
2.  Should be fully usable offline, without remote services nor servers.
3.  Should offer sufficient in-built support for fully offline use.

#### App Format
1.  Should work without requiring an installer.
2.  Should be packaged as a single `jar` or `zip` file.
3.  Should not exceed 100MB in size for the file.

#### Visual Performance
1.  GUI should work _well_ for:
  * standard screen resolutions 1920x1080 and higher
  * for screen scales 100% and 125%
2.  GUI should work for:
  * resolutions 1280x720 and higher
  * for screen scales 150%

#### Functional Performance
1.  Should be able to hold up to 100 persons without a noticeable sluggishness in performance for typical usage.
2.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
3.  Should respond in at most 2 seconds for any command.

#### Data
1.  Should not rely on database management systems.
2.  Should be formatted in human-readable format.
3.  Should be saved locally in human-editable text file.
4.  Should be recoverable in the event of corruption.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Employee profile**: Grouped information about an employee's name, phone number, email, address, associated tags (if any), salary and certificates (if any)
* **Invalid command**: A command that is not supported by the application
* **Above average typing speed**: An average typing speed > 80 WPM

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for Manual Testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing

</box>

### Launch and Shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
