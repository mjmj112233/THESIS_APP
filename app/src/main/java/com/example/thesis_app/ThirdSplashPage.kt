package com.example.thesis_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thesis_app.ui.theme.Blackk
import com.example.thesis_app.ui.theme.BlueGreen
import com.example.thesis_app.ui.theme.DarkGreen
import com.example.thesis_app.ui.theme.DirtyWhite
import com.example.thesis_app.ui.theme.Slime
import com.example.thesis_app.ui.theme.alt
import com.example.thesis_app.ui.theme.captionFont
import com.example.thesis_app.ui.theme.titleFont

@Composable
fun thirdPage(navController: NavController) {
    // State to track if the dialog is shown
    val showDialog = remember { mutableStateOf(false) }

    // State for checkboxes
    val agreeTerms = remember { mutableStateOf(false) }
    val agreePrivacy = remember { mutableStateOf(false) }

    // State for enabling/disabling the button
    val isAcceptEnabled = agreeTerms.value && agreePrivacy.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGreen)
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.b2),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, BlueGreen),
                        startY = 0f,
                        endY = 1500f
                    )
                )
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = buildAnnotatedString {
                    append("We also value your data! Read ")
                    withStyle(style = SpanStyle(color = Slime, fontFamily = titleFont, fontSize = 20.sp)) {
                        append("Spot’s ")
                    }
                    append("Terms & Conditions here.")
                },
                style = TextStyle(
                    fontFamily = captionFont,
                    fontSize = 16.sp,
                    lineHeight = 25.sp,
                    textAlign = TextAlign.Justify,
                    color = DirtyWhite
                ),
                modifier = Modifier.padding(start = 30.dp, end = 35.dp, bottom = 40.dp)
            )

            // Button to open dialog
            Button(
                onClick = { showDialog.value = true },
                colors = ButtonDefaults.buttonColors(Slime),
                modifier = Modifier
                    .padding(start = 30.dp, bottom = 50.dp, end = 30.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Terms ")
                        withStyle(style = SpanStyle(fontFamily = alt)) {
                            append("&")
                        }
                        append(" Conditions")
                    },
                    color = DarkGreen,
                    fontFamily = titleFont,
                    fontSize = 24.sp
                )
            }
        }

        // Terms and Conditions and Privacy Policy Dialog
        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(800.dp),
                text = {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()  // Set a height to make it scrollable
                            .padding(horizontal = 10.dp)
                    ) {
                        item {
                            // Terms and Conditions
                            Text(
                                text = "Terms and Conditions",
                                fontFamily = titleFont,
                                fontSize = 24.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            Text(
                                text = "Last updated October 19, 2024",
                                fontFamily = captionFont,
                                fontSize = 12.sp,
                                color = DarkGreen,
                            )

                            Text(
                                text = "1. Acceptance of Terms",
                                fontFamily = titleFont,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                            Text(
                                text = "By downloading, accessing, or using Spot, you agree to be bound by these Terms and Conditions. If you do not agree to all the terms and conditions outlined here, please refrain from using the application.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

                            // Section Title: Health Disclaimer
                            Text(
                                text = "2. Health Disclaimer",
                                fontFamily = titleFont,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "Spot is a fitness application designed for physically able individuals aged 18-25 years old. However, the routines and recommendations provided by the app are generic in nature and do not take into account any personal physical injuries, ailments, or pre-existing health conditions.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

                            // Section Title: Professional Guidance
                            Text(
                                text = "3. Professional Guidance",
                                fontFamily = titleFont,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "We strongly recommend that users consult with a certified fitness instructor or healthcare professional before starting any workout routine, especially if they have concerns about their physical condition. While Spot does not replace professional fitness instruction, it provides a low-level alternative by generating a generic 7-day workout routine based on user assessments and demographics.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

                            // Section Title: User Responsibility
                            Text(
                                text = "4. User Responsibility",
                                fontFamily = alt,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "As a user, it is your responsibility to:\n• Ensure that you are physically capable of performing the exercises and workouts included in the app.\n• Know and disclose any physical injuries, medical conditions, or other health concerns you may have to a professional before using the app's workouts.\n• Stop using the app and seek medical advice if you experience pain, discomfort, or any adverse health reactions while using the workouts provided by Spot.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

                            // Section Title: Limitation of Liability
                            Text(
                                text = "5. Limitation of Liability",
                                fontFamily = titleFont,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "Spot, and its developers, will not be liable for any injuries, health complications, or damages arising from the use of the app or the application of its workout routines. The user acknowledges that engaging in physical exercise has inherent risks, and they assume full responsibility for any injuries or adverse effects experienced while using the app.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

                            // Section Title: Not Medical or Professional Advice
                            Text(
                                text = "6. Not Medical or Professional Advice",
                                fontFamily = titleFont,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "The content within Spot is for informational and educational purposes only. It is not intended to be a substitute for professional medical advice, diagnosis, or treatment. Always seek the advice of a physician or other qualified healthcare provider with any questions you may have regarding a medical condition or fitness regimen.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

                            // Section Title: Data Collection and Usage
                            Text(
                                text = "7. Data Collection and Usage",
                                fontFamily = titleFont,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "By using Spot, you consent to the collection, use, and processing of the data you provide, such as demographic information and generated workout routines. This data may be used in an anonymous and aggregated form for the purpose of improving the app’s features, performance, and user experience.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

                            // Section Title: Changes to Terms and Conditions
                            Text(
                                text = "8. Changes to Terms and Conditions",
                                fontFamily = titleFont,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "We may modify these Terms and Conditions at any time. Changes will be effective immediately upon posting, and continued use of Spot after any modifications signifies your acceptance of the updated terms.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

                            // Section Title: Governing Law
                            Text(
                                text = "9. Governing Law",
                                fontFamily = titleFont,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "These terms and conditions are governed by and construed in accordance with the laws of the Republic of the Philippines, without regard to its conflict of law principles.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            // Privacy Policy Section
                            // Privacy Policy Section Title
                            Text(
                                text = "Privacy Policy",
                                fontFamily = titleFont,
                                fontSize = 24.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            Text(
                                text = "Last updated October 19, 2024",
                                fontFamily = captionFont,
                                fontSize = 12.sp,
                                color = DarkGreen,
                            )

// Section Title: Information We Collect
                            Text(
                                text = "1. Information We Collect",
                                fontFamily = titleFont,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                            Text(
                                text = "When you use Spot, we may collect the following types of information:\n\n" +
                                        "• Personal Information: This may include data such as your name, age, height, weight, fitness goals, and other demographic information.\n" +
                                        "• Health and Fitness Data: Information related to your workout routines, fitness assessments, and any preferences you provide while using the application.\n" +
                                        "• Device Information: We may collect information about your mobile device, including IP address, operating system, and browser type.\n" +
                                        "• Usage Data: We may collect information on how you interact with the app, including which features you use and the duration of your sessions.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

// Section Title: How We Use Your Information
                            Text(
                                text = "2. How We Use Your Information",
                                fontFamily = titleFont,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "The information we collect from you may be used in the following ways:\n\n" +
                                        "• Personalized Fitness Recommendations: To generate 7-day workout routines based on your demographics and fitness assessments.\n" +
                                        "• Service Improvements: To improve the app’s features, functionality, and user experience based on aggregate user data.\n" +
                                        "• Algorithm Development: To enhance our algorithm by using anonymized data for training purposes, helping us provide better and more accurate fitness recommendations in the future.\n" +
                                        "• Communication: To send notifications, updates, or important information about the app.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

// Section Title: Data Usage for Algorithm Training
                            Text(
                                text = "3. Data Usage for Algorithm Training",
                                fontFamily = titleFont,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "By using Spot, you consent to our use of your anonymized and aggregated data for the purpose of improving the performance of the app’s fitness recommendation algorithm. This data will be stripped of personally identifiable information and used only in ways that enhance the accuracy of the workout routines provided by the app.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

// Section Title: Data Sharing and Disclosure
                            Text(
                                text = "4. Data Sharing and Disclosure",
                                fontFamily = alt,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "We do not sell, trade, or otherwise transfer your personal information to outside parties. However, we may share your data with:\n\n" +
                                        "• Service Providers: Third-party services that assist us in operating the app, conducting business, or servicing users, provided that those parties agree to keep this information confidential.\n" +
                                        "• Legal Obligations: If required by law, or if we believe disclosure is necessary to protect our rights, enforce our Terms and Conditions, or comply with a judicial proceeding, court order, or legal process.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

// Section Title: Data Security
                            Text(
                                text = "5. Data Security",
                                fontFamily = titleFont,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "We take appropriate security measures to protect your personal information from unauthorized access, disclosure, alteration, or destruction. However, no internet-based service is 100% secure, and we cannot guarantee the absolute security of your information.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

// Section Title: Retention of Data
                            Text(
                                text = "6. Retention of Data",
                                fontFamily = titleFont,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "We retain your personal information only for as long as is necessary for the purposes set out in this Privacy Policy or as required by law. Once no longer needed, your personal data will be deleted or anonymized.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

// Section Title: Your Data Rights
                            Text(
                                text = "7. Your Data Rights",
                                fontFamily = titleFont,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "You have the right to:\n\n" +
                                        "• Access and Correct: You may access and update the personal information we have on file for you.\n" +
                                        "• Data Deletion: You may request the deletion of your personal data by contacting us at spot.cs@gmail.com.\n" +
                                        "• Withdraw Consent: You may withdraw your consent so we can process your data at any time, though this may affect your ability to use certain app features.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

// Section Title: Cookies and Tracking Technologies
                            Text(
                                text = "8. Cookies and Tracking Technologies",
                                fontFamily = titleFont,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "Spot may use cookies and similar technologies to track your usage and provide a better user experience. These technologies help us analyze traffic and understand user behavior, enabling us to improve our services.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

// Section Title: Children’s Privacy
                            Text(
                                text = "9. Children’s Privacy",
                                fontFamily = titleFont,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "Spot is not intended for individuals under the age of 18. We do not knowingly collect personal information from anyone under 18. If we become aware that we have inadvertently collected information from a child under 18, we will delete such information as soon as possible.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

// Section Title: Changes to the Privacy Policy
                            Text(
                                text = "10. Changes to the Privacy Policy",
                                fontFamily = titleFont,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "We reserve the right to modify this Privacy Policy at any time. Any changes will be effective immediately upon posting, and continued use of Spot signifies your acceptance of the updated policy.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

// Section Title: Contact Us
                            Text(
                                text = "11. Contact Us",
                                fontFamily = titleFont,
                                fontSize = 16.sp,
                                color = DarkGreen,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "If you have any questions or concerns about this Privacy Policy or the handling of your personal data, please contact us at spot.cs@gmail.com.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

// Closing Statement
                            Text(
                                text = "By using Spot, you agree to the terms of this Privacy Policy. It is your responsibility to review this Privacy Policy periodically for any changes.",
                                fontFamily = captionFont,
                                fontSize = 13.sp,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Justify,
                                color = Blackk
                            )

                        }
                    }
                },
                confirmButton = {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Checkbox(
                                checked = agreeTerms.value,
                                onCheckedChange = { agreeTerms.value = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = DarkGreen,
                                    uncheckedColor = DarkGreen
                                )

                            )
                            Text(
                                text = "I agree to Spot's Terms and Conditions",
                                fontFamily = captionFont,
                                fontSize = 14.sp,
                                color = DarkGreen
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Checkbox(
                                checked = agreePrivacy.value,
                                onCheckedChange = { agreePrivacy.value = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = DarkGreen,
                                    uncheckedColor = DarkGreen
                                )
                            )
                            Text(
                                text = "I agree to Spot's Privacy Policy",
                                fontFamily = captionFont,
                                fontSize = 14.sp,
                                color = DarkGreen
                            )
                        }

                        // Button to confirm agreement, disabled if not both checkboxes are checked
                        Button(
                            onClick = {
                                navController.navigate("sixth")
                            },
                            colors = ButtonDefaults.buttonColors(Slime),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            enabled = isAcceptEnabled // Enable button only if both checkboxes are checked
                        ) {
                            Text(
                                "Proceed",
                                color = DarkGreen,
                                fontFamily = titleFont,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            )
        }
    }
}



