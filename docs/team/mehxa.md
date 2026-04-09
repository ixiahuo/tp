---
  layout: default.md
  title: "Ennaaya's Project Portfolio Page"
---

### Project: Big Brother

Big Brother is a desktop address book application for HR managers in startups to maintain and update employee details. It is optimised for use via a CLI, but also has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the capability for `find` command to search for contacts by tag(s), certificate(s) and before a certain expiry date for certificates.
  * What it does: Allows the user to search and filter contacts using one category or a combination of different categories, including the ability to expand and narrow down searches via AND and OR logic.
  * Justification: This feature improves user experience significantly because a user can now search for a particular person using different attributes, or filter all records based on certain criteria (e.g Records tagged with IT and have the OSCP cert).
  * Highlights: This enhancement affects existing commands and attributes to be added in future. The implementation was challenging because I had to give the user the freedom to execute powerful & complex search commands without making the input or code overly complex.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2526-s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=Mehxa&tabRepo=AY2526S2-CS2103T-T09-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:
  * Managed releases `v1.3`-`1.5` on GitHub

* **Enhancements to existing features**:
  * Modified `find` command to be able to perform partial keyword matching across all categories 

* **Documentation**:
  * User Guide:
    * Find Command
  * Developer Guide:
    * Use Cases
    * Find Class Diagram
    * Find Command

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#241](https://github.com/NUS-CS2103-AY2526-S2/forum/issues/241#issuecomment-3916529691)
  * Contributed to forum discussions (examples: [#177](https://github.com/NUS-CS2103-AY2526-S2/forum/issues/177), [#557](https://github.com/NUS-CS2103-AY2526-S2/forum/issues/577), [#559](https://github.com/NUS-CS2103-AY2526-S2/forum/issues/559))

