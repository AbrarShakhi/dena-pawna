import com.abrarshakhi.denapawna.features.data.local.entity.EntryEntity
import com.abrarshakhi.denapawna.features.data.local.entity.PersonEntity
import com.abrarshakhi.denapawna.features.domain.model.Entry
import com.abrarshakhi.denapawna.features.domain.model.Person


fun PersonEntity.toDomain(entries: List<EntryEntity>): Person {
    return Person(
        id = this.personId,
        fullName = this.name,
        totalAmount = entries.sumOf { it.amount },
        entries = entries.map { it.toDomain() })
}

fun EntryEntity.toDomain(): Entry {
    return Entry(
        createdAt = this.createdAt, amount = this.amount, type = this.type, note = this.note
    )
}