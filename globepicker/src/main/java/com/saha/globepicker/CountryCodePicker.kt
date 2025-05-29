package com.saha.globepicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.saha.globepicker.data.GlobeData
import com.saha.globepicker.helper.Helpers
import com.saha.globepicker.models.GlobeCountry


@Composable
fun CountryCodePicker(
    selectedCountry: GlobeCountry? = null,
    showCountryFlag: Boolean = true,
    showCountryName: Boolean = true,
    showCountryDialCode: Boolean = true,
    showCountryCode: Boolean = false,

    showCountryFlagOnExpand: Boolean = true,
    showCountryNameOnExpand: Boolean = true,
    showCountryDialCodeOnExpand: Boolean = true,
    showCountryCodeOnExpand: Boolean = false,

    detectCountry: Boolean = true,

    onCountrySelected: (GlobeCountry) -> Unit
) {
    val context = LocalContext.current

    val defaultCountry = if (detectCountry) {
        val countryCode = Helpers.getSimCountryCode(context)
        countryCode?.let {
            return@let GlobeData.countryList.find { it.code.uppercase() == countryCode.uppercase() }
        } ?: run {
            GlobeData.countryList[0]
        }
    } else GlobeData.countryList[0]

    var mSelectedCountry by rememberSaveable {
        mutableStateOf(
            selectedCountry?.code ?: defaultCountry.code
        )
    }

    val countryList = GlobeData.countryList

    LaunchedEffect(Unit) {
        findCountryWithCountryCode(
            countryList, mSelectedCountry
        )?.let { onCountrySelected.invoke(it) }
    }

    MyCountryCodePicker(
        countryList = countryList,
        selectedCountry = findCountryWithCountryCode(countryList, mSelectedCountry)
            ?: defaultCountry,
        showCountryFlag = showCountryFlag,
        showCountryName = showCountryName,
        showCountryDialCode = showCountryDialCode,
        showCountryCode = showCountryCode,

        showCountryFlagOnExpand = showCountryFlagOnExpand,
        showCountryNameOnExpand = showCountryNameOnExpand,
        showCountryDialCodeOnExpand = showCountryDialCodeOnExpand,
        showCountryCodeOnExpand = showCountryCodeOnExpand,

        ) {
        mSelectedCountry = it.code
        onCountrySelected.invoke(it)
    }
}

@Composable
private fun MyCountryCodePicker(
    countryList: List<GlobeCountry>,
    selectedCountry: GlobeCountry,
    showCountryFlag: Boolean = true,
    showCountryName: Boolean = true,
    showCountryDialCode: Boolean = true,
    showCountryCode: Boolean = false,

    showCountryFlagOnExpand: Boolean = true,
    showCountryNameOnExpand: Boolean = true,
    showCountryDialCodeOnExpand: Boolean = true,
    showCountryCodeOnExpand: Boolean = false,

    onCountrySelected: (GlobeCountry) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    // Filtered list based on search query
    val filteredList = remember(searchQuery.trim(), countryList) {
        countryList.filter {
            it.name.startsWith(
                searchQuery, ignoreCase = true
            ) || it.dialCode.contains(searchQuery) || it.code.contains(
                searchQuery, ignoreCase = true
            )
        }
    }

    Box(modifier = Modifier.wrapContentSize()) {

        val selectedItemText = buildString {
            if (showCountryFlag) append("${selectedCountry.flag} ")
            if (showCountryName) append("${selectedCountry.name} ")
            if (showCountryCode) append(selectedCountry.code)
            if (showCountryDialCode) append(" (${selectedCountry.dialCode})")
        }

        // Picker button
        Row(modifier = Modifier
            .padding(12.dp)
            .clickable { expanded = true }
            .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically) {

            Text(text = selectedItemText)

            Spacer(modifier = Modifier.width(8.dp))

            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }

        // Dialog with searchable country list
        if (expanded) {
            Dialog(onDismissRequest = { expanded = false }) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.85f)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Select a Country", style = MaterialTheme.typography.titleMedium)

                        Spacer(modifier = Modifier.height(8.dp))

                        // Search field
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search...") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Scrollable country list
                        LazyColumn {
                            items(filteredList) { country ->

                                val itemText = buildString {
                                    if (showCountryFlagOnExpand) append("${country.flag} ")
                                    if (showCountryNameOnExpand) append("${country.name} ")
                                    if (showCountryCodeOnExpand) append(country.code)
                                    if (showCountryDialCodeOnExpand) append(" (${country.dialCode})")
                                }

                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onCountrySelected(country)
                                        expanded = false
                                        searchQuery = ""
                                    }
                                    .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = itemText)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun findCountryWithCountryCode(
    countryList: List<GlobeCountry>, countryCode: String
): GlobeCountry? {
    return countryList.find { it.code.uppercase() == countryCode.uppercase() }
}