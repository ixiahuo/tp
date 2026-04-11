---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# Big Brother User Guide

Big Brother allows you to manage employee contacts, on your desktop, with keyboard commands. If you are a Human Resource staff member of a small startup (~50 people) who enjoys fast typing, Big Brother is for you. Achieve efficiency with easy-to-learn controls and a simple, consolidated display. 

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` installed in your Computer.<br>
   You may find instructions on how to do so for your operating system version [here](https://se-education.org/guides/tutorials/javaInstallation.html).<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [here](https://github.com/AY2526S2-CS2103T-T09-1/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for Big Brother.

4. Open a command terminal (you can search for it in the start menu) and change the working folder to the one you put the app in. All operating systems support this with the `cd` command:<br>
   **Windows** `cd C:\Users\your_username\big_brother_home_folder`<br>
   **Linux** `cd /home/your_username/big_brother_home_folder`<br>
   **Mac** `cd /Users/your_username/big_brother_home_folder`<br>

5. Run the `java -jar bigbrother.jar` command to start the app.<br>
   Note the app name may be slightly different due to versions.<br>
   A GUI similar to the below should appear in a few seconds.<br>

<img src="images/Ui.png" width="750" style="margin-bottom:30px"/>

6. Type a command in the command box (the red-brown rectangle at the top) and press Enter to execute it.<br>
* Refer to the [Features](#features) below for details of each command.<br>
* Refer to the [Input Validation, Duplicate Handling and Utilities](#input-validation-duplicate-handling-and-utilities) below to determine valid inputs, duplication rules and app utilities to help with usage.<br>
* Refer to the [Summary](#command-summary) below for a summary of all available commands.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters that you supply.<br>
  e.g. for `n/NAME`, `NAME` is the parameter.

* Arguments not in square brackets are compulsory.<br>
  e.g. `n/NAME`, `INDEX`

* Arguments in square brackets are optional. Further explanations will be provided when relevant.<br>
  e.g. `[a/TAGS_TO_ADD]`, `[d/TAGS_TO_DELETE]`

* `INDEX` is a special argument that refers to the id number of the person you want to change. You can find a person's current id by looking at the left-side of their name in the display.

* Arguments can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE`, `p/PHONE n/NAME` is also acceptable.

* Multiple prefixes must be separated by whitespaces.<br>
  e.g. if the command specifies `n/NAME p/PHONE`, `n/NAMEp/PHONE` is not acceptable.

* Prefix symbol and `/` **cannot** be separated by whitespaces.<br>
  e.g. if the command specifies `n/NAME`, `n /NAME` is not acceptable.

* Extraneous parameters for commands that do not take in arguments (such as [`help`](#viewing-in-app-help-menu-help) , [`list`](#listing-all-contacts-list), [`exit`](#exiting-the-program-exit) and [`clear`](#clearing-all-entries-clear)) will be ignored.<br>
  e.g. if you input `help 123`, it will be interpreted as just [`help`](#viewing-in-app-help-menu-help) .

* If you encounter any validation issues, refer to [Input Validation, Duplicate Handling and Utilities](#input-validation-duplicate-handling-and-utilities) for clarification.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Navigating the GUI (Graphic User Interface)
* The GUI display is structured such that the main contacts list is a big scrollable section, and the contact entries are smaller scrollable sections.

* You can hover your mouse cursor over the desired scroll bar, then scroll each section independently.

* You can hover your mouse cursor over the main scroll bar (rightmost), to scroll the list as a whole.

* When you type and enter a command that modifies the contact list or a contact entry, every individual contact entry will scroll to the top.

* The main contact list **does not have autoscroll**, so you may need to scroll manually to see the changes after performing a command (e.g. after [`add`](#adding-a-new-contact-add), the newly added contact is at the bottom).

* Mouseless-support is planned to be implemented in a future update.

* When adjusting the display size, the contact entries temporarily disappear. They will reappear once the size has stabilized.

### Viewing in-app help menu : `help`
Format: `help`

<img src="images/helpMessage.png" width="750" style="margin-bottom:30px"/>

<box type="tip" seamless>

**Tips for in-app help**

> You can automatically close the pop-ups with Enter on Windows and Linux, or space bar on Mac.<br>

> If you need more help with a command marked by a `*`, enter it with no arguments into the command box.
> Example: to get more help for [`add`](#adding-a-new-contact-add), simply enter [`add`](#adding-a-new-contact-add) into the command box.
</box>

<br>

### Adding a new contact : `add`
Format: `add n/NAME [p/PHONE] [e/EMAIL] [a/ADDRESS] [s/SALARY]`
<br>
Parameters:
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-primary">NAME</a>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-dark">PHONE</a>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-dark">EMAIL</a>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-dark">ADDRESS</a>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-dark">SALARY</a>
* Adds a person, with your specified attributes, to the contact list.
* You may omit any (or all) of the optional arguments.

Example: `add n/John Doe p/+65 98765432 e/johnd@example.com a/Abc Rd, Blk 123, #01-01 s/3000`

Expected result (starting with the existing sample data):

<img src="images/addSuccess.png" width="750" style="margin-bottom:30px"/>

### Editing an existing contact : `edit`
Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [s/SALARY]`
<br>
Parameters:
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-primary">INDEX</a>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-dark">NAME</a>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-dark">PHONE</a>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-dark">EMAIL</a>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-dark">ADDRESS</a>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-dark">SALARY</a>

* Edits the person at the specified [`INDEX`](#input-validation-duplicate-handling-and-utilities) of the displayed person list.
* **You must provide at least one of the optional arguments.**
* Once you enter the command, existing values will be updated to your input values.
* **You may clear attributes by entering an empty prefix.** (see Ex 2)
* Input values can be the same as existing values (e.g. if person with [`INDEX`](#input-validation-duplicate-handling-and-utilities) 2 already has [`SALARY`](#input-validation-duplicate-handling-and-utilities) of `3000`, you can still perform `edit 2 s/3000`)

Example:
1. `edit 1 p/+017 91234567 e/johndoe@example.com` edits person 1's [`PHONE`](#input-validation-duplicate-handling-and-utilities) number to `+017 91234567` and the [`EMAIL`](#input-validation-duplicate-handling-and-utilities) to `johndoe@example.com`
2. `edit 1 p/ e/` clears person 1's [`PHONE`](#input-validation-duplicate-handling-and-utilities) and [`EMAIL`](#input-validation-duplicate-handling-and-utilities). These attributes will now appear blank

<box type="info" seamless>

**Note on duplicate warning**

> If you `edit` a contact such that it now matches another existing contact in your list, Big Brother will perform the edit but will trigger a **warning pop-up** to alert you of the duplicate. You can choose to keep the duplicate or delete it later.
</box>

<br>

### Deleting an existing contact : `delete`
Format: `delete INDEX`
<br>
Parameters:
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-primary">INDEX</a>
* Deletes the person at the specified [`INDEX`](#input-validation-duplicate-handling-and-utilities) of the displayed person list.

Examples:
1. [`list`](#listing-all-contacts-list) followed by `delete 2` deletes the 2nd person in the address book.
2. [`find n/Betsy`](#searching-contacts-by-criteria-find) followed by `delete 1` deletes the 1st person in the results of the [`find`](#searching-contacts-by-criteria-find) command, if present.

<br>

### Searching contacts by criteria : `find`
Format: `find [n/NAME] [t/TAG] [c/CERT_NAME] [e/CERT_EXPIRY_DATE]`
<br>
Parameters:
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-dark">NAME</a>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-dark">TAG</a>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-dark">CERT_NAME</a>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-dark">CERT_EXPIRY_DATE</a>

* Finds persons based on the given criteria.
* **You must provide at least one of the optional arguments.**
* For [`NAME`](#input-validation-duplicate-handling-and-utilities), [`TAG`](#input-validation-duplicate-handling-and-utilities) and [`CERT_NAME`](#input-validation-duplicate-handling-and-utilities), matching is case-insensitive and can match part of the word.
  * e.g. A search for `n/john` will find people with [`NAME`](#input-validation-duplicate-handling-and-utilities) 'Johny'
* For [`CERT_EXPIRY_DATE`](#input-validation-duplicate-handling-and-utilities), matching is for certificates that expire **before** the provided date.
* If a person has only 1 certificate with no expiry date, then performing `find e/CERT_EXPIRY_DATE` will never display this person since a "forever valid" certificate will never expire before any given date.
* But if a person has more than 1 certificate, some with and some without an expiry date, all of their certificates will still be displayed so long as at least 1 of their certificates match the search.
* You may use a prefix more than once to expand the search (i.e. `OR` search) (see Ex 1), the only exception is [`CERT_EXPIRY_DATE`](#input-validation-duplicate-handling-and-utilities).
  * Use repeated prefixes, not spaces, for `OR` (i.e. `find t/HR t/IT` and not `find t/HR IT`)
* You may also use multiple different arguments to narrow down the search (i.e. `AND` search) (see Ex 3).

Examples:
1. `find n/Alex Y n/David` returns all persons whose name contains `Alex Y` or `David`.
2. `find c/OSCP` returns all persons with certificate names containing `OSCP`.
3. `find n/Alex t/IT e/2027-03-15` returns all persons whose name contains `Alex`, with tags that contain `IT` and with certificates that expire before 15th March 2027.

<box type="tip" seamless>

**Tip on viewing the full list again**
> If you want to see the original contact list after performing a `find` command, use the [`list`](#listing-all-contacts-list) command instead of the [`undo`](#restoring-the-contact-list-undo) command, since finding is not a data-modifying command that changes state of the contact list.
</box>

<br>

### Listing all contacts : `list`
Format: `list`

<br>

### Adding and deleting tags : `tag`
Format: `tag INDEX [a/TAGS_TO_ADD] [c/COLOUR_OF_TAGS_TO_ADD] [d/TAGS_TO_DELETE]`
<br>
Parameters:
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-primary">INDEX</a>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-dark">TAGS_TO_ADD</a>
<span class="badge bg-secondary">COLOUR_OF_TAGS_TO_ADD</span>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-dark">TAGS_TO_DELETE</a>

* Adds or deletes tags of the person at the specified `INDEX` of the displayed person list.
* **You must either specify the add `a/` prefix or the delete `d/` prefix. Both arguments cannot be specified at the same time.**
* If multiple tags are to be added or deleted, you should separate their names are by spaces. (e.g. `a/TAG1 TAG2`). Note that the `tag` command is the only command with this property.
* There are 5 colour options for `COLOUR_OF_TAGS_TO_ADD`: `RED`, `YELLOW`, `GREEN`, `BLUE` (default), and `PURPLE`.
  * You can either specify one colour per tag in `a/` (separated by space, i.e `a/TAG1 TAG2 c/RED GREEN`), or specify one single colour to be applied to all tags (i.e `a/TAG1 TAG2 c/red`).
  * colours are case-insensitive, so `c/red` and `c/RED` are both valid.
  * When deleting, do not use the `c/` prefix.

Examples:
All the below commands affect the person at `INDEX` 1
1. `tag 1 a/IT Intern c/RED` adds two tags `IT` and `Intern` with a **RED** colour.
2. `tag 1 d/Best_Employee` deletes a tag `Best_Employee`.
3. `tag 1 a/HR Best_Employee` adds two tags `HR` and `Best_Employee` with the default colouration.
4. `tag 1 a/Admin HR c/YELLOW GREEN` adds one **YELLOW** tag named `Admin` and one **GREEN** tag named `HR`.
<box type="info" seamless>

**Notes on duplicate / non-existent tag handling**
> If you add duplicate tag(s) (i.e. the contact already has a tag of that name, regardless of colour), they will be silently ignored when adding a mix of duplicate and non-duplicate tag(s).
> Likewise, non-existent tag(s) (i.e. the contact does not have a tag with that name) will be silently ignored when deleting a mix of existing and non-existing tag(s).
> If you specify duplicate tag names in the command itself, Big Brother will throw an error. (i.e. `tag 1 a/TAG TAG c/RED BLUE` is not allowed)

</box>
<br>

### Adding certificates : `cert-add`
Format: `cert-add INDEX n/CERT_NAME [e/CERT_EXPIRY_DATE]`.
<br>
Parameters:
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-primary">INDEX</a>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-primary">CERT_NAME</a>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-secondary">CERT_EXPIRY_DATE</a>

* Adds a Certificate to a person at the specified [`INDEX`](#input-validation-duplicate-handling-and-utilities).
* **You must supply the certificate with a name (which is case-insensitive).**
* Expiry dates are optional (you can omit `e/` for Certificates that have no expiry date).
* Expiry dates must be formatted as **YYYY-MM-DD**.

Examples:<br>
1. `cert-add 1 n/OSCP e/2028-03-05` adds a certificate named OSCP with an expiry date on 5th March 2028 to the first person in the list.
2. `cert-add 1 n/CompTIA` adds a certificate named CompTIA with no expiry date to the first person in the list.

<br>

### Deleting certificates : `cert-del`
Format: `cert-del INDEX n/CERT_NAME`
<br>
Parameters:
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-primary">INDEX</a>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-primary">CERT_NAME</a>

* Deletes a Certificate from a person at the specified [`INDEX`](#input-validation-duplicate-handling-and-utilities).
* **You must specify the name of the Certificate to be deleted, which is case-insensitive, via the `n/` prefix.**

Example: `cert-del 1 n/OSCP`
1. Deletes the certificate named OSCP from the first person in the list.

<br>

### Editing certificates : `cert-edit`
Format: `cert-edit INDEX n/CERT_NAME [ne/NEW_CERT_NAME] [ee/NEW_CERT_EXPIRY_DATE]`
<br>
Parameters:
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-primary">INDEX</a>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-primary">CERT_NAME</a>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-dark">NEW_CERT_NAME</a>
<a href="#input-validation-duplicate-handling-and-utilities" class="badge bg-dark">NEW_CERT_EXPIRY_DATE</a>

* Edits a certificate of the person at the specified [`INDEX`](#input-validation-duplicate-handling-and-utilities) of the displayed person list.
* **You must specify the name of the certificate to be edited, which is case-insensitive, via the `n/` prefix.**
* **You must also include at least one of the `ne/` and/or the `ee/` flags, depending on whether the name or the expiry date has to be edited.**
* If the new expiry date is to be updated to "No Expiry", user input after `ee/` is left empty(e.g. `cert-edit 1 n/Marketing ee/`).
* Overwriting a Certificate with the same `CERT_NAME` and `CERT_EXPIRY_DATE` is allowed.

Example: `cert-edit 1 n/OSCP ne/OSCP2`
* Edits the certificate originally named 'OSCP' held by the first person in the list, updating its name to 'OSCP2'.

<br>

### Restoring the contact list : `undo`
Format: `undo`

* Undoes the most recently used command which changed the state of the contact list.

<box type="warning" seamless>

**CAUTION:**
> [`list`](#listing-all-contacts-list) and [`find`](#searching-contacts-by-criteria-find) do not change the state of the contact list, so an `undo` will undo the most recent command that changed the state (e.g. [`add`](#adding-a-new-contact-add) followed by [`find`](#searching-contacts-by-criteria-find) followed by `undo` will undo [`add`](#adding-a-new-contact-add))

> When an `undo` succeeds, another `undo` cannot be run until another command that changes the state is run (e.g. `undo` followed by [`add`](#adding-a-new-contact-add) followed by `undo`)

</box>

<br>

### Sorting all contacts : `sort`
Format: `sort`
* Sorts the contact list in alphabetical order of name.
* Uppercase is prioritised over lowercase (i.e. "Bob" would be positioned before "alice").
* The full contact list will be sorted, however filtered entries remain filtered.
  * e.g. if [`find`]((#searching-contacts-by-criteria-find)) was called before [`sort`](#sorting-all-contacts-sort), only the found contacts remain displayed.

<box type="tip" seamless>

**Tip on state of the contact list**
> `sort` will change the state of the contact list, so that further commands will be built on the sorted list. If this is undesired, you can run [`undo`](#restoring-the-contact-list-undo)  immediately to restore the previous order.

</box>

<br>
  
### Clearing all entries : `clear`
Format: `clear`

* You can use this to instantly clear the entire record.

<box type="tip" seamless>

**Tip on accidental clear**
> If you accidentally ran `clear`, you can run [`undo`](#restoring-the-contact-list-undo)  to restore the cleared entries.
</box>

<br>

### Exiting the program : `exit`
Format: `exit`

<br>

### Input Validation, Duplicate Handling and Utilities
| Parameter        | Input Validation                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            | Duplicate Handling                                               | Whitespace Trimming Utility                                                                                                                                                                                                                                |
|------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| NAME             | 1. Cannot be empty<br>2. Only letter, whitespaces and forward slash<br/>3. Letters immediately beside forward slash must be uppercase (e.g. `S/O`)                                                                                                                                                                                                                                                                                                                                                                                                          | case-*insensitive* comparison                                    | 1. Leading, trailing and internal whitespaces for `/` will be trimmed (e.g.   `S   /  O` will be trimmed to `S/O`). <br/> 2.Internal whitespaces between words will be trimmed to 1.                                                                       |
| PHONE            | 1. Can be empty<br>2.  `+` then immediately followed by COUNTRY_CODE(1 to 3 digits) followed by space followed by PHONE(3 to 15 digits)<br/>                                                                                                                                                                                                                                                                                                                                                                                                                | digits and whitespaces match exactly                             | 1. Leading and trailing whitespaces will be trimmed.<br/>2. Internal whitespaces between `+` and COUNTRY_CODE will be trimmed. <br/>3. Internal whitespaces in PHONE will be trimmed to 1.<br/>(e.g. ` +  33 22 34 55 ` will be trimmed to `+33 22 34 55`) |
| EMAIL            | 1. Can be empty<br>2.  Emails should be of the format `local-part@domain`, where `local-part` should:<br/>a .contain only alphanumeric characters and `+_.-`<br/>b. not start or end with `+_.-`<br/> c. not contain consecutive `+_.-`<br/>3. and `domain` is made of domain labels where each should:<br>a. be separated by `.`<br/>b. contain only alphanumeric characters and hyphens<br/>c. not contain consecutive hyphens<br/>d. start and end only with alphanumeric characters<br/>e. be at least 2 characters long for the last domain label<br/> | case-*sensitive* comparison                                      | Leading, trailing and internal whitespaces will be trimmed.                                                                                                                                                                                                |
| ADDRESS          | 1. Can be empty<br/>2.  Only alphanumeric characters, whitespaces and `#,-<`<br/> 3. At most 100 characters long                                                                                                                                                                                                                                                                                                                                                                                                                                            | case-*insensitive* comparison                                    | 1. Leading and trailing whitespaces will be trimmed.<br/> 2. Internal whitespaces will be trimmed to 1.                                                                                                                                                    |
| SALARY           | 1. Can be empty<br/>2.  Only digits                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         | digits match exactly                                             | Leading, trailing and internal whitespaces will be trimmed.                                                                                                                                                                                                |
| TAG              | 1. Only alphanumeric characters and `!@#$?\|<>_*&:;=`<br/>2. At most 30 characters long                                                                                                                                                                                                                                                                                                                                                                                                                                                                     | case-*sensitive* match.<br>Colours do not affect duplicate match | Leading and trailing whitespaces will be trimmed.                                                                                                                                                                                                          |
| CERT_NAME        | 1. Only alphanumeric characters and whitespaces<br/>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        | case-*insensitive* match                                         | 1. Leading and trailing whitespaces will be trimmed.<br/> 2. Internal whitespaces will be trimmed to 1.                                                                                                                                                    |
| CERT_EXPIRY_DATE | 1. If used in a compulsory prefix, must follow format `YYYY-MM-DD` or be empty <br/>2. If not empty, must be a valid date.<br/>                                                                                                                                                                                                                                                                                                                                                                                                                             | same `YYYY-MM-DD` or is empty                                    | Leading and trailing whitespaces will be trimmed.                                                                                                                                                                                                          |
| INDEX            | 1. Must be a positive non-zero integer (e.g. 1)<br/>2. Cannot be higher than the recorded number of employees.<br/>3. No internal whitespaces are allowed (e.g. if contact list has a person at index `10`, INDEX `10` is valid while `1 0` is invalid)                                                                                                                                                                                                                                                                                                     | N.A.                                                             | Leading and trailing whitespaces will be trimmed.                                                                                                                                                                                                          |

<box type="info" seamless>

**Additional infomation on duplicate persons**
> Possible right after executing [`add`](#adding-a-new-contact-add) or [`edit`](#editing-an-existing-contact-edit) commands<br>
> (1) [`EMAIL`](#input-validation-duplicate-handling-and-utilities) and [`PHONE`](#input-validation-duplicate-handling-and-utilities) are empty: duplicates if [`NAME`](#input-validation-duplicate-handling-and-utilities) are the same<br>
> (2) Else, 2 persons are duplicates if their [`NAME`](#input-validation-duplicate-handling-and-utilities) & [`PHONE`](#input-validation-duplicate-handling-and-utilities) & [`EMAIL`](#input-validation-duplicate-handling-and-utilities) are the same<br><br>
> **Good news**: there will be a warning pop-up message if duplicate persons are detected after executing a command. It is then up to you to delete duplicates.

**Additional infomation on duplicate certificates**
> Possible right after executing [`cert-add`](#adding-certificates-cert-add) or [`cert-edit`](#editing-certificates-cert-edit) commands<br>
> Certificates are duplicates if [`CERT_NAME`](#input-validation-duplicate-handling-and-utilities) are duplicates. [`CERT_EXPIRY_DATE`](#input-validation-duplicate-handling-and-utilities) is not taken into account.

**Additional infomation on duplicate tags**
> Possible right after executing [`tag`](#adding-and-deleting-tags-tag), see [`tag`](#adding-and-deleting-tags-tag) for more details.

</box>

<br>

### Saving the data
Big Brother's data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file
Big Brother's data is saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**CAUTION:**
> If your changes to the data file makes its format invalid, Big Brother will discard all data and start with an empty data file at the next run.  Hence, it is **recommended to make a manual backup of the file before editing it**. Support for the prevention of data loss in the event of corrupted or wrongly-formatted data is planned to be added in a future update. <br><br>
> Furthermore, certain edits can cause the Big Brother to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.

</box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Big Brother home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary
| Format                                                                                                        |
|---------------------------------------------------------------------------------------------------------------|
| [`add INDEX n/NAME [p/PHONE] [e/EMAIL] [a/ADDRESS] [s/SALARY]`](#adding-a-new-contact-add)                    |
| [`edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [s/SALARY]`](#editing-an-existing-contact-edit)         |
| [`delete INDEX`](#deleting-an-existing-contact-delete)                                                        |
| [`clear`](#clearing-all-entries-clear)                                                                        |
| [`undo`](#restoring-the-contact-list-undo)                                                                    |
| [`cert-add INDEX n/CERT_NAME [e/CERT_EXPIRY_DATE]`](#adding-certificates-cert-add)                            |
| [`cert-edit INDEX n/CERT_NAME [ne/NEW_CERT_NAME] [ee/NEW_CERT_EXPIRY_CERT]`](#editing-certificates-cert-edit) |
| [`cert-del INDEX n/CERT_NAME`](#deleting-certificates-cert-del)                                               |
| [`tag INDEX [a/TAGS_TO_ADD] [c/COLOUR_OF_TAGS_TO_ADD] [d/TAGS_TO_DELETE]`](#adding-and-deleting-tags-tag)     |
| [`sort`](#sorting-all-contacts-sort)                                                                          |
| [`find [n/NAME] [t/TAG] [c/CERT_NAME] [e/CERT_EXPIRY_DATE]`](#searching-contacts-by-criteria-find)            |
| [`list`](#listing-all-contacts-list)                                                                          |
| [`exit`](#exiting-the-program-exit)                                                                           |
| [`help`](#viewing-in-app-help-menu-help)                                                                      |
