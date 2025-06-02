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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.saha.globepicker.data.GlobeData
import com.saha.globepicker.helper.Helpers
import com.saha.globepicker.models.GlobeCountry

@Composable
fun CountryCodePicker(
    modifier: Modifier = Modifier,
    selectedCountry: String? = null,
    showCountryFlag: Boolean = true,
    showCountryName: Boolean = true,
    showCountryDialCode: Boolean = true,
    showCountryCode: Boolean = false,

    showCountryFlagOnExpand: Boolean = true,
    showCountryNameOnExpand: Boolean = true,
    showCountryDialCodeOnExpand: Boolean = true,
    showCountryCodeOnExpand: Boolean = false,

    detectCountry: Boolean = true,
    isDisabled: Boolean = false,

    includeCountries: List<String> = listOf(),
    excludeCountries: List<String> = listOf(),

    dropdownIcon: @Composable (() -> Unit)? = null,

    // Optional colors with defaults from MaterialTheme (auto adapts light/dark)
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    dialogBackgroundColor: Color = MaterialTheme.colorScheme.surface,

    onCountrySelected: (GlobeCountry) -> Unit
) {
    require(!(includeCountries.isNotEmpty() && excludeCountries.isNotEmpty())) {
        "includeCountries and excludeCountries cannot be used at the same time."
    }

    val context = LocalContext.current

    val allCountries = GlobeData.countryList

    val filteredCountryList = remember(includeCountries, excludeCountries) {
        val includeSet = includeCountries.map { it.uppercase() }.toSet()
        val excludeSet = excludeCountries.map { it.uppercase() }.toSet()

        allCountries.filter {
            (includeSet.isEmpty() || it.code.uppercase() in includeSet) &&
                    (excludeSet.isEmpty() || it.code.uppercase() !in excludeSet)
        }
    }

    val detectedCountry = if (detectCountry) {
        val simCode = Helpers.getSimCountryCode(context)
        filteredCountryList.find { it.code.equals(simCode, ignoreCase = true) }
    } else null

    val defaultCountry = detectedCountry ?: filteredCountryList.firstOrNull()

    var selectedCountryCode by rememberSaveable {
        mutableStateOf(selectedCountry?.uppercase() ?: defaultCountry?.code.orEmpty())
    }

    val currentCountry =
        Helpers.findCountryByCode(filteredCountryList, selectedCountryCode) ?: defaultCountry ?: return

    LaunchedEffect(selectedCountryCode) {
        Helpers.findCountryByCode(filteredCountryList, selectedCountryCode)?.let {
            onCountrySelected(it)
        }
    }

    MyCountryCodePicker(
        modifier = modifier,
        countryList = filteredCountryList,
        selectedCountry = currentCountry,
        showCountryFlag = showCountryFlag,
        showCountryName = showCountryName,
        showCountryDialCode = showCountryDialCode,
        showCountryCode = showCountryCode,
        showCountryFlagOnExpand = showCountryFlagOnExpand,
        showCountryNameOnExpand = showCountryNameOnExpand,
        showCountryDialCodeOnExpand = showCountryDialCodeOnExpand,
        showCountryCodeOnExpand = showCountryCodeOnExpand,
        isDisabled = isDisabled || includeCountries.size  <= 1,
        dropdownIcon = dropdownIcon,
        textColor = textColor,
        dialogBackgroundColor = dialogBackgroundColor,
        onCountrySelected = {
            selectedCountryCode = it.code
            onCountrySelected(it)
        }
    )
}

@Composable
private fun MyCountryCodePicker(
    modifier: Modifier = Modifier,
    countryList: List<GlobeCountry>,
    selectedCountry: GlobeCountry,
    showCountryFlag: Boolean,
    showCountryName: Boolean,
    showCountryDialCode: Boolean,
    showCountryCode: Boolean,
    showCountryFlagOnExpand: Boolean,
    showCountryNameOnExpand: Boolean,
    showCountryDialCodeOnExpand: Boolean,
    showCountryCodeOnExpand: Boolean,
    isDisabled: Boolean,
    dropdownIcon: @Composable (() -> Unit)? = null,
    textColor: Color,
    dialogBackgroundColor: Color,
    onCountrySelected: (GlobeCountry) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val filteredList = remember(searchQuery, countryList) {
        val query = searchQuery.trim().lowercase()
        if (query.isEmpty()) countryList
        else countryList.filter {
            it.name.lowercase().contains(query) ||
                    it.dialCode.contains(query) ||
                    it.code.lowercase().contains(query)
        }
    }

    Box(modifier = modifier.wrapContentSize()) {
        val selectedText = buildString {
            if (showCountryFlag) append("${selectedCountry.flag} ")
            if (showCountryName) append(selectedCountry.name)
            if (showCountryCode) append(" ${selectedCountry.code}")
            if (showCountryDialCode) append(" (${selectedCountry.dialCode})")
        }.trim()

        Row(
            modifier = Modifier
                .let {
                    if (!isDisabled) it.clickable {
                        keyboardController?.hide()
                        expanded = true
                    } else it
                }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = selectedText, color = textColor)
            Spacer(modifier = Modifier.width(8.dp))

            if (!isDisabled) {
                if (dropdownIcon != null) {
                    dropdownIcon()
                } else {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = if (expanded) "Collapse country list" else "Expand country list",
                        tint = textColor
                    )
                }
            }
        }

        if (expanded) {
            Dialog(onDismissRequest = {
                expanded = false
                searchQuery = ""
            }) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = dialogBackgroundColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.85f)
                        .widthIn(max = 400.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Select a Country",
                            style = MaterialTheme.typography.titleMedium,
                            color = textColor
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search...", color = textColor.copy(alpha = 0.5f)) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(color = textColor),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = textColor,
                                unfocusedBorderColor = textColor.copy(alpha = 0.5f),
                                cursorColor = textColor
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        LazyColumn {
                            if (filteredList.isEmpty()) {
                                item {
                                    Text(
                                        "No countries found",
                                        modifier = Modifier.padding(16.dp),
                                        color = textColor
                                    )
                                }
                            } else {
                                items(filteredList, key = { it.code }) { country ->
                                    val countryText = buildString {
                                        if (showCountryFlagOnExpand) append("${country.flag} ")
                                        if (showCountryNameOnExpand) append("${country.name} ")
                                        if (showCountryCodeOnExpand) append(country.code)
                                        if (showCountryDialCodeOnExpand) append(" (${country.dialCode})")
                                    }.trim()

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                keyboardController?.hide()
                                                onCountrySelected(country)
                                                expanded = false
                                                searchQuery = ""
                                            }
                                            .padding(vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = countryText, color = textColor)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}