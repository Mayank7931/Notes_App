package com.example.notesapp.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notesapp.NotesViewModel


//@Preview
@Composable
fun AddNote(navController: NavController,viewModel: NotesViewModel) {

    val context = LocalContext.current

    var notesTitle by remember {
        mutableStateOf("")
    }

    var notesInfo by remember {
        mutableStateOf("")
    }

    Column {

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.padding(20.dp)) {
            
            OutlinedTextField(value = notesTitle, onValueChange = {
                notesTitle = it
            }, placeholder = { Text(text = "Enter Title")})

            Spacer(modifier = Modifier.width(20.dp))

            if (!notesTitle.equals("")){

                val keyboardController = LocalSoftwareKeyboardController.current

                IconButton(onClick = { keyboardController?.hide() })
                {
                    Icon(imageVector = Icons.Default.Done, contentDescription = "add",
                        modifier = Modifier.size(100.dp))
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize(0.9f)){

            Column {

            if (!notesInfo.equals("")){

                val keyboardController = LocalSoftwareKeyboardController.current

                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                    horizontalArrangement = Arrangement.End)
                {
                    IconButton(onClick = {
                        keyboardController?.hide() }) {
                        Icon(
                            imageVector = Icons.Default.Done, contentDescription = "add",
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }
            }

            OutlinedTextField(modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(), value = notesInfo, onValueChange = {
                notesInfo = it
            }, placeholder = { Text(text = "Enter Details")})
          }
        }

        Button(modifier = Modifier
            .fillMaxWidth(0.5f)
            .align(Alignment.CenterHorizontally),

            onClick = {
                viewModel.addNote(notesTitle,notesInfo)
                Toast.makeText(context,"note added",Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            })
        {
            Text(text = "save note")
        }

    }


}
