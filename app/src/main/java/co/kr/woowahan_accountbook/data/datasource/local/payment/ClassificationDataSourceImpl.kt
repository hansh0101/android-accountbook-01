package co.kr.woowahan_accountbook.data.datasource.local.payment

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import co.kr.woowahan_accountbook.data.dto.ClassificationDto
import javax.inject.Inject

class ClassificationDataSourceImpl @Inject constructor(
    private val readableDatabase: SQLiteDatabase,
    private val writableDatabase: SQLiteDatabase
) : ClassificationDataSource {
    override fun getClassification(id: Int): ClassificationDto {
        val projection = arrayOf("_ID", "CLASSIFICATION_TYPE", "CLASSIFICATION_COLOR", "IS_INCOME")
        val selection = "_ID = ?"
        val selectionArgs = arrayOf("$id")
        val cursor = readableDatabase.query(
            "CLASSIFICATION",
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        ).apply { moveToFirst() }
        val classification = ClassificationDto(
            id = cursor.getInt(0),
            classificationType = cursor.getString(1),
            classificationColor = cursor.getString(2),
            isIncome = cursor.getInt(3) == 1
        )
        cursor.close()
        return classification
    }

    override fun getClassifications(): List<ClassificationDto> {
        val sortOrder = "_ID ASC"
        val cursor = readableDatabase.query(
            "CLASSIFICATION",
            null,
            null,
            null,
            null,
            null,
            sortOrder
        )
        val classifications = mutableListOf<ClassificationDto>()
        while (cursor.moveToNext()) {
            classifications.add(
                ClassificationDto(
                    id = cursor.getInt(0),
                    classificationType = cursor.getString(1),
                    classificationColor = cursor.getString(2),
                    isIncome = cursor.getInt(3) == 1
                )
            )
        }
        cursor.close()
        return classifications
    }

    override fun insertClassification(type: String, color: String, isIncome: Int) {
        val values = ContentValues().apply {
            put("CLASSIFICATION_TYPE", type)
            put("CLASSIFICATION_COLOR", color)
            put("IS_INCOME", isIncome)
        }
        writableDatabase.insert("CLASSIFICATION", null, values)
    }

    override fun updateClassification(id: Int, type: String, color: String, isIncome: Int) {
        TODO("Not yet implemented")
    }
}