package com.hardik.challengearraylistfilter.presentation.model

import com.hardik.challengearraylistfilter.data.remote.dto.Index

data class FilterData(
    val filterName: String,
    var isSelected: Boolean,
    val subFilters: List<SubFilter>
)

data class SubFilter(
    val name: String,
    var isSelected: Boolean
)

data class FilterModel(
    val styleTags: List<SubFilter> = listOf(),
    val skillTags: List<SubFilter> = listOf(),
    val seriesTags: List<SubFilter> = listOf(),
    val curriculumTags: List<SubFilter> = listOf(),
    val educator: List<SubFilter> = listOf(),
    val owned: List<SubFilter> = listOf()
)
fun setSubFilters(indexList: List<Index>): FilterModel {
    val styleTags = indexList.flatMap { it.styleTags }.distinct().map { SubFilter(it, false) }
    val skillTags = indexList.flatMap { it.skillTags }.distinct().map { SubFilter(it, false) }
    val seriesTags = indexList.flatMap { it.seriesTags }.distinct().map { SubFilter(it, false) }
    val curriculumTags = indexList.flatMap { it.curriculumTags }.distinct().map { SubFilter(it, false) }
    val educator = indexList.flatMap { listOf(it.educator) }.distinct().map { SubFilter(it, false) }
    val owned = indexList.flatMap { listOf(it.owned.toString()) }.distinct().map { SubFilter(it, false) }

    return FilterModel(
        styleTags = styleTags,
        skillTags = skillTags,
        seriesTags = seriesTags,
        curriculumTags = curriculumTags,
        educator = educator,
        owned = owned
    )
}


fun getUniqueValues(indexList: List<Index>): List<FilterData> {
    val filterModel = setSubFilters(indexList)
    return listOf(
        FilterData("Styles", false, filterModel.styleTags),
        FilterData("Skills", false, filterModel.skillTags),
        FilterData("Series", false, filterModel.seriesTags),
        FilterData("Curriculums", false, filterModel.curriculumTags),
        FilterData("Educator", false, filterModel.educator),
        FilterData("Owned", false, filterModel.owned)
    )
}
fun getUniqueValues(filterModel: FilterModel): List<FilterData> {
    val styleFilter = FilterData("Styles", false, filterModel.styleTags)
    val skillFilter = FilterData("Skills", false, filterModel.skillTags)
    val seriesFilter = FilterData("Series", false, filterModel.seriesTags)
    val curriculumFilter = FilterData("Curriculums", false, filterModel.curriculumTags)
    val educatorFilter = FilterData("Educator", false, filterModel.educator)
    val ownedFilter = FilterData("Owned", false, filterModel.owned)

    return listOf(styleFilter, skillFilter, seriesFilter, curriculumFilter, educatorFilter, ownedFilter)
}