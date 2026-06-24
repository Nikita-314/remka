# Architecture

This document describes the intended architecture for Remka.

## First Version

The first version is local-first:

- the Android app stores data on the device;
- the app works without an internet connection;
- reminders are scheduled locally;
- server sync is added later.

This makes the project easier to learn and test.

## Android App Layers

Recommended structure:

```text
app/
  data/
    db/
    repository/
    crypto/
  domain/
    model/
    usecase/
  ui/
    screen/
    component/
    theme/
  reminder/
```

### UI Layer

Uses Jetpack Compose.

Responsibilities:

- display screens;
- collect user input;
- show loading and empty states;
- call ViewModels.

### Domain Layer

Contains app concepts and business logic.

Responsibilities:

- vehicle models;
- event models;
- plan models;
- validation rules;
- use cases.

### Data Layer

Handles persistence and sync.

Responsibilities:

- Room entities;
- DAOs;
- repositories;
- file storage;
- encryption before sync.

### Reminder Layer

Uses WorkManager for planned maintenance reminders.

Responsibilities:

- schedule reminders;
- cancel reminders;
- reschedule reminders after edits.

## Client-Side Encryption

The long-term privacy goal is that the server stores data but cannot read it.

High-level approach:

```text
plain user data
  -> serialize
  -> encrypt on device
  -> upload encrypted bytes
  -> store on server
```

The server should only receive:

- encrypted record payloads;
- encrypted files;
- technical metadata needed for sync.

The server should avoid receiving readable:

- comments;
- photos;
- receipt contents;
- registration numbers;
- addresses;
- names of helpers;
- shop details.

Some metadata may remain visible for sync, such as:

- user id;
- encrypted record id;
- record type;
- created at;
- updated at;
- deleted flag.

This tradeoff can be improved later, but the first goal is to protect the actual user content.

## Backend Later

Possible backend modules:

```text
server/
  auth/
  sync/
  files/
  users/
```

Server responsibilities:

- authenticate users;
- store encrypted records;
- store encrypted files;
- return sync changes;
- never decrypt private content.

## Open Questions

- How should account recovery work if encryption keys are lost?
- Should users be able to share one vehicle with another user?
- Should shared vehicle data use group keys?
- Should attachments be searchable locally only?
- Which metadata is acceptable to store unencrypted?
