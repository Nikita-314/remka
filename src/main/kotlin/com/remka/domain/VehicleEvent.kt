package com.remka.domain

enum class VehicleEventType {
    MAINTENANCE,
    REPAIR,
    INSTALLED_PART,
    PURCHASE,
    DIAGNOSTIC,
    WASH,
    CUSTOM
}

data class VehicleEvent(
    val id: String,
    val vehicleId: String,
    val type: VehicleEventType,
    val title: String,
    val date: String,
    val mileage: Long? = null,
    val cost: Double? = null,
    val shopName: String? = null,
    val comment: String? = null,
    val participants: List<EventParticipant> = emptyList()
)

data class EventParticipant(
    val name: String,
    val workDescription: String? = null
)
