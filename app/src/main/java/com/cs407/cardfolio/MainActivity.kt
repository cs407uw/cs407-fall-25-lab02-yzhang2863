package com.cs407.cardfolio

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cs407.cardfolio.ui.theme.CardfolioTheme
import com.cs407.cardfolio.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CardfolioTheme {
                val gradientTop = AppTheme.customColors.gradientTop
                val gradientBottom = AppTheme.customColors.gradientBottom

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(gradientTop, gradientBottom)
                            )
                        )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
                        )
                        Cardfolio()
                    }
                }
            }
        }
    }
}

@Composable
fun Cardfolio() {
    var isEditing by remember { mutableStateOf(true) }
    var name by remember { mutableStateOf("") }
    var hobby by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    val outlineColor = MaterialTheme.colorScheme.outline
    val context = LocalContext.current
    val toastSuccess = stringResource(id = R.string.toast_success)
    val toastMissingPrefix = "Please enter: "

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.extraLarge,
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                AssistChip(
                    onClick = { isEditing = !isEditing },
                    label = { Text(if (isEditing) "Editing" else "Locked") },
                    leadingIcon = {
                        Icon(
                            imageVector = if (isEditing) Icons.Default.Edit else Icons.Default.Lock,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    modifier = Modifier.padding(8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_img),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(84.dp)
                            .clip(CircleShape)
                            .border(1.dp, outlineColor, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = if (name.isBlank()) stringResource(R.string.card_name) else name,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = if (hobby.isBlank()) stringResource(R.string.card_hobby) else hobby,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                HorizontalDivider(color = outlineColor)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { if (isEditing) name = it },
                        label = { Text(stringResource(R.string.card_name_label)) },
                        leadingIcon = { Icon(Icons.Default.Person, null) },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = !isEditing
                    )
                    OutlinedTextField(
                        value = hobby,
                        onValueChange = { if (isEditing) hobby = it },
                        label = { Text(stringResource(R.string.card_hobby_label)) },
                        leadingIcon = { Icon(Icons.Default.Favorite, null) },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = !isEditing
                    )
                    OutlinedTextField(
                        value = age,
                        onValueChange = { input ->
                            if (isEditing && input.all { it.isDigit() }) age = input
                        },
                        label = { Text(stringResource(R.string.card_age_label)) },
                        leadingIcon = { Icon(Icons.Default.Info, null) },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = !isEditing,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        supportingText = { if (isEditing) Text(stringResource(R.string.age_warning)) }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { isEditing = true },
                        enabled = !isEditing,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(Icons.Default.Edit, null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(stringResource(R.string.button_edit))
                    }
                    Button(
                        onClick = {
                            val missing = buildList {
                                if (name.isBlank()) add("Name")
                                if (hobby.isBlank()) add("Hobby")
                                if (age.isBlank()) add("Age")
                            }
                            if (missing.isNotEmpty()) {
                                Toast.makeText(
                                    context,
                                    toastMissingPrefix + missing.joinToString(", "),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                isEditing = false
                                Toast.makeText(
                                    context,
                                    toastSuccess,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        enabled = isEditing
                    ) {
                        Icon(Icons.Default.Check, null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(stringResource(R.string.button_show))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_7")
@Composable
fun CardfolioPreview() {
    CardfolioTheme { Cardfolio() }
}