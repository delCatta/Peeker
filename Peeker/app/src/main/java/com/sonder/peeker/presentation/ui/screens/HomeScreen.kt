package com.sonder.peeker.presentation.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sonder.peeker.presentation.document_list.DocumentListScreen

@Composable
fun HomeScreen() {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Tus acciones */ }) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Crear Documento"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
                GreetingSection()
                DocumentListScreen()
            }
        }
    }

}

@Composable
fun GreetingSection(name: String = "Diego Cattarinich Clavel") {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(text = "Welcome", style = MaterialTheme.typography.body1, color = Pink)
            Text(text = name, style = MaterialTheme.typography.h2)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .padding(10.dp)
        ){
            Icon(Icons.Outlined.NotificationsNone,
                contentDescription = "Notificaciones",
                tint = Pink
            )
            Icon(Icons.Outlined.Person,
                contentDescription = "Profile",
                tint = Pink
            )
        }


    }

}

