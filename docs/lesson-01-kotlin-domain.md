# Урок 1. Первые модели Remka на Kotlin

В этом шаге мы заменили стандартный `Hello, Kotlin` на маленький прототип приложения Remka.

## Что появилось

Основные файлы:

- `src/main/kotlin/com/remka/domain/Vehicle.kt` — модель транспорта;
- `src/main/kotlin/com/remka/domain/VehicleEvent.kt` — модель события в истории транспорта;
- `src/main/kotlin/com/remka/domain/MaintenancePlan.kt` — модель будущей задачи;
- `src/main/kotlin/com/remka/data/RemkaRepository.kt` — простое хранилище в памяти;
- `src/main/kotlin/com/remka/Main.kt` — демонстрация работы приложения.

## Kotlin: enum class

`enum class` нужен, когда значение должно быть одним из заранее известных вариантов.

Пример:

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

Так мы не пишем тип транспорта обычной строкой `"motorcycle"`, где легко ошибиться, а выбираем из безопасного списка.

## Kotlin: data class

`data class` удобен для описания данных.

Пример:

```kotlin
data class Vehicle(
    val id: String,
    val type: VehicleType,
    val name: String
)
```

Kotlin сам создаёт для такого класса полезные вещи: сравнение, красивый `toString`, копирование через `copy`.

## Kotlin: nullable-поля

Знак `?` означает, что значения может не быть.

Пример:

```kotlin
val registrationNumber: String? = null
```

Это подходит для госномера: у велосипеда или нового мотоцикла его может не быть.

## Kotlin: значения по умолчанию

У параметров можно задавать значения по умолчанию.

Пример:

```kotlin
val participants: List<EventParticipant> = emptyList()
```

Если участников события нет, мы не храним `null`, а используем пустой список.

## Kotlin: коллекции

В `RemkaRepository` пока используются обычные списки:

```kotlin
private val vehicles = mutableListOf<Vehicle>()
```

`mutableListOf` означает, что список можно изменять: добавлять транспорт, события и планы.

Позже вместо этого появится база данных Room.

## Что важно понять

Мы пока не делаем экран Android. Мы сначала описываем предметную область:

- что такое транспорт;
- что такое событие;
- что такое план;
- как получить историю конкретного транспорта.

Когда эти понятия станут понятными, Android-экран будет просто красивым способом показать те же данные.

## Как запустить

```bash
./gradlew run
```

Ожидаемый результат: в консоли появится карточка мотоцикла, история работ и будущий план.
