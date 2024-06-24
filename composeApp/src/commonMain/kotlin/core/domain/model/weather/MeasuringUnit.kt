package core.domain.model.weather

enum class MeasuringUnit(val value: String, val label: String) {


    STANDARD("Standard", "F"),
    METRIC("Metric", "C"),
    IMPERIAL("Imperial", "F"),

}