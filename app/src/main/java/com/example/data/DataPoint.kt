package com.example.data

import com.example.protos.Shared

data class DataPoint(val columnName: String, val value: Double?)

fun serverResponseToList(protoDataPoints: MutableList<Shared.DataPoint>): MutableList<DataPoint>
{
    val dataPoints = MutableList(0) { DataPoint("", null)}
    for (point in protoDataPoints)
        if (point.`null`)
            dataPoints.add(DataPoint(point.columnName, null))
        else
            dataPoints.add(DataPoint(point.columnName, point.value))

    return dataPoints
}

fun listToClientMessage(dataPoints: MutableList<DataPoint>): MutableList<Shared.DataPoint>
{
    val protoPoints = MutableList(0) {Shared.DataPoint.newBuilder().build()}
    for (point in dataPoints)
    {
        val protoPoint = Shared.DataPoint.newBuilder()
        protoPoint.columnName = point.columnName
        if (point.value != null)
            protoPoint.value = point.value
        else
            protoPoint.`null` = true

        protoPoints.add(protoPoint.build())
    }
    return protoPoints
}