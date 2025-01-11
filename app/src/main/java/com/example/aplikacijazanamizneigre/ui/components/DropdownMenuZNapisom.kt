package com.example.aplikacijazanamizneigre.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DropdownMenuZNapisom(label: String, options: List<String>, izbrana: MutableState<String>) {
    val razsirjen = remember { mutableStateOf(false) }
    val barve = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.DarkGray,
        focusedIndicatorColor = Color.Black,
        unfocusedIndicatorColor = Color.Black,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Black
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = izbrana.value.ifEmpty { label },
            onValueChange = { },
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    imageVector = if (razsirjen.value) {
                        androidx.compose.material.icons.Icons.Filled.KeyboardArrowUp
                    }
                    else{
                        androidx.compose.material.icons.Icons.Filled.KeyboardArrowDown
                    },
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        razsirjen.value = !razsirjen.value
                    }
                )
            },
            colors = barve,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { razsirjen.value = !razsirjen.value }
        )
        DropdownMenu(
            expanded = razsirjen.value,
            onDismissRequest = { razsirjen.value = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        izbrana.value = option
                        razsirjen.value = false
                    },
                    text = { Text(option) }
                )
            }
        }
    }
}
