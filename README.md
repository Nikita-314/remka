# Remka

Remka is a mobile app for keeping a private, visual maintenance history for vehicles: motorcycles, cars, scooters, bicycles, ATVs, boats, and anything else a user wants to care for.

The project is intentionally built as a learning Kotlin project. The first target is an Android app written in Kotlin with Jetpack Compose.

## Product Idea

A user can add a vehicle, for example a motorcycle, and then keep a beautiful and useful history of everything that happens to it:

- installed parts, such as a luggage rack;
- maintenance work, such as an oil change;
- repairs and diagnostics;
- purchases with price, shop, address, and receipt;
- photos before, during, and after work;
- mileage at the moment of the event;
- comments and custom notes;
- people who helped and what exactly they did;
- future plans with reminders.

The app should feel like a clear timeline of the vehicle's life, not like a boring spreadsheet.

## MVP

The first version should work without a server. This keeps the learning path smaller and lets us build the core experience first.

MVP features:

- create, edit, and delete vehicles;
- show a visual vehicle card;
- create maintenance and modification events;
- attach photos to events;
- add cost, shop, address, mileage, and comments;
- create future tasks;
- show reminders for planned tasks;
- store data locally on the device.

Recommended Android stack:

- Kotlin;
- Jetpack Compose for UI;
- Room for local database;
- DataStore for app settings;
- WorkManager for reminders;
- Coil for image loading;
- Material 3 for UI components.

## Later Versions

After the local MVP is stable, the project can grow into a synced private app.

Possible backend stack:

- Kotlin Ktor server;
- PostgreSQL database;
- S3-compatible object storage for photos and receipts;
- user accounts;
- encrypted sync between devices.

## Privacy Model

The app stores sensitive personal information: vehicle numbers, locations, receipts, repair history, photos, and names of people involved. The long-term goal is to protect this data even if someone gets access to the server.

The preferred model is client-side encryption:

- the Android app encrypts data before uploading it;
- the server stores encrypted records and encrypted files;
- the server should not be able to read private user data;
- encryption keys stay on the user's device or are protected by the user's secret;
- Android Keystore can protect local keys on the device.

This affects architecture from the beginning. Data sync, file storage, search, account recovery, and sharing all need to be designed with encryption in mind.

## Main Entities

### Vehicle

A vehicle owned or tracked by the user.

Fields:

- id;
- type: motorcycle, car, scooter, bicycle, ATV, boat, other;
- name;
- manufacturer;
- model;
- year;
- registration number;
- vin or frame number;
- current mileage;
- cover photo;
- created at;
- updated at.

### VehicleEvent

An event in the vehicle timeline.

Examples:

- installed luggage rack;
- changed oil;
- replaced lamp;
- fixed drain plug;
- washed vehicle;
- bought spare part;
- custom event.

Fields:

- id;
- vehicle id;
- type;
- title;
- date and time;
- mileage;
- cost;
- currency;
- shop name;
- shop address;
- work location;
- comment;
- created by user id;
- created at;
- updated at.

### Attachment

A file connected to a vehicle, event, purchase, or plan.

Fields:

- id;
- owner type;
- owner id;
- file type: photo, receipt, document, other;
- local uri;
- remote encrypted file id;
- encrypted file metadata;
- created at.

### Participant

A person who helped with a task.

Fields:

- id;
- display name;
- optional contact;
- optional user id if this person also uses the app.

### EventParticipant

The connection between an event and a participant.

Fields:

- event id;
- participant id;
- role or work description;
- comment.

### MaintenancePlan

A future task.

Examples:

- replace lamp on July 10;
- buy oil filter;
- ask someone to bring a part;
- check chain tension after 500 km.

Fields:

- id;
- vehicle id;
- title;
- planned date and time;
- reminder date and time;
- target mileage;
- place to buy;
- responsible person;
- comment;
- status: planned, done, cancelled;
- created at;
- updated at.

## First Screens

1. Vehicle list
   Shows all vehicles as visual cards.

2. Add vehicle
   Lets the user choose vehicle type, name, model, number, mileage, and photo.

3. Vehicle details
   Shows the vehicle summary, timeline, plans, and attachments.

4. Add event
   Lets the user add a maintenance, repair, purchase, or custom event.

5. Add plan
   Lets the user create a future task with a reminder.

6. Event details
   Shows photos, cost, shop, mileage, comments, and participants.

## Development Roadmap

### Stage 1: Project Setup

- Create Android project.
- Configure Kotlin, Compose, Material 3.
- Add basic navigation.
- Add app theme.

### Stage 2: Local Vehicle List

- Create Vehicle model.
- Create Room database.
- Add vehicle list screen.
- Add vehicle creation screen.

### Stage 3: Vehicle Timeline

- Create VehicleEvent model.
- Add event list for each vehicle.
- Add event creation screen.
- Support basic event types.

### Stage 4: Photos and Receipts

- Add image picker.
- Store local attachment references.
- Show photo gallery inside event details.

### Stage 5: Plans and Reminders

- Create MaintenancePlan model.
- Add plan creation screen.
- Schedule local reminders with WorkManager.

### Stage 6: Better UX

- Add icons and visual vehicle cards.
- Add filters by event type.
- Add cost summaries.
- Add mileage-based hints.

### Stage 7: Encryption Preparation

- Add local crypto service interface.
- Decide which fields must be encrypted.
- Prepare encrypted export/import format.

### Stage 8: Backend Prototype

- Create Ktor server.
- Add auth.
- Add encrypted record sync.
- Add encrypted file upload.

## Learning Notes

The project should be developed in small steps. Each step should produce a working app, even if it is simple.

Good learning order:

1. Kotlin basics.
2. Compose screens.
3. Navigation.
4. Local database with Room.
5. Image picker and file handling.
6. Reminders.
7. Clean architecture basics.
8. Networking.
9. Encryption.
10. Backend.

## Current Status

The repository contains the initial product and architecture plan. The Android project has not been generated yet.
