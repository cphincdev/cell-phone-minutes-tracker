Volume
3
UNIVERSITY OF WINDSOR
03-60422 Agile Software Development Project
Cell Phone Minutes and SMS Tracker

GROUP NUMBER 1
http://sites.google.com/site/cellminstracker/









> GROUP MEMBERS:
> > HAN WEI DONG
> > RAHBIVEER SHERGILL
> > ROHAN MAHENDROO



> Table of Contents

Chapter 1 System Requirement…………………………. 1
  1. 1 Hardware Requirement………………………...  1
  1. 2 Operation System and Software……………. 1

Chapter 2 Our Android application project…………………1
> 2-1 project details………………….1
> 2-2project interface……………….
> 2-3Working…………..

Chapter3 Agile Practices in our Project…………………
> 3-1	XP
> > 3-1-1XP practices
> > 3-1-2Design Patterns for XP

> 3-2 TDD (Test Driven development)
> > 3-2-1 android unit testing

Chapter4 Issues Face and Overcame

> 4-1 Common Problems
> 4-2 General Issues Related to Our Project
> 4-3 New Discoveries and Implementations






> Chapter 1 System Requirement
1-1	Hardware Requirement
Need an android phone or tablet with option of SIM Card. This application needs SIM card or GSM activated to run the Application.
1-2	Operation System and Software
Operation System: Windows XP/Vista/7 32bit or 64 bit, Linux 2.6 or above
Eclipse and Android
Chapter 2 Our Android Application Project
> 2-1 Project Details
Cell Phone Minutes Tracker is an Mobile app for Android Smart phones where the main idea is to keep a track of your cell phone minutes used and SMS's sent while keeping in consideration of your plan's allotted minutes and SMS (if opted by the user and the service provider).

•	Runs in the background and keeps track of your phone activity (call timer and SMS)
•	Allows the user to keep a critical cap (limit) for the minutes and SMSes sent through the app's interface.
•	Prompt's the user according to the cap set when user is beginning to type a SMS or making a Call.
•	User has to enter the Plan details and has an              option for "fab" (favorite) numbers.
•	User has to enter the minutes used and SMS sent so far and the billing cycle date, only once on the first run.
•	User can edit the plan details any time.

> 2-2Project Interface




2-3Working



Chapter 3 Agile Practices in our Project
3-1-1 XP practices

•	Pair programming
Pair programming involves developers working together on the same code.
It facilitates and ensures code quality Bugs can be caught early Adherence to design standards is ensured Poor style and standards violations are prevented.

•	Continuous integration
Continuous integration involves integrating even minor changes into the build
Minor changes result in small code changes
Small code changes necessarily have simple interaction with the rest of the software
Simple interactions are unlikely to cause large integration errors.

•	Coding standard
Jalopy: Re-formats code to a user-defined style
This program has an Ant task so it can automate
Check Style: Verifies that code adheres to coding styles
PMD: Checks code for common anti patterns, or bad practices
J Depend: Checks coupling between classes, and generates a report
Y Guard: Obfuscates code to prevent reverse engineering.

•	Refactoring
Refactoring is re-working your code

•	Small releases
The small releases are XP projects progress in a series of iterations which is short iterations that the team can deliver properly.

•	The planning game
The “Planning Game” is the XP name for released and iteration planning during which the developer and customer make predictions about future.

•	Testing
It is just a practice of XP its focus on testing.

•	Sustainable pace
An XP team encourages working at a sustainable pace. The XP team moving at a consistent but brisk pace will achieve more over a period of time than will a team working at a pace they can’t sustain over a long period.

•	Team code ownership
The common among non-XP teams for individual developers to “own” or assume full responsibility for portions of a system’s code.

•	Simple design
The team has the simplest design that delivers the features.
	The operational code and the test code fully convey the programmer’s intent for the behavior of the code.
	There is no duplicated code.
	The system uses the least number of classes
	The system uses the least number of methods.

•	On-Site Customer
It used to be common for a customer to write a requirements document, throw it over a wall to programmers who would write the code and then throw the system over another wall to some testers.

3-1-2Design Patterns for XP
•	     Single-responsibility principle
•	 Open-closed principle
•	 Liskov substitution principle
•	 Dependency-inversion principle
•	 Interface-segregation principle


3-2 TDD (Test Driven development)

TDD emphasizes testing first, writing code later

3-2-1 Android Unit Testing
Junit Test  is an open source framework designed for the purpose of writing and running tests in the Java programming language.  Junit,  originally written by Erich Gamma and Kent Beck, has been important in the evolution of test-driven development, which is part of a larger software design paradigm known as Extreme Programming
Chapter4  Issues Face and Overcame
> 4-1 Common Problems
Understanding and facing new android and java exceptions libraries and features such as:
•	Null pointer exceptions
•	Unknown errors
•	Class path exceptions
•	Zygote.Runtime exceptions
•	SQLiteOpenHelper exceptions
•	SQLiteDataBase exceptions
4-2 General Issues Related to Our Project
4-3 New Discoveries and Implementations