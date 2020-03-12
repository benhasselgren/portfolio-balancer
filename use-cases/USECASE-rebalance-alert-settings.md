# USE CASE: 6 - Allow a user to rebalance alert setting.

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *user* I will be able to edit the rebalance alert setting to be able to customise my portfolio rigorously.

### Level

Primary task.

### Preconditions

App contains current portfolio settings and a current alert setting value.

### Success End Condition

User has updated their rebalance alert setting.

### Failed End Condition

Rebalance alert setting not updated.

### Primary Actor

User.

### Trigger

User presses the update portfolio button after entering new alert value.

## MAIN SUCCESS SCENARIO

1. User updates alert setting.
2. Alert settings has to be between 5-30 pounds.
3. User presses update button and changes saved.

## EXTENSIONS

2. **When a user enters amount not between the range, the portfolio still accepts it**:
    1. User emails developers that there is a bug
3. **When update button is pressed the change is not succefully saved**:
    1. User emails developers that there is a bug
    
## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0

#### Layout Refernce
https://github.com/Kevin-Sim/SET08103/blob/master/labs/lab05/use-case-4.md