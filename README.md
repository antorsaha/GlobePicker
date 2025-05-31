GlobePicker A Country Code Picker Library For Jetpack Compose
[![](https://jitpack.io/v/antorsaha/GlobePicker.svg)](https://jitpack.io/#antorsaha/GlobePicker)
==================================

![ViewCount](https://views.whatilearened.today/views/github/antorsaha/GlobePicker.svg) [![GitHub issues](https://img.shields.io/github/issues/antorsaha/GlobePicker)](https://github.com/antorsaha/GlobePicker/issues)  [![GitHub forks](https://img.shields.io/github/forks/antorsaha/GlobePicker)](https://github.com/antorsaha/GlobePicker/network) [![GitHub stars](https://img.shields.io/github/stars/antorsaha/GlobePicker)](https://github.com/antorsaha/GlobePicker/stargazers) [![GitHub license](https://img.shields.io/github/license/antorsaha/GlobePicker)](https://github.com/antorsaha/GlobePicker/blob/master/License.txt) 



If you are looking for an android library for Country Selector or Country Spinner or Country Phone Code selector, this is the perfect place for you.

Globe picker (Country code picker) 
<img src="https://raw.githubusercontent.com/antorsaha/libraryAssets/refs/heads/master/globePickerCC.jpg" width="100">  
or  
<img src="https://raw.githubusercontent.com/antorsaha/libraryAssets/refs/heads/master/globePickerFull.jpg" width="230">
  is an android library which provides a highly customizable and modern Country Code Picker built with Jetpack Compose. It supports flags, dial codes, ISO codes, names, filtering, and light/dark mode – all out of the box.
  

Introduction
------------
* Give a professional touch to your well designed form like login screen, sign up screen, edit profile screen with GlobePicker. When used as phone code picker, it helps by removing confusion about how to add phone number and making view more understandable. 
	  
* With GlobePicker you can get following views easily without boilerplate code. (Left: Country code selector with dialcode. Right: c  ountry name with dial code)

    <img src="https://raw.githubusercontent.com/antorsaha/libraryAssets/refs/heads/master/globePickerCC.jpg" width="300">     <img src="https://raw.githubusercontent.com/antorsaha/libraryAssets/refs/heads/master/globePickerFull.jpg" width="400">    
    
* Tapping on the picker will open a dialog to search and select country (First: Country code selector with dialcode. Second: country name with dial code)
 <img src="https://raw.githubusercontent.com/antorsaha/libraryAssets/refs/heads/master/globePickerDialogSS.jpg" width="280"> 
 <img src="https://raw.githubusercontent.com/antorsaha/libraryAssets/refs/heads/master/GlobePickerDialogSS2.jpg" width="280">

Features
--------------

* 🌐 List of all countries with ISO code, name, dial code, and emoji flag
* 🔍 Searchable and filterable
* 🎨 Customizable styles and visibility options
* 📱 Auto-detect country from SIM
* 🌙 Dark mode compatible
* 🧩 Simple integration with flexible configuration
* ✅ Include or exclude specific countries


How to add to your project
--------------

Step 1. Add it in your settings.gradle.kts at the end of repositories
````settings
  dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url = uri("https://jitpack.io") }
		}
	}
````
Step 2. Add the dependency
  ````gradle.kts
  dependencies {
	        implementation("com.github.antorsaha:GlobePicker:x.y.z")
	}
   ````
   App version [![](https://jitpack.io/v/antorsaha/GlobePicker.svg)](https://jitpack.io/#antorsaha/GlobePicker)
  * If you are using some other version of gradle theck [check here](https://jitpack.io/#antorsaha/GlobePicker)
  

Usage
--------
````
  CountryCodePicker(
    selectedCountry = selectedCountry, //Initial selection just. no need to manage state by using this it will manage state by itself
    onCountrySelected = {globeCountry->
      //Do whatever you want
     }
  )
````


### Full Parameters
````
  CountryCodePicker(
    modifier = Modifier,
    selectedCountry = null, // Optional default selected
    showCountryFlag = true,
    showCountryName = true,
    showCountryDialCode = true,
    showCountryCode = false,

    showCountryFlagOnExpand = true,
    showCountryNameOnExpand = true,
    showCountryDialCodeOnExpand = true,
    showCountryCodeOnExpand = false,

    detectCountry = true, // Try to auto-detect SIM country
    isDisabled = false,

    includeCountries = listOf(), // ISO codes (e.g., "BD", "US")
    excludeCountries = listOf(),

    dropdownIcon = { CustomDropdownIcon() }, // Optional custom dropdown icon

    textColor = MaterialTheme.colorScheme.onSurface,
    dialogBackgroundColor = MaterialTheme.colorScheme.surface,

    onCountrySelected = { country -> /* handle selection */ }
)

````

<img src="https://raw.githubusercontent.com/antorsaha/libraryAssets/refs/heads/master/globePickerDemo1.png" width="1280"> 
<img src="https://raw.githubusercontent.com/antorsaha/libraryAssets/refs/heads/master/globePickerdemo2.png" width="1280">

<img src="https://raw.githubusercontent.com/antorsaha/libraryAssets/refs/heads/master/globePickerdemo3.png" width="1280">
<img src="https://raw.githubusercontent.com/antorsaha/libraryAssets/refs/heads/master/globePickerdemo4.png" width="1280">

### Customization Option
* ``showCountryFlag`` Show emoji flag in closed state
* ``showCountryName`` Show country name in closed state
* ``showCountryDialCode`` Show dial code (e.g. +880)
* ``showCountryCode`` Show ISO code (e.g. BD)
* ``detectCountry`` Auto-select country based on SIM
* ``includeCountries`` Restrict dropdown to only these ISO codes
* ``excludeCountries`` Remove these countries from the dropdown
* ``dropdownIcon`` Provide a custom trailing icon
* ``textColor`` Customize text and icon color
* ``dialogBackgroundColor`` Customize dialog's background color

### Search Behavior
Search matches:
- Country name
- Dial code (e.g., "+880")
- ISO code (e.g., "BD")

## Constraints
- ``includeCountries`` and ``excludeCountries`` cannot be used together.

## Contributions
Pull requests, issues, and improvements are welcome! Please follow the existing code style and Kotlin best practices. After reviewing the pull request code it will merge to main branch.


## Credits
- [CountryCodePickerProject](https://github.com/hbb20/CountryCodePickerProject) by [hbb20](https://github.com/hbb20)
- Initial feature developed by [Antor Saha](https://github.com/antorsaha)
- Initial Country list Gathered by [Ferdous](https://github.com/ferdous-mahmud)


## License

[Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

  