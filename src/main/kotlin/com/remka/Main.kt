package com.remka

import com.remka.data.RemkaRepository
import com.remka.domain.EventParticipant
import com.remka.domain.MaintenancePlan
import com.remka.domain.Vehicle
import com.remka.domain.VehicleEvent
import com.remka.domain.VehicleEventType
import com.remka.domain.VehicleType

fun main() {
    val repository = RemkaRepository()

    val motorcycle = Vehicle(
        id = "vehicle-1",
        type = VehicleType.MOTORCYCLE,
        name = "Мой мотоцикл",
        manufacturer = "Honda",
        model = "CB400",
        year = 2007,
        registrationNumber = "A123BC",
        currentMileage = 42000
    )

    repository.addVehicle(motorcycle)

    repository.addEvent(
        VehicleEvent(
            id = "event-1",
            vehicleId = motorcycle.id,
            type = VehicleEventType.INSTALLED_PART,
            title = "Установил багажник",
            date = "2026-06-24",
            mileage = 42010,
            cost = 8500.0,
            shopName = "MotoParts",
            comment = "Багажник встал хорошо, но нужно позже проверить крепления.",
            participants = listOf(
                EventParticipant(
                    name = "Никита",
                    workDescription = "Установка и проверка креплений"
                )
            )
        )
    )

    repository.addEvent(
        VehicleEvent(
            id = "event-2",
            vehicleId = motorcycle.id,
            type = VehicleEventType.MAINTENANCE,
            title = "Поменял масло",
            date = "2026-06-24",
            mileage = 42100,
            cost = 3200.0,
            shopName = "Oil Market",
            comment = "Сливная пробка плохо закручивается, нужно заменить."
        )
    )

    repository.addPlan(
        MaintenancePlan(
            id = "plan-1",
            vehicleId = motorcycle.id,
            title = "Поменять лампочку",
            plannedDate = "2026-07-10",
            reminderDate = "2026-07-09",
            placeToBuy = "Автомагазин у дома",
            comment = "Перед покупкой проверить тип лампы."
        )
    )

    printVehicleCard(repository, motorcycle.id)
}

private fun printVehicleCard(repository: RemkaRepository, vehicleId: String) {
    val vehicle = repository.getVehicles().first { vehicle -> vehicle.id == vehicleId }
    val events = repository.getEventsForVehicle(vehicle.id)
    val plans = repository.getPlansForVehicle(vehicle.id)

    println("=== ${vehicle.name} ===")
    println("Тип: ${vehicle.type}")
    println("Модель: ${vehicle.manufacturer} ${vehicle.model}, ${vehicle.year}")
    println("Госномер: ${vehicle.registrationNumber ?: "не указан"}")
    println("Пробег: ${vehicle.currentMileage ?: "не указан"} км")
    println()

    println("История:")
    events.forEach { event ->
        println("- ${event.date}: ${event.title}")
        println("  Тип: ${event.type}")
        println("  Пробег: ${event.mileage ?: "не указан"} км")
        println("  Стоимость: ${event.cost ?: "не указана"}")
        println("  Магазин: ${event.shopName ?: "не указан"}")
        println("  Комментарий: ${event.comment ?: "нет"}")

        if (event.participants.isNotEmpty()) {
            println("  Участники:")
            event.participants.forEach { participant ->
                println("  - ${participant.name}: ${participant.workDescription ?: "работа не описана"}")
            }
        }
    }

    println()
    println("Планы:")
    plans.forEach { plan ->
        println("- ${plan.plannedDate}: ${plan.title}")
        println("  Напомнить: ${plan.reminderDate ?: "не задано"}")
        println("  Где купить: ${plan.placeToBuy ?: "не указано"}")
        println("  Комментарий: ${plan.comment ?: "нет"}")
    }
}
