# Banquet Management System

#### YANG Xikun, YANG Jinkun, REN Yixiao, Arda EREN, MIAO Xutao
### Designer of the application: YANG Xikun, YANG Jinkun
### Maintainer of the repo: YANG Jinkun
### Designer of the database schemas: REN Yixiao

## Overview
This document outlines the database schema for a Banquet Management System. The system manages banquets, meals, attendees, registrations, and administrators.

## Tables

### BANQUETS
Stores information about banquet events.

| Column | Type | Constraints                         | Description |
|--------|------|-------------------------------------|-------------|
| `BIN` | `INTEGER` | `PRIMARY KEY, AUTOINCREMENT`        | Unique banquet identifier |
| `Name` | `VARCHAR(255)` | `NOT NULL`                            | Name of the banquet |
| `DateTime` | `DATETIME` | `NOT NULL`                            | Date and time of the banquet |
| `Address` | `VARCHAR(255)` | `NOT NULL`                            | Physical address |
| `Location` | `VARCHAR(255)` | `NOT NULL`                            | Specific location details |
| `ContactStaffName` | `VARCHAR(255)` | `NOT NULL`                            | Staff contact person |
| `Available` | `CHAR(1)` | `NOT NULL, CHECK ( IN ('Y', 'N') )` | Availability status |
| `Quota` | `INTEGER` | `NOT NULL, CHECK ( >= 0 )`          | Maximum capacity |

### MEALS
Contains meal options for each banquet.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `BIN` | `INTEGER` | `NOT NULL, FK` | Reference to `BANQUETS` |
| `ID` | `INTEGER` | `NOT NULL` | Meal identifier |
| `Name` | `VARCHAR(255)` | `NOT NULL` | Name of the meal |
| `Type` | `VARCHAR(255)` | `NOT NULL` | Type of meal |
| `Price` | `DECIMAL(5,2)` | `NOT NULL` | Cost of the meal |
| `SpecialCuisine` | `VARCHAR(255)` |  | Special cuisine details |

Primary Key: `(BIN, ID)`  
Foreign Key: `BIN REFERENCES BANQUETS(BIN)`

### ATTENDEE_ACCOUNTS
Manages attendee information.

| Column | Type | Constraints                   | Description |
|--------|------|-------------------------------|-------------|
| `ID` | `VARCHAR(255)` | `PRIMARY KEY`                 | Unique attendee identifier |
| `HashedPassword` | `VARCHAR(255)` | `NOT NULL`                    | Encrypted password |
| `HashedSalt` | `VARCHAR(255)` | `NOT NULL`                    | Password salt |
| `Name` | `VARCHAR(255)` | `NOT NULL`                    | Attendee name |
| `Type` | `VARCHAR(255)` | `NOT NULL`                    | Attendee type |
| `MobileNo` | `INTEGER` | `NOT NULL, CHECK( 8 DIGITS )` | Contact number |
| `Organization` | `VARCHAR(255)` |                               | Associated organization |

### REGISTRATIONS
Tracks banquet registrations.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| ID | `INTEGER` | `PRIMARY KEY, AUTOINCREMENT` | Registration identifier |
| AttendeeID | `VARCHAR(255)` | `NOT NULL, FK` | Reference to `ATTENDEE_ACCOUNTS` |
| GuestName | `VARCHAR(255)` | `NOT NULL` | Name of guest |
| BIN | `INTEGER` | `NOT NULL, FK` | Reference to `BANQUETS` |
| MealID | `INTEGER` | `NOT NULL, FK` | Reference to `MEALS` |
| Drink | `VARCHAR(255)` | `NOT NULL` | Drink preference |
| Seat | `VARCHAR(255)` |  | Seat assignment |

Foreign Keys:
- `AttendeeID REFERENCES ATTENDEE_ACCOUNTS(ID)`
- `BIN REFERENCES BANQUETS(BIN)`
- `(BIN, MealID) REFERENCES MEALS(BIN, ID)`

### ADMINISTRATORS
Stores administrator accounts.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `ID` | `VARCHAR(255)` | `PRIMARY KEY` | Admin identifier |
| `HashedPassword` | `VARCHAR(255)` | `NOT NULL` | Encrypted password |
| `HashedSalt` | `VARCHAR(255)` | `NOT NULL` | Password salt |

## Relationships
- Each `BANQUET` can have 0 ~ 4 `MEALS`
- Each `REGISTRATION` is associated with one `ATTENDEE_ACCOUNT`, one `BANQUET`, and one `MEAL`
- All foreign key relationships implements `ON DELETE CASCADE`.

## Notes
- Mobile numbers are restricted to `8` digits
- Availability is marked with `Y` or `N`
- Quota must be non-negative
- `DateTime` is stored as `TEXT` in SQLite

## DDL for the table creation
SQL DDL courtesy of YANG Xikun and REN Yixiao:

```sql
PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS BANQUETS (
    BIN INTEGER PRIMARY KEY AUTOINCREMENT,
    Name VARCHAR(255) NOT NULL,
    DateTime DATETIME NOT NULL, -- This will then be TEXT.
    Address VARCHAR(255) NOT NULL,
    Location VARCHAR(255) NOT NULL,
    ContactStaffName VARCHAR(255) NOT NULL,
    Available CHAR(1) NOT NULL CHECK ( Available IN ('Y', 'N') ),
    Quota INTEGER NOT NULL CHECK ( Quota >= 0 )
);

CREATE TABLE IF NOT EXISTS MEALS (
    BIN INTEGER NOT NULL,
    ID INTEGER NOT NULL,
    Name VARCHAR(255) NOT NULL,
    Type VARCHAR(255) NOT NULL,
    Price DECIMAL(5,2) NOT NULL CHECK ( Price >= 0 ),
    SpecialCuisine VARCHAR(255),
    CONSTRAINT MEALS_PK PRIMARY KEY (BIN, ID),
    CONSTRAINT MEALS_FK FOREIGN KEY (BIN) REFERENCES BANQUETS(BIN) ON DELETE CASCADE -- ON UPDATE CASCADE;
);

CREATE TABLE IF NOT EXISTS ATTENDEE_ACCOUNTS (
    ID VARCHAR(255) PRIMARY KEY CHECK ( ID LIKE '_%@_%._%' ),
    HashedPassword VARCHAR(255) NOT NULL,
    HashedSalt VARCHAR(255) NOT NULL,
    Name VARCHAR(255) NOT NULL, -- the check are assigned to the Model part
    Address VARCHAR(255),
    Type VARCHAR(255) NOT NULL,
    MobileNo INTEGER NOT NULL CHECK ( LENGTH ( CAST ( MobileNo AS TEXT ) ) = 8 ),
    Organization VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS REGISTRATIONS (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    AttendeeID VARCHAR(255),
    GuestName VARCHAR(255),
    BIN INTEGER NOT NULL,
    MealID INTEGER NOT NULL,
    Drink VARCHAR(255) NOT NULL,
    Seat VARCHAR(255),
    FOREIGN KEY (AttendeeID) REFERENCES ATTENDEE_ACCOUNTS(ID) ON DELETE CASCADE,
    FOREIGN KEY (BIN) REFERENCES BANQUETS(BIN) ON DELETE CASCADE,
    FOREIGN KEY (BIN, MealID) REFERENCES MEALS(BIN, ID) ON DELETE CASCADE,
    CONSTRAINT IDENTIFIER CHECK ( AttendeeID IS NOT NULL OR GuestName IS NOT NULL )
);

CREATE TABLE IF NOT EXISTS ADMINISTRATORS (
    ID VARCHAR(255) PRIMARY KEY,
    HashedPassword VARCHAR(255) NOT NULL,
    HashedSalt VARCHAR(255) NOT NULL
);
```