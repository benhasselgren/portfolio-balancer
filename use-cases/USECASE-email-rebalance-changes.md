# USE CASE: 8 - Send user an email with portfolio changes when it's rebalanced.

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *user* I want to receive an email when my portfolio is rebalanced with the changes made to the portfolio to be able to make the changes on my real portfolio.

### Level

Primary task.

### Preconditions

App has been rebalanced by user.

### Success End Condition

User is sent email.

### Failed End Condition

User is not sent email.

### Primary Actor

App.

### Trigger

A portfolio is rebalanced.

## MAIN SUCCESS SCENARIO

1. Portfolio is rebalanced.
2. App sends email with changes to portfolio.
3. User receives email.

## EXTENSIONS

2. **When a portfolio is rebalanced, the email is not sent**:
    1. User emails developers that there is a bug
3. **When an email is sent, the user does not recieve it**:
     1. User emails developers that there is a bug
    
## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0

#### Layout Refernce
https://github.com/Kevin-Sim/SET08103/blob/master/labs/lab05/use-case-4.md