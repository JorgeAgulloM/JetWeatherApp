package com.demo.jetweatherapp.widgetsimport android.widget.Toastimport androidx.compose.foundation.backgroundimport androidx.compose.foundation.clickableimport androidx.compose.foundation.layout.*import androidx.compose.material.icons.Iconsimport androidx.compose.material.icons.filled.*import androidx.compose.material.icons.rounded.MoreVertimport androidx.compose.material3.*import androidx.compose.runtime.*import androidx.compose.ui.Alignmentimport androidx.compose.ui.Modifierimport androidx.compose.ui.draw.scaleimport androidx.compose.ui.draw.shadowimport androidx.compose.ui.graphics.Colorimport androidx.compose.ui.graphics.vector.ImageVectorimport androidx.compose.ui.platform.LocalContextimport androidx.compose.ui.text.TextStyleimport androidx.compose.ui.text.font.FontWeightimport androidx.compose.ui.unit.Dpimport androidx.compose.ui.unit.dpimport androidx.compose.ui.unit.spimport androidx.hilt.navigation.compose.hiltViewModelimport androidx.navigation.NavControllerimport com.demo.jetweatherapp.model.Favoriteimport com.demo.jetweatherapp.navigation.WeatherScreensimport com.demo.jetweatherapp.screens.favoriteScreen.FavoriteViewModel//@Preview@Composablefun WeatherAppBar(    title: String = "title",    icon: ImageVector? = null,    isMainScreen: Boolean = true,    elevation: Dp = 8.dp,    navController: NavController,    favoriteViewModel: FavoriteViewModel = hiltViewModel(),    onAddActionClicked: () -> Unit = {},    onButtonClicked: () -> Unit = {}) {    val showDialog = remember {        mutableStateOf(false)    }    val context = LocalContext.current    if (showDialog.value) ShowSettingDropDownMenu(        showDialog = showDialog,        navController = navController    )    SmallTopAppBar(        title = {            Text(                text = title,                color = MaterialTheme.colorScheme.secondary,                style = TextStyle(                    fontWeight = FontWeight.Bold,                    fontSize = 24.sp                ),                modifier = Modifier.padding(start = 4.dp)            )        },        actions = {            if (isMainScreen) {                IconButton(onClick = {                    onAddActionClicked.invoke()                }) {                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")                }                IconButton(onClick = {                    showDialog.value = true                }) {                    Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "More icon")                }            } else Box {}        },        navigationIcon = {            if (icon != null) {                Icon(                    imageVector = icon,                    contentDescription = null,                    tint = MaterialTheme.colorScheme.surfaceTint,                    modifier = Modifier.clickable {                        onButtonClicked.invoke()                    }                )            }            if (isMainScreen) {                val isAlreadyFavList =                    favoriteViewModel.favList.collectAsState().value.filter { city ->                        (city.city == title.split(",")[0])                    }                Icon(                    imageVector =                    if (isAlreadyFavList.isEmpty()) Icons.Default.FavoriteBorder                    else Icons.Default.Favorite,                    contentDescription = "Favorite Icon",                    modifier = Modifier                        .padding(start = 4.dp)                        .scale(0.9f)                        .clickable {                            val dataList = title.split(",")                            if (isAlreadyFavList.isEmpty()) {                                favoriteViewModel                                    .insertFavorite(                                        Favorite(                                            city = dataList[0],                                            country = dataList[1]                                        )                                    )                                    .run {                                        Toast                                            .makeText(                                                context,                                                "Added to favorites",                                                Toast.LENGTH_LONG                                            )                                            .show()                                    }                            } else {                                favoriteViewModel                                    .deleteFavorite(                                        Favorite(                                            city = dataList[0],                                            country = dataList[1]                                        )                                    )                                    .run {                                        Toast                                            .makeText(                                                context,                                                "Deleted from favorites",                                                Toast.LENGTH_LONG                                            )                                            .show()                                    }                            }                        },                    tint =                    if (isAlreadyFavList.isEmpty()) MaterialTheme.colorScheme.secondary                    else Color.Red.copy(alpha = 0.8f)                )            }        },        modifier = Modifier.shadow(            elevation = elevation,            clip = true,            ambientColor = MaterialTheme.colorScheme.primary        )        //colors = TopAppBarDefaults.smallTopAppBarColors(Color.Transparent)    )}@Composablefun ShowSettingDropDownMenu(    showDialog: MutableState<Boolean>,    navController: NavController) {    var expanded by remember {        mutableStateOf(true)    }    val items = listOf("About", "Favorites", "Settings")    Column(        modifier = Modifier            .fillMaxWidth()            .wrapContentSize(Alignment.TopEnd)            .absolutePadding(top = 45.dp, right = 20.dp)    ) {        DropdownMenu(            expanded = expanded,            onDismissRequest = { expanded = false }, modifier = Modifier                .width(140.dp)                .background(                    color = MaterialTheme.colorScheme.background                )        ) {            items.forEachIndexed { index, text ->                DropdownMenuItem(                    text = {                        Row(verticalAlignment = Alignment.CenterVertically) {                            Icon(                                imageVector = when (text) {                                    "About" -> Icons.Default.Info                                    "Favorite" -> Icons.Default.Favorite                                    else -> Icons.Default.Settings                                },                                contentDescription = "",                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),                                modifier = Modifier.padding(end = 4.dp)                            )                            Text(                                text = text,                                modifier = Modifier.clickable {                                    navController.navigate(                                        when (text) {                                            "About" -> WeatherScreens.AboutScreen.name                                            "Favorites" -> WeatherScreens.FavoritesScreen.name                                            else -> WeatherScreens.SettingsScreen.name                                        }                                    )                                },                                color = MaterialTheme.colorScheme.secondary,                                fontWeight = FontWeight.Bold                            )                        }                    },                    onClick = {                        expanded = false                        showDialog.value = false                    },                    enabled = true                )            }        }    }}