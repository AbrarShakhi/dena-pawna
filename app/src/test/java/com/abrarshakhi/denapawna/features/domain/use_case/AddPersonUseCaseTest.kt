package com.abrarshakhi.denapawna.features.domain.use_case

import com.abrarshakhi.denapawna.core.utils.Outcome
import com.abrarshakhi.denapawna.features.data.repository.FakePersonRepository
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
            fullName = "Tom",
            phone = ""
        )

        assertTrue(result is Outcome.Err)
    }


    @Test
    fun `empty phone is allowed`() = runTest {
        val result = useCase.addPerson(
            fullName = "John Doe",
            phone = ""
        )

        assertTrue(result is Outcome.Ok)
        assertNull(repository.addedPhone)
    }

    @Test
    fun `invalid phone returns error`() = runTest {
        val result = useCase.addPerson(
            fullName = "John Doe",
            phone = "123abc"
        )

        assertTrue(result is Outcome.Err)
    }

    @Test
    fun `valid phone is saved correctly`() = runTest {
        val result = useCase.addPerson(
            fullName = "John Doe",
            phone = "+8801712345678"
        )

        assertTrue(result is Outcome.Ok)
        assertEquals("John Doe", repository.addedFullName)
        assertEquals("+8801712345678", repository.addedPhone)
    }

    @Test
    fun `phone is trimmed before saving`() = runTest {
        val result = useCase.addPerson(
            fullName = "  John Doe  ",
            phone = "  01712345678  "
        )

        assertTrue(result is Outcome.Ok)
        assertEquals("John Doe", repository.addedFullName)
        assertEquals("01712345678", repository.addedPhone)
    }

}