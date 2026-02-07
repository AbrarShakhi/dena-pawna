package com.abrarshakhi.denapawna.features.domain.use_case

import com.abrarshakhi.denapawna.core.utils.Outcome
import com.abrarshakhi.denapawna.features.data.repository.FakePersonRepository
import com.abrarshakhi.denapawna.features.domain.model.Person
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddPersonUseCaseTest {

    private lateinit var repository: FakePersonRepository
    private lateinit var useCase: AddPersonUseCase

    @Before
    fun setup() {
        repository = FakePersonRepository()
        useCase = AddPersonUseCase(repository)
    }

    @Test
    fun `name shorter than 4 chars returns error`() = runTest {
        val result = useCase.addPerson(
            Person(
                id = 0, fullName = "Tom", phoneNumber = null
            )
        )

        assertTrue(result is Outcome.Err)
        assertNull(repository.addedPerson)
    }

    @Test
    fun `empty phone is allowed`() = runTest {
        val result = useCase.addPerson(
            Person(
                id = 0, fullName = "John Doe", phoneNumber = ""
            )
        )

        assertTrue(result is Outcome.Ok)
        assertEquals("John Doe", repository.addedPerson?.fullName)
        assertNull(repository.addedPerson?.phoneNumber)
    }

    @Test
    fun `invalid phone returns error`() = runTest {
        val result = useCase.addPerson(
            Person(
                id = 0, fullName = "John Doe", phoneNumber = "123abc"
            )
        )

        assertTrue(result is Outcome.Err)
        assertNull(repository.addedPerson)
    }

    @Test
    fun `valid phone is saved correctly`() = runTest {
        val result = useCase.addPerson(
            Person(
                id = 0, fullName = "John Doe", phoneNumber = "+8801712345678"
            )
        )

        assertTrue(result is Outcome.Ok)
        assertEquals("John Doe", repository.addedPerson?.fullName)
        assertEquals("+8801712345678", repository.addedPerson?.phoneNumber)
    }

    @Test
    fun `name and phone are trimmed before saving`() = runTest {
        val result = useCase.addPerson(
            Person(
                id = 0, fullName = "  John Doe  ", phoneNumber = "  01712345678  "
            )
        )

        assertTrue(result is Outcome.Ok)
        assertEquals("John Doe", repository.addedPerson?.fullName)
        assertEquals("01712345678", repository.addedPerson?.phoneNumber)
    }

}