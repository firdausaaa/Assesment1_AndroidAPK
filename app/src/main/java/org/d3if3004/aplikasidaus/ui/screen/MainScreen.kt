package org.d3if3004.aplikasidaus.ui.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3004.aplikasidaus.R
import org.d3if3004.aplikasidaus.navigation.Screen
import org.d3if3004.aplikasidaus.ui.theme.AplikasiDausTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController){
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = stringResource(id = R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ){ padding ->
        ScreenContent(Modifier.padding(padding))
    }
}


@Composable
fun ScreenContent(modifier: Modifier) {
    var number by rememberSaveable { mutableIntStateOf(0) }
    var number2 by rememberSaveable { mutableIntStateOf(0) }
    var namaTeam1 by rememberSaveable { mutableStateOf("") }
    var namaTeam2 by rememberSaveable { mutableStateOf("") }
    var showPopup by remember { mutableStateOf(false) }
    val opsiBabak = listOf(
        stringResource(id = R.string.babak1),
        stringResource(id = R.string.babak2)
    )
    var babak by rememberSaveable {
        mutableStateOf(opsiBabak[0])
    }
    val babak2 by rememberSaveable {
        mutableStateOf(opsiBabak[1])
    }
    var teamError by rememberSaveable {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround

    ) {
        OutlinedTextField(
            value = namaTeam1,
            onValueChange = { namaTeam1 = it },
            label = { Text(text = stringResource(id = R.string.team1)) },
            isError = teamError,
            trailingIcon = { IconPicker(teamError) },
            supportingText = { ErrorHint(teamError)},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.size(width = 150.dp, height = 60.dp),

            )
        Row {
            Text(
                text = babak,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        OutlinedTextField(
            value = namaTeam2,
            onValueChange = { namaTeam2 = it },
            label = { Text(text = stringResource(id = R.string.team2)) },
            isError = teamError,
            trailingIcon = { IconPicker(teamError) },
            supportingText = { ErrorHint(teamError)},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.size(width = 150.dp, height = 60.dp),
        )
    }
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            onClick = {
                number++
            },
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 11.dp)
        ) {
            Text(text = "+")
        }
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = stringResource(id = R.string.versus),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = number2.toString(),
            style = MaterialTheme.typography.displayMedium
        )
        Button(
            onClick = {
                number2++
            },
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 11.dp)
        ) {
            Text(text = "+")
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
            Button(
                onClick = {
                    teamError =(namaTeam1 == namaTeam2)
                    if (teamError)return@Button

                    shareData(context,
                        context.getString(R.string.bagikan_template, babak,namaTeam1, namaTeam2, number.toString(), number2.toString(), )
                        )

                },
                modifier = modifier.absolutePadding(top = 200.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Bagikan", style = MaterialTheme.typography.titleLarge)
            }
        Button(
            onClick = {
                babak = babak2
                number = 0
                number2 = 0
            },
                modifier = Modifier.absolutePadding(top = 250.dp),
            shape = RoundedCornerShape(10.dp)
            ) {
            Text(text = "Babak", style = MaterialTheme.typography.titleLarge)
        }
        Button(
            onClick = {
                number = 0
                number2 = 0
                babak = opsiBabak[0]
                namaTeam1 = ""
                namaTeam2 = ""
            },
            modifier = modifier.absolutePadding(top = 200.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Reset", style = MaterialTheme.typography.titleLarge)
        }

    }
}

@Composable
fun IconPicker(isError: Boolean) {
    if (isError){
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    }
}

private fun shareData(context: Context, message: String){
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if(shareIntent.resolveActivity(context.packageManager) != null){
        context.startActivity(shareIntent)
    }
}
@Composable
fun MyPopup(message: String,showPopup: MutableState<Boolean>) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, style = MaterialTheme.typography.bodyLarge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {tutup(showPopup)}) {
                Text("Tutup")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {shareData(context = context, message = message)}) {
                Text("Bagikan")

            }
        }
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.invalid))
    }
}
fun tutup(showPopup: MutableState<Boolean>) {
    showPopup.value = false

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AplikasiDausTheme {
        MainScreen(rememberNavController())
    }
}