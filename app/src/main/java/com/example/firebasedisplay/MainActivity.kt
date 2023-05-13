package com.example.firebasedisplay

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var name_edt:EditText
    lateinit var email_edt:EditText
    lateinit var age_edt:EditText
    lateinit var btn_submit:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name_edt = findViewById(R.id.edt_name)
        email_edt = findViewById(R.id.edt_email)
        age_edt = findViewById(R.id.edt_age)
        btn_submit = findViewById(R.id.submit_btn)

        btn_submit.setOnClickListener {
            var name = name_edt.text.toString().trim()
            var email = email_edt.text.toString().trim()
            var age = age_edt.text.toString().trim()

            var time_id = System.currentTimeMillis().toString()
            //Progress Bar
            var progress = ProgressDialog(this)
            progress.setTitle("Saving Data")
            progress.setMessage("Please wait")
            //Validate
            if (name.isEmpty()||email.isEmpty()||age.isEmpty()){
                Toast.makeText(this, "Cannot Submit an empty field!", Toast.LENGTH_SHORT).show()
            }
            else{
                //Upload data to firebase
                var child = FirebaseDatabase.getInstance().reference.child("Names/"+time_id)
                var user_data = User(name,email,age,time_id)
                //show progress
                progress.show()
                // save data
                child.setValue(user_data).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this, "Data Uploaded successfully!", Toast.LENGTH_SHORT).show()
                        //navigate to next page
                        var gotoview = Intent(this, view_users::class.java)
                        startActivity(gotoview)
                        finish()
                    }else{
                        Toast.makeText(this, "Failed to Upload Data!", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }
}
//list view in xml
//class adapter