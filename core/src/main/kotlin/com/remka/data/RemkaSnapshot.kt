package com.remka.data

import com.remka.domain.Attachment
import com.remka.domain.MaintenancePlan
import com.remka.domain.Vehicle
import com.remka.domain.VehicleEvent
import com.remka.domain.VehicleFolder
import com.remka.domain.UserAccount
import kotlinx.serialization.Serializable

@Serializable
data class RemkaSnapshot(
    val vehicles: List<Vehicle> = emptyList(),
    val events: List<VehicleEvent> = emptyList(),
    val plans: List<MaintenancePlan> = emptyList(),
    val attachments: List<Attachment> = emptyList(),
    val folders: List<VehicleFolder> = emptyList(),
    val currentUser: UserAccount? = null,
    val knownUsers: List<UserAccount> = emptyList(),
    val pendingSyncVersion: Long = 0,
    val lastSyncedVersion: Long = 0
)
