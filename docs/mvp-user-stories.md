# MVP User Stories

## Vehicle Management

As a user, I want to add a vehicle so that I can track its history.

Acceptance criteria:

- I can choose the vehicle type.
- I can add a name.
- I can add manufacturer and model.
- I can add a registration number if it exists.
- I can add current mileage.
- I can optionally add a photo.

## Vehicle Timeline

As a user, I want to see all actions for a vehicle in one timeline so that I understand what happened and when.

Acceptance criteria:

- Events are ordered by date.
- Each event shows title, type, date, and mileage if available.
- Events can contain photos.
- Events can contain cost and shop information.

## Add Maintenance Event

As a user, I want to record an oil change so that I know when and at what mileage it happened.

Acceptance criteria:

- I can select event type "oil change".
- I can add oil photo.
- I can add shop name and address.
- I can add price.
- I can add mileage.
- I can add a comment.

## Add Modification Event

As a user, I want to record an installed part so that I remember what was installed and where it came from.

Acceptance criteria:

- I can select event type "installed part".
- I can add part photos.
- I can add where I bought the part.
- I can add cost.
- I can add receipt photo.
- I can add comments.

## Participants

As a user, I want to mark people who helped me so that the event history shows who did what.

Acceptance criteria:

- I can add a helper name.
- I can describe what the helper did.
- An event can have multiple helpers.

## Maintenance Plans

As a user, I want to create a future task so that I do not forget planned work.

Acceptance criteria:

- I can add title.
- I can select vehicle.
- I can choose planned date and time.
- I can choose reminder date and time.
- I can add photos or notes.
- I receive a local reminder.

## Local Privacy

As a user, I want my data to stay on my device in the first version so that I can use the app before server sync exists.

Acceptance criteria:

- Data is stored locally.
- The app works offline.
- No data is sent to a server in the MVP.
