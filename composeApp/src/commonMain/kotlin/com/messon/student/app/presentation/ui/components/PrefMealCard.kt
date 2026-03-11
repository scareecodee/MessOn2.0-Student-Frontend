package com.messon.student.app.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.student.app.presentation.ui.screens.mess.MealMenu
import com.messon.student.app.theme.CustomColors
import com.messon.student.app.utils.MealType
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansbold
import messon.composeapp.generated.resources.opensansregular
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font

@Composable
fun PrefMealCard(
    title: String,
    icon: ImageVector,
    menu: MealMenu,
    selectedType: MealType,
    onSelect: (MealType) -> Unit,
    iconColor: Color
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(28.dp),
        border = BorderStroke(1.dp, CustomColors.LightGrayBlue.copy(alpha = 0.2f)),
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            val (iconBox, titleText, badge, infoBox, vegBtn, nonVegBtn) = createRefs()

            Box(
                modifier = Modifier
                    .constrainAs(iconBox) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .background(iconColor.copy(0.1f), RoundedCornerShape(12.dp))
                    .padding(10.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(18.dp)
                )
            }

            Text(
                text = title,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(Res.font.opensansbold) ),
                color = CustomColors.BlackberryCream,
                modifier = Modifier.constrainAs(titleText) {
                    top.linkTo(iconBox.top)
                    bottom.linkTo(iconBox.bottom)
                    start.linkTo(iconBox.end, margin = 12.dp)
                }
            )

            if (selectedType != MealType.NONE) {
                Surface(
                    color = CustomColors.LightBlue,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.constrainAs(badge) {
                        top.linkTo(iconBox.top)
                        bottom.linkTo(iconBox.bottom)
                        end.linkTo(parent.end)
                    }
                ) {
                    Text(
                        text = "Selected",
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(Res.font.opensanssemiBold) ),
                        color = CustomColors.RichCerulean,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .constrainAs(infoBox) {
                        top.linkTo(iconBox.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .background(CustomColors.LightLavender, RoundedCornerShape(16.dp))
                    .border(1.dp, CustomColors.LightGrayBlue.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    PrefMenuRow(
                        type = "Veg",
                        text = menu.veg.ifBlank { "Not available" },
                        color = CustomColors.Green500
                    )

                    Divider(
                        color = CustomColors.LightGrayBlue.copy(alpha = 0.3f),
                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 24.dp)
                    )

                    PrefMenuRow(
                        type = "Non-Veg",
                        text = menu.nonVeg.ifBlank { "Not available" },
                        color = CustomColors.Red500
                    )
                }
            }

            PrefSelectionButton(
                title = "Veg",
                icon = Icons.Default.CheckCircle,
                isSelected = selectedType == MealType.VEG,
                activeColor = CustomColors.Green500,
                activeBgColor = CustomColors.Green50,
                modifier = Modifier.constrainAs(vegBtn) {
                    top.linkTo(infoBox.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(nonVegBtn.start, margin = 6.dp)
                    width = Dimension.fillToConstraints
                }
            ) { onSelect(MealType.VEG) }

            PrefSelectionButton(
                title = "Non-Veg",
                icon = Icons.Default.Favorite,
                isSelected = selectedType == MealType.NON_VEG,
                activeColor = CustomColors.Red500,
                activeBgColor = CustomColors.Red50,
                modifier = Modifier.constrainAs(nonVegBtn) {
                    top.linkTo(infoBox.bottom, margin = 16.dp)
                    start.linkTo(vegBtn.end, margin = 6.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            ) { onSelect(MealType.NON_VEG) }
        }
    }
}
