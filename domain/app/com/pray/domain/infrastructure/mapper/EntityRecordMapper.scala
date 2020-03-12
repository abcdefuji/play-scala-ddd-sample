package com.pray.domain.infrastructure.mapper

import com.pray.domain.infrastructure.Entity
import com.pray.domain.infrastructure.common.Identifier
import com.pray.infrastructure.db.Record

trait EntityRecordMapper[ID <: Identifier[Long], E <: Entity[ID], T <: Record] {

  protected def toRecord(model: E): T

  protected def toEntity(record: T): E

}
