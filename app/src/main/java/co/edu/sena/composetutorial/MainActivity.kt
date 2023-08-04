package co.edu.sena.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.edu.sena.composetutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContent defines the layout of the activity, where functions that support composition are called.
        setContent {
            ComposeTutorialTheme {
                // Calling the function that displays the message
                // PreviewMessageCard()
                Surface(modifier = Modifier.fillMaxSize()) {
                    MessageCard(Message("Android", "Jetpack compose"))
                }

                Conversation(SampleData.conversationSample)
            }
        }
    }

    data class Message(val author: String, val body: String)

    // Composable to declare a function and display message
    @Composable
    fun MessageCard(msg: Message) {
        Row(modifier = Modifier.padding(all = 20.dp)) {
            // for put a images
            Image(
                painter = painterResource(R.drawable.descarga),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    // Set image size to 40 dp
                    .size(40.dp)
                    // Clip image to be shaped as a circle
                    .clip(CircleShape)
                    .border(1.5.dp, Color.White, CircleShape)
            )
            // Add a horizontal space between the image and the column
            Spacer(modifier = Modifier.width(8.dp))

            // Variable to make the message expand when clicked
            var isExpanded by remember { mutableStateOf(false) }

            // Variable to change the color of the message when clicked
            val surfaceColor by animateColorAsState(
                if (isExpanded) MaterialTheme.colorScheme.primary else Color.Cyan
            )

            // Click function to expand the message
            Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
                Text(
                    text = msg.author,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleSmall
                )
                // Add a vertical space between the author and message texts
                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 1.dp,
                    color = surfaceColor,
                    modifier = Modifier
                        .animateContentSize()
                        .padding(1.dp)
                ) {
                    Text(
                        text = msg.body,
                        modifier = Modifier.padding(all = 4.dp),
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                // Delete button (Only changes color when clicked)
                ButtonDelete(
                    text = "Delete",
                    onClick = {

                    },
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }

//    // function to display the message
//    @Preview
//    @Composable
//    fun PreviewMessageCard() {
//        MessageCard("Android")
//    }

    // To see the changes live
    // (this function does not compile)
    @Preview(showBackground = true)
    @Composable
    fun MessageCardPreview() {
        ComposeTutorialTheme {
            Surface {
                MessageCard(
                    msg = Message("Messi", "Hey, Hello world")
                )
            }
        }
    }


    // Function to put more "chats" in the form of a list
    @Composable
    fun Conversation(messages: List<Message>) {
        LazyColumn {
            items(messages) { message ->
                MessageCard(message)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewConversation() {
        ComposeTutorialTheme {
            Conversation(SampleData.conversationSample)
        }
    }

    @Composable
    fun ButtonDelete(
        text: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        // Mutable state to store button color
        var buttonColor by remember { mutableStateOf(Color.Blue) }

        Surface(
            shape = MaterialTheme.shapes.medium,
            color = buttonColor, // Use mutable state color
            shadowElevation = 4.dp,
            modifier = modifier
                .clickable {
                    onClick()
                    // Change the color of the button when clicked
                    buttonColor = if (buttonColor == Color.Red) {
                        Color.Blue
                    } else {
                        Color.Red
                    }
                }
                .padding(16.dp)
        ) {
            Text(
                text = text,
                color = Color.White,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

}