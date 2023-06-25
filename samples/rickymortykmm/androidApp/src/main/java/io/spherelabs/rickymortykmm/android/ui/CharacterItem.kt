package io.spherelabs.rickymortykmm.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import io.spherelabs.rickymortykmm.remote.dto.CharacterDto

@Composable
fun CharacterItem(
    modifier: Modifier = Modifier,
    character: CharacterDto,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable {
            onClick()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = modifier
                .padding(8.dp)
                .width(110.dp)
                .height(110.dp),
            painter = rememberImagePainter(character.image),
            contentDescription = null
        )
        Text(
            text = character.name,
            modifier = modifier.fillMaxWidth()
        )
    }
}
