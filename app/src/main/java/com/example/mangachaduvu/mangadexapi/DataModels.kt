package com.example.mangachaduvu.mangadexapi



data class MangaResponse(
    val result: String,
    val response: String,
    val data: MangaData
)

data class MangaData(
    val id: String,
    val type: String,
    val attributes: MangaAttributes,
    val relationships: List<MangaRelationship>
)

data class MangaAttributes(
    val title: Map<String, String>,
    val description: Map<String, String>
)

data class MangaRelationship(
    val id: String,
    val type: String
)

data class MangaInfo(
    val data: MangaData
)


data class MangaRelationships(
    val id: String,
    val type: String
)

data class MangaSearchResponse(
    val data: List<MangaData>
)


data class CoverResponse(
    val result: String,
    val response: String,
    val data: CoverData  // This is the list of cover data
)

data class CoverData(
    val id: String,
    val type: String,
    val attributes: CoverAttributes,  // Contains file name, volume, etc.
    val relationships: List<CoverRelationship>  // You can leave this empty if not needed
)

data class CoverAttributes(
    val volume: String,
    val fileName: String,  // The actual filename or URL for the cover image
    val description: String,
    val locale: String,
    val version: Int,
    val createdAt: String,
    val updatedAt: String
)

data class CoverRelationship(
    val id: String,
    val type: String,
)


data class Relationship(
    val id: String, // Related entity ID
    val type: String, // Type of the related entity (e.g., manga)
    val related: String // Related manga title
)
data class ApiResponse<T>(
    val data: List<MangaInfo>
)

