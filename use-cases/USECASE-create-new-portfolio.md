# USE CASE: 2 - Allow a user to create a new portfolio

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *user* I want to be able to create a new portfolio so I can have a myriad of portfolios on the app.

### Level

Primary task.

### Preconditions

App contains all current company share price data.

### Success End Condition

User has created a new portfolio.

### Failed End Condition

New portfolio not created.

### Primary Actor

User.

### Trigger

User presses the add portfolio button on the main home screen.

## MAIN SUCCESS SCENARIO

1. User presses the add portfolio button.
2. User enters the porfolios details (name, descrtiption, amount) and presses next.
3. User searches for adds companies that they want to invest in, and presses next when finished.
4. User sets target percentages for each company and when percentage equals 100%, they can then create portfolio.

## EXTENSIONS

1. **When add button is pressed the create porfolio activity doesn't appear.**:
    1. User emails developers that there is a bug
2. **When next button is pressed the add companies activity doesn't appear.**:
    1. User emails developers that there is a bug
3. **When next button is pressed the set target percentages activity doesn't appear.**:
    1. User emails developers that there is a bug
4. **When create portfolio button is pressed portfolio is not created succesfully.**:
    1. User emails developers that there is a bug
    
## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0

#### Layout Refernce
https://github.com/Kevin-Sim/SET08103/blob/master/labs/lab05/use-case-4.md