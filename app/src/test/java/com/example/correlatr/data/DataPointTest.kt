package com.example.correlatr.data

import com.example.data.DataPoint
import com.example.data.listToClientMessage
import com.example.data.serverResponseToList
import com.example.protos.Shared
import org.junit.Test

import org.junit.Assert.*


class DataPointTest {
    @Test
    fun listToClientMessageTest()
    {
        val dataPoints = MutableList(0) {DataPoint("", null)}
        dataPoints.add(DataPoint("foo", 4.5))
        dataPoints.add(DataPoint("bar", 0.0))
        dataPoints.add(DataPoint("baz", null))


        val results = listToClientMessage(dataPoints)
        assertEquals(results[0].columnName,"foo")
        assertEquals(results[0].value, 4.5, 1e-6)
        assertFalse(results[0].`null`)
        assertEquals(results[1].columnName,"bar")
        assertEquals(results[1].value, 0.0, 1e-6)
        assertFalse(results[1].`null`)
        assertEquals(results[2].columnName,"baz")
        assertTrue(results[2].`null`)
    }

    @Test
    fun serverResponseToListTest()
    {
        val dataPoints = MutableList(0) {Shared.DataPoint.newBuilder().build()}

        val point0 = Shared.DataPoint.newBuilder()
        point0.columnName = "foo"
        point0.value = 4.5
        dataPoints.add(point0.build())

        val point1 = Shared.DataPoint.newBuilder()
        point1.columnName = "bar"
        point1.value = 0.0
        dataPoints.add(point1.build())

        val point2 = Shared.DataPoint.newBuilder()
        point2.columnName = "baz"
        point2.`null` = true
        dataPoints.add(point2.build())

        val results = serverResponseToList(dataPoints)
        assertEquals(DataPoint("foo", 4.5), results[0])
        assertEquals(DataPoint("bar", 0.0), results[1])
        assertEquals(DataPoint("baz", null), results[2])


    }
}
