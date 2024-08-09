package com.example.notesapp.screen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notesapp.NotesData
import com.example.notesapp.NotesViewModel
import com.example.notesapp.Routes
import com.example.notesapp.ui.theme.Indigo
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUI(navController: NavController,viewModel: NotesViewModel) {

    val notesList by viewModel.notesLiveData.observeAsState()

    val context = LocalContext.current

        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = { TopAppBar(
            title = { Text(text = "Notes App",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
                fontSize = 40.sp,
                color = Color.White) },
            colors = TopAppBarDefaults.topAppBarColors(Color.LightGray)
            ) },

            floatingActionButton = { IconButton(onClick =
            { navController.navigate(Routes.AddNoteScreen)
                Log.d("nav","addNotesScreen")
            Toast.makeText(context,"add your note",Toast.LENGTH_SHORT)})
            {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add new",
                    modifier = Modifier.size(300.dp))
            }})
        {
            innerPadding ->



            Surface(modifier = Modifier
                .padding(innerPadding))
            {

                GradientBackground()

                notesList?.let {
                    LazyColumn(reverseLayout = true) {
                        itemsIndexed(it){index, item : NotesData->
                            NotesColumn(item,
                                onDelete = { viewModel.deleteNote(item.id) },
                                onUpdate = {viewModel.updateNote(item.title,item.details,item.id)},
                                viewModel = viewModel)
                        }
                    }
                }

            }
        }
    }

@Composable
fun GradientBackground() {

    val background = Brush.linearGradient(listOf(Indigo, Color.White, Color.Gray))

    Box(modifier = Modifier
        .fillMaxSize()
        .background(background)){

    }

}


@Composable
fun NotesColumn(notesData: NotesData, onDelete: ()->Unit, viewModel: NotesViewModel,onUpdate: () -> Unit) {

    var showNote by remember {
        mutableStateOf(false)
    }

    var editNote by remember {
        mutableStateOf(false)
    }
    
    Spacer(modifier = Modifier.height(20.dp))

    Card(
        onClick = {showNote = !showNote},
        modifier = Modifier, elevation = CardDefaults.cardElevation(10.dp))
    {
        Row (modifier = Modifier.fillMaxSize()){
            
            Spacer(modifier = Modifier.width(10.dp))
            
            Column (modifier = Modifier
                .fillMaxSize(0.8f)
                .padding(5.dp))
            {
            Text(text = "${notesData.title}", fontSize = 20.sp)
                
            Text(text = SimpleDateFormat("hh:mm aa      dd/MM/yyyy", Locale.ENGLISH).format(notesData.date), fontSize = 12.sp)
            }

            IconButton(onClick = { editNote = true }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "update")
            }
            IconButton(onClick = {
                onDelete()
            }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
            }
        }
    }

    if (editNote){
      updateNote(curTitle = notesData.title, curDetails = notesData.details,notesData.id, viewModel,onDismiss = { editNote=!editNote }) {
          editNote = false
      }
    }

    if (showNote){
        ViewNote(notesData.title,notesData.details) { showNote = !showNote }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ViewNote(title:String, details:String,onDismiss: () -> Unit) {

    BasicAlertDialog(onDismissRequest = { onDismiss() },
        modifier = Modifier.background(Color.LightGray)) {

        Column {
            Text(text = title, modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center, fontSize = 30.sp)

            HorizontalDivider()

            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){
                Text(text = details)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun updateNote(curTitle:String, curDetails:String,id:Int,viewModel: NotesViewModel,onDismiss: () -> Unit,onUpdate: ()->Unit) {

    var updatedTitle by remember {
        mutableStateOf(curTitle)
    }

    var updatedDetail by remember {
        mutableStateOf(curDetails)
    }

    BasicAlertDialog(modifier = Modifier.background(Color.LightGray),
        onDismissRequest = { onDismiss() })
    {
        Column (
            Modifier
                .fillMaxSize()
                .padding(10.dp))
        {
            OutlinedTextField(value = updatedTitle, onValueChange = {
                updatedTitle = it
            })

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(modifier = Modifier.fillMaxSize(0.9f),
                value = updatedDetail, onValueChange = {
                updatedDetail = it
            })

            Spacer(modifier = Modifier.height(10.dp))

            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { viewModel.updateNote(updatedTitle,updatedDetail,id)
                onUpdate()})
            {
                Text(text = "save")
            }
        }
    }
}
