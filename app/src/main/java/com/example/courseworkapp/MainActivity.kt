package com.example.courseworkapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.courseworkapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        /*// Получение данных из Firestore
        db.collection("rooms")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("FirestoreTest", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("FirestoreTest", "Error getting documents: ", exception)
            }

        db.collection("testCollection").add(hashMapOf("key" to "value"))
            .addOnSuccessListener {
                Log.d("FirestoreTest", "Document added with ID: ${it.id}")
            }
            .addOnFailureListener { e ->
                Log.w("FirestoreTest", "Error adding document", e)
            }*/
    }

    override fun onStart() {
        super.onStart()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            // Если пользователь авторизован, идем в MainNavGraph
            Log.d("Test", "signIn:success email: ${auth.currentUser?.email}")
            navController.navigate(R.id.main_navigation)


        } else {
            Log.d("Test", "signIn:failure no current user")
            navController.navigate(R.id.auth_navigation)
        }
    }
}