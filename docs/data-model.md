# Data Model

This is the first draft of the Remka data model.

## VehicleType

```kotlin
enum class VehicleType {
    MOTORCYCLE,
    CAR,
    SCOOTER,
    BICYCLE,
    ATV,
    BOAT,
    OTHER
}
```

## Vehicle

```kotlin
data class Vehicle(
    val id: String,
    val type: VehicleType,
    val name: String,
    val manufacturer: String?,
    val model: String?,
    val year: Int?,
    val registrationNumber: String?,
    val vinOrFrameNumber: String?,
    val currentMileage: Long?,
    val coverPhotoUri: String?,
    val createdAt: Long,
    val updatedAt: Long
)
```

## VehicleEventType

```kotlin
enum class VehicleEventType {
    MAINTENANCE,
    REPAIR,
    INSTALLED_PART,
    PURCHASE,
    DIAGNOSTIC,
    WASH,
    CUSTOM
}
```

## VehicleEvent

```kotlin
data class VehicleEvent(
    val id: String,
    val vehicleId: String,
    val type: VehicleEventType,
    val title: String,
    val happenedAt: Long,
    val mileage: Long?,
    val cost: Double?,
    val currency: String?,
    val shopName: String?,
    val shopAddress: String?,
    val workLocation: String?,
    val comment: String?,
    val createdAt: Long,
    val updatedAt: Long
)
```

## AttachmentType

```kotlin
enum class AttachmentType {
    PHOTO,
    RECEIPT,
    DOCUMENT,
    OTHER
}
```

## Attachment

```kotlin
data class Attachment(
    val id: String,
    val ownerType: String,
    val ownerId: String,
    val type: AttachmentType,
    val localUri: String?,
    val remoteEncryptedFileId: String?,
    val encryptedMetadata: String?,
    val createdAt: Long
)
```

## Participant

```kotlin
data class Participant(
    val id: String,
    val displayName: String,
    val contact: String?,
    val linkedUserId: String?
)
```

## EventParticipant

```kotlin
data class EventParticipant(
    val eventId: String,
    val participantId: String,
    val workDescription: String?,
    val comment: String?
)
```

## MaintenancePlanStatus

```kotlin
enum class MaintenancePlanStatus {
    PLANNED,
    DONE,
    CANCELLED
}
```

## MaintenancePlan

```kotlin
data class MaintenancePlan(
    val id: String,
    val vehicleId: String,
    val title: String,
    val plannedAt: Long?,
    val reminderAt: Long?,
    val targetMileage: Long?,
    val placeToBuy: String?,
    val responsiblePerson: String?,
    val comment: String?,
    val status: MaintenancePlanStatus,
    val createdAt: Long,
    val updatedAt: Long
)
```

## Notes

For the Android Room database, enums can be stored as strings with type converters.

Timestamps can start as `Long` values in milliseconds since epoch. Later, the project can move to `kotlinx.datetime.Instant` in the domain layer if needed.
