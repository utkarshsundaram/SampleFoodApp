package com.example.samplefoodapp.domain.util

interface DomainMapper <T, DomainModel> {

    fun mapFromDomainModel(entity: T): DomainModel

    fun mapToDomainModel(domainModel: DomainModel): T
}