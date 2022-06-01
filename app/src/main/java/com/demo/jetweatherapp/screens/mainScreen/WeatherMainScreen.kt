package com.demo.jetweatherapp.screens.mainScreenimport android.annotation.SuppressLintimport androidx.compose.foundation.backgroundimport androidx.compose.foundation.layout.*import androidx.compose.foundation.lazy.LazyColumnimport androidx.compose.foundation.lazy.itemsimport androidx.compose.foundation.shape.CircleShapeimport androidx.compose.foundation.shape.RoundedCornerShapeimport androidx.compose.material3.*import androidx.compose.runtime.Composableimport androidx.compose.runtime.produceStateimport androidx.compose.ui.Alignmentimport androidx.compose.ui.Modifierimport androidx.compose.ui.graphics.Colorimport androidx.compose.ui.text.font.FontStyleimport androidx.compose.ui.text.font.FontWeightimport androidx.compose.ui.unit.dpimport androidx.hilt.navigation.compose.hiltViewModelimport androidx.navigation.NavControllerimport com.demo.jetweatherapp.data.DataOrExceptionimport com.demo.jetweatherapp.model.Weatherimport com.demo.jetweatherapp.navigation.WeatherScreensimport com.demo.jetweatherapp.utils.formatDateimport com.demo.jetweatherapp.utils.formatDecimalsimport com.demo.jetweatherapp.widgets.*@Composablefun WeatherMainScreen(    navController: NavController,    mainViewModel: MainViewModel = hiltViewModel()) {    val weatherData =        produceState<DataOrException<Weather, Boolean, Exception>>(            initialValue = DataOrException(loading = true)        ) {            value = mainViewModel.getWeatherData(city = "Elche")        }.value    if (weatherData.loading == true) {        CircularProgressIndicator()    } else if (weatherData.data != null) {        MainScaffold(weather = weatherData.data!!, navController = navController)    }}@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")@OptIn(ExperimentalMaterial3Api::class)@Composablefun MainScaffold(weather: Weather, navController: NavController) {    Scaffold(topBar = {        WeatherAppBar(            title = weather.city.name + ", ${weather.city.country}",            //icon = Icons.Default.ArrowBack,            navController = navController,            onAddActionClicked = {                navController.navigate(WeatherScreens.SearchScreen.name)            }        ) {        }    }) {        Box(modifier = Modifier.padding(top = it.calculateTopPadding())) {            MainContent(data = weather)        }    }}@Composablefun MainContent(data: Weather) {    val weatherItem = data.list[0]    val imagesUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"    Column(        modifier = Modifier            .padding(4.dp)            .fillMaxWidth(),        verticalArrangement = Arrangement.Center,        horizontalAlignment = Alignment.CenterHorizontally    ) {        Text(            text = formatDate(weatherItem.dt), //Wed, nov 30            style = MaterialTheme.typography.bodyLarge,            //color = MaterialTheme.colorScheme.onSecondary,            fontWeight = FontWeight.SemiBold,            modifier = Modifier.padding(6.dp)        )        Surface(            modifier = Modifier                .padding(4.dp)                .size(200.dp),            shape = CircleShape,            color = Color(0xFFFFC400)        ) {            Column(                verticalArrangement = Arrangement.Center,                horizontalAlignment = Alignment.CenterHorizontally            ) {                WeatherStateImage(imageUrl = imagesUrl)                Text(                    text = formatDecimals(weatherItem.temp.day) + "º",                    style = MaterialTheme.typography.displayMedium,                    fontWeight = FontWeight.ExtraBold                )                Text(text = weatherItem.weather[0].main, fontStyle = FontStyle.Italic)            }        }        HumidityWindPressureRow(weather = weatherItem)        Divider(modifier = Modifier.background(color = MaterialTheme.colorScheme.onSecondary))        SunsetSunRiseRow(weather = data.list[0])        Text(            text = "This week",            style = MaterialTheme.typography.bodyLarge,            fontWeight = FontWeight.Bold        )        Surface(            modifier = Modifier.fillMaxSize(),            color = MaterialTheme.colorScheme.inverseOnSurface,            shape = RoundedCornerShape(14.dp)        ) {            LazyColumn(modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(1.dp)) {                items(items = data.list) { weatherItem ->                    //Text(weatherItem.temp.max.toString())                    WeatherDetailRow(weatherItem = weatherItem)                }            }        }    }}