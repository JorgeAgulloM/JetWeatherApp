package com.demo.jetweatherapp.screens.settingsScreenimport androidx.compose.foundation.backgroundimport androidx.compose.foundation.layout.*import androidx.compose.foundation.shape.RoundedCornerShapeimport androidx.compose.material.icons.Iconsimport androidx.compose.material.icons.filled.ArrowBackimport androidx.compose.material3.*import androidx.compose.runtime.*import androidx.compose.ui.Alignmentimport androidx.compose.ui.Alignment.Companion.CenterHorizontallyimport androidx.compose.ui.Modifierimport androidx.compose.ui.draw.clipimport androidx.compose.ui.graphics.Colorimport androidx.compose.ui.graphics.RectangleShapeimport androidx.compose.ui.text.font.FontWeightimport androidx.compose.ui.unit.dpimport androidx.compose.ui.unit.spimport androidx.hilt.navigation.compose.hiltViewModelimport androidx.navigation.NavControllerimport com.demo.jetweatherapp.model.Unitimport com.demo.jetweatherapp.ui.theme.MyPrimaryColorimport com.demo.jetweatherapp.widgets.WeatherAppBar@OptIn(ExperimentalMaterial3Api::class)@Composablefun WeatherSettingsScreen(    navController: NavController,    settingsViewModel: SettingsViewModel = hiltViewModel()) {    Scaffold(topBar = {        WeatherAppBar(            title = "Settings",            icon = Icons.Default.ArrowBack,            isMainScreen = false,            navController = navController        ) {            navController.popBackStack()        }    }) {        Surface(            modifier = Modifier                .padding(top = it.calculateTopPadding())                .fillMaxSize()        ) {            var unitToggleState by remember { mutableStateOf(false) }            val measurementUnits = listOf("imperial", "metric")            val choiceFromDb = settingsViewModel.unitList.collectAsState().value            val defaultChoice = if (choiceFromDb.isEmpty()) measurementUnits[0]            else choiceFromDb[0].unit            var choiceState by remember { mutableStateOf(defaultChoice) }            Column(                verticalArrangement = Arrangement.Center,                horizontalAlignment = Alignment.CenterHorizontally            ) {                Text(                    text = "Change Units of Measurement",                    modifier = Modifier.padding(bottom = 16.dp)                )                IconToggleButton(                    checked = !unitToggleState,                    onCheckedChange = {                        unitToggleState = !unitToggleState                        choiceState = if (unitToggleState)                            measurementUnits[0]                        else                            measurementUnits[1]                    },                    modifier = Modifier                        .fillMaxWidth(0.5f)                        //.clip(shape = RoundedCornerShape(24.dp))                        .padding(6.dp)                        .background(color = MyPrimaryColor)                ) {                    Text(                        text = if (unitToggleState) "Fahrenheit ºF" else "Celsius ºC",                        fontSize = 18.sp,                        color = MaterialTheme.colorScheme.onSecondary                    )                }                Button(                    onClick = {                        settingsViewModel.apply {                            deleteAllUnits()                            insertUnit(                                unit = Unit(                                    unit = choiceState                                )                            )                        }                        navController.popBackStack()                    },                    modifier = Modifier                        .padding(4.dp)                        .align(CenterHorizontally),                    shape = RoundedCornerShape(34.dp),                    colors = ButtonDefaults.buttonColors(                        contentColor = MyPrimaryColor                    )                ) {                    Text(                        text = "Save",                        modifier = Modifier.padding(4.dp),                        color = Color.White,                        fontSize = 18.sp,                        fontWeight = FontWeight.Bold                    )                }            }        }    }}