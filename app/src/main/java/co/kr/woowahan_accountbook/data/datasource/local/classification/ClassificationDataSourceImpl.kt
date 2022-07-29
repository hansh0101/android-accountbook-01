package co.kr.woowahan_accountbook.data.datasource.local.classification

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import co.kr.woowahan_accountbook.domain.entity.dto.ClassificationDto
import javax.inject.Inject

class ClassificationDataSourceImpl @Inject constructor(
    private val readableDatabase: SQLiteDatabase,
    private val writableDatabase: SQLiteDatabase
) : ClassificationDataSource {
    override fun getClassification(id: Int): ClassificationDto {
        val selection = "_ID = ?"
        val selectionArgs = arrayOf("$id")
        val cursor = readableDatabase.query(
            "CLASSIFICATION",
            null,
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

    override fun getClassificationsByType(isIncome: Boolean): List<ClassificationDto> {
        val sortOrder = "_ID ASC"
        val selection = "IS_INCOME = ?"
        val selectionArgs = if (isIncome) arrayOf("1") else arrayOf("0")
        val cursor = readableDatabase.query(
            "CLASSIFICATION",
            null,
            selection,
            selectionArgs,
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

    override fun insertClassification(type: String, color: String, isIncome: Boolean) {
        val values = ContentValues().apply {
            put("CLASSIFICATION_TYPE", type)
            put("CLASSIFICATION_COLOR", color)
            put("IS_INCOME", if (isIncome) 1 else 0)
        }
        writableDatabase.insert("CLASSIFICATION", null, values)
    }

    override fun updateClassification(id: Int, type: String, color: String, isIncome: Boolean) {
        val values = ContentValues().apply {
            put("CLASSIFICATION_TYPE", type)
            put("CLASSIFICATION_COLOR", color)
            put("IS_INCOME", if (isIncome) 1 else 0)
        }
        val selection = "_ID LIKE ?"
        val selectionArgs = arrayOf("$id")
        writableDatabase.update("CLASSIFICATION", values, selection, selectionArgs)
    }
}