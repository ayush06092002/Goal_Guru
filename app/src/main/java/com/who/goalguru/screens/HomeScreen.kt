package com.who.goalguru.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.who.goalguru.models.ToDo
import com.who.goalguru.utils.fontFamily
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
                    text = LocalDate.now().toString().format(
                        DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")
                    ),
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
            text = "Your Current Tasks",
            style = TextStyle(
                fontSize = 20.sp,
                color = Color(color = 0xFF31446C),
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
        )
        TaskList(tasks = toDoList, viewModel = viewModel)
    }
}

@Composable
fun TaskList(tasks: List<ToDo>, viewModel: ToDoViewModel){
    LazyColumn(
        modifier = Modifier
    ) {
        items(tasks) { task ->
            TaskBox(task = task, viewModel)
        }
    }
}

@Composable
fun TaskBox(task: ToDo, viewModel: ToDoViewModel){
    var checked by remember { mutableStateOf(false) }
    LaunchedEffect(task.isCompleted) {
        checked = task.isCompleted
    }
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
            ) {
                if(task.isImportant){
                    Canvas(modifier = Modifier.size(10.dp), onDraw = {
                        drawCircle(color = Color.Red)
                    })
                }
                Text(
                    text = task.task,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color(color = 0xFF31446C),
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        textDecoration = if(task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                    )
                )
                Text(text = "Due Date: ${task.dueDate}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color(color = 0xFF31446C),
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        textDecoration = if(task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
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

    Row(modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically) {
        FloatingActionButton(
            onClick = { showDatePicker = true },
            modifier = Modifier.padding(top = 10.dp, bottom = 16.dp, end = 5.dp),
            containerColor = Color(0xFF31446C),
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Todo")
        }
        Text(text = "Add New Task", style = TextStyle(
            fontSize = 20.sp,
            color = Color(color = 0xFF31446C),
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold
        ))
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        isDialogOpen = true
                        showDatePicker = false
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
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
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
                        label = { Text("Task",
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color(color = 0xFF31446C),
                                fontFamily = fontFamily

                            )
                        ) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color(0xFF31446C),
                        )

                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isImportant,
                            onCheckedChange = { onImportantCheckedChange(it) },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFFF4863C)
                            )
                        )
                        Text(text = "Important",
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color(color = 0xFFF5A921),
                                fontFamily = fontFamily
                            )
                        )
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

