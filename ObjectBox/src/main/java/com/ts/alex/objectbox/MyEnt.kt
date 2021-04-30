package com.ts.alex.objectbox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
data class MyEnt(
    @Id
    var id: Long = 0,
    val name: String? = null,
){
    var another: ToMany<Another>? = null
}
