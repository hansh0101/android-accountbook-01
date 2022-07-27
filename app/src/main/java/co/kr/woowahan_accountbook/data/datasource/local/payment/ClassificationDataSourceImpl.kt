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
        TODO("Not yet implemented")
    }

    override fun getClassifications(): List<ClassificationDto> {
        TODO("Not yet implemented")
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