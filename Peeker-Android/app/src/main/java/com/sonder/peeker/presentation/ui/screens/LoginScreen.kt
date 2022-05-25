package com.sonder.peeker.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

import com.sonder.peeker.R
import com.sonder.peeker.presentation.ui.components.RoundedButton
import com.sonder.peeker.presentation.ui.components.TransparentTextField
import com.sonder.peeker.presentation.ui.theme.Graphite
import com.sonder.peeker.presentation.ui.theme.Pink
import com.sonder.peeker.presentation.ui.theme.White

@Composable
fun LoginScreen() {
    val emailValue = rememberSaveable { mutableStateOf("") }
    val passwordValue = rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Pink)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_lighthouse_rafiki),
            contentDescription = "Login Image",
            contentScale = ContentScale.Inside
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            ConstraintLayout {
                val (surface, fab) = createRefs()
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .constrainAs(surface) {
                            bottom.linkTo(parent.bottom)
                        },
                    color = Graphite,
                    shape = RoundedCornerShape(topStartPercent = 8, topEndPercent = 8)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = "¡Hola!",
                                style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Medium)
                            )
                            Text(
                                text = "Ingresa a Peeker",
                                style = MaterialTheme.typography.h2
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            TransparentTextField(
                                textFieldValue = emailValue,
                                textLabel = "Email",
                                keyboardType = KeyboardType.Email,
                                keyboardActions = KeyboardActions(
                                    onNext = {
                                        focusManager.moveFocus(FocusDirection.Down)
                                    }
                                ),
                                imeAction = ImeAction.Next
                            )
                            TransparentTextField(
                                textFieldValue = passwordValue,
                                textLabel = "Contraseña",
                                keyboardType = KeyboardType.Password,
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()
                                    }
                                    // TODO: Login Operation.
                                ),
                                imeAction = ImeAction.Done,
                                trailingIcon = {
                                    IconButton(onClick = {
                                        passwordVisibility = !passwordVisibility
                                    }) {
                                        Icon(
                                            imageVector = if (passwordVisibility) {
                                                Icons.Rounded.Visibility
                                            } else {
                                                Icons.Rounded.VisibilityOff
                                            },
                                            contentDescription = "Toggle Password Visibility"
                                        )
                                    }
                                },
                                visualTransformation = if (passwordVisibility) {
                                    VisualTransformation.None
                                } else {
                                    PasswordVisualTransformation()
                                }
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "¿Olvidaste tu contraseña?",
                                style = MaterialTheme.typography.body2,
                                textAlign = TextAlign.End
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            RoundedButton(
                                text = "Entrar",
                                displayProgressBar = false,
                                onClick = {
                                    // TODO: Login
                                })
                            ClickableText(text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = White,
                                    )
                                ) {
                                    append("¿No tienes una cuenta?\u00A0")
                                }

                                withStyle(
                                    style = SpanStyle(
                                        color = Pink,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("Regístrate")
                                }
                            }, onClick = {
                                // TODO: Navigate to Register.
                            })
                        }
                    }
                }
                FloatingActionButton(
                    modifier = Modifier
                        .size(72.dp)
                        .constrainAs(fab) {
                            top.linkTo(surface.top, margin = (-36.dp))
                            end.linkTo(surface.end, margin = 36.dp)
                        },
                    backgroundColor = Pink,
                    onClick = { /*TODO*/ }) {
                    Icon(
                        modifier = Modifier.size(42.dp),
                        imageVector = Icons.Rounded.ArrowForward,
                        contentDescription = "Forward Icon",
                        tint = Graphite
                    )


                }
            }

        }
    }

}