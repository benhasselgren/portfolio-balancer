# USE CASE: 7 - Send user a notification that a portfolio is unbalanced.

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *user* I want to receive a notification when a portfolio becomes unbalanced to be able to be more aware when a portfolio is unbalanced

### Level

Primary task.

### Preconditions

App knows if a portfolio is unbalanced.

### Success End Condition

User is notified.

### Failed End Condition

User is not notified.

### Primary Actor

App.

### Trigger

A portfolio becomes unbalanced.

## MAIN SUCCESS SCENARIO

1. Portfolio becomes unbalanced.
2. App sends notification.
3. User receives notification.

## EXTENSIONS

2. **When a portfolio is unbalanced the app does not send a notification**:
    1. User emails developers that there is a bug
    
## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0