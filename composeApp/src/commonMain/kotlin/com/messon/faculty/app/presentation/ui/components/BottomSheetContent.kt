package com.messon.faculty.app.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.GroupAdd
import androidx.compose.material.icons.outlined.PostAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.messon.faculty.app.presentation.viewmodel.FilePickerViewModel
import com.messon.faculty.app.theme.CustomColors
import com.messon.faculty.app.utils.FilePickerState
import com.messon.faculty.app.utils.Screen
import messon_faculty.composeapp.generated.resources.Res
import messon_faculty.composeapp.generated.resources.publicsansbold
import messon_faculty.composeapp.generated.resources.publicsansregular
import messon_faculty.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun BottomSheetContent(
    modifier: Modifier,
    onUploadCsvClick:()-> Unit,
    onAddManuallyClick:()-> Unit
){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.fillMaxSize()
            .padding(start = 16.dp, end=16.dp,top=5.dp, bottom = 15.dp)
    ){
        BoxedIcon(
            modifier = Modifier,
            boxColor = CustomColors.RichCerulean.copy(alpha = 0.1f),
            iconColor = CustomColors.RichCerulean,
            iconsSize =30.dp,
            painter = rememberVectorPainter(Icons.Outlined.GroupAdd),
            contentDescription ="Add Students",
            shape = CircleShape,
        )
        Text(
            text = "Add Students",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            maxLines = 1,
            color = Color.Black
        )

        Text(
            text = "Choose how you want to add \n new students to the mess database."
            , fontSize = 12.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansregular)),
            maxLines =2,
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )

        BottomSheetButton(
            onClick = {
               onUploadCsvClick()
            },
            painter = rememberVectorPainter(Icons.Outlined.CloudUpload),
            contentDescription = "upload csv",
            title = "Upload CSV File"
        )

        Spacer(modifier = Modifier.size(8.dp))

        BottomSheetButton(
            onClick = {
                onAddManuallyClick()
            },
            painter = rememberVectorPainter(Icons.Outlined.PostAdd),
            contentDescription = "Add Manually",
            title = "Add Manually"
        )

    }
}


@Composable
fun BottomSheetButton(
    onClick:()-> Unit,
    painter: Painter,
    contentDescription: String,
    title: String
){
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(width = 0.5.dp, CustomColors.RichCerulean),
        onClick =onClick,
        modifier = Modifier.height(65.dp)
            .fillMaxWidth()
            .padding(horizontal =5.dp)
    ){
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BoxedIcon(
                modifier = Modifier,
                boxColor = CustomColors.RichCerulean.copy(alpha = 0.1f),
                iconColor = CustomColors.RichCerulean,
                iconsSize = 30.dp,
                painter = painter,
                shape = RoundedCornerShape(15.dp),
                contentDescription = contentDescription
            )
            Text(
                text = title,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                maxLines = 1,
                color = Color.Black
            )
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "go to next screen",
                tint = CustomColors.RichCerulean
            )
        }
    }
}