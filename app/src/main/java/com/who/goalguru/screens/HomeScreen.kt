package com.who.goalguru.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.who.goalguru.models.ToDo
import com.who.goalguru.utils.fontFamily
import java.time.LocalDate
import java.util.Calendar

@Composable
fun HomeScreen(
//    viewModel: ToDoViewModel
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color = Color(0xFF878AF5)),
            contentAlignment = Alignment.BottomStart){
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.Start,
            ){
                Text(modifier = Modifier
                    .padding(start = 15.dp),
                    text = "Today, 15 May",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.White,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Thin
                    )
                )
                Text(modifier = Modifier
                    .padding(start = 15.dp),
                    text = "My Tasks",
                    style = TextStyle(
                        fontSize = 25.sp,
                        color = Color.White,
                        fontFamily = fontFamily
                    )
                )
            }

        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                DateTimePickerComponent()
                TodoList()
            }
        }
    }
}

@Composable
fun TodoList() {
    Column(
        modifier = Modifier
    ) {
        val viewModel = hiltViewModel<ToDoViewModel>()
        val toDoList by viewModel.toDoList.collectAsState()
        Text(
            text = "Today",
            style = TextStyle(
                fontSize = 20.sp,
                color = Color(color = 0xFF31446C),
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
        )
        TaskList(tasks = toDoList, viewModel = viewModel)
        Text(
            text = "Tomorrow",
            style = TextStyle(
                fontSize = 20.sp,
                color = Color(color = 0xFF31446C),
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = "Upcoming",
            style = TextStyle(
                fontSize = 20.sp,
                color = Color(color = 0xFF31446C),
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Composable
fun TaskList(tasks: List<ToDo>, viewModel: ToDoViewModel){
    LazyColumn(
        modifier = Modifier
            .height(200.dp)
    ) {
        items(tasks) { task ->
            TaskBox(task = task, viewModel)
        }
    }
}

@Composable
fun TaskBox(task: ToDo, viewModel: ToDoViewModel){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, end = 20.dp, bottom = 10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .clickable {  }
            ) {
                Text(
                    text = task.task,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color(color = 0xFF31446C),
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = {
                    viewModel.updateIsCompleted(task.id, it)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerComponent() {
    var date by remember { mutableStateOf(Calendar.getInstance().timeInMillis) }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = date)
    var showDatePicker by remember { mutableStateOf(false) }
    var isDialogOpen by remember { mutableStateOf(false) }
    var newTaskText by remember { mutableStateOf("") }
    val selectedDate = remember {
        mutableStateOf(
            Calendar.getInstance().apply {
                timeInMillis = datePickerState.selectedDateMillis ?: date
            }
        )
    }
    var isImportant by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    LaunchedEffect(datePickerState.selectedDateMillis) {
        date = datePickerState.selectedDateMillis ?: date
        selectedDate.value.timeInMillis = datePickerState.selectedDateMillis ?: date
    }

    FloatingActionButton(
        onClick = { showDatePicker = true },
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Todo")
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (selectedDate.value.after(Calendar.getInstance())) {
                            Log.d("DateTimePickerComponent", "Selected Date: ${selectedDate.value.time.toInstant().atZone(selectedDate.value.timeZone.toZoneId()).toLocalDate()}")
                            isDialogOpen = true
                            showDatePicker = false
                        } else {
                            Log.d("DateTimePickerComponent", "Selected Date: ${selectedDate.value.time.toInstant().atZone(selectedDate.value.timeZone.toZoneId()).toLocalDate()}")
                            Toast.makeText(context, "Please select a future date", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) {
                    Text("Cancel")
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = Color(0xFF31446C),
            )
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    todayContentColor = Color.White,
                    todayDateBorderColor = Color(0xFF666AF6),
                    selectedDayContentColor = Color.White,
                    selectedDayContainerColor = Color(0xFF666AF6),
                )
            )
        }
    }
    val viewModel = hiltViewModel<ToDoViewModel>()

    if (isDialogOpen) {
        AddTodoDialog(
            isOpen = isDialogOpen,
            onDismiss = { isDialogOpen = false },
            onAddTodo = {
                Log.d("DateTimePickerComponent", "Selected Date: ${selectedDate.value.time.toInstant().atZone(selectedDate.value.timeZone.toZoneId()).toLocalDate()}")
                viewModel.addToDoTask(ToDo(
                    task = newTaskText,
                    isCompleted = false,
                    isImportant = isImportant,
                    dueDate = selectedDate.value.time.toInstant().atZone(selectedDate.value.timeZone.toZoneId()).toLocalDate()
                ))
                newTaskText = ""
                isImportant = false
                isDialogOpen = false
            },
            newTaskText = newTaskText,
            onTaskTextChanged = { newTaskText = it },
            selectedDate = selectedDate.value.time.toInstant().atZone(selectedDate.value.timeZone.toZoneId()).toLocalDate(),
            isImportant = isImportant,
            onImportantCheckedChange = { isImportant = it }
        )
    }
}

@Composable
fun AddTodoDialog(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onAddTodo: () -> Unit,
    newTaskText: String,
    onTaskTextChanged: (String) -> Unit,
    selectedDate: LocalDate?,
    isImportant: Boolean,
    onImportantCheckedChange: (Boolean) -> Unit
) {
    if (isOpen) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(color = Color.White),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Add New Todo", style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(color = 0xFF31446C),
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold
                    )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = newTaskText,
                        onValueChange = { onTaskTextChanged(it) },
                        label = { Text("Task") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isImportant,
                            onCheckedChange = { onImportantCheckedChange(it) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Important")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onAddTodo,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Add")
                    }
                }
            }
        }
    }
}