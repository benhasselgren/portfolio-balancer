# USE CASE: 4 - Allow a user to edit companies target percentages

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *user* I will be able to edit companies target percentages in the portfolio to change how money is invested across the portfolio.

### Level

Primary task.

### Preconditions

App contains all current portfolio data.

### Success End Condition

User has edited target percentages and portfolio has been rebalanced to those new percentages.

### Failed End Condition

Portfolio not rebalanced with new target percentages.

### Primary Actor

User.

### Trigger

User presses portfolio settings button, edits target percentages, and presses rebalance.

## MAIN SUCCESS SCENARIO

1. User presses portfolio settings button.
2. User changes target percentages
3. Once total percentage equals 100%, user presses rebalance.
4. Portfolio is rebalanced.

## EXTENSIONS

1. **When portfolio settings button is pressed no activity appears.**:
    1. User emails developers that there is a bug
3. **App allows user to rebelance with total percentage not equal to 100%.**:
    1. User emails developers that there is a bug
4. **Portfolio is not rebalanced.**:
    1. User emails developers that there is a bug
    
## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0