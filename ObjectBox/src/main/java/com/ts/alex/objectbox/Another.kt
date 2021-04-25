package com.ts.alex.objectbox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Another(
    @Id
    var id: Long = 0,
    val data: String
)
